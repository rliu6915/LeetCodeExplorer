package ProblemDB;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class ProblemSorterTest {

    List<Problem> problems;

    @Before
    public void setUp() {
        problems = new ArrayList<>();
        Problem a = new Problem(1, "a");
        a.setDifficulty(2);
        a.setAcceptance(22.2);
        a.setFrequency(22.2);
        a.setRating(66);
        Set<String> topics1 = new HashSet<>();
        topics1.add("array");
        topics1.add("hash table");
        a.setTopics(topics1);
        Set<String> companies1 = new HashSet<>();
        companies1.add("microsoft");
        companies1.add("facebook");
        a.setCompanies(companies1);
        problems.add(a);

        Problem b = new Problem(2, "b");
        b.setDifficulty(1);
        b.setAcceptance(11.1);
        b.setFrequency(11.1);
        b.setRating(33);
        Set<String> topics2 = new HashSet<>();
        topics2.add("array");
        topics2.add("list");
        b.setTopics(topics2);
        Set<String> companies2 = new HashSet<>();
        companies2.add("amazon");
        companies2.add("microsoft");
        b.setCompanies(companies2);
        problems.add(b);

        Problem c = new Problem(3, "c");
        c.setDifficulty(3);
        c.setAcceptance(33.3);
        c.setFrequency(33.3);
        c.setRating(99);
        Set<String> topics3 = new HashSet<>();
        topics3.add("graph");
        topics3.add("tree");
        c.setTopics(topics3);
        Set<String> companies3 = new HashSet<>();
        companies3.add("facebook");
        companies3.add("google");
        c.setCompanies(companies3);
        problems.add(c);
    }

    @Test
    public void testSortByFrequencyDesc() {
        ProblemSorter.sortProblems(problems, ComparatorEnum.BY_FREQUENCY_DESC);
        assertEquals(3, problems.get(0).getId());
        assertEquals(1, problems.get(1).getId());
        assertEquals(2, problems.get(2).getId());
    }

    @Test
    public void testSortByRatingDesc() {
        ProblemSorter.sortProblems(problems, ComparatorEnum.BY_RATING_DESC);
        assertEquals(3, problems.get(0).getId());
        assertEquals(1, problems.get(1).getId());
        assertEquals(2, problems.get(2).getId());
    }

    @Test
    public void testSortByDifficultyLevelAsc() {
        ProblemSorter.sortProblems(problems, ComparatorEnum.BY_DIFFICULTY_LEVEL_ASC);
        assertEquals(2, problems.get(0).getId());
        assertEquals(1, problems.get(1).getId());
        assertEquals(3, problems.get(2).getId());
    }

    @Test
    public void testSortByDifficultyLevelDesc() {
        ProblemSorter.sortProblems(problems, ComparatorEnum.BY_DIFFICULTY_LEVEL_DESC);
        assertEquals(3, problems.get(0).getId());
        assertEquals(1, problems.get(1).getId());
        assertEquals(2, problems.get(2).getId());
    }

    @Test
    public void testSortByAcceptanceDesc() {
        ProblemSorter.sortProblems(problems, ComparatorEnum.BY_ACCEPTANCE_DESC);
        assertEquals(3, problems.get(0).getId());
        assertEquals(1, problems.get(1).getId());
        assertEquals(2, problems.get(2).getId());
    }

    @Test
    public void testSortByAcceptanceAsc() {
        ProblemSorter.sortProblems(problems, ComparatorEnum.BY_ACCEPTANCE_ASC);
        assertEquals(2, problems.get(0).getId());
        assertEquals(1, problems.get(1).getId());
        assertEquals(3, problems.get(2).getId());
    }

    @Test
    public void testFilterByDifficulty() {
        ProblemSorter.filterByDifficulty(problems, 2);
        assertEquals(1, problems.size());
        assertEquals(1, problems.get(0).getId());
    }

    @Test
    public void testFilterByTopicsAND() {
        Set<String> targetTopics = new HashSet<>();
        targetTopics.add("array");
        targetTopics.add("hash table");
        ProblemSorter.filterByTopicsAND(problems, targetTopics);
        assertEquals(1, problems.size());
        assertEquals(1, problems.get(0).getId());
    }

    @Test
    public void testFilterByTopicsOR() {
        Set<String> targetTopics = new HashSet<>();
        targetTopics.add("array");
        targetTopics.add("hash table");
        ProblemSorter.filterByTopicsOR(problems, targetTopics);
        assertEquals(2, problems.size());
        assertEquals(1, problems.get(0).getId());
        assertEquals(2, problems.get(1).getId());
    }

    @Test
    public void testFilterByCompaniesAND() {
        Set<String> targetCompanies = new HashSet<>();
        targetCompanies.add("google");
        targetCompanies.add("facebook");
        ProblemSorter.filterByCompaniesAND(problems, targetCompanies);
        assertEquals(1, problems.size());
        assertEquals(3, problems.get(0).getId());
    }

    @Test
    public void testFilterByCompaniesOR() {
        Set<String> targetCompanies = new HashSet<>();
        targetCompanies.add("google");
        targetCompanies.add("facebook");
        ProblemSorter.filterByCompaniesOR(problems, targetCompanies);
        assertEquals(2, problems.size());
        assertEquals(1, problems.get(0).getId());
        assertEquals(3, problems.get(1).getId());
    }
}
