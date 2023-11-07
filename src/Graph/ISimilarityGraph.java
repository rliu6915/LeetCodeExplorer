package Graph;


/**
 * This class represents an unweighted graph,
 * where vertices are Problems and edges represent "similar question" relationships.
 * The ID of the problem will be the index of the vertex in the graph.
 */
public interface ISimilarityGraph {

    /**
     * @return the number of vertices
     */
    int vertexCount();


    /**
     * @return the current number of edges
     */
    int edgeCount();


    /**
     * Adds a new edge from vertex v to vertex w
     *
     * @param v - the from vertex
     * @param w - the to vertex
     */
    void addEdge(int v, int w);


    /**
     * Removes the edge from the graph
     * If the edge does not exist, this method does nothing
     *
     * @param v - the from vertex
     * @param w - the to vertex
     */
    void removeEdge(int v, int w);


    /**
     * Returns true iff the graph has the edge
     *
     * @param v - the from vertex
     * @param w - the to vertex
     * @return true if the graph has a (v,w) edge
     */
    boolean hasEdge(int v, int w);


    /**
     * Returns an array containing the indices of the neighbors of v
     *
     * @param v - the vertex
     * @return the array of neighbors
     */
    int[] neighbors(int v);


}