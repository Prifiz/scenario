package org.myhomeapps.walkers;

import mockit.Expectations;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.myhomeapps.menuentities.MenuFrame;
import org.myhomeapps.menuentities.MenuItem;

import static org.myhomeapps.walkers.PredefinedMenuOrderIterator.NOT_MY_START_VERTEX_MSG;

public class PredefinedMenuOrderIteratorTest {

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Test
    public void constructorPositiveTest() {
        DefaultDirectedGraph<MenuFrame, DefaultEdge> graph = new DefaultDirectedGraph<>(DefaultEdge.class);
        MenuFrame startVertex = new MenuFrame();
        new Expectations(graph) {{
           graph.containsVertex(startVertex); result = true;
        }};
        PredefinedMenuOrderIterator<MenuFrame, DefaultEdge> iterator =
                new PredefinedMenuOrderIterator<>(graph, startVertex);
        Assert.assertEquals(startVertex, iterator.getCurrentVertex());
    }

    @Test
    public void constructorWithUnknownStartVertexTest() {
        expectedEx.expectMessage(NOT_MY_START_VERTEX_MSG);
        expectedEx.expect(IllegalArgumentException.class);
        DefaultDirectedGraph<MenuFrame, DefaultEdge> graph = new DefaultDirectedGraph<>(DefaultEdge.class);
        MenuFrame startVertex = new MenuFrame();
        new PredefinedMenuOrderIterator<>(graph, startVertex);
    }

    @Test
    public void startVertexHasNextTest() {
        MenuFrame startVertex = new MenuFrame("start");
        DefaultDirectedGraph<MenuFrame, DefaultEdge> graph = new DefaultDirectedGraph<>(DefaultEdge.class);
        graph.addVertex(startVertex);
        MenuFrame nextVertex = new MenuFrame("end");
        graph.addVertex(nextVertex);
        graph.addEdge(startVertex, nextVertex);
        PredefinedMenuOrderIterator<MenuFrame, DefaultEdge> iterator =
                new PredefinedMenuOrderIterator<>(graph, startVertex);
        Assert.assertTrue(iterator.hasNext());
    }

    @Test
    public void endVertexHasNoNextTest() {
        DefaultDirectedGraph<MenuFrame, DefaultEdge> graph = new DefaultDirectedGraph<>(DefaultEdge.class);
        MenuFrame startVertex = new MenuFrame("start");
        graph.addVertex(startVertex);
        MenuFrame nextVertex = new MenuFrame("next");
        graph.addVertex(nextVertex);
        graph.addEdge(startVertex, nextVertex);
        PredefinedMenuOrderIterator<MenuFrame, DefaultEdge> iterator =
                new PredefinedMenuOrderIterator<>(graph, nextVertex);
        Assert.assertFalse(iterator.hasNext());
    }

    @Test
    public void midVertexHasNextTest() {
        DefaultDirectedGraph<MenuFrame, DefaultEdge> graph = new DefaultDirectedGraph<>(DefaultEdge.class);
        MenuFrame startVertex = new MenuFrame("start");
        graph.addVertex(startVertex);
        MenuFrame midVertex = new MenuFrame("mid");
        graph.addVertex(midVertex);
        graph.addEdge(startVertex, midVertex);
        MenuFrame endVertex = new MenuFrame("end");
        graph.addVertex(endVertex);
        graph.addEdge(midVertex, endVertex);
        PredefinedMenuOrderIterator<MenuFrame, DefaultEdge> iterator =
                new PredefinedMenuOrderIterator<>(graph, midVertex);
        Assert.assertTrue(iterator.hasNext());
    }

    @Test
    public void iterateFirstElementTest() {
        DefaultDirectedGraph<MenuFrame, DefaultEdge> graph = new DefaultDirectedGraph<>(DefaultEdge.class);
        MenuFrame startVertex = new MenuFrame("start");
        graph.addVertex(startVertex);
        MenuFrame endVertex = new MenuFrame("end");
        graph.addVertex(endVertex);
        graph.addEdge(startVertex, endVertex);
        PredefinedMenuOrderIterator<MenuFrame, DefaultEdge> iterator =
                new PredefinedMenuOrderIterator<>(graph, startVertex);
        Assert.assertEquals(startVertex, iterator.next());
    }

    @Test
    public void endReachedTest() {
        DefaultDirectedGraph<MenuFrame, DefaultEdge> graph = new DefaultDirectedGraph<>(DefaultEdge.class);
        MenuFrame startVertex = new MenuFrame("start");
        graph.addVertex(startVertex);
        MenuFrame endVertex = new MenuFrame("end");
        graph.addVertex(endVertex);
        graph.addEdge(startVertex, endVertex);
        PredefinedMenuOrderIterator<MenuFrame, DefaultEdge> iterator =
                new PredefinedMenuOrderIterator<>(graph, startVertex);
        iterator.next();
        MenuFrame maybeEndElement = iterator.next();
        Assert.assertEquals(endVertex, maybeEndElement);
        Assert.assertEquals(endVertex, iterator.next());
    }

    @Test
    public void nextByItemIncorrectInputTest() {
        DefaultDirectedGraph<MenuFrame, DefaultEdge> graph = new DefaultDirectedGraph<>(DefaultEdge.class);
        MenuFrame startVertex = new MenuFrame("startWithItems");
        MenuItem first = new MenuItem()
                .withName("first")
                .withText("First")
                .goTo("end");
        MenuItem second = new MenuItem()
                .withName("second")
                .withText("Second");
        startVertex.addItem(first);
        startVertex.addItem(second);
        graph.addVertex(startVertex);
        MenuFrame endVertex = new MenuFrame("end");
        graph.addVertex(endVertex);
        graph.addEdge(startVertex, endVertex);
        PredefinedMenuOrderIterator<MenuFrame, DefaultEdge> iterator =
                new PredefinedMenuOrderIterator<>(graph, startVertex);
        iterator.next();
        Assert.assertEquals(startVertex, iterator.next("Incorrect Input"));
    }

    @Test
    public void nextByItemTest() {
        DefaultDirectedGraph<MenuFrame, DefaultEdge> graph = new DefaultDirectedGraph<>(DefaultEdge.class);
        MenuFrame startVertex = new MenuFrame("startWithItems");
        MenuItem first = new MenuItem()
                .withName("first")
                .withText("First")
                .goTo("end");
        MenuItem second = new MenuItem()
                .withName("second")
                .withText("Second");
        startVertex.addItem(first);
        startVertex.addItem(second);
        graph.addVertex(startVertex);
        MenuFrame endVertex = new MenuFrame("end");
        graph.addVertex(endVertex);
        graph.addEdge(startVertex, endVertex);
        PredefinedMenuOrderIterator<MenuFrame, DefaultEdge> iterator =
                new PredefinedMenuOrderIterator<>(graph, startVertex);
        iterator.next();
        Assert.assertEquals(endVertex, iterator.next("First"));
    }

}
