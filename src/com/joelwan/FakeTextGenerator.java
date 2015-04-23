package com.joelwan;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FakeTextGenerator {


    /**
     * Main entry method
     * Takes a file name, reads the content, generates the fake text and returns the resulting output string
     * @param filePath
     * @return
     */
    public String generateSentence(String filePath) {
        String content = getFileContent(filePath);

        //index content
        WordsIndexer wordsIndexer = new WordsIndexer(content);
        if(wordsIndexer.getIndex().size() < 1) {
            return "";   //return nothing if there is no index.
        }

        String key = wordsIndexer.getRandomWordPair();

        String nextWord = wordsIndexer.getRandomNextWord(key);
        StringWriter sw = new StringWriter().append(key);

        int wordCount = 2; //They key contains 2 words
        while (nextWord != null && !nextWord.isEmpty() && wordCount < 50){
            sw.append(" ").append(nextWord);
            wordCount++;
            key = wordsIndexer.generateNextKey(key, nextWord);
            nextWord = wordsIndexer.getRandomNextWord(key);
        }
        return sw.toString();
    }



    protected static String getFileContent(String filePath) {
        String content = null;
        try {
            content = new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    public static  void main(String[] args){
        if(args.length < 1){
            System.out.println("Usage : FakeTextGenerator [filename]");
            System.exit(1);
        }
        String filePath = args[0];
        FakeTextGenerator fakeTextGenerator = new FakeTextGenerator();
        String sentence = fakeTextGenerator.generateSentence(filePath);
        System.out.println(sentence);
    }
}
