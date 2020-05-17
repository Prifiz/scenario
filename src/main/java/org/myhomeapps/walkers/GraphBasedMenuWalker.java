package org.myhomeapps.walkers;

import org.apache.commons.lang3.StringUtils;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.traverse.GraphIterator;
import org.myhomeapps.adapters.CommandLineAdapter;
import org.myhomeapps.config.ConfigParser;
import org.myhomeapps.config.SimpleYamlParser;
import org.myhomeapps.formatters.SimpleMenuFormatter;
import org.myhomeapps.menuentities.*;
import org.myhomeapps.menuentities.input.AbstractInputRule;
import org.myhomeapps.menuentities.input.InputCheckingRule;
import org.myhomeapps.menuentities.input.InputRule;
import org.myhomeapps.printers.FormattedMenuPrinter;
import org.myhomeapps.walkers.validators.*;
import org.reflections.Reflections;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.*;

public final class GraphBasedMenuWalker extends Observable implements MenuWalker {

    private final MenuSystem menuSystem;
    private final DefaultDirectedGraph<MenuFrame, DefaultEdge> menuGraph;
    private Set<Macro> macros = new HashSet<>();

    public GraphBasedMenuWalker() throws IOException {
        ConfigParser parser = new SimpleYamlParser("menuSystem.yaml");
        MacrosParser macrosParser = new DefaultMacrosParser(); // will be used in validations

        menuSystem = parser.parseMenuSystem();
        menuGraph = (DefaultDirectedGraph<MenuFrame, DefaultEdge>) new DefaultGraphBuilder().buildFramesGraph(menuSystem);

        List<GraphValidator<MenuFrame, DefaultEdge>> validators = new ArrayList<>();
        validators.add(new DeadEndsValidator(macrosParser));
        validators.add(new MultipleHomeFramesValidator(macrosParser));
        validators.add(new EndlessCyclesValidator(macrosParser));
        validators.add(new FramesWithoutTextValidator());
        validators.add(new MenuItemsWithoutTextValidator());

        List<GraphIssue> issues = new ArrayList<>();
        validators.forEach(currentValidator -> issues.addAll(currentValidator.validate(menuGraph)));


        issues.forEach(graphIssue -> {
            System.out.println(graphIssue.getName() + ":");
            graphIssue.getOccurrences().forEach(occurrence -> System.out.println("\t" + occurrence));
        });

        if (!issues.isEmpty()) {
            System.exit(0);// FIXME not so hardcore exit needed here
        }
    }



    @Override
    public void run() throws IOException {
        MacrosParser macrosParser = new DefaultMacrosParser();
        MenuFrame homeFrame = menuSystem.getHomeFrame(macrosParser);
        GraphIterator<MenuFrame, DefaultEdge> it = new PredefinedMenuOrderIterator<>(menuGraph, homeFrame);

        Reflections reflections = new Reflections("org.myhomeapps");
        Set<Class<?>> annotatedClasses = reflections.getTypesAnnotatedWith(InputCheckingRule.class);

        while (it.hasNext()) {
            MenuFrame currentMenu = it.next();

            doRun(currentMenu, macrosParser);

            while (currentMenu.getInputRules().stream()
                    .map(inputRule -> parseRule(inputRule, annotatedClasses))
                    .anyMatch(rule -> !rule.checkRule(currentMenu.getUserInput()))) {

                doRun(currentMenu, macrosParser);
            }

        }
    }

    private AbstractInputRule parseRule(InputRule inputRule, Set<Class<?>> annotatedClasses) throws RuntimeException {

        for(Class<?> clazz : annotatedClasses) {
            try {
                Constructor<?> cons = clazz.getConstructor(String.class);
                AbstractInputRule rule;
                if(StringUtils.isNotBlank(inputRule.getErrorMessage())) {
                    rule = (AbstractInputRule) cons.newInstance(inputRule.getErrorMessage());
                } else {
                    rule = (AbstractInputRule) clazz.newInstance();
                }
                if (rule.getRule().equals(inputRule.getRule())) {
                    return rule;
                }
            } catch (Exception ex) {
                throw new RuntimeException("Couldn't parse rule", ex);
            }
        }
        throw new RuntimeException("No such rule declared: " + inputRule.getRule());
    }

    private void doRun(MenuFrame currentMenu, MacrosParser macrosParser) {
        new FormattedMenuPrinter(new SimpleMenuFormatter(), System.out).print(currentMenu);

        Macros macros = macrosParser.parseMacros(currentMenu.getProperties());

        if(macros.isInputExpected()) {
            currentMenu.setUserInput(new Scanner(System.in).nextLine());
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
