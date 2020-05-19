package org.myhomeapps.walkers.graphbuilders;

import lombok.Getter;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.myhomeapps.menuentities.MenuFrame;
import org.myhomeapps.menuentities.MenuSystem;

import java.io.IOException;

public class DefaultGraphBuilder implements GraphBuilder {

    private MenuSystem menuSystem;
    @Getter
    private DefaultDirectedGraph<MenuFrame, DefaultEdge> menuGraph;

    public DefaultGraphBuilder(MenuSystem menuSystem) {
        this.menuSystem = menuSystem;
        this.menuGraph = new DefaultDirectedGraph<>(DefaultEdge.class);
    }

    public Graph<MenuFrame, DefaultEdge> buildFramesGraph() {
        menuSystem.getMenuSystem().forEach(frame -> menuGraph.addVertex(frame));

        menuGraph.vertexSet().forEach(sourceCandidate -> {
            try {
                addEdges(sourceCandidate);
            } catch (IOException ex) {
                System.out.println("Something went wrong while adding graph edges");
                System.out.println(ex.getMessage());
            }
        });
        return menuGraph;
    }

    protected void addEdges(MenuFrame sourceCandidate) throws IOException {
        GotoLevel gotoLevel = new GotoLevelFactory(sourceCandidate).defineLevel();
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
                System.out.println("Something went wrong with goto levels...");
            }
        }
    }

    protected void addItemBasedEdges(MenuFrame sourceCandidate) {
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

    protected void addMenuBasedEdges(MenuFrame sourceCandidate) {
        String gotoMenu = sourceCandidate.getGotoMenu();
        menuGraph.vertexSet().forEach(targetCandidate -> {
            String name = targetCandidate.getName();
            if (name.equals(gotoMenu)) {
                menuGraph.addEdge(sourceCandidate, targetCandidate);
            }
        });
    }

}
