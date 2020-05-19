package org.myhomeapps.walkers;

import org.apache.commons.lang3.StringUtils;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.traverse.GraphIterator;
import org.myhomeapps.adapters.CommandLineAdapter;
import org.myhomeapps.config.ConfigParser;
import org.myhomeapps.config.SimpleYamlParser;
import org.myhomeapps.formatters.SimpleMenuFormatter;
import org.myhomeapps.menuentities.MenuFrame;
import org.myhomeapps.menuentities.MenuSystem;
import org.myhomeapps.menuentities.input.AbstractInputRule;
import org.myhomeapps.menuentities.input.InputCheckingRule;
import org.myhomeapps.menuentities.input.InputRule;
import org.myhomeapps.menuentities.properties.DefaultPropertiesParser;
import org.myhomeapps.menuentities.properties.Properties;
import org.myhomeapps.menuentities.properties.PropertiesParser;
import org.myhomeapps.printers.FormattedMenuPrinter;
import org.myhomeapps.walkers.graphbuilders.DefaultGraphBuilder;
import org.myhomeapps.walkers.validators.*;
import org.reflections.Reflections;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.*;
import java.util.stream.Collectors;

public final class GraphBasedMenuWalker extends Observable implements MenuWalker {

    private final DefaultDirectedGraph<MenuFrame, DefaultEdge> menuGraph;

    public GraphBasedMenuWalker() throws IOException {
        ConfigParser parser = new SimpleYamlParser("menuSystem.yaml");
        PropertiesParser propertiesParser = new DefaultPropertiesParser();

        MenuSystem menuSystem = parser.parseMenuSystem();
        menuGraph = (DefaultDirectedGraph<MenuFrame, DefaultEdge>) new DefaultGraphBuilder(menuSystem).buildFramesGraph();

//        new SimpleGraphPainter<MenuFrame, DefaultEdge>().paint(menuGraph, "graph.png");

        List<AbstractValidator<MenuFrame, DefaultEdge>> validators = new ArrayList<>();
        validators.add(new DeadEndsValidator<>(menuGraph, propertiesParser));
        validators.add(new MultipleHomeFramesValidator<>(menuGraph, propertiesParser));
        validators.add(new EndlessCyclesValidator<>(menuGraph, propertiesParser));
        validators.add(new FramesWithoutTextValidator<>(menuGraph));
        validators.add(new MenuItemsWithoutTextValidator<>(menuGraph));
        validators.add(new DuplicatedFramesValidator<>(menuGraph));

        List<? extends GraphIssue> issues = validators.stream()
                .filter(validator -> !validator.validate().getOccurrences().isEmpty())
                .map(AbstractValidator::validate)
                .collect(Collectors.toList());

        issues.forEach(graphIssue -> {
            System.out.println(graphIssue.getName() + ":");
            graphIssue.getOccurrences().forEach(occurrence -> System.out.println("\t" + occurrence));
        });

        if (!issues.isEmpty()) {
            System.exit(0);// FIXME not so hardcore exit needed here
        }
    }

    private MenuFrame findHomeFrame(PropertiesParser propertiesParser) {
        return menuGraph.vertexSet().stream()
                .filter(frame -> propertiesParser.parseProperties(frame.getProperties()).containsHome())
                .findAny()
                .orElseThrow(() -> new IllegalStateException("Couldn't find home frame"));
    }

    @Override
    public void run() {
        PropertiesParser propertiesParser = new DefaultPropertiesParser();
        MenuFrame homeFrame = findHomeFrame(propertiesParser);
        GraphIterator<MenuFrame, DefaultEdge> graphIterator = new PredefinedMenuOrderIterator<>(menuGraph, homeFrame);

        Reflections reflections = new Reflections("org.myhomeapps");
        Set<Class<?>> annotatedClasses = reflections.getTypesAnnotatedWith(InputCheckingRule.class);

        while (graphIterator.hasNext()) {
            MenuFrame currentMenu = graphIterator.next();
            doRun(currentMenu, propertiesParser);
            while (currentMenu.getInputRules().stream()
                    .map(inputRule -> parseRule(inputRule, annotatedClasses))
                    .anyMatch(rule -> !rule.checkRule(currentMenu.getUserInput()))) {
                doRun(currentMenu, propertiesParser);
            }
        }
    }

    private AbstractInputRule parseRule(InputRule inputRule, Set<Class<?>> annotatedClasses) throws RuntimeException {

        for (Class<?> clazz : annotatedClasses) {
            try {
                Constructor<?> ruleConstructor = clazz.getConstructor(String.class);
                AbstractInputRule ruleInstance;
                if (StringUtils.isNotBlank(inputRule.getErrorMessage())) {
                    ruleInstance = (AbstractInputRule) ruleConstructor.newInstance(inputRule.getErrorMessage());
                } else {
                    ruleInstance = (AbstractInputRule) ruleConstructor.newInstance();
                }
                if (ruleInstance.getRule().equals(inputRule.getRule())) {
                    return ruleInstance;
                }
            } catch (Exception ex) {
                throw new RuntimeException("Couldn't parse rule", ex);
            }
        }
        throw new RuntimeException("No such rule declared: " + inputRule.getRule());
    }

    private void doRun(MenuFrame currentMenu, PropertiesParser propertiesParser) {
        new FormattedMenuPrinter(new SimpleMenuFormatter(), System.out).print(currentMenu);
        Properties properties = propertiesParser.parseProperties(currentMenu.getProperties());
        if (properties.isInputExpected()) {
            currentMenu.setUserInput(new Scanner(System.in).nextLine());
        }
    }

    @Override
    public void registerAdapter(CommandLineAdapter adapter) {
        menuGraph.vertexSet().forEach(frame -> frame.addObserver(adapter));
    }

    // TODO custom input control

}
