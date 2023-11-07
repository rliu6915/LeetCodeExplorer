import ProblemDB.Problem;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class ExplorerTest {

    private int numProblems;

    @Before
    public void setUp() throws IOException {
        numProblems = Explorer.init("test.csv");
    }

    @Test
    public void testInit() {
        assertEquals(19, numProblems);
        assertNotNull(Explorer.db);
        assertNotNull(Explorer.gh);
        assertNotNull(Explorer.wordTrie);
        assertNotNull(Explorer.invertedIndex);
    }

    @Test
    public void testSearchByName() {
        List<Problem> problems = Explorer.searchByName("tWo");
        assertEquals(3, problems.size());
        assertTrue(problems.contains(Explorer.db.getProblemById(1)));
        assertTrue(problems.contains(Explorer.db.getProblemById(2)));
        assertTrue(problems.contains(Explorer.db.getProblemById(4)));

        problems = Explorer.searchByName("integer");
        assertEquals(4, problems.size());
        assertTrue(problems.contains(Explorer.db.getProblemById(7)));
        assertTrue(problems.contains(Explorer.db.getProblemById(8)));
        assertTrue(problems.contains(Explorer.db.getProblemById(12)));
        assertTrue(problems.contains(Explorer.db.getProblemById(13)));

        problems = Explorer.searchByName("to integer");
        assertEquals(3, problems.size());
        assertTrue(problems.contains(Explorer.db.getProblemById(8)));
        assertTrue(problems.contains(Explorer.db.getProblemById(12)));
        assertTrue(problems.contains(Explorer.db.getProblemById(13)));

    }

    @Test
    public void testSearchByDescription() {
        List<Problem> problems = Explorer.searchByDescription("LisT");
        assertEquals(2, problems.size());
        assertEquals(19, problems.get(0).getId());
        assertEquals(2, problems.get(1).getId());

        List<Problem> problemsOne = Explorer.searchByDescription("pAYPALISHIRING");
        assertEquals(1, problemsOne.size());
        assertEquals(6, problemsOne.get(0).getId());

        List<Problem> problemsTwo = Explorer.searchByDescription("lry");
        assertEquals(4, problemsTwo.size());
        assertEquals(4, problemsTwo.get(0).getId());
        assertEquals(2, problemsTwo.get(1).getId());
        assertEquals(3, problemsTwo.get(2).getId());
        assertEquals(1, problemsTwo.get(3).getId());
    }


    @Test
    public void testGeneratePlan() {
        List<Problem> problems = Explorer.generatePlan(1, 6);
        assertEquals(3, problems.size());
        problems = Explorer.generatePlan(1, 2, 1, 0);
        assertEquals(2, problems.size());
        assertEquals(1, problems.get(0).getId());
        assertEquals("3sum", problems.get(1).getTitle());
        assertEquals(15, problems.get(1).getId());
    }

    @Test
    public void testGetAllProblems() {
        List<Problem> problems = Explorer.getAllProblems();
        assertEquals(19, problems.size());
    }

}
