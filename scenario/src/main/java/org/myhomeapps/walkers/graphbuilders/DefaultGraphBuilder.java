package org.myhomeapps.walkers.graphbuilders;

import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.myhomeapps.menuentities.MenuFrame;
import org.myhomeapps.menuentities.MenuSystem;

import java.io.IOException;

@Getter
public class DefaultGraphBuilder implements GraphBuilder {

    Logger logger = LogManager.getLogger(getClass());

    private final MenuSystem menuSystem;
    private final DefaultDirectedGraph<MenuFrame, DefaultEdge> menuGraph;

    public DefaultGraphBuilder(MenuSystem menuSystem) {
        this.menuSystem = menuSystem;
        this.menuGraph = new DefaultDirectedGraph<>(DefaultEdge.class);
    }

    public Graph<MenuFrame, DefaultEdge> buildFramesGraph() {
        menuSystem.getMenuSystem().forEach(menuGraph::addVertex);

        menuGraph.vertexSet().forEach(sourceCandidate -> {
            try {
                addEdges(sourceCandidate);
            } catch (IOException ex) {
                logger.error("Something went wrong while adding graph edges");
                logger.error(ex.getMessage());
            }
        });
        return menuGraph;
    }

    void addEdges(MenuFrame sourceCandidate) throws IOException {
        GotoLevel gotoLevel = sourceCandidate.getGotoLevel();
        switch (gotoLevel) {
            case ITEM: {
                addItemBasedEdges(sourceCandidate);
                break;
            }
            case MENU: {
                addMenuBasedEdges(sourceCandidate);
                break;
            }
            default: {
                logger.warn("Something went wrong with goto levels...");
            }
        }
    }

    void addItemBasedEdges(MenuFrame sourceCandidate) {
        sourceCandidate.getItems().forEach(menuItem -> {
            String itemGotoMenu = menuItem.getGotoMenu();
            menuSystem.getMenuSystem().forEach(targetCandidate -> {
                String name = targetCandidate.getName();
                if (name.equals(itemGotoMenu)) {
                    menuGraph.addEdge(sourceCandidate, targetCandidate);
                }
            });
        });
    }

    void addMenuBasedEdges(MenuFrame sourceCandidate) {
        String gotoMenu = sourceCandidate.getGotoMenu();
        menuGraph.vertexSet().forEach(targetCandidate -> {
            String name = targetCandidate.getName();
            if (name.equals(gotoMenu)) {
                menuGraph.addEdge(sourceCandidate, targetCandidate);
            }
        });
    }

}
