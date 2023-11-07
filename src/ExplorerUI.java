import ProblemDB.ComparatorEnum;
import ProblemDB.Problem;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import static ProblemDB.ProblemSorter.*;

/**
 * The user interface of the LeetCode Explorer.
 */
public class ExplorerUI {
    static Scanner in;

    /**
     * Main method of the ExplorerUI.
     *
     * @param args command line arguments - input dataset file
     */
    public static void main(String[] args) {
        // Parse the arguments
        if (args == null || args.length == 0 || args.length > 2) {
            System.out.println("Expected syntax: java ExplorerUI " +
                "<dataset file> [optional: <user progress file>]");
            return;
        }

        // initialization
        String datasetFile = args[0];
        try {
            Explorer.init(datasetFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // main loop
        run();
    }

    /**
     * The main loop of the ExplorerUI.
     */
    private static void run() {
        in = new Scanner(System.in);
        boolean running = true;
        System.out.println("Welcome to LeetCode Explorer!");
        while (running) {
            System.out.println("********** Menu **********");
            System.out.println("1. Search problems by title");
            System.out.println("2. Search problems by description");
            System.out.println("3. Generate a progressive plan of problems to solve");
            System.out.println("4. Filter and/or sort problems by topics, companies, frequency, etc.");
            String input = PrintUtils
                .askForInput(in, "Please enter a command id or 'q' to quit: ");
            // execute corresponding methods
            switch (input) {
                case "1" -> searchByName();
                case "2" -> searchByDescription();
                case "3" -> generatePlan();
                case "4" -> filterAndSort();
                case "q" -> running = false;
                default -> System.out.println("# Invalid input.");
            }
        }
    }


    /**
     * Search problems by name (potentially partial name)
     * The user will be prompted to enter a search term, get a list of problems as result,
     * then be asked if they want the detailed information of any problem,
     * or if they want to go back to the parent menu.
     */
    private static void searchByName() {
        String term = PrintUtils.askForInput(in, "Please enter a search term: ");
        List<Problem> results = Explorer.searchByName(term);
        if (results.isEmpty()) {
            System.out.println("Sorry, no results found.");
            return;
        }
        printResults(results);
        PrintUtils.askForInput(in, "Enter anything to go back to the main menu:");
    }

    /**
     * Search problems by description
     * The user will be prompted to enter a search term, get a list of problems as result,
     * then be asked if they want the detailed information of any problem,
     * or if they want to go back to the parent menu.
     */
    private static void searchByDescription() {
        String searchTerm = PrintUtils.askForInput(in, "Please enter a search term: ");
        List<Problem> results = Explorer.searchByDescription(searchTerm);
        printResults(results);
        PrintUtils.askForInput(in, "Enter anything to go back to the main menu:");
    }

    /**
     * Generate a progressive plan of problems to solve related to the target problem.
     * - User is expected to first get the id of a target problem by other methods,
     * Then the user will be asked if they want the detailed information of any problem,
     * or if they want to go back to the parent menu.
     */

    private static void generatePlan() {
        int id;

        while (true) {
            System.out.print("Please enter the target problem ID: ");
            if (in.hasNextInt()) {
                id = in.nextInt();
                if (Explorer.db.getProblemById(id) != null) {
                    break;
                }
            } else {
                in.next(); // Clear the invalid input
            }
            System.out.println("# Invalid ID or problem not found. " +
                    "Please enter a valid problem ID.");
        }

        System.out.println("Do you want to specify the number of " +
                "problems for each difficulty level? (yes/no)");
        String choice;
        while (true) {
            choice = in.next().toLowerCase();
            if (choice.equalsIgnoreCase("yes")
                || choice.equalsIgnoreCase("no")
                || choice.equalsIgnoreCase("y")
                || choice.equalsIgnoreCase("n")) {
                break;
            }
            System.out.println("# Invalid input. Please enter 'yes' or 'no'.");
        }
        List<Problem> results;

        if (choice.equals("yes") || choice.equals("y")) {
            int numEasy, numMedium, numHard;
            while (true) {
                System.out.print("Enter the number of easy problems you want: ");
                numEasy = getNextPositiveInt();
                System.out.print("Enter the number of medium problems you want: ");
                numMedium = getNextPositiveInt();
                System.out.print("Enter the number of hard problems you want: ");
                numHard = getNextPositiveInt();
                if (numEasy >= 0 && numMedium >= 0 && numHard >= 0) {
                    break;
                }
                System.out.println("# Invalid input. Please enter non-negative " +
                        "values for the number of problems.");
            }
            results = Explorer.generatePlan(id, numEasy, numMedium, numHard);
        } else {
            System.out.print("Enter the total number of problems you want: ");
            int numProblems = getNextPositiveInt();
            results = Explorer.generatePlan(id, numProblems);
        }

        printResults(results);
        PrintUtils.askForInput(in, "Enter anything to go back to the main menu:");
    }

    /**
     * Get the next positive integer from the user input
     *
     * @return the next positive integer
     */
    private static int getNextPositiveInt() {
        int num;
        while (true) {
            if (in.hasNextInt()) {
                num = in.nextInt();
                if (num >= 0) {
                    break;
                }
            } else {
                in.next(); // Clear the invalid input
            }
            System.out.println("Invalid input. Please enter a non-negative integer.");
        }
        return num;
    }

    /**
     * Filter or sort problems as user required
     */
    private static void filterAndSort() {
        List<Problem> problems = Explorer.getAllProblems();
        String reply = PrintUtils.askForInput(in,
            "Enter 'f' to filter, 's' to sort, or 'q' to return to menu: ");
        while (!reply.equalsIgnoreCase("q")) {
            if (reply.equalsIgnoreCase("f")) {
                filterResultsAndDisplay(problems);
            } else if (reply.equalsIgnoreCase("s")) {
                sortResultsAndDisplay(problems);
            } else {
                System.out.println("# Invalid input.");
            }
            reply = PrintUtils.askForInput(in,
                "Enter 'f' to filter, 's' to sort, or 'q' to return to menu: ");
        }
    }

    /**
     * Filter a list of problems by topics and/or companies and/or difficulty
     * as specified by the user,
     * then print them in a formatted way.
     * This method includes interaction with the user
     *
     * @param toBeFiltered the list of problems to be filtered
     */
    private static void filterResultsAndDisplay(List<Problem> toBeFiltered) {
        // This can be done with static methods in ProblemDB.ProblemSorter
        while (true) {
            String reply = PrintUtils.askForInput(in,
                "Enter 't' to filter by topics, 'c' to filter by companies, " +
                    "'d' to filter by difficulty, or 'q' to return to previous level: ");
            if (reply.equalsIgnoreCase("q")) {
                return;
            } else if (reply.equalsIgnoreCase("t")) {
                Set<String> topics = new HashSet<>();
                while (true) {
                    reply = PrintUtils.askForInput(in,
                        "Enter a topic, or 'q' to indicate the end of set: ");
                    if (reply.equalsIgnoreCase("q")) {
                        break;
                    }
                    topics.add(reply);
                }
                while (true) {
                    reply = PrintUtils.askForInput(in,
                        "Enter 'a' to get problems with all these topics" +
                            ", 'o' to get problems with at least one of these topics: ");
                    if (reply.equalsIgnoreCase("a")) {
                        filterByTopicsAND(toBeFiltered, topics);
                        break;
                    } else if (reply.equalsIgnoreCase("o")) {
                        filterByTopicsOR(toBeFiltered, topics);
                        break;
                    } else {
                        System.out.println("# Invalid input.");
                    }
                }
                printResults(toBeFiltered);
                return;
            } else if (reply.equalsIgnoreCase("c")) {
                Set<String> topics = new HashSet<>();
                while (true) {
                    reply = PrintUtils.askForInput(in,
                        "Enter a company name, or 'q' to indicate the end of set: ");
                    if (reply.equalsIgnoreCase("q")) {
                        break;
                    }
                    topics.add(reply);
                }
                while (true) {
                    reply = PrintUtils.askForInput(in,
                        "Enter 'a' to get problems asked by all these companies" +
                            ", 'o' to get problems asked by at least one of these companies: ");
                    if (reply.equalsIgnoreCase("a")) {
                        filterByCompaniesAND(toBeFiltered, topics);
                        break;
                    } else if (reply.equalsIgnoreCase("o")) {
                        filterByCompaniesOR(toBeFiltered, topics);
                        break;
                    } else {
                        System.out.println("# Invalid input.");
                    }
                }
                printResults(toBeFiltered);
                return;
            } else if (reply.equalsIgnoreCase("d")) {
                while (true) {
                    reply = PrintUtils.askForInput(in,
                        "Enter 'e' for easy problems, 'm' for medium problems, " +
                            "'h' to for hard problems: ");
                    int difficulty = -1;
                    switch (reply.toLowerCase()) {
                        case "e" -> difficulty = 1;
                        case "m" -> difficulty = 2;
                        case "h" -> difficulty = 3;
                        default -> System.out.println("# Invalid input.");
                    }
                    if (difficulty < 0) {
                        continue;
                    }
                    filterByDifficulty(toBeFiltered, difficulty);
                    printResults(toBeFiltered);
                    return;
                }
            } else {
                System.out.println("# Invalid input.");
            }
        }
    }

    /**
     * Sort a list of problems by criteria specified by the user,
     * then print them in a formatted way.
     * This method includes interaction with the user
     *
     * @param toBeSorted the list of problems to be sorted
     */
    private static void sortResultsAndDisplay(List<Problem> toBeSorted) {
        // This can be done with static methods in ProblemDB.ProblemSorter
        // provide an option to sort the results in different ways
        // while loop to display the results and prompt user for an input
        // (potential sorting criteria or exit)
        while (true) {
            String reply = PrintUtils.askForInput(in,
                "Enter 'f' to sort by frequency, 'r' to sort by rating, " +
                    "'d' to sort by difficulty, 'a' to sort by acceptance rate, " +
                    "or 'q' to return to previous level: ");
            ComparatorEnum comparator;
            if (reply.equalsIgnoreCase("q")) {
                return;
            } else if (reply.equalsIgnoreCase("f")) {
                comparator = ComparatorEnum.BY_FREQUENCY_DESC;
            } else if (reply.equalsIgnoreCase("r")) {
                comparator = ComparatorEnum.BY_RATING_DESC;
            } else if (reply.equalsIgnoreCase("d")) {
                while (true) {
                    reply = PrintUtils.askForInput(in,
                        "Enter 'A' to sort from easy to hard, " +
                            "'B' to sort from hard to easy: ");
                    if (reply.equalsIgnoreCase("A")) {
                        comparator = ComparatorEnum.BY_DIFFICULTY_LEVEL_ASC;
                        break;
                    } else if (reply.equalsIgnoreCase("B")) {
                        comparator = ComparatorEnum.BY_DIFFICULTY_LEVEL_DESC;
                        break;
                    } else {
                        System.out.println("# Invalid input.");
                    }
                }
            } else if (reply.equalsIgnoreCase("a")) {
                while (true) {
                    reply = PrintUtils.askForInput(in,
                        "Enter 'A' to sort in ascending order, " +
                            "'B' to sort in descending order:");
                    if (reply.equalsIgnoreCase("A")) {
                        comparator = ComparatorEnum.BY_ACCEPTANCE_ASC;
                        break;
                    } else if (reply.equalsIgnoreCase("B")) {
                        comparator = ComparatorEnum.BY_ACCEPTANCE_DESC;
                        break;
                    } else {
                        System.out.println("# Invalid input.");
                    }
                }
            } else {
                System.out.println("# Invalid input.");
                continue;
            }
            sortProblems(toBeSorted, comparator);
            printResults(toBeSorted);
        }
    }

    /**
     * Print results and ask user if they want to see the detailed information of any problem
     * (ask for an id, or a NO).
     * Print the detailed information if the user wants to,
     * and waits for the next input to go back to the list (print the list again).
     *
     * @param results the list of problems to be printed
     */
    private static void printResults(List<Problem> results) {
        while (true) {
            System.out.println("********** Results **********");
            PrintUtils.printProblems(results);
            String reply = PrintUtils.askForInput(in,
                "Enter the id of the problem you want to see the details of, or 'no':");
            if (reply.equalsIgnoreCase("no")
                || reply.equalsIgnoreCase("n")) {
                break;
            } else {
                int id;
                try {
                    id = Integer.parseInt(reply);
                } catch (NumberFormatException e) {
                    System.out.println("# Invalid input.");
                    continue;
                }
                Problem problem = Explorer.db.getProblemById(id);
                PrintUtils.printProblemDetails(problem);
                PrintUtils.askForInput(in, "Enter anything to go back to the results:");
            }
        }
    }
}
