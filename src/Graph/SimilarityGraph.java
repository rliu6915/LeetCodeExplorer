package Graph;


public class SimilarityGraph implements ISimilarityGraph {
    private Edge[] nodeArray;
    private int numEdge;

    /**
     * Constructor
     * @param n - the number of vertices
     */
    public SimilarityGraph(int n) {
        nodeArray = new Edge[n];
        for (int i = 0; i < n; i++) {
            nodeArray[i] = new Edge(-1, null);
        }
        numEdge = 0;
    }

    /**
     * Get the number of vertices in the graph
     * @return the number of vertices
     */
    public int vertexCount() {
        return nodeArray.length;
    }

    /**
     * Get the number of edges in the graph
     * @return the number of edges
     */
    public int edgeCount() {
        return numEdge;
    }

    /**
     * Find the edge between v and w
     * @param v - the from vertex
     * @param w - the to vertex
     * @return the edge between v and w
     */
    private Edge find(int v, int w) {
        Edge curr = nodeArray[v];
        while (curr.next != null && curr.next.vertex < w) {
            curr = curr.next;
        }
        return curr;
    }

    /**
     * Add an edge between v and w
     * @param v - the from vertex
     * @param w - the to vertex
     */
    public void addEdge(int v, int w) {
        Edge curr = find(v, w);
        if (curr.next == null || curr.next.vertex != w) {
            curr.next = new Edge(w, curr.next);
            numEdge++;
        }
    }

    /**
     * Remove the edge between v and w
     * @param v - the from vertex
     * @param w - the to vertex
     */
    public void removeEdge(int v, int w) {
        Edge curr = find(v, w);
        if (curr.next != null && curr.next.vertex == w) {
            curr.next = curr.next.next;
            numEdge--;
        }
    }

    /**
     * Check if there is an edge between v and w
     * @param v - the from vertex
     * @param w - the to vertex
     * @return true if there is an edge between v and w
     */
    public boolean hasEdge(int v, int w) {
        Edge curr = find(v, w);
        return curr.next != null && curr.next.vertex == w;
    }

    /**
     * Get the neighbors of a vertex
     * @param v - the vertex
     * @return the array of neighbors
     */
    public int[] neighbors(int v) {
        int cnt = 0;
        Edge curr;
        for (curr = nodeArray[v].next; curr != null; curr = curr.next) {
            cnt++;
        }
        int[] temp = new int[cnt];
        cnt = 0;
        for (curr = nodeArray[v].next; curr != null; curr = curr.next) {
            temp[cnt++] = curr.vertex;
        }
        return temp;
    }

    private static class Edge {
        int vertex;
        Edge next;

        /**
         * Constructor
         * @param vertex - the vertex
         * @param next - the next edge
         */
        Edge(int vertex, Edge next) {
            this.vertex = vertex;
            this.next = next;
        }
    }


}
