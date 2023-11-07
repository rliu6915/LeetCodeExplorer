package Trie;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class TrieNodeTest {

    TrieNode t0;
    TrieNode t1;
    TrieNode t2;

    @Before
    public void setUp() {
        t0 = new TrieNode();
        t1 = new TrieNode("test");
        t2 = new TrieNode("test");
    }

    @Test
    public void testConstructor() {
        // case 1
        TrieNode t = new TrieNode();
        assertEquals("", t.getTerm());
        assertEquals(0, t.getProblemIDs().size());
        assertEquals(0, t.getNumPrefixes());
        assertEquals(36, t.getReferences().length);
        // case 2
        t = new TrieNode("test");
        assertEquals("test", t.getTerm());
        assertEquals(0, t.getProblemIDs().size());
        assertEquals(0, t.getNumPrefixes());
        assertEquals(36, t.getReferences().length);
    }

    @Test
    public void testGetTerm() {
        assertEquals("", t0.getTerm());
        assertEquals("test", t1.getTerm());
        assertEquals("test", t2.getTerm());
    }

    @Test
    public void testSetTerm() {
        assertEquals("", t0.getTerm());
        t0.setTerm("a");
        assertEquals("a", t0.getTerm());
        t0.setTerm("word");
        assertEquals("word", t0.getTerm());
    }

    @Test
    public void testGetProblemIDs() {
        assertEquals(0, t0.getProblemIDs().size());
        assertEquals(0, t1.getProblemIDs().size());
        assertEquals(0, t2.getProblemIDs().size());
    }

    @Test
    public void testSetProblemIDs() {
        assertEquals(0, t0.getProblemIDs().size());
        List<Integer> testIDs = new ArrayList<>();
        testIDs.add(1);
        testIDs.add(2);
        testIDs.add(3);
        t0.setProblemIDs(testIDs);
        assertEquals(3, t0.getProblemIDs().size());
    }

    @Test
    public void testAddProblem() {
        assertEquals(0, t0.getProblemIDs().size());
        t0.addProblem(1);
        assertEquals(1, t0.getProblemIDs().size());
        t0.addProblem(2);
        assertEquals(2, t0.getProblemIDs().size());
        t0.addProblem(3);
        assertEquals(3, t0.getProblemIDs().size());

        assertEquals(1, t0.getProblemIDs().get(0).intValue());
        assertEquals(2, t0.getProblemIDs().get(1).intValue());
        assertEquals(3, t0.getProblemIDs().get(2).intValue());
    }

    @Test
    public void testGetPrefixes() {
        assertEquals(0, t0.getNumPrefixes());
        assertEquals(0, t1.getNumPrefixes());
        assertEquals(0, t2.getNumPrefixes());
    }

    @Test
    public void testSetPrefixes() {
        assertEquals(0, t0.getNumPrefixes());
        t0.setNumPrefixes(8);
        assertEquals(8, t0.getNumPrefixes());
        t0.setNumPrefixes(1024);
        assertEquals(1024, t0.getNumPrefixes());
        t0.setNumPrefixes(999);
        assertEquals(999, t0.getNumPrefixes());
    }

    @Test
    public void testIncrementPrefixes() {
        assertEquals(0, t0.getNumPrefixes());
        t0.incrementPrefixes();
        assertEquals(1, t0.getNumPrefixes());
        t0.incrementPrefixes();
        assertEquals(2, t0.getNumPrefixes());
        t0.incrementPrefixes();
        assertEquals(3, t0.getNumPrefixes());
    }

    @Test
    public void testGetReferences() {
        assertEquals(36, t0.getReferences().length);
        assertEquals(36, t1.getReferences().length);
        assertEquals(36, t2.getReferences().length);
    }

    @Test
    public void testSetReferences() {
        TrieNode[] ref0 = t0.getReferences();
        assertEquals(36, ref0.length);
        assertNull(ref0[0]);
        assertNull(ref0[26]);
        TrieNode[] ref = new TrieNode[36];
        ref[0] = t1;
        ref[26] = t2;
        t0.setReferences(ref);
        assertEquals(t1, t0.getReferences()[0]);
        assertEquals(t2, t0.getReferences()[26]);
    }

}
