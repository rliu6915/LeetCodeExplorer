package ProblemDB;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This class provides sorting methods according to different criteria.
 * It is separated from the database as part of the Visitor design pattern.
 */
public class ProblemSorter {
    // BY_FREQUENCY_DESC
    static Comparator<Problem> byFrequencyDescending = (o1, o2) -> {
        if (o1.getFrequency() > o2.getFrequency()) {
            return -1;
        } else if (o1.getFrequency() < o2.getFrequency()) {
            return 1;
        } else {
            return o1.getId() - o2.getId();
        }
    };
    // BY_RATING_DESC
    static Comparator<Problem> byRatingDescending =
        (o1, o2) -> o1.getRating() != o2.getRating() ?
            o2.getRating() - o1.getRating() : o1.getId() - o2.getId();
    // BY_DIFFICULTY_LEVEL_ASC
    static Comparator<Problem> byDifficultyLevelEasyToHard =
        (o1, o2) -> o1.getDifficulty() != o2.getDifficulty() ?
            o1.getDifficulty() - o2.getDifficulty() : o1.getId() - o2.getId();
    // BY_DIFFICULTY_LEVEL_DESC
    static Comparator<Problem> byDifficultyLevelHardToEasy =
        (o1, o2) -> o1.getDifficulty() != o2.getDifficulty() ?
            o2.getDifficulty() - o1.getDifficulty() : o1.getId() - o2.getId();
    // BY_ACCEPTANCE_DESC
    static Comparator<Problem> byAcceptanceRateDescending = (o1, o2) -> {
        if (o1.getAcceptance() > o2.getAcceptance()) {
            return -1;
        } else if (o1.getAcceptance() < o2.getAcceptance()) {
            return 1;
        } else {
            return o1.getId() - o2.getId();
        }
    };
    // BY_ACCEPTANCE_ASC
    static Comparator<Problem> byAcceptanceRateAscending = (o1, o2) -> {
        if (o1.getAcceptance() > o2.getAcceptance()) {
            return 1;
        } else if (o1.getAcceptance() < o2.getAcceptance()) {
            return -1;
        } else {
            return o1.getId() - o2.getId();
        }
    };

    /**
     * A universal sorting method to sort the list of problems with the specified comparator.
     * This method is parametrized to sort by different criteria.
     *
     * @param problems       the list of problems to be sorted
     * @param comparatorName the name of the comparator
     */
    public static void sortProblems(List<Problem> problems, ComparatorEnum comparatorName) {
        switch (comparatorName) {
            case BY_FREQUENCY_DESC -> problems.sort(byFrequencyDescending);
            case BY_RATING_DESC -> problems.sort(byRatingDescending);
            case BY_DIFFICULTY_LEVEL_ASC -> problems.sort(byDifficultyLevelEasyToHard);
            case BY_DIFFICULTY_LEVEL_DESC -> problems.sort(byDifficultyLevelHardToEasy);
            case BY_ACCEPTANCE_DESC -> problems.sort(byAcceptanceRateDescending);
            case BY_ACCEPTANCE_ASC -> problems.sort(byAcceptanceRateAscending);
        }
    }

    /**
     * Filter the list of problems by the difficulty level.
     *
     * @param originalList the original list of problems to be filtered
     * @param difficulty   the difficulty level to be filtered by
     */
    public static void filterByDifficulty
    (List<Problem> originalList, int difficulty) {
        originalList.removeIf(p -> p.getDifficulty() != difficulty);
    }

    /**
     * Filter the list of problems by the related topics.
     *
     * @param originalList  the original list of problems to be filtered
     * @param relatedTopics the set of topics to be filtered by
     */
    public static void filterByTopicsAND
    (List<Problem> originalList, Set<String> relatedTopics) {
        for (String topic : relatedTopics) {
            originalList.removeIf(p -> !p.getTopics().contains(topic.toLowerCase()));
        }
    }

    /**
     * Filter the list of problems by the related topics.
     *
     * @param originalList  the original list of problems to be filtered
     * @param relatedTopics the set of topics to be filtered by
     */
    public static void filterByTopicsOR
    (List<Problem> originalList, Set<String> relatedTopics) {
        Set<Problem> toBeRemoved = new HashSet<>();
        for (Problem p : originalList) {
            boolean atLeastOne = false;
            for (String topic : relatedTopics) {
                if (p.getTopics().contains(topic.toLowerCase())) {
                    atLeastOne = true;
                    break;
                }
            }
            if (!atLeastOne) {
                toBeRemoved.add(p);
            }
        }
        originalList.removeAll(toBeRemoved);
    }

    /**
     * Filter the list of problems by the companies.
     *
     * @param originalList the original list of problems to be filtered
     * @param companies    the set of companies to be filtered by
     */
    public static void filterByCompaniesAND
    (List<Problem> originalList, Set<String> companies) {
        for (String company : companies) {
            originalList.removeIf(p -> !p.getCompanies().contains(company.toLowerCase()));
        }
    }

    /**
     * Filter the list of problems by the companies.
     *
     * @param originalList the original list of problems to be filtered
     * @param companies    the set of companies to be filtered by
     */
    public static void filterByCompaniesOR
    (List<Problem> originalList, Set<String> companies) {
        Set<Problem> toBeRemoved = new HashSet<>();
        for (Problem p : originalList) {
            boolean atLeastOne = false;
            for (String company : companies) {
                if (p.getCompanies().contains(company.toLowerCase())) {
                    atLeastOne = true;
                    break;
                }
            }
            if (!atLeastOne) {
                toBeRemoved.add(p);
            }
        }
        originalList.removeAll(toBeRemoved);
    }
}
