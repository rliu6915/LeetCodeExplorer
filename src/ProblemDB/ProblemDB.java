package ProblemDB;

import java.util.*;

public class ProblemDB implements IProblemDB {
    // store problems by id
    private HashMap<Integer, Problem> problemsById;
    // store problems by title
    private HashMap<String, Integer> problemsByTitle;
    // store problems by topic
    private HashMap<String, Set<Problem>> problemsByTopic;

    public ProblemDB() {
        problemsById = new HashMap<>();
        problemsByTitle = new HashMap<>();
        problemsByTopic = new HashMap<>();
    }

    public List<Problem> getAllProblems() {
        return new ArrayList<>(problemsById.values());
    }

    /**
     * Add a problem to the database and map its name to its id (sequential).
     * @param problem the problem to be added
     */
    @Override
    public void addProblem(Problem problem) {
        // Add problem to problemsById
        int id = problem.getId();
        problemsById.put(id, problem);

        // Add problem to problemsByTitle
        problemsByTitle.put(problem.getTitle(), problem.getId());

        // Add problem to problemsByTopic
        if (problem.getTopics() == null) {
            return;
        }
        for (String topic : problem.getTopics()) {
            problemsByTopic.putIfAbsent(topic, new HashSet<>());
            problemsByTopic.get(topic).add(problem);
        }
    }

    /**
     * Get a problem by its id.
     * @param id the id of the problem
     * @return the problem
     */
    @Override
    public Problem getProblemById(int id) {
        // Retrieve problem by ID from problemsById
        return problemsById.get(id);
    }

    /**
     * Get a problem by its title.
     * @param title the title of the problem
     * @return the problem
     */
    @Override
    public Problem getProblemByTitle(String title) {
        // Retrieve problem by title from problemsByTitle
        int id = problemsByTitle.getOrDefault(title, -1);
        return id >= 0 ? getProblemById(id) : null;
    }

    /**
     * Get a set of problems by topic.
     * @param topic the topic of the problem
     * @return a set of problems
     */
    @Override
    public Set<Problem> getProblemsByTopic(String topic) {
        // Retrieve problems by topic from problemsByTopic
        return problemsByTopic.getOrDefault(topic, new HashSet<>());
    }


    /**
     * Get a list of problems by a list of IDs.
     * @param ids a list of IDs
     * @return a list of problems
     */
    @Override
    public List<Problem> getProblems(List<Integer> ids) {
        // Retrieve problems by a list of IDs
        List<Problem> result = new ArrayList<>();
        for (Integer id : ids) {
            Problem problem = getProblemById(id);
            if (problem != null) {
                result.add(problem);
            }
        }
        return result;
    }
}
