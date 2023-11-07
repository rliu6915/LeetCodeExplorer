package Trie;

import java.util.LinkedList;
import java.util.List;

/**
 * The implementation of ITrieNode.
 */
public class WordTrie implements IWordTrie {

    private final TrieNode root = new TrieNode();

    /**
     * Adds a new word with its associated weight to the Trie.
     * If the word contains an invalid character, simply do nothing.
     *
     * @param word the word to be added to the Trie
     */
    @Override
    public boolean addWord(String word, int problemID) {
        word = word.toLowerCase();
        char[] arr = word.toCharArray();
        // check if word is valid
        for (char c : arr) {
            if (!Character.isLetter(c) && !Character.isDigit(c)) {
                //if any non-letter-non-number, do nothing
                return false;
            }
        }
        // traverse to the corresponding nodes and update them
        TrieNode ptr = root;
        ptr.incrementPrefixes();
        String query = "";
        for (char c : arr) {
            query += c;
            if (Character.isLetter(c)) {    // if letter
                if (ptr.getReferences()[c - 'a'] == null) {
                    ptr.getReferences()[c - 'a'] = new TrieNode(query);
                }
                ptr = ptr.getReferences()[c - 'a'];
                ptr.incrementPrefixes();
            } else if (Character.isDigit(c)) {  // if number
                if (ptr.getReferences()[c - '0' + 26] == null) {
                    ptr.getReferences()[c - '0' + 26] = new TrieNode(query);
                }
                ptr = ptr.getReferences()[c - '0' + 26];
                ptr.incrementPrefixes();
            }
        }
        // link the problem to the leaf node of word
        ptr.getProblemIDs().add(problemID);
        return true;
    }

    /**
     * @param prefix the prefix
     * @return the root of the subTrie corresponding to the last character of
     * the prefix. If the prefix is not represented in the trie, return null.
     */
    @Override
    public TrieNode getSubTrie(String prefix) {
        char[] arr = prefix.toCharArray();
        // check if prefix is valid
        for (char c : arr) {
            if (!Character.isLetter(c) && !Character.isDigit(c)) {
                //if any non-letter-non-digit, do nothing
                return null;
            }
        }
        // traverse from the root of trie to the root node of subtrie
        TrieNode ptr = root;
        for (char c : arr) {
            if (ptr == null) {
                return null;
            }
            if (Character.isLetter(c)) ptr = ptr.getReferences()[c - 'a'];
            else if (Character.isDigit(c)) ptr = ptr.getReferences()[c - '0' + 26];
        }
        return ptr;
    }

    /**
     * @param prefix the prefix
     * @return the number of words that start with prefix.
     */
    @Override
    public int countPrefixes(String prefix) {
        TrieNode sub = getSubTrie(prefix);
        if (sub == null) {
            return -1;
        }
        return sub.getNumPrefixes();
    }

    /**
     * Get the IDs of problems whose name contains a word with the given prefix.
     *
     * @param prefix the prefix of the word to search for
     * @return a List containing all the problems with name containing word
     * starting with the prefix. Return an empty list if there are none.
     */
    @Override
    public List<Integer> getProblemsByWord(String prefix) {
        // return all the problems in the subtrie of prefix
        TrieNode subRoot = getSubTrie(prefix);
        if (subRoot == null) {
            return new LinkedList<>();
        }
        return getProblemsInSubTrie(subRoot);
    }

    /**
     * Helper function to get all the problems in the subtrie of root.
     *
     * @param root the root of the subtrie
     * @return a List containing all the problems in the subtrie of root
     */
    private List<Integer> getProblemsInSubTrie(TrieNode root) {
        List<Integer> res = new LinkedList<>();
        // add all the problems in the current node(root)
        if (root.getProblemIDs().size() > 0) {
            res.addAll(root.getProblemIDs());
        }
        // add all the problems in the child subtries
        for (TrieNode child : root.getReferences()) {
            if (child == null) {
                continue;
            }
            if (child.getNumPrefixes() > 0) {
                // recursive call
                res.addAll(getProblemsInSubTrie(child));
            }
        }
        return res;
    }

    /**
     * Get the IDs of problems whose name contains words with the given prefixes.
     * This should return an intersection of the results of calling getProblemsByWord.
     *
     * @param prefixes the prefixes of the words to search for
     * @return a List containing all the problems with name containing all words
     * starting with given prefixes. Return an empty list if there are none.
     */
    @Override
    public List<Integer> getProblemsByWords(List<String> prefixes) {
        List<Integer> res = getProblemsByWord(prefixes.get(0));
        for (int i = 1; i < prefixes.size(); ++i) {
            // retain the intersection of the results
            res.retainAll(getProblemsByWord(prefixes.get(i)));
        }
        return res;
    }
}
