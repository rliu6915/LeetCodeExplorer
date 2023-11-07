import ProblemDB.Problem;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;

import static org.junit.Assert.assertEquals;

public class PrintUtilsTest {

    Scanner input;
    ByteArrayOutputStream output;
    PrintStream originalOut;
    List<Problem> problems;

    @Before
    public void setUp() {
        output = new ByteArrayOutputStream();
        originalOut = System.out;
        System.setOut(new PrintStream(output));

        problems = new ArrayList<>();

        Problem a = new Problem(1, "Two Sum");
        a.setDifficulty(1);
        a.setAcceptance(22.2);
        a.setFrequency(22.2);
        a.setRating(66);
        a.setUrl("https://leetcode.com/problems/two-sum/");
        a.setDescription("""
            Given an array of integers nums and an integer target, return indices of the two numbers such that they add up to target.

            You may assume that each input would have exactly one solution, and you may not use the same element twice.

            You can return the answer in any order.

            \s

            Example 1:

            Input: nums = [2,7,11,15], target = 9
            Output: [0,1]
            Output: Because nums[0] + nums[1] == 9, we return [0, 1].
            Example 2:

            Input: nums = [3,2,4], target = 6
            Output: [1,2]
            Example 3:

            Input: nums = [3,3], target = 6
            Output: [0,1]
            \s

            Constraints:

            2 <= nums.length <= 104
            -109 <= nums[i] <= 109
            -109 <= target <= 109
            Only one valid answer exists.
            \s

            Follow-up: Can you come up with an algorithm that is less than O(n2) time complexity?

            Accepted
            4,731,212
            Submissions
            9,724,612""");
        Set<String> topics1 = new HashSet<>();
        topics1.add("Array");
        topics1.add("Hash Table");
        topics1.add("Two Pointers");
        topics1.add("Binary Search");
        topics1.add("Sort");
        topics1.add("Heap");
        a.setTopics(topics1);
        Set<String> companies1 = new HashSet<>();
        companies1.add("LinkedIn");
        companies1.add("Uber");
        companies1.add("Amazon");
        companies1.add("Facebook");
        companies1.add("Microsoft");
        companies1.add("Apple");
        companies1.add("Bloomberg");
        companies1.add("Yelp");
        companies1.add("Adobe");
        companies1.add("Yahoo");
        companies1.add("VMware");
        companies1.add("Morgan Stanley");
        a.setCompanies(companies1);
        problems.add(a);

        Problem b = new Problem(2, "Add Two Numbers");
        b.setDifficulty(2);
        b.setUrl("https://leetcode.com/problems/add-two-numbers/");
        problems.add(b);

        Problem c = new Problem(3, "Longest Substring Without Repeating Characters");
        c.setDifficulty(3);
        c.setUrl("https://leetcode.com/problems/longest-substring-without-repeating-characters/");
        problems.add(c);
    }

    @Test
    public void testAskForInput() {
        input = new Scanner(new ByteArrayInputStream("Charlie\n".getBytes()));
        String name = PrintUtils.askForInput(input, "Please enter your name: ");
        assertEquals("Please enter your name: \n", output.toString());
        assertEquals("Charlie", name);
    }

    @Test
    public void testPrintProblem() {
        Problem p = problems.get(0);
        String problemSummary = p.getSummary();
        String expectedOutput = "1\tTwo Sum (Easy): Frequency:22.2, Accept:22.2, Rating:66, URL:https://leetcode.com/problems/two-sum/";

        assertEquals(expectedOutput, problemSummary);
    }

    @Test
    public void testPrintProblems() {
        PrintUtils.printProblems(problems);
        assertEquals("""
            1\tTwo Sum (Easy): Frequency:22.2, Accept:22.2, Rating:66, URL:https://leetcode.com/problems/two-sum/
            2\tAdd Two Numbers (Medium): Frequency:0.0, Accept:0.0, Rating:0,\sURL:https://leetcode.com/problems/add-two-numbers/
            3\tLongest Substring Without Repeating Characters (Hard): Frequency:0.0, Accept:0.0, Rating:0,\sURL:https://leetcode.com/problems/longest-substring-without-repeating-characters/
            """, output.toString());
    }

    @Test
    public void testPrintProblemDetails() {
        Problem p = problems.get(0);
        System.setOut(originalOut);
        PrintUtils.printProblemDetails(p);
    }

}
