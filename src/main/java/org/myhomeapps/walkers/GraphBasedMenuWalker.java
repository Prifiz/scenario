package org.myhomeapps.walkers;

import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.traverse.GraphIterator;
import org.myhomeapps.adapters.CommandLineAdapter;
import org.myhomeapps.config.ConfigParser;
import org.myhomeapps.config.SimpleYamlParser;
import org.myhomeapps.formatters.SimpleMenuFormatter;
import org.myhomeapps.menuentities.*;
import org.myhomeapps.printers.FormattedMenuPrinter;
import org.myhomeapps.walkers.validators.DeadEndsFinderValidator;
import org.myhomeapps.walkers.validators.GraphValidator;

import java.io.IOException;
import java.util.*;

public final class GraphBasedMenuWalker extends Observable implements MenuWalker {

    private final MenuSystem menuSystem;
    private final DefaultDirectedGraph<MenuFrame, DefaultEdge> menuGraph;
    private Set<Macro> macros = new HashSet<>();

    public GraphBasedMenuWalker() throws IOException {
        ConfigParser parser = new SimpleYamlParser("menuSystem.yaml");
        MacrosParser macrosParser = new DefaultMacrosParser(); // will be used in validations

        menuSystem = parser.parseMenuSystem(macrosParser);
        menuGraph = (DefaultDirectedGraph<MenuFrame, DefaultEdge>) new DefaultGraphBuilder().buildFramesGraph(menuSystem);

        List<GraphValidator<MenuFrame, DefaultEdge>> validators = new ArrayList<>();
        validators.add(new DeadEndsFinderValidator());
        //validators.add(new DuplicatesFinderValidator());
        // ...

        List<GraphIssue> issues = new ArrayList<>();
        validators.forEach(currentValidator -> issues.addAll(currentValidator.validate(menuGraph)));


        issues.forEach(graphIssue -> {
            System.out.println(graphIssue.getName());
            graphIssue.getOccurrences().forEach(System.out::println);
        });

        if (!issues.isEmpty()) {
            System.exit(0);// FIXME not so hardcore exit needed here
        }
    }



    @Override
    public void run() {
        MacrosParser macrosParser = new DefaultMacrosParser();
        MenuFrame homeFrame = menuSystem.getHomeFrame(macrosParser);
        GraphIterator<MenuFrame, DefaultEdge> it = new PredefinedMenuOrderIterator<>(menuGraph, homeFrame);
        while (it.hasNext()) {
            MenuFrame currentMenu = it.next();
            new FormattedMenuPrinter(new SimpleMenuFormatter(), System.out).print(currentMenu);

            Macros macros = macrosParser.parseMacros(currentMenu.getProperties());

            if(macros.isInputExpected()) {
                currentMenu.setUserInput(new Scanner(System.in).nextLine());
            }
        }
    }

    @Override
    public void registerAdapter(CommandLineAdapter adapter) {
        menuGraph.vertexSet().forEach(frame -> frame.addObserver(adapter));
    }

    @Override
    public void registerCustomMacro(Macro customMacro) { // FIXME how will it work for user??? Behavior??!!
        // Can be used in custom validations!!!
        macros.add(customMacro);
    }

    // TODO custom input control

}
