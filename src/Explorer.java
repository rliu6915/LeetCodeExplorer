import Graph.GraphHandler;
import ProblemDB.IProblemDB;
import ProblemDB.Problem;
import ProblemDB.ProblemDB;
import Trie.IWordTrie;
import Trie.WordTrie;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static ProblemDB.ComparatorEnum.BY_DIFFICULTY_LEVEL_ASC;
import static ProblemDB.ProblemSorter.sortProblems;

/**
 * Explorer class that provides the main interface for users to interact with the database.
 */
public class Explorer {

    static IProblemDB db;
    static Map<String, List<Entry<Integer, Double>>> invertedIndex;
    static GraphHandler gh;
    static IWordTrie wordTrie;


    /**
     * Process the dataset to build the database, index, graph, and trie
     *
     * @param datasetFile the path to the dataset file
     * @return the total number of problems
     * @throws IOException if the file is not found or cannot be read
     */
    public static int init(String datasetFile) throws IOException {
        db = new ProblemDB();
        wordTrie = new WordTrie();
        int id = 0;
        // descriptions
        List<String> descriptions = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(datasetFile))) {
            // Skip the first line of column names
            reader.readNext();

            Pattern simPattern = Pattern.compile("\\[(.*?),");
            String[] values;
            while ((values = reader.readNext()) != null) {
                ++id;   // save the trouble parsing an int
                List<String> columns = Arrays.asList(values);

                // Feed IndexBuilder
                // description
                String description = columns.get(2);
                // truncate the description at the first occurrence of a newline
                int emptyLineIndex = description.indexOf("\n\n");
                if (emptyLineIndex != -1) {
                    description = description.substring(0, emptyLineIndex);
                }
                descriptions.add(description);

                // Build word trie
                // get title
                String title = columns.get(1).toLowerCase();
                String[] words = title.split(" ");
                // add each word in the title to the trie
                for (String word : words) {
                    wordTrie.addWord(word, id);
                }

                // Build problem and add to database
                // get difficulty
                String difficulty = columns.get(4);
                int difficultyInt = 1;
                switch (difficulty) {
                    case "Easy":
                        break;
                    case "Medium":
                        difficultyInt = 2;
                        break;
                    case "Hard":
                        difficultyInt = 3;
                        break;
                    default:
                        System.out.println("Invalid difficulty level: " + difficulty);
                }
                // get and parse similar questions
                String similarQuestions = columns.get(18);
                Set<String> similarQuestionsTitles = new HashSet<>();
                Matcher matcher = simPattern.matcher(similarQuestions);
                while (matcher.find()) {
                    similarQuestionsTitles.add(matcher.group(1).trim().toLowerCase());
                }
                // get and parse companies
                String companies = columns.get(12);
                Set<String> companiesSet = new HashSet<>();
                String[] companiesArray = companies.split(",");
                for (String company : companiesArray) {
                    companiesSet.add(company.trim().toLowerCase());
                }
                // get and parse topics
                String topics = columns.get(13);
                Set<String> topicsSet = new HashSet<>();
                String[] topicsArray = topics.split(",");
                for (String topic : topicsArray) {
                    topicsSet.add(topic.trim().toLowerCase());
                }
                // construct problem
                Problem problem = new Problem(id, title, similarQuestionsTitles, difficultyInt);
                problem.setTitle(title);
                problem.setDescription(description);
                problem.setDifficulty(difficultyInt);
                problem.setUrl(columns.get(8));
                problem.setCompanies(companiesSet);
                problem.setTopics(topicsSet);
                try {
                    problem.setAcceptance(Double.parseDouble(columns.get(6)));
                    problem.setFrequency(Double.parseDouble(columns.get(7)));
                    problem.setRating(Integer.parseInt(columns.get(16)));
                } catch (NumberFormatException e) {
                    continue;   // drop the problem if data is corrupted
                }
                // add to database
                db.addProblem(problem);
            }
            // Close the CSVReader
            reader.close();

            // Build index
            List<List<String>> parseFeeds = Index.IndexBuilder.parseFeed(descriptions);
            List<Map<String, Double>> index = Index.IndexBuilder.buildIndex(parseFeeds);
            invertedIndex = Index.IndexBuilder.buildInvertedIndex(index);

            // Build graph
            gh = new GraphHandler(db);
            gh.buildGraph();

        } catch (CsvValidationException e) {
            e.printStackTrace();
        }
        return id;
    }

    /**
     * Search for problems by name, retrieve Problem objects from the database
     *
     * @param term the search term
     * @return a list of problems that match the search term
     */
    public static List<Problem> searchByName(String term) {
        // utilize the trie built in init()
        String[] words = term.toLowerCase().split(" ");
        List<Integer> ids = wordTrie.getProblemsByWords(Arrays.asList(words));
        // retrieve the problems from the database with IDs
        return db.getProblems(ids);
    }

    /**
     * Search for problems by description, retrieve Problem objects from the database
     *
     * @param term the search term
     * @return a list of problems that match the search term
     */
    public static List<Problem> searchByDescription(String term) {
        // utilize the inverted index built in init()
        List<Integer> ids = Index.IndexBuilder.searchProblems(term, invertedIndex);
        // retrieve the problems from the database
        return db.getProblems(ids);
    }

    /**
     * Generate a plan based on similar problems
     *
     * @param id  the id of the problem
     * @param num the number of similar problems to be returned
     * @return a list of similar problems
     */
    public static List<Problem> generatePlan(int id, int num) {
        List<Problem> res = gh.getSimilarProblems(id, num);
        sortProblems(res, BY_DIFFICULTY_LEVEL_ASC);
        return res;
    }

    /**
     * Generate a plan of problems based on the given problem ID
     * and the number of problems of each difficulty level
     * @param id the problem ID
     * @param numEasy the number of easy problems
     * @param numMedium the number of medium problems
     * @param numHard the number of hard problems
     * @return a list of problems
     */
    public static List<Problem> generatePlan(int id, int numEasy,
                                             int numMedium, int numHard) {
        List<Problem> res = gh.getSimilarProblems(id, numEasy, numMedium, numHard);
        sortProblems(res, BY_DIFFICULTY_LEVEL_ASC);
        return res;
    }

    /**
     * Get all problems in the database
     * @return a list of all problems
     */
    public static List<Problem> getAllProblems() {
        // return a copy of the list of problems,
        // to avoid modifying the original list
        return new ArrayList<>(db.getAllProblems());
    }
}
