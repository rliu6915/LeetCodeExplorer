package ProblemDB;

import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class ProblemTest {

    Problem a;
    Problem b;
    Problem c;

    @Before
    public void setUp() {
        a = new Problem(1, "Two Sum");
        b = new Problem(2, "Add Two Numbers");
        c = new Problem(3, "Longest Substring Without Repeating Characters");
    }

    @Test
    public void testConstructors() {
        Problem p = new Problem(1, "Two Sum");
        assertEquals(1, p.getId());
        assertEquals("Two Sum", p.getTitle());

        p = new Problem();
        assertEquals(0, p.getId());
        assertNull(p.getTitle());

        Set<String> Similarity = new HashSet<>();
        Similarity.add("Two Sum");
        Similarity.add("Add Two Numbers");
        Similarity.add("Longest Substring Without Repeating Characters");
        p = new Problem(1, "Two Sum", Similarity, 1);
        assertEquals(1, p.getId());
        assertEquals("Two Sum", p.getTitle());
        assertEquals(Similarity, p.getSimilarQuestions());
        assertEquals(1, p.getDifficulty());
    }

    @Test
    public void testGetAndSetId() {
        assertEquals(1, a.getId());
        a.setId(2);
        assertEquals(2, a.getId());
    }

    @Test
    public void testGetAndSetTitle() {
        assertEquals("Two Sum", a.getTitle());
        a.setTitle("Add Two Numbers");
        assertEquals("Add Two Numbers", a.getTitle());
    }

    @Test
    public void testGetAndSetDescription() {
        assertNull(a.getDescription());
        a.setDescription("Given an array of integers nums and an integer target, " +
            "return indices of the two numbers such that they add up to target.");
        assertEquals("Given an array of integers nums and an integer target, " +
            "return indices of the two numbers such that they add up to target.", a.getDescription());
    }

    @Test
    public void testGetAndSetDifficulty() {
        assertEquals(0, a.getDifficulty());
        a.setDifficulty(1);
        assertEquals(1, a.getDifficulty());
    }

    @Test
    public void testGetDifficultyStr() {
        assertEquals("Unknown", a.getDifficultyStr());
        a.setDifficulty(1);
        assertEquals("Easy", a.getDifficultyStr());
        a.setDifficulty(2);
        assertEquals("Medium", a.getDifficultyStr());
        a.setDifficulty(3);
        assertEquals("Hard", a.getDifficultyStr());
    }

    @Test
    public void testGetAndSetAcceptance() {
        assertEquals(0.0, a.getAcceptance(), 0.0);
        a.setAcceptance(55.5);
        assertEquals(55.5, a.getAcceptance(), 0.0);
    }

    @Test
    public void testGetAndSetFrequency() {
        assertEquals(0.0, a.getFrequency(), 0.0001);
        a.setFrequency(65.6);
        assertEquals(65.6, a.getFrequency(), 0.0001);
    }

    @Test
    public void testGetAndSetRating() {
        assertEquals(0, a.getRating());
        a.setRating(67);
        assertEquals(67, a.getRating());
    }

    @Test
    public void testGetAndSetUrl() {
        assertNull(a.getUrl());
        a.setUrl("https://leetcode.com/problems/two-sum/");
        assertEquals("https://leetcode.com/problems/two-sum/", a.getUrl());
    }

    @Test
    public void testGetAndSetTopics() {
        assertNull(a.getTopics());
        Set<String> topics = new HashSet<>();
        topics.add("Array");
        topics.add("Hash Table");
        a.setTopics(topics);
        assertEquals(2, a.getTopics().size());
        assertTrue(a.getTopics().contains("Array"));
        assertTrue(a.getTopics().contains("Hash Table"));
    }

    @Test
    public void testGetAndSetCompanies() {
        assertNull(a.getCompanies());
        Set<String> companies = new HashSet<>();
        companies.add("Amazon");
        companies.add("Google");
        a.setCompanies(companies);
        assertEquals(2, a.getCompanies().size());
        assertTrue(a.getCompanies().contains("Amazon"));
        assertTrue(a.getCompanies().contains("Google"));
    }

    @Test
    public void testGetAndSetSimilarQuestions() {
        assertNull(a.getSimilarQuestions());
        Set<String> similar = new HashSet<>();
        similar.add("Add Two Numbers");
        similar.add("Longest Substring Without Repeating Characters");
        a.setSimilarQuestions(similar);
        assertEquals(2, a.getSimilarQuestions().size());
        assertTrue(a.getSimilarQuestions().contains("Add Two Numbers"));
        assertTrue(a.getSimilarQuestions().
            contains("Longest Substring Without Repeating Characters"));
    }

    @Test
    public void testGetDetails() {
        a.setDifficulty(1);
        a.setAcceptance(55.5);
        a.setFrequency(65.6);
        a.setRating(67);
        a.setUrl("https://leetcode.com/problems/two-sum/");
        Set<String> topics = new HashSet<>();
        topics.add("Array");
        topics.add("Hash Table");
        a.setTopics(topics);
        Set<String> companies = new HashSet<>();
        companies.add("Amazon");
        companies.add("Google");
        a.setCompanies(companies);
        a.setDescription("Given an array of integers nums and an integer target, " +
            "return indices of the two numbers such that they add up to target.");
        Set<String> similar = new HashSet<>();
        similar.add("Add Two Numbers");
        similar.add("Longest Substring Without Repeating Characters");
        a.setSimilarQuestions(similar);
        assertEquals("""
            --------------------------
            ID: 1
            Title: Two Sum
            Difficulty: Easy
            URL: https://leetcode.com/problems/two-sum/
            Frequency: 65.6
            Acceptance: 55.5
            Rating: 67
            Companies: [Google, Amazon]
            Topics: [Array, Hash Table]
            Description:
            \tGiven an array of integers nums and an integer target, return indices of the two numbers such that they add up to target.
            Similar Questions: [Add Two Numbers, Longest Substring Without Repeating Characters]
            --------------------------
            """, a.getDetails());
    }

    @Test
    public void testGetSummary() {
        a.setDifficulty(1);
        a.setAcceptance(55.5);
        a.setFrequency(65.6);
        a.setRating(67);
        a.setUrl("https://leetcode.com/problems/two-sum/");
        assertEquals("1\tTwo Sum (Easy): Frequency:65.6, Accept:55.5, Rating:67, " +
            "URL:https://leetcode.com/problems/two-sum/", a.getSummary());
    }

}
