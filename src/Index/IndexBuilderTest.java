package Index;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class IndexBuilderTest {
    // please run test with coverage and make sure 80+% of lines are covered

    private List<String> feeds;
    private List<List<String>> problemToWords;

    @Before
    public void setUp() {
        feeds = Arrays.asList(
            "ProblemDB.Problem 1: Find ???????the sum of all ####natural numbers below 1000.",
            "ProblemDB.Problem 2: Find ///////:the sum of all fiNd finddddd "
                + "$$$$$$even Fibonacci numbers below 4 million."
        );

        problemToWords = Arrays.asList(
            Arrays.asList("problemdb", "problem", "1", "find", "the", "sum", "of", "all",
                "natural", "numbers", "below", "1000"),
            Arrays.asList("problemdb", "problem", "2", "find", "the", "sum", "of", "all", "find",
                "finddddd", "even", "fibonacci", "numbers", "below", "4", "million")
        );
    }

    @Test
    public void testParseFeed() {
        List<List<String>> expected = problemToWords;

        assertEquals(expected, IndexBuilder.parseFeed(feeds));
    }

    @Test
    public void testBuildIndex() {
        List<Map<String, Double>> index = IndexBuilder.buildIndex(problemToWords);
        assertEquals(2, index.size());
        assertTrue(index.get(0).containsKey("problem"));
        assertTrue(index.get(1).containsKey("fibonacci"));
    }

    @Test
    public void testBuildInvertedIndex() {
        List<Map<String, Double>> index = IndexBuilder.buildIndex(problemToWords);
        Map<String, List<Entry<Integer, Double>>> invertedIndex =
            IndexBuilder.buildInvertedIndex(index);

        assertTrue(invertedIndex.containsKey("problem"));
        assertTrue(invertedIndex.containsKey("fibonacci"));

        assertEquals(2, invertedIndex.get("problem").size());
        assertEquals(1, invertedIndex.get("fibonacci").size());

    }

    @Test
    public void testSearchProblems() {
        List<Map<String, Double>> index = IndexBuilder.buildIndex(problemToWords);
        Map<String, List<Entry<Integer, Double>>> invertedIndex =
            IndexBuilder.buildInvertedIndex(index);

        List<Integer> result1 = IndexBuilder.searchProblems("problem", invertedIndex);
        List<Integer> result2 = IndexBuilder.searchProblems("fibonacci", invertedIndex);
        List<Integer> result3 = IndexBuilder.searchProblems("nonexistent", invertedIndex);
        List<Integer> result4 = IndexBuilder.searchProblems("Find", invertedIndex);

        assertEquals(2, result1.size());
        assertEquals(1, result2.size());
        assertEquals(0, result3.size());
        assertEquals(2, result4.size());
    }

}
