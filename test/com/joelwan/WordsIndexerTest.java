package com.joelwan;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class WordsIndexerTest {

    private WordsIndexer wordsIndex;
    private String sampleText = "I wish I may I wish I might. " +
            "Mr. Jones, of the Manor Farm, had locked the hen-houses for the night, " +
            "but was too drunk to remember to shut the pop-holes. " +
            "With the ring of light from his lantern dancing from side to side, " +
            "he lurched across the yard, kicked off his boots at the back door, drew himself a " +
            "last glass of beer from the barrel in the scullery, and made his way up to bed, where Mrs. " +
            "Jones was already snoring. Remember to do something else";

    @Before
    public void setUp() throws Exception {
        wordsIndex = new WordsIndexer(this.sampleText);
    }

    @Test
    public void testGetSentences() throws Exception {
        List<String> sentences = wordsIndex.getSentences(sampleText);
        assertTrue(sentences.contains("I wish I may I wish I might"));
        assertTrue(sentences.contains(" Mr. Jones, of the Manor Farm, had locked the hen-houses for the night, but was too drunk to remember to shut the pop-holes"));
        assertTrue(sentences.contains(" With the ring of light from his lantern dancing from side to side, he lurched across the yard, kicked off his boots at the back door, drew himself a last glass of beer from the barrel in the scullery, and made his way up to bed, where Mrs. Jones was already snoring"));
    }

    @Test
    public void testGenerateNextKey() throws Exception {
        assertEquals("two three", wordsIndex.generateNextKey("one two", "three"));
    }


    @Test
    public void testIndexContents() throws Exception {
        Map<String, List<String>> index = wordsIndex.getIndex();
        assertSuggestionsContains(index, "remember to", "shut");
        assertSuggestionsContains(index, "i wish", "i");
        assertSuggestionsContains(index, "side to", "side");
        assertSuggestionsContains(index, "remember to", "shut");
        assertSuggestionsContains(index, "remember to", "do");
        assertSuggestionsContains(index, "mr jones", "of");
    }

    private void assertSuggestionsContains(Map<String, List<String>> index, String wordPair, String suggestion) {
        assertTrue(index.get(wordPair).contains(suggestion));
    }
}