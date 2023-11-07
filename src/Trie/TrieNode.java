package Trie;

import java.util.LinkedList;
import java.util.List;

public class TrieNode implements ITrieNode {

    private String term;    // we don't need weights unless we're doing an autocomplete
    private List<Integer> problemIDs = new LinkedList<>();   // problems whose names contain this word
    private int prefixes = 0;
    private TrieNode[] references;  // of size 36: we use the last ten slots for numbers 0-9

    /**
     * Initialize a Node with an empty string and 0 weight; useful for
     * writing tests.
     */
    public TrieNode() {
        term = "";
        references = new TrieNode[36];
    }

    /**
     * Initialize a Node with the given query string and weight.
     *
     * @throws IllegalArgumentException if query is null or if weight is negative.
     */
    public TrieNode(String query) {
        term = query;
        references = new TrieNode[36];
    }

    @Override
    public String getTerm() {
        return term;
    }

    @Override
    public void setTerm(String term) {
        this.term = term;
    }

    @Override
    public List<Integer> getProblemIDs() {
        return problemIDs;
    }

    @Override
    public void setProblemIDs(List<Integer> lis) {
        this.problemIDs = lis;
    }

    @Override
    public void addProblem(int id) {
        problemIDs.add(id);
    }

    @Override
    public int getNumPrefixes() {
        return prefixes;
    }

    @Override
    public void setNumPrefixes(int prefixes) {
        this.prefixes = prefixes;
    }

    @Override
    public void incrementPrefixes() {
        this.prefixes++;
    }

    @Override
    public TrieNode[] getReferences() {
        return references;
    }

    @Override
    public void setReferences(TrieNode[] references) {
        this.references = references;
    }
}
