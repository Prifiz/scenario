package org.myhomeapps.walkers;

import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.myhomeapps.adapters.CommandLineAdapter;
import org.myhomeapps.formatters.SimpleMenuFormatter;
import org.myhomeapps.menuentities.Bindings;
import org.myhomeapps.menuentities.MenuFrame;
import org.myhomeapps.menuentities.input.AbstractInputChecker;
import org.myhomeapps.menuentities.input.AbstractInputRule;
import org.myhomeapps.menuentities.input.DefaultInputChecker;
import org.myhomeapps.menuentities.properties.DefaultPropertiesParser;
import org.myhomeapps.menuentities.properties.Properties;
import org.myhomeapps.menuentities.properties.PropertiesParser;
import org.myhomeapps.walkers.validators.*;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class GraphBasedMenuWalker implements MenuWalker {

    private final DefaultDirectedGraph<MenuFrame, DefaultEdge> menuGraph;
    private final InputAsker inputAsker;
    private final Set<CommandLineAdapter> adapters = new HashSet<>();
    private final AbstractInputChecker inputChecker = new DefaultInputChecker();
    private boolean inBuiltGraphValidationNeeded = true;

    GraphBasedMenuWalker(DefaultDirectedGraph<MenuFrame, DefaultEdge> menuGraph) {
        this.menuGraph = menuGraph;
        this.inputAsker = new InputAsker(System.in, System.out);
    }

    public GraphBasedMenuWalker withCustomInputProcessors(AbstractInputRule... processors) {
        this.inputChecker.initCustomRules(processors);
        return this;
    }
//    public GraphBasedMenuWalker withCustomIO(InputStream in, PrintStream out) {
//        this.inputAsker = new InputAsker(in, out);
//        return this;
//    }

    @Override
    public GraphBasedMenuWalker disableInBuiltValidation() {
        this.inBuiltGraphValidationNeeded = false;
        return this;
    }

    @Override
    public GraphBasedMenuWalker enableInBuiltValidation() {
        this.inBuiltGraphValidationNeeded = true;
        return this;
    }

    @Override
    public MenuWalker registerAdapter(CommandLineAdapter adapter) {
        adapters.add(adapter);
        return this;
    }

    @Override
    public void run() throws IOException {
        if(inBuiltGraphValidationNeeded) {
            ValidationExecutor validationExecutor = new InbuiltValidationExecutor(menuGraph);
            List<? extends GraphIssue> issues = validationExecutor.validate();
            if(!issues.isEmpty()) {
                throw new MenuValidationException(new GraphIssuesReportBuilder(issues).buildValidationReport());
            }
        }

        PropertiesParser propertiesParser = new DefaultPropertiesParser();
        MenuFrame homeFrame = findHomeFrame(propertiesParser);

        PredefinedMenuOrderIterator<MenuFrame, DefaultEdge> graphIterator =
                new PredefinedMenuOrderIterator<>(menuGraph, homeFrame);

        String userInput = "";
        while (graphIterator.hasNext()) {
            MenuFrame currentMenu = graphIterator.next(userInput);
            do {
                userInput = askForInput(currentMenu, propertiesParser);
            } while (userInput != null && !inputChecker.isInputCorrect(
                    currentMenu.getInputRules(), userInput));
            bindAdapters(currentMenu.getBindings(), userInput); // FIXME get return values from adapters
        }
    }

    private MenuFrame findHomeFrame(PropertiesParser propertiesParser) {
        return menuGraph.vertexSet().stream()
                .filter(frame -> propertiesParser.parseProperties(frame.getProperties()).containsHome())
                .findAny()
                .orElseThrow(() -> new IllegalStateException("Couldn't find home frame"));
    }

    private void bindAdapters(Bindings bindings, String userInput) throws IOException {
        for(CommandLineAdapter commandLineAdapter : adapters) {
            commandLineAdapter.bind(bindings, userInput);
        }
    }

    private String askForInput(MenuFrame currentMenu, PropertiesParser propertiesParser) {
        Properties properties = propertiesParser.parseProperties(currentMenu.getProperties());
        if (properties.isInputExpected()) {
            return inputAsker.ask(new SimpleMenuFormatter().format(currentMenu));
        }
        return null;
    }
}
