package org.prifizapps.walkers;

import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.prifizapps.menuentities.MenuFrame;
import org.prifizapps.menuentities.MenuSystem;
import org.prifizapps.menuentities.properties.DefaultPropertiesParser;
import org.prifizapps.menuentities.properties.PropertiesParser;
import org.prifizapps.walkers.graphbuilders.DefaultGraphBuilder;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;

/**
 * Singleton object for menu system initialization.
 */
public class MenuWalkerInitiator {

    private static final MenuWalkerInitiator instance = new MenuWalkerInitiator();

    private static PropertiesParser propertiesParser = new DefaultPropertiesParser();

    private MenuWalkerInitiator() {
    }

    /**
     * Initializes {@link MenuWalker} from configuration provided as an {@link InputStream}.
     * This can be helpful if the configuration file is stored as project resource.
     * @param menuConfigInputStream {@link InputStream} reading the menu configuration file.
     * @return {@link MenuWalker} object which can be used to configure and run CLI menu.
     */
    public static MenuWalker initMenu(InputStream menuConfigInputStream) {
        DefaultDirectedGraph<MenuFrame, DefaultEdge> menuGraph = buildGraph(menuConfigInputStream);
        return new GraphBasedMenuWalker(menuGraph, propertiesParser);
    }

    /**
     * Initializes {@link MenuWalker} from configuration provided as file with the specified path.
     * @param yamlConfig path to configuration file.
     * @return {@link MenuWalker} object which can be used to configure and run CLI menu.
     */
    public static MenuWalker initMenu(String yamlConfig) {
        DefaultDirectedGraph<MenuFrame, DefaultEdge> menuGraph = buildGraph(yamlConfig);
        return new GraphBasedMenuWalker(menuGraph, propertiesParser);
    }

    static DefaultDirectedGraph<MenuFrame, DefaultEdge> buildGraph(InputStream menuConfigInputStream) {
        MenuSystem menuSystem = new Yaml().loadAs(menuConfigInputStream, MenuSystem.class);
        return (DefaultDirectedGraph<MenuFrame, DefaultEdge>)
                new DefaultGraphBuilder(menuSystem).buildFramesGraph(propertiesParser);
    }

    static DefaultDirectedGraph<MenuFrame, DefaultEdge> buildGraph(String configContent) {
        MenuSystem menuSystem = new Yaml().loadAs(configContent, MenuSystem.class);
        return (DefaultDirectedGraph<MenuFrame, DefaultEdge>)
                new DefaultGraphBuilder(menuSystem).buildFramesGraph(propertiesParser);
    }
}
