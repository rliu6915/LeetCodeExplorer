package Graph;

import ProblemDB.IProblemDB;
import ProblemDB.Problem;

import java.util.*;

/**
 * This class fills an unweighted graph where a vertex repertoire is a list of problems
 * and edges represent "similar question" relationships.
 */
public class GraphHandler {
    private ISimilarityGraph graph;
    private IProblemDB problemDB;
    private int numProblems;

    /**
     * Constructor.
     *
     * @param problemDB the problem database
     */
    public GraphHandler(IProblemDB problemDB) {
        this.graph = new SimilarityGraph(problemDB.getAllProblems().size() + 1);
        this.problemDB = problemDB;
        this.numProblems = problemDB.getAllProblems().size();
    }

    public ISimilarityGraph getGraph() {
        return graph;
    }

    public void setGraph(ISimilarityGraph graph) {
        this.graph = graph;
    }

    public IProblemDB getProblemDB() {
        return problemDB;
    }

    public void setProblemDB(IProblemDB problemDB) {
        this.problemDB = problemDB;
        this.graph = new SimilarityGraph(problemDB.getAllProblems().size() + 1);
        this.numProblems = problemDB.getAllProblems().size();
        buildGraph();
        System.out.println("Graph created/updated with new problem database!");
    }

    public int getNumProblems() {
        return numProblems;
    }

    public void setNumProblems(int numProblems) {
        this.numProblems = numProblems;
    }


    /**
     * Build the graph by adding edges between similar problems.
     * Should be called after the problem database is initialized.
     */
    public void buildGraph() {
        // Add edges between similar problems
        for (Problem problem : problemDB.getAllProblems()) {
            int fromId = problem.getId();
            Set<String> similarTitles = problem.getSimilarQuestions();
            for (String title : similarTitles) {
                Problem toProblem = problemDB.getProblemByTitle(title);
                if (toProblem != null) { // check if the title exists in the problemDB
                    int toId = toProblem.getId();
                    if (!graph.hasEdge(fromId, toId)) {
                        graph.addEdge(fromId, toId);
                        graph.addEdge(toId, fromId);
                    }
                }
            }
        }
    }


    /**
     * Get a list of similar problems using BFS.
     *
     * @param id          the id of the starting problem
     * @param numProblems the number of similar problems to be returned
     * @return a list of similar problems
     */
    public List<Problem> getSimilarProblems(int id, int numProblems) {
        int numEasy = numProblems / 3;
        int numMedium = numProblems / 3;
        int numHard = numProblems - numEasy - numMedium;
        return getSimilarProblems(id, numEasy, numMedium, numHard);
    }

    /**
     * Get a list of similar problems using BFS.
     *
     * @param id        the id of the starting problem
     * @param numEasy   the number of easy problems to be returned
     * @param numMedium the number of medium problems to be returned
     * @param numHard   the number of hard problems to be returned
     * @return a list of similar problems
     */
    public List<Problem> getSimilarProblems(int id, int numEasy,
                                            int numMedium, int numHard) {
        // Initialize the result list and the set of visited vertices
        List<Problem> result = new ArrayList<>();
        Set<Integer> visited = new HashSet<>();

        // Initialize the queue with the starting vertex
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(id);
        visited.add(id);

        // Initialize the counter variables for each difficulty level
        int easyCount = 0;
        int mediumCount = 0;
        int hardCount = 0;

        // Perform BFS until the queue is empty or the desired number of problems is found
        while (!queue.isEmpty()) {
            // Remove the next vertex from the queue
            int currentId = queue.poll();

            // Get the current problem
            Problem currentProblem = problemDB.getProblemById(currentId);

            // Update the counter variables for the current problem's difficulty level
            switch (currentProblem.getDifficulty()) {
                case 1 -> {
                    if (easyCount < numEasy) {
                        result.add(currentProblem);
                        easyCount++;
                    }
                }
                case 2 -> {
                    if (mediumCount < numMedium) {
                        result.add(currentProblem);
                        mediumCount++;
                    }
                }
                case 3 -> {
                    if (hardCount < numHard) {
                        result.add(currentProblem);
                        hardCount++;
                    }
                }
            }

            // Stop adding problems of a difficulty level if we have reached the quota
            if (easyCount >= numEasy && mediumCount >= numMedium
                && hardCount >= numHard) {
                break;
            }

            // Add the unvisited neighbors to the queue
            for (int neighbor : graph.neighbors(currentId)) {
                if (!visited.contains(neighbor)) {
                    queue.offer(neighbor);
                    visited.add(neighbor);
                }
            }
        }

        // Return the result list
        if (result.size() == numEasy + numMedium + numHard) {
            return result;
        } else {
            System.out.println("Not enough similar problems found, but returned what we got!");
            return result;
        }
    }

}
