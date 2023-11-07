package Trie;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class WordTrieTest {

    WordTrie a;

    @Before
    public void setUp() {
        a = new WordTrie();
        a.addWord("apple", 3);
        a.addWord("apply", 6);
        a.addWord("adobe", 4);
        a.addWord("pineapple", 5);
    }

    @Test
    public void testAddWord() {
        // case 1
        assertEquals(1, a.getSubTrie("apply").getProblemIDs().size());
        assertTrue(a.getSubTrie("apply").getProblemIDs().contains(6));
        a.addWord("apply", 9);
        assertEquals(2, a.getSubTrie("apply").getProblemIDs().size());
        assertTrue(a.getSubTrie("apply").getProblemIDs().contains(9));
        // case 2
        assertEquals(0, a.getSubTrie("app").getProblemIDs().size());
        a.addWord("app", 23);
        assertEquals(1, a.getSubTrie("app").getProblemIDs().size());
        assertTrue(a.getSubTrie("app").getProblemIDs().contains(23));
        // case 3
        assertNull(a.getSubTrie("2"));
        assertTrue(a.addWord("20", 23));
        assertEquals(0, a.getSubTrie("2").getProblemIDs().size());
        assertEquals(1, a.getSubTrie("2").getNumPrefixes());
        // case invalid
        assertFalse(a.addWord("0%m", 23));
    }

    @Test
    public void testGetSubTrie() {
        TrieNode subRoot = a.getSubTrie("app");
        assertEquals("app", subRoot.getTerm());
        assertEquals(0, subRoot.getProblemIDs().size());
        assertEquals(2, subRoot.getNumPrefixes());
        assertEquals("appl", subRoot.getReferences()['l' - 'a'].getTerm());
        assertEquals("apply", subRoot.getReferences()['l' - 'a']
            .getReferences()['y' - 'a'].getTerm());
        assertEquals("apple", subRoot.getReferences()['l' - 'a']
            .getReferences()['e' - 'a'].getTerm());
        assertNull(subRoot.getReferences()[0]);
        // case not found
        assertNull(a.getSubTrie("abc"));
        // case invalid
        assertNull(a.getSubTrie("abc*0"));
    }

    @Test
    public void testCountPrefixes() {
        assertEquals(3, a.countPrefixes("a"));
        assertEquals(2, a.countPrefixes("appl"));
        assertEquals(1, a.countPrefixes("p"));
        assertEquals(1, a.countPrefixes("pineapple"));
        // case invalid
        assertEquals(-1, a.countPrefixes("abc_"));
    }

    @Test
    public void testGetProblemsByWord() {
        a.addWord("app", 11);
        List<Integer> lis = a.getProblemsByWord("app");
        assertEquals(3, lis.size());
        assertTrue(lis.contains(3));
        assertTrue(lis.contains(6));
        assertTrue(lis.contains(11));
        assertEquals(11, lis.get(0).intValue());
        assertEquals(3, lis.get(1).intValue());
        assertEquals(6, lis.get(2).intValue());
        // case invalid
        assertEquals(0, a.getProblemsByWord("ap&").size());
    }

    @Test
    public void testGetProblemsByWords() {
        a.addWord("apple", 5);
        a.addWord("pie", 3);

        List<String> term = List.of("apple", "pie");
        List<Integer> lis = a.getProblemsByWords(term);
        assertEquals(1, lis.size());
        assertEquals(3, lis.get(0).intValue());

        term = List.of("apple", "pi");
        lis = a.getProblemsByWords(term);
        assertEquals(2, lis.size());
        assertTrue(lis.contains(3));
        assertTrue(lis.contains(5));
    }
}
