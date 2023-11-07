package Graph;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import ProblemDB.*;


import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GraphHandlerTest {
    private ISimilarityGraph graph;
    private IProblemDB problemDB;
    private GraphHandler graphHandler;

    @Before
    public void setUp() {
        Set<String> s1 = new HashSet<>(Arrays.asList("B", "C"));
        Set<String> s2 = new HashSet<>(Arrays.asList("C", "D"));
        Set<String> s3 = new HashSet<>(Arrays.asList("D", "E"));
        Set<String> s4 = new HashSet<>(List.of("E"));
        Set<String> s5 = new HashSet<>(List.of("F"));
        List<Problem> problems = Arrays.asList(
                new Problem(1, "A", s1, 1),
                new Problem(2, "B", s2, 1),
                new Problem(3, "C", s3,2),
                new Problem(4, "D", s4, 2),
                new Problem(5, "E", s5, 3),
                new Problem(6, "F", new HashSet<>(), 3)
        );
        problemDB = new ProblemDB();
        for (Problem problem : problems) {
            problemDB.addProblem(problem);
        }
        graphHandler = new GraphHandler(problemDB);
        graphHandler.buildGraph();
        graph = graphHandler.getGraph();
    }
    @Test
    public void testBuildGraph() {
        // Check graph properties
        assertEquals(7, graph.vertexCount());
        assertEquals(16, graph.edgeCount());

        // Check edge existence
        assertTrue(graph.hasEdge(1, 2));
        assertTrue(graph.hasEdge(1, 3));
        assertTrue(graph.hasEdge(2, 3));
        assertTrue(graph.hasEdge(2, 4));
        assertTrue(graph.hasEdge(3, 4));
        assertTrue(graph.hasEdge(3, 5));
        assertTrue(graph.hasEdge(4, 5));
        assertTrue(graph.hasEdge(5, 6));
    }

    @Test
    public void testGetters() {
        assertEquals(graph, graphHandler.getGraph());
        assertEquals(problemDB, graphHandler.getProblemDB());
        assertEquals(6, graphHandler.getNumProblems());
    }

    @Test
    public void testSetProblemDB() {
        Set<String> s1 = new HashSet<>(Arrays.asList("B", "C"));
        Set<String> s2 = new HashSet<>(Arrays.asList("C", "D"));
        Set<String> s3 = new HashSet<>(Arrays.asList("D", "E"));
        Set<String> s4 = new HashSet<>(List.of("E"));
        Set<String> s5 = new HashSet<>(List.of("F"));
        List<Problem> newProblems = Arrays.asList(
                new Problem(1, "A", s1, 1),
                new Problem(2, "B", s2, 1),
                new Problem(3, "C", s3,2),
                new Problem(4, "D", s4, 2),
                new Problem(5, "E", s5, 3),
                new Problem(6, "F", new HashSet<>(), 3),
                new Problem(7, "G", new HashSet<>(List.of("A")), 1),
                new Problem(8, "H", new HashSet<>(
                                Arrays.asList("C", "G")), 2)
        );
        IProblemDB newProblemDB = new ProblemDB();
        for (Problem problem : newProblems) {
            newProblemDB.addProblem(problem);
        }
        graphHandler.setProblemDB(newProblemDB);
        ISimilarityGraph newGraph = graphHandler.getGraph();
        assertEquals(9, newGraph.vertexCount());
        assertEquals(22, newGraph.edgeCount());
    }

    @Test
    public void testGetSimilarProblems() {
        List<Problem> similarProblems = graphHandler.
                getSimilarProblems(1, 4);

        assertEquals(4, similarProblems.size());
        assertTrue(similarProblems.contains(problemDB.getProblemById(1)));
        assertTrue(similarProblems.contains(problemDB.getProblemById(3)));
        assertTrue(similarProblems.contains(problemDB.getProblemById(5)));
        assertTrue(similarProblems.contains(problemDB.getProblemById(6)));

        similarProblems = graphHandler.getSimilarProblems(1, 10);
        assertEquals(6, similarProblems.size());


    }
}
