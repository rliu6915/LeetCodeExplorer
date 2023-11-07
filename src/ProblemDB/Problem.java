package ProblemDB;

import java.util.HashSet;
import java.util.Set;

/**
 * This class represents a problem in LeetCode.
 */
public class Problem {

    // private fields
    private int id;
    private String title;
    private String description;


    private double acceptance;
    private double frequency;

    private int rating;
    private int difficulty;
    private String url;
    private Set<String> companies;
    private Set<String> topics;

    // a list of similar questions (title)
    private Set<String> similarQuestions;

    public Problem() {  // for potential testing purposes
    }

    public Problem(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public Problem(int id, String title, Set<String> Similarity, int difficulty) {
        this.id = id;
        this.title = title;
        this.similarQuestions = Similarity;
        this.difficulty = difficulty;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAcceptance() {
        return acceptance;
    }

    public void setAcceptance(double acceptance) {
        this.acceptance = acceptance;
    }

    public double getFrequency() {
        return frequency;
    }

    public void setFrequency(double frequency) {
        this.frequency = frequency;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getDifficulty() {
        return difficulty;
    }

    /**
     * Set the difficulty level of this problem
     *
     * @param difficulty 1: easy, 2: medium, 3: hard
     */
    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public String getDifficultyStr() {
        return switch (difficulty) {
            case 1 -> "Easy";
            case 2 -> "Medium";
            case 3 -> "Hard";
            default -> "Unknown";
        };
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Get all the companies of this problem
     * Return a copy to avoid modification
     *
     * @return a set of the companies
     */
    public Set<String> getCompanies() {
        return companies == null ? null : new HashSet<>(companies);    // compatible with Java 8
        // return Set.copyOf(companies);   // Java 10
    }

    public void setCompanies(Set<String> companies) {
        this.companies = companies;
    }

    /**
     * Get all the topics of this problem
     * Return a copy to avoid modification
     *
     * @return a set of the topics
     */
    public Set<String> getTopics() {
        return topics == null ? null : new HashSet<>(topics);   // compatible with Java 8
        // return Set.copyOf(topics);  // Java 10
    }

    public void setTopics(Set<String> topics) {
        this.topics = topics;
    }


    public Set<String> getSimilarQuestions() {
        return similarQuestions;
    }

    public void setSimilarQuestions(Set<String> similarQuestions) {
        this.similarQuestions = similarQuestions;
    }

    /**
     * Get the details of this problem
     * @return a string of the details
     */
    public String getDetails() {
        return "--------------------------\n" +
            "ID: " + id + "\n" +
            "Title: " + title + "\n" +
            "Difficulty: " + getDifficultyStr() + "\n" +
            "URL: " + url + "\n" +
            "Frequency: " + frequency + "\n" +
            "Acceptance: " + acceptance + "\n" +
            "Rating: " + rating + "\n" +
            "Companies: " + companies + "\n" +
            "Topics: " + topics + "\n" +
            "Description:\n\t" + description.replace(
                    "\n", "\n\t") + "\n" +
            "Similar Questions: " + similarQuestions + "\n"
            + "--------------------------\n";
    }

    /**
     * Get the summary of this problem
     * @return a string of the summary
     */
    public String getSummary() {
        return id + "\t" + title + " (" + getDifficultyStr() + "): Frequency:"
                + frequency + ", Accept:" + acceptance + ", Rating:"
                + rating + ", URL:" + url;
    }


}
