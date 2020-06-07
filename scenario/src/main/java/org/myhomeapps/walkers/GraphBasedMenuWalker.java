package org.myhomeapps.walkers;

import org.apache.commons.lang3.StringUtils;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.myhomeapps.adapters.CommandLineAdapter;
import org.myhomeapps.formatters.SimpleMenuFormatter;
import org.myhomeapps.menuentities.Bindings;
import org.myhomeapps.menuentities.MenuFrame;
import org.myhomeapps.menuentities.MenuSystem;
import org.myhomeapps.menuentities.input.AbstractInputRule;
import org.myhomeapps.menuentities.input.InputCheckingRule;
import org.myhomeapps.menuentities.input.InputRule;
import org.myhomeapps.menuentities.properties.DefaultPropertiesParser;
import org.myhomeapps.menuentities.properties.Properties;
import org.myhomeapps.menuentities.properties.PropertiesParser;
import org.myhomeapps.walkers.graphbuilders.DefaultGraphBuilder;
import org.myhomeapps.walkers.validators.*;
import org.reflections.Reflections;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class GraphBasedMenuWalker implements MenuWalker {

    private final DefaultDirectedGraph<MenuFrame, DefaultEdge> menuGraph;
    private InputAsker inputAsker;
    private Set<CommandLineAdapter> adapters = new HashSet<>();

    public GraphBasedMenuWalker(String yamlConfig) throws IOException {
        super();
        this.menuGraph = buildGraph(yamlConfig);
        validateGraph(menuGraph);
    }

    public GraphBasedMenuWalker(InputStream menuConfigInputStream, InputAsker inputAsker) throws IOException {
        this.inputAsker = inputAsker;
        this.menuGraph = buildGraph(menuConfigInputStream);
        validateGraph(menuGraph);
    }

    DefaultDirectedGraph<MenuFrame, DefaultEdge> buildGraph(InputStream menuConfigInputStream) {
        MenuSystem menuSystem = new Yaml().loadAs(menuConfigInputStream, MenuSystem.class);
        return (DefaultDirectedGraph<MenuFrame, DefaultEdge>) new DefaultGraphBuilder(menuSystem).buildFramesGraph();
    }

    DefaultDirectedGraph<MenuFrame, DefaultEdge> buildGraph(String configContent) {
        MenuSystem menuSystem = new Yaml().loadAs(configContent, MenuSystem.class);
        return (DefaultDirectedGraph<MenuFrame, DefaultEdge>) new DefaultGraphBuilder(menuSystem).buildFramesGraph();
    }

    void validateGraph(DefaultDirectedGraph<MenuFrame, DefaultEdge> menuGraph)
            throws MenuValidationException {
        List<AbstractValidator<MenuFrame, DefaultEdge>> validators =
                prepareValidators(new DefaultPropertiesParser(), menuGraph);
        List<? extends GraphIssue> issues = doValidateGraph(validators);
        processValidationResult(issues);
    }

    void processValidationResult(List<? extends GraphIssue> issues) throws MenuValidationException {
        if(!issues.isEmpty()) {
            throw new MenuValidationException(buildGraphIssuesReport(issues));
        }
    }

    List<? extends GraphIssue> doValidateGraph(List<AbstractValidator<MenuFrame, DefaultEdge>> validators) {
        return validators.stream()
                .filter(validator -> !validator.validate().getOccurrences().isEmpty())
                .map(AbstractValidator::validate)
                .collect(Collectors.toList());
    }

    String buildGraphIssuesReport(List<? extends GraphIssue> issues) {
        StringBuffer stringBuffer = new StringBuffer();
        issues.forEach(graphIssue -> {
            stringBuffer.append("\n" + graphIssue.getName() + ":\n");
            graphIssue.getOccurrences().forEach(occurrence -> stringBuffer.append("\t" + occurrence + "\n"));
        });
        return stringBuffer.toString();
    }

    List<AbstractValidator<MenuFrame, DefaultEdge>> prepareValidators(
            PropertiesParser propertiesParser, DefaultDirectedGraph<MenuFrame, DefaultEdge> menuGraph) {
        List<AbstractValidator<MenuFrame, DefaultEdge>> validators = new ArrayList<>();
        validators.add(new DeadEndsValidator<>(menuGraph, propertiesParser));
        validators.add(new MultipleHomeFramesValidator<>(menuGraph, propertiesParser));
        validators.add(new EndlessCyclesValidator<>(menuGraph, propertiesParser));
        validators.add(new FramesWithoutTextValidator<>(menuGraph));
        validators.add(new MenuItemsWithoutTextValidator<>(menuGraph));
        validators.add(new DuplicatedFramesValidator<>(menuGraph));
        return validators;
    }

    MenuFrame findHomeFrame(PropertiesParser propertiesParser) {
        return menuGraph.vertexSet().stream()
                .filter(frame -> propertiesParser.parseProperties(frame.getProperties()).containsHome())
                .findAny()
                .orElseThrow(() -> new IllegalStateException("Couldn't find home frame"));
    }

    @Override
    public void run() throws IOException {
        PropertiesParser propertiesParser = new DefaultPropertiesParser();
        MenuFrame homeFrame = findHomeFrame(propertiesParser);

        PredefinedMenuOrderIterator<MenuFrame, DefaultEdge> graphIterator =
                new PredefinedMenuOrderIterator<>(menuGraph, homeFrame);
        Reflections reflections = new Reflections("org.myhomeapps");
        Set<Class<?>> annotatedClasses = reflections.getTypesAnnotatedWith(InputCheckingRule.class);

        String userInput = "";
        while (graphIterator.hasNext()) {
            MenuFrame currentMenu = graphIterator.next(userInput);
            do {
                userInput = askForInput(currentMenu, propertiesParser);
            } while (userInput != null && !isInputCorrect(currentMenu, annotatedClasses, userInput));
            bindAdapters(currentMenu.getBindings(), userInput); // FIXME get return values from adapters
        }
    }

    void bindAdapters(Bindings bindings, String userInput) throws IOException {
        for(CommandLineAdapter commandLineAdapter : adapters) {
            commandLineAdapter.bind(bindings, userInput);
        }
    }

    boolean isInputCorrect(MenuFrame currentMenu, Set<Class<?>> annotatedClasses, String userInput) {
        if(currentMenu.getInputRules().isEmpty()) {
            return true;
        }
        return currentMenu.getInputRules().stream()
                .map(inputRule -> parseRule(inputRule, annotatedClasses))
                .anyMatch(rule -> rule.checkRule(userInput));
    }

    AbstractInputRule parseRule(InputRule inputRule, Set<Class<?>> annotatedClasses) throws RuntimeException {

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

    String askForInput(MenuFrame currentMenu, PropertiesParser propertiesParser) {
        Properties properties = propertiesParser.parseProperties(currentMenu.getProperties());
        if (properties.isInputExpected()) {
            return inputAsker.ask(new SimpleMenuFormatter().format(currentMenu));
        }
        return null;
    }

    @Override
    public MenuWalker registerAdapter(CommandLineAdapter adapter) {
        adapters.add(adapter);
        return this;
    }

    // TODO custom input control

}
