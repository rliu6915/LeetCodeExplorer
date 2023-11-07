import ProblemDB.Problem;

import java.util.List;
import java.util.Scanner;

/**
 * Utility class for printing.
 */
public class PrintUtils {

    /**
     * Print message and read inputs.
     *
     * @param message to be printed
     * @param sc      scanner
     * @return user input
     */
    static String askForInput(Scanner sc, String message) {
        System.out.println(message);
        return sc.nextLine();
    }

    /**
     * Print a single problem in a formatted way.
     * Only print the brief information of the problem:
     * <id>, <title>, <difficulty>, <url>.
     *
     * @param p problem to be printed
     */
    static void printProblemSummary(Problem p) {
        System.out.println(p.getSummary());
    }

    /**
     * Print a list of problems in a formatted way.
     *
     * @param problems list of problems to be printed
     */
    static void printProblems(List<Problem> problems) {
        for (Problem p : problems) {
            printProblemSummary(p);
        }
    }

    /**
     * Print detailed information of a problem in a formatted way.
     * Detailed information includes all the fields of a problem.
     *
     * @param p problem to be printed
     */
    static void printProblemDetails(Problem p) {
        System.out.println(p.getDetails());
    }
}
