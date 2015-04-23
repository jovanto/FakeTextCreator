package com.joelwan;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class to create a map of all word pairs with all the possibilities for the next word for each word pair.
 */

public class WordsIndexer {

    private Map<String, List<String>> index;
    Random rand = new Random();

    public WordsIndexer(String inputText) {
        index = new HashMap<String, List<String>>();
        String lowerCaseText = inputText.toLowerCase();
        this.index(lowerCaseText);
    }

    public String getRandomWordPair() {
        List<String> wordPairs = new ArrayList<String>();
        wordPairs.addAll(index.keySet());
        return wordPairs.get(rand.nextInt(wordPairs.size()));
    }

    /**
     * Takes a word pair as input and returns a random suggestion from the list
     * of possible next words
     *
     * @param key
     * @return next word
     */
    public String getRandomNextWord(String key) {
        if (index.containsKey(key)) {
            List<String> possibleNextWords = this.index.get(key);
            if (possibleNextWords != null && !possibleNextWords.isEmpty()) {
                return possibleNextWords.get(rand.nextInt(possibleNextWords.size()));
            }
        }
        return "";
    }

    public String generateNextKey(String key, String nextWord) {
        String[] keyWords = key.split(" ");
        String newKey = keyWords[1] + " " + nextWord;
        return newKey;
    }


    public Map<String, List<String>> getIndex() {
        return this.index;
    }

    private int randInt(int min, int max) {
        return rand.nextInt((max - min) + 1) + min;
    }

    protected void index(String input) {
        List<String> sentences = getSentences(input);
        for (String current : sentences) {
            addToIndex(current);
        }
    }

    /**
     * Splits a large block of text into separate sentences.
     */
    protected List<String> getSentences(String text) {
        String endOfSentenceRegex = "(?<!mr|mrs|dr|ms|Mr|Mrs|Dr|Ms)[\\.\\?\\!]+";
        final String[] lines = text.split(endOfSentenceRegex);
        return Arrays.asList(lines);
    }


    /**
     * Reads the inputString and adds word pairs from this string into the
     * word pair -> possibilities index
     *
     * @param inputString
     * @return the current index map
     */
    protected void addToIndex(String inputString) {
        List<String> words = getWords(inputString);

        for (int i = 0; i < words.size() - 1; i++) {
            String currentWordPair = words.get(i) + " " + words.get(i + 1);
            List<String> currentSuggestions = index.get(currentWordPair);

            //get all possible words coming after this word pair
            if (currentSuggestions == null) {
                index.put(currentWordPair, new ArrayList<String>());
            }
            String possibleNextWord = i + 2 <= words.size() - 1 ? words.get(i + 2) : null;
            if (possibleNextWord != null) {
                if (!index.get(currentWordPair).contains(currentWordPair)) {
                    index.get(currentWordPair).add(possibleNextWord);
                }
            }
        }
    }

    /**
     * Splits the original string into an array of words, removing punctuation, extra spaces etc.
     *
     * @param in
     * @return
     */
    private List<String> getWords(String in) {
        String wordRegex = "([\\w\\']+)";
        Pattern p = Pattern.compile(wordRegex);
        Matcher m = p.matcher(in);
        List<String> words = new ArrayList<>();
        while (m.find()) {
            words.add(m.group(1));
        }
        return words;
    }
}
