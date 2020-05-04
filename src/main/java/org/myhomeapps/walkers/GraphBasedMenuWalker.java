package org.myhomeapps.walkers;

import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.traverse.GraphIterator;
import org.myhomeapps.adapters.CommandLineAdapter;
import org.myhomeapps.config.ConfigParser;
import org.myhomeapps.config.SimpleYamlParser;
import org.myhomeapps.formatters.SimpleMenuFormatter;
import org.myhomeapps.menuentities.MenuFrame;
import org.myhomeapps.menuentities.MenuSystem;
import org.myhomeapps.printers.FormattedMenuPrinter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Scanner;

public final class GraphBasedMenuWalker extends Observable implements MenuWalker {

    private final MenuSystem menuSystem;
    private final DefaultDirectedGraph<MenuFrame, DefaultEdge> menuGraph;

    public GraphBasedMenuWalker() throws IOException {
        ConfigParser parser = new SimpleYamlParser("menuSystem.yaml");
        menuSystem = parser.parseMenuSystem();
        menuGraph = (DefaultDirectedGraph<MenuFrame, DefaultEdge>) new DefaultGraphBuilder().buildFramesGraph(menuSystem);

        List<GraphValidator<MenuFrame, DefaultEdge>> validators = new ArrayList<>();
        validators.add(new DuplicatesFinderValidator());
        // ...

        List<GraphIssue> issues = new ArrayList<>();
        validators.forEach(currentValidator -> issues.addAll(currentValidator.validate(menuGraph)));


        issues.forEach(graphIssue -> {
            System.out.println(graphIssue.getName());
            graphIssue.getOccurrences().forEach(System.out::println);
        });

        if (!issues.isEmpty()) {
            System.exit(0);
        }

    }

    @Override
    public void run() {
        GraphIterator<MenuFrame, DefaultEdge> it = new PredefinedMenuOrderIterator<>(
                menuGraph, menuSystem.getHomeFrame());
        while (it.hasNext()) {
            MenuFrame currentMenu = it.next();
            new FormattedMenuPrinter(new SimpleMenuFormatter(), System.out).print(currentMenu);
            if(currentMenu.isInputExpected()) {
                currentMenu.setUserInput(new Scanner(System.in).nextLine());
            }
        }
    }

    @Override
    public void registerAdapter(CommandLineAdapter adapter) {
        menuGraph.vertexSet().forEach(frame -> frame.addObserver(adapter));
    }

}
