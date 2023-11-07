package ProblemDB;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.*;

public class ProblemDBTest {

    private ProblemDB problemDB;
    private Problem problem1;
    private Problem problem2;
    private Problem problem3;

    @Before
    public void setUp() {
        problemDB = new ProblemDB();
        problem1 = new Problem(0, "Problem1");
        problem1.setTopics(new HashSet<>(Arrays.asList("Topic1", "Topic2")));
        problem2 = new Problem(1, "Problem2");
        problem2.setTopics(new HashSet<>(Arrays.asList("Topic1", "Topic3")));
        problem3 = new Problem(2, "Problem3");
        problem3.setTopics(new HashSet<>(Arrays.asList("Topic2", "Topic3")));

        problemDB.addProblem(problem1);
        problemDB.addProblem(problem2);
        problemDB.addProblem(problem3);
    }

    @Test
    public void testGetAllProblems() {
        List<Problem> allProblems = problemDB.getAllProblems();
        assertEquals(3, allProblems.size());
    }

    @Test
    public void testAddProblem() {
        Problem problem4 = new Problem(3, "Problem4");
        problem4.setTopics(new HashSet<>(List.of("Topic4")));
        problemDB.addProblem(problem4);

        assertEquals(problem4, problemDB.getProblemById(3));
        assertEquals(problem4, problemDB.getProblemByTitle("Problem4"));
    }

    @Test
    public void testGetProblemById() {
        assertEquals(problem1, problemDB.getProblemById(0));
        assertEquals(problem2, problemDB.getProblemById(1));
        assertNull(problemDB.getProblemById(5));
    }

    @Test
    public void testGetProblemByTitle() {
        assertEquals(problem1, problemDB.getProblemByTitle("Problem1"));
        assertEquals(problem3, problemDB.getProblemByTitle("Problem3"));
        assertNull(problemDB.getProblemByTitle("Problem5"));
    }

    @Test
    public void testGetProblemsByTopic() {
        Set<Problem> topic1Problems = problemDB.getProblemsByTopic("Topic1");
        assertTrue(topic1Problems.contains(problem1));
        assertTrue(topic1Problems.contains(problem2));
        assertEquals(2, topic1Problems.size());

        Set<Problem> topic3Problems = problemDB.getProblemsByTopic("Topic3");
        assertTrue(topic3Problems.contains(problem2));
        assertTrue(topic3Problems.contains(problem3));
        assertEquals(2, topic3Problems.size());

        Set<Problem> topic5Problems = problemDB.getProblemsByTopic("Topic5");
        assertTrue(topic5Problems.isEmpty());
    }

    @Test
    public void testGetProblems() {
        List<Integer> ids = Arrays.asList(0, 2);
        List<Problem> problems = problemDB.getProblems(ids);
        assertTrue(problems.contains(problem1));
        assertFalse(problems.contains(problem2));
        assertTrue(problems.contains(problem3));
        assertEquals(2, problems.size());
    }
}
