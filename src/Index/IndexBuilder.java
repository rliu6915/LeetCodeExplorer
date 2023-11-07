package Index;

import java.util.*;
import java.util.Map.Entry;

/**
 * This class is to build inverted index based on the descriptions of the problems,
 * to query the problems with terms appearing in the descriptions.
 * It is separated from the database as part of the Visitor design pattern.
 */
public class IndexBuilder {

    /**
     * Parse each feed (potentially the problem's description) in the list
     * and return a List of all the words in each feed, punctuation and special
     * characters removed.
     * The index of each feed is the same as the identification of the ProblemDB.Problem,
     * so there's no need for a Map, and we can reduce space complexity by using
     * a List instead.
     *
     * @param feeds a List of text feeds to parse
     * @return a 'map' of each problem (identified by the index) and the list of
     * words in its description.
     */
    public static List<List<String>> parseFeed(List<String> feeds) {
        List<List<String>> parseFeeds = new ArrayList<>();
        for (String feed : feeds) {
            // remove punctuation and special chars
            String feedCleaned = feed.
                replaceAll("[^a-zA-Z0-9\\s]", " ").toLowerCase();
            // split by whitespace 
            String[] splits = feedCleaned.split("\\s+");
            List<String> tokens = new ArrayList<>(Arrays.asList(splits));
            parseFeeds.add(tokens);
        }
        return parseFeeds;
    }

    /**
     * @param problemToWords computed by parseFeed()
     * @return the forward index: a 'map' of all problems and their
     * tags/keywords. the index is the ID of the problem, the value
     * is a map of a tag term and its TFIDF value.
     * The values (Map<String, Double>) are sorted
     * by lexicographic order on the key (tag term)
     */
    public static List<Map<String, Double>> buildIndex(
        List<List<String>> problemToWords) {

        List<Map<String, Double>> index = new ArrayList<>();
        for (List<String> words : problemToWords) {
            Map<String, Double> tfidfMap = calculateTfidf(words, problemToWords);
            index.add(new TreeMap<>(tfidfMap));
        }
        return index;
    }

    // helper method
    /**
     * Calculate tfidf
     * @param words: the list of words
     * @param problemToWords: the list of problems that contain words 
     * @return tfidf value
     */
    private static Map<String, Double> calculateTfidf(List<String> words,
                                                      List<List<String>> problemToWords) {
        Map<String, Double> tfidfMap = new HashMap<>();
        Map<String, Integer> wordCounts = new HashMap<>();
        for (String word : words) {
            wordCounts.put(word, wordCounts.getOrDefault(word, 0) + 1);
        }
        for (Map.Entry<String, Integer> entry : wordCounts.entrySet()) {
            String word = entry.getKey();
            int count = entry.getValue();
            // double tf = (double) count / (double) words.size();
            double tf = (double) count;
            // calculate number of documents with term t in it
            double countWithinTerm = 0;
            for (List<String> list : problemToWords) {
                if (list.contains(word)) {
                    countWithinTerm++;
                }
            }
            // idf
            double idf = Math.log((double) problemToWords.size() / countWithinTerm);
            tfidfMap.put(word, tf * idf);
        }
        return tfidfMap;
    }

    /**
     * Build an inverted index consisting of a map of each tag term and a Collection (Java)
     * of Entry objects mapping a problem ID with the TFIDF value of the term
     * (for that problem)
     * The Java collection (value) is sorted by reverse tag term TFIDF value
     * (the ID of the problem in which a term has the highest TFIDF should be listed first).
     *
     * @param index the index computed by buildIndex()
     * @return inverted index - a sorted Map of the problem IDs in which term is a keyword
     */
    public static Map<String, List<Entry<Integer, Double>>> buildInvertedIndex(
        List<Map<String, Double>> index) {
        Map<String, List<Entry<Integer, Double>>> invertedIndex = new TreeMap<>();
        for (int problemId = 0; problemId < index.size(); problemId++) {
            Map<String, Double> tfidfMap = index.get(problemId);
            for (Map.Entry<String, Double> e : tfidfMap.entrySet()) {
                String term = e.getKey();
                double tfif = e.getValue();
                // problem Id = index + 1
                Entry<Integer, Double> problemIdWithTfif =
                    new AbstractMap.SimpleEntry<>(problemId + 1, tfif);
                if (!invertedIndex.containsKey(term)) {
                    invertedIndex.put(term, new ArrayList<>());
                }
                invertedIndex.get(term).add(problemIdWithTfif);
            }
        }
        // collections sort based on the value of tdif ?
        for (List<Entry<Integer, Double>> l : invertedIndex.values()) {
            Collections.sort(l, (e1, e2) -> {
                int tfidfComparison = Double.compare(e2.getValue(), e1.getValue());
                if (tfidfComparison != 0) {
                    return tfidfComparison;
                } else {
                    return Integer.compare(e1.getKey(), e2.getKey());
                }
            });
        }
        return invertedIndex;
    }


    /**
     * @param queryTerm
     * @param invertedIndex
     * @return a list of IDs of problems in which the query term appears
     */
    public static List<Integer> searchProblems(
        String queryTerm,
        Map<String, List<Entry<Integer, Double>>> invertedIndex) {
        String termCleaned = queryTerm.toLowerCase();
        List<Integer> problemIds = new ArrayList<>();
        if (invertedIndex.containsKey(termCleaned)) {
            for (Entry<Integer, Double> entry : invertedIndex.get(termCleaned)) {
                problemIds.add(entry.getKey());
            }
        }
        return problemIds;
    }

}