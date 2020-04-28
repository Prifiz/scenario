package org.myhomeapps.walkers;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.myhomeapps.menuentities.MenuFrame;
import org.myhomeapps.menuentities.MenuItem;
import org.myhomeapps.menuentities.MenuSystem;
import org.myhomeapps.menuentities.ServiceItem;

import java.io.IOException;

public class DefaultGraphBuilder implements GraphBuilder {

    @Override
    public Graph<MenuFrame, DefaultEdge> buildFramesGraph(MenuSystem menuSystem) {
        DefaultDirectedGraph<MenuFrame, DefaultEdge> menuGraph = new DefaultDirectedGraph<>(DefaultEdge.class);
        menuSystem.getMenuSystem().forEach(menuFrame -> {
            menuGraph.addVertex(menuFrame);
        });

        menuGraph.vertexSet().forEach(sourceCandidate -> {
            try {
                GotoLevelFactory.GotoLevel gotoLevel = new GotoLevelFactory(sourceCandidate).getLevel();

                switch (gotoLevel) {
                    case ITEM: {
                        sourceCandidate.getItems().forEach(menuItem -> {
                            String itemGotoMenu = menuItem.getGotoMenu();
                            menuSystem.getMenuSystem().forEach(targetCandidate -> {
                                String name = targetCandidate.getName();
                                if (name.equals(itemGotoMenu)) {
                                    menuGraph.addEdge(sourceCandidate, targetCandidate);
                                }
                            });
                        });
                        break;
                    }
                    case MENU: {
                        String gotoMenu = sourceCandidate.getGotoMenu();
                        menuGraph.vertexSet().forEach(targetCandidate -> {
                            String name = targetCandidate.getName();
                            if (name.equals(gotoMenu)) {
                                menuGraph.addEdge(sourceCandidate, targetCandidate);
                            }
                        });
                        break;
                    }
                    default: {
                        System.out.println("Something went wrong with goto levels...");
                    }
                }
            } catch (IOException ex) {
                System.out.println("Something went wrong with graph building");
                System.out.println(ex.getMessage());
            }
        });
        return menuGraph;
    }

    @Override
    public Graph<MenuItem, DefaultEdge> buildItemsGraph(MenuSystem menuSystem) {
        DefaultDirectedGraph<MenuItem, DefaultEdge> itemsGraph = new DefaultDirectedGraph<>(DefaultEdge.class);
        menuSystem.getMenuSystem().forEach(menuFrame -> {
            if (menuFrame.hasItems()) {
                menuFrame.getItems().forEach(itemsGraph::addVertex);
            } else {
                itemsGraph.addVertex(new ServiceItem(menuFrame.getName(), menuFrame.getGotoMenu()));
                // TODO: implicit NoItem for each frame with no visible items?
            }
        });

        itemsGraph.vertexSet().forEach(sourceCandidate -> {
            String itemGotoMenu = sourceCandidate.getGotoMenu();
            itemsGraph.vertexSet().forEach(targetCandidate -> {
                if (targetCandidate.getName().equals(itemGotoMenu)) {
                    itemsGraph.addEdge(sourceCandidate, targetCandidate);
                }
            });
        });
        return itemsGraph;
    }
}
