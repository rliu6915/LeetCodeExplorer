package Trie;

import java.util.List;

/**
 * Interface for a TrieNode.
 */
public interface ITrieNode {

    /**
     * Returns the term stored at this node.
     *
     * @return the term stored at this node.
     */
    String getTerm();

    /**
     * Sets the term stored at this node.
     *
     * @param term the term to be stored at this node.
     */
    void setTerm(String term);

    /**
     * Returns the list of problem IDs stored at this node.
     *
     * @return the list of problem IDs stored at this node.
     */
    List<Integer> getProblemIDs();

    /**
     * Sets the list of problem IDs stored at this node.
     *
     * @param lis the list of problem IDs to be stored at this node.
     */
    void setProblemIDs(List<Integer> lis);

    /**
     * Adds a problem ID to the list of problem IDs stored at this node.
     *
     * @param id the problem ID to be added to the list of problem IDs stored at this node.
     */
    void addProblem(int id);

    /**
     * Returns the number of prefixes that end at this node.
     *
     * @return the number of prefixes that end at this node.
     */
    int getNumPrefixes();

    /**
     * Sets the number of prefixes that end at this node.
     *
     * @param prefixes the number of prefixes that end at this node.
     */
    void setNumPrefixes(int prefixes);

    /**
     * Returns the array of references stored at this node.
     *
     * @return the array of references stored at this node.
     */
    TrieNode[] getReferences();

    /**
     * Sets the array of references stored at this node.
     *
     * @param references the array of references to be stored at this node.
     */
    void setReferences(TrieNode[] references);

    /**
     * Increments the number of prefixes that end at this node by 1.
     */
    void incrementPrefixes();

}
