package Trie;

import java.util.List;

/**
 * This class represents a Trie data structure for storing words in problems' names
 * and the related problems.
 * For example:
 * - problem 1 "Two Sum" is a problem name, and "Two" and "Sum" are words in the name,
 * - so 1 is linked to the nodes corresponding to "Two" and "Sum".
 * Please refer to Trie.TrieNode class for details of link problems to nodes.
 */
public interface IWordTrie {

    /**
     * Adds a new word with its associated weight to the Trie.
     * If the word contains an invalid character, simply do nothing.
     *
     * @param word the word to be added to the Trie
     */
    boolean addWord(String word, int problemID);

    /**
     * @param prefix the prefix
     * @return the root of the subTrie corresponding to the last character of
     * the prefix. If the prefix is not represented in the trie, return null.
     */
    TrieNode getSubTrie(String prefix);


    /**
     * @param prefix the prefix
     * @return the number of words that start with prefix.
     */
    int countPrefixes(String prefix);


    /**
     * Get the IDs of problems whose name contains a word with the given prefix.
     *
     * @param prefix the prefix of the word to search for
     * @return a List containing all the problems with name containing word
     * starting with the prefix. Return an empty list if there are none.
     */
    List<Integer> getProblemsByWord(String prefix);

    /**
     * Get the IDs of problems whose name contains words with the given prefixes.
     * This should return an intersection of the results of calling getProblemsByWord.
     *
     * @param prefixes the prefixes of the words to search for
     * @return a List containing all the problems with name containing all words
     * starting with given prefixes. Return an empty list if there are none.
     */
    List<Integer> getProblemsByWords(List<String> prefixes);

}