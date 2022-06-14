package test;

import org.junit.Test;
import sol.Dijkstra;
import sol.TravelController;
import src.IDijkstra;
import src.Transport;
import test.simple.SimpleEdge;
import test.simple.SimpleGraph;
import test.simple.SimpleVertex;

import java.util.List;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Your Dijkstra's tests should all go in this class!
 * The test we've given you will pass if you've implemented Dijkstra's correctly, but we still
 * expect you to write more tests using the City and Transport classes.
 * You are welcome to write more tests using the Simple classes, but you will not be graded on
 * those.
 *
 * TODO: Recreate the test below for the City and Transport classes
 * TODO: Expand on your tests, accounting for basic cases and edge cases
 */
public class DijkstraTest {

    private static final double DELTA = 0.001;

    private SimpleGraph graph;
    private SimpleVertex a;
    private SimpleVertex b;
    private SimpleVertex c;
    private SimpleVertex d;
    private SimpleVertex e;

    /**
     * Creates a simple graph.
     * You'll find a similar method in each of the Test files.
     * Normally, we'd like to use @Before, but because each test may require a different setup,
     * we manually call the setup method at the top of the test.
     *
     * TODO: create more setup methods!
     */
    private void createSimpleGraph() {
        this.graph = new SimpleGraph();

        this.a = new SimpleVertex("a");
        this.b = new SimpleVertex("b");
        this.c = new SimpleVertex("c");
        this.d = new SimpleVertex("d");
        this.e = new SimpleVertex("e");

        this.graph.addVertex(this.a);
        this.graph.addVertex(this.b);
        this.graph.addVertex(this.c);
        this.graph.addVertex(this.d);
        this.graph.addVertex(this.e);

        this.graph.addEdge(this.a, new SimpleEdge(100, this.a, this.b));
        this.graph.addEdge(this.a, new SimpleEdge(3, this.a, this.c));
        this.graph.addEdge(this.a, new SimpleEdge(1, this.a, this.e));
        this.graph.addEdge(this.c, new SimpleEdge(6, this.c, this.b));
        this.graph.addEdge(this.c, new SimpleEdge(2, this.c, this.d));
        this.graph.addEdge(this.d, new SimpleEdge(1, this.d, this.b));
        this.graph.addEdge(this.d, new SimpleEdge(5, this.e, this.d));
    }

    @Test
    public void testSimple() {
        this.createSimpleGraph();

        IDijkstra<SimpleVertex, SimpleEdge> dijkstra = new Dijkstra<>();
        Function<SimpleEdge, Double> edgeWeightCalculation = e -> e.weight;
        // a -> c -> d -> b
        List<SimpleEdge> path =
            dijkstra.getShortestPath(this.graph, this.a, this.b, edgeWeightCalculation);
        assertEquals(6, SimpleGraph.getTotalEdgeWeight(path), DELTA);
        assertEquals(3, path.size());

        // c -> d -> b
        path = dijkstra.getShortestPath(this.graph, this.c, this.b, edgeWeightCalculation);
        assertEquals(3, SimpleGraph.getTotalEdgeWeight(path), DELTA);
        assertEquals(2, path.size());
    }

    @Test
    public void testComplex() {
        TravelController tc = new TravelController();
        tc.load("/Users/jarraomar/Desktop/cs200/projects/travel-planner/data/cities2.csv","/Users/jarraomar/Desktop/cs200/projects/travel-planner/data/transport2.csv");
        List<Transport> cheapPath = tc.cheapestRoute("Boston", "Providence");
        List<Transport> fastPath = tc.fastestRoute("Boston", "Providence");
        assertEquals(3,cheapPath.size());
        assertEquals(1, fastPath.size());
    }

    @Test
    public void noPath() {
        TravelController tc = new TravelController();
        tc.load("/Users/jarraomar/Desktop/cs200/projects/travel-planner/data/cities2.csv","/Users/jarraomar/Desktop/cs200/projects/travel-planner/data/transport2.csv");
        List<Transport> cheapPath = tc.cheapestRoute("Jamaica", "Ethiopia");
        List<Transport> fastPath = tc.fastestRoute("Jamaica", "Jamaica");
        assertEquals(0,cheapPath.size());
        assertEquals(0,fastPath.size());
    }

    @Test
    public void atStart(){
        TravelController tc = new TravelController();
        tc.load("/Users/jarraomar/Desktop/cs200/projects/travel-planner/data/cities2.csv","/Users/jarraomar/Desktop/cs200/projects/travel-planner/data/transport2.csv");
        List<Transport> cheapPath = tc.cheapestRoute("Boston", "Boston");
        List<Transport> fastPath = tc.fastestRoute("Boston", "Boston");
        assertEquals(0,cheapPath.size());
        assertEquals(0,fastPath.size());
    }

    // TODO: write more tests + make sure you test all the cases in your testing plan!
}
