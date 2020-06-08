package org.myhomeapps.walkers;

import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.myhomeapps.menuentities.MenuFrame;
import org.myhomeapps.menuentities.MenuSystem;
import org.myhomeapps.walkers.graphbuilders.DefaultGraphBuilder;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;

public class MenuWalkerInitiator {

    private static final MenuWalkerInitiator instance = new MenuWalkerInitiator();

    private MenuWalkerInitiator() {
    }

    public static MenuWalker initMenu(InputStream menuConfigInputStream) {
        DefaultDirectedGraph<MenuFrame, DefaultEdge> menuGraph = buildGraph(menuConfigInputStream);
        return new GraphBasedMenuWalker(menuGraph);
    }

    public static MenuWalker initMenu(String yamlConfig) {
        DefaultDirectedGraph<MenuFrame, DefaultEdge> menuGraph = buildGraph(yamlConfig);
        return new GraphBasedMenuWalker(menuGraph);
    }

    static DefaultDirectedGraph<MenuFrame, DefaultEdge> buildGraph(InputStream menuConfigInputStream) {
        MenuSystem menuSystem = new Yaml().loadAs(menuConfigInputStream, MenuSystem.class);
        return (DefaultDirectedGraph<MenuFrame, DefaultEdge>) new DefaultGraphBuilder(menuSystem).buildFramesGraph();
    }

    static DefaultDirectedGraph<MenuFrame, DefaultEdge> buildGraph(String configContent) {
        MenuSystem menuSystem = new Yaml().loadAs(configContent, MenuSystem.class);
        return (DefaultDirectedGraph<MenuFrame, DefaultEdge>) new DefaultGraphBuilder(menuSystem).buildFramesGraph();
    }
}
