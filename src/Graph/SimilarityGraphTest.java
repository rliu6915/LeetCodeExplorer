package Graph;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SimilarityGraphTest {

    private SimilarityGraph graph;

    @Before
    public void setUp() {
        graph = new SimilarityGraph(5);
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 2);
        graph.addEdge(1, 3);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);
    }

    @Test
    public void testVertexCount() {
        assertEquals(5, graph.vertexCount());
    }

    @Test
    public void testEdgeCount() {
        assertEquals(6, graph.edgeCount());
    }

    @Test
    public void testHasEdge() {
        assertTrue(graph.hasEdge(0, 1));
        assertTrue(graph.hasEdge(0, 2));
        assertTrue(graph.hasEdge(1, 2));
        assertTrue(graph.hasEdge(1, 3));
        assertTrue(graph.hasEdge(2, 3));
        assertTrue(graph.hasEdge(3, 4));
        assertFalse(graph.hasEdge(0, 3));
        assertFalse(graph.hasEdge(1, 4));
    }

    @Test
    public void testNeighbors() {
        assertArrayEquals(new int[]{1, 2}, graph.neighbors(0));

    }

    @Test
    public void testRemoveEdge() {
        graph.removeEdge(0, 1);
        assertFalse(graph.hasEdge(0, 1));
        assertEquals(5, graph.edgeCount());

        graph.removeEdge(1, 3);
        assertFalse(graph.hasEdge(1, 3));
        assertEquals(4, graph.edgeCount());

        graph.removeEdge(3, 4);
        assertFalse(graph.hasEdge(3, 4));
        assertEquals(3, graph.edgeCount());
    }

    @Test
    public void testAddEdge() {
        graph.addEdge(0, 3);
        assertTrue(graph.hasEdge(0, 3));
        assertEquals(7, graph.edgeCount());

        graph.addEdge(1, 4);
        assertTrue(graph.hasEdge(1, 4));
        assertEquals(8, graph.edgeCount());

        graph.addEdge(2, 4);
        assertTrue(graph.hasEdge(2, 4));
        assertEquals(9, graph.edgeCount());
    }
}

