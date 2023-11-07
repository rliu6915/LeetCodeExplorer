package ProblemDB;

import java.util.List;
import java.util.Set;

/**
 * This class stores all the problems in the database and provides filtering methods.
 */
public interface IProblemDB {
    /**
     * Add a problem to the database and map its name to its id (sequential).
     * @param problem the problem to be added
     */
    void addProblem(Problem problem);

    /**
     * Get a problem by its id.
     * @param id the id of the problem
     * @return the problem with the given id
     */
    Problem getProblemById(int id);

    /**
     * Get a problem by its title.
     * @param title the title of the problem
     * @return the problem with the given title
     */
    Problem getProblemByTitle(String title);

    /**
     * Get a set of problems by their titles.
     * @param topic the topic of the problems
     * @return a set of problems with the given topic
     */
    Set<Problem> getProblemsByTopic(String topic);

    /**
     * Get a set of problems by their titles.
     * @param ids the ids of the problems
     * @return a set of problems with the given ids
     */
    List<Problem> getProblems(List<Integer> ids);

    /**
     * Get a set of problems
     * @return a set of all problems
     */
    List<Problem> getAllProblems();


}
