package com.jackrabbit.util;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * BytePairEncoder
 * 
 * This class is responsible tokenization of a given text file.
 * Begins with pretokenization, then tokenization, then output.
 */
public class BytePairEncoder {
    /*
     * Encode Steps
     * 1. Calculate the frequency of each pair of characters in the text.
     * 2. Add the most frequent pair to the vocabulary.
     * 4. Repeat steps 1-3 until the desired vocabulary size is reached.
     * 5. Encode the text using the vocabulary from back to front.
     *      5a. Replace each pair of characters in the text with the corresponding vocabulary token.
     * 
     * Decode Steps
     * 1. Reverse step 5 of the encode steps
     * 2. Decode the text using the vocabulary from front to back.
     */
    private ArrayList<String> vocabulary;
    private ArrayList<String> encodedText;
    private String text;

    private int VOCAB_SIZE = 140;

    // -------------------------- GETTERS AND SETTERS --------------------------------
    // Getters and setters for VOCAB_SIZE
    public int getVocabSize() {
        return this.VOCAB_SIZE;
    }

    public void setVocabSize(int vocabSize) {
        this.VOCAB_SIZE = vocabSize;
    }

    // Getters and setters for text
    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    // Getters and setters for vocabulary
    public ArrayList<String> getVocabulary() {
        return this.vocabulary;
    }

    public void setVocabulary(ArrayList<String> vocabulary) {
        this.vocabulary = vocabulary;
    }

    // -------------------------- VOCABULARY METHODS --------------------------------
    // Serialize the vocabulary
    public String serializeVocabulary() {
        String serialized = "";
        for (String token : vocabulary) {
            serialized += token + "\n";
        }
        return serialized;
    }

    // Import a serialized vocabulary
    public void importSerializedVocabulary(String serialVocab) {
        String[] tokens = serialVocab.split("\n");
        // Make sure to clear the vocabulary first
        this.vocabulary = new ArrayList<String>();
        for (String token : tokens) {
            this.vocabulary.add(token);
        }
    }

    // Initialize ASCII into vocabulary
    private void initializeVocabulary() {
        for (int i = 32; i < 127; i++) {
            vocabulary.add(Character.toString((char) i));
        }
    }

    // -------------------------- ENCODING/DECODING METHODS --------------------------------
    public BytePairEncoder (String text) {
        this.text = text;
        this.vocabulary = new ArrayList<String>();

        // Populates vocab with ASCII characters
        initializeVocabulary();

        // Splits the text characterwise into encodedText
        this.encodedText = new ArrayList<String>();
        for (int i = 0; i < text.length(); i++) {
            this.encodedText.add(text.substring(i, i + 1));
        }
    }

    private String mostFrequentPair () {
        // Catch out of bounds
        if (encodedText.size() < 2) {
            return "";
        }

        // Records each pair and it's frequency
        Hashtable<String, Integer> pairFrequency = new Hashtable<String, Integer>();
        // Will end up being the most frequent pair. Start with first pair
        String frequentPair = encodedText.get(0) + encodedText.get(1);

        // Calculate frequencies of each pair of characters
        for (int i = 0; i < encodedText.size() - 1; i++) {
            String pair = encodedText.get(i) + encodedText.get(i + 1);
            if (pairFrequency.containsKey(pair)) {
                pairFrequency.put(pair, pairFrequency.get(pair) + 1);
            } else {
                pairFrequency.put(pair, 1);
            }
        }

        // Calculate the most frequent pair
        for (String pair : pairFrequency.keySet()) {
            if (pairFrequency.get(pair) > pairFrequency.get(frequentPair)) {
                frequentPair = pair;
            }
        }

        return frequentPair;
    }

    private void mergeStep() {
        String pair = mostFrequentPair();
        vocabulary.add(pair);

        // Replace all instances of the most frequent pair with the new vocabulary token
        for (int i = 0; i < encodedText.size() - 1; i++) {            String compPair = encodedText.get(i) + encodedText.get(i + 1);
            if (compPair.equals(pair)) {
                encodedText.set(i, pair);
                encodedText.remove(i + 1);
            }
        }
    }

    public ArrayList<Integer> encode() {
        // Repeat merge step until vocab size is reached or encodedText only has one element
        while (vocabulary.size() < VOCAB_SIZE ||
                encodedText.size() > 1) {
            mergeStep();
        }

        // Encode the text using the vocabulary from back to front
        ArrayList<Integer> encoded = new ArrayList<>();
        for (String token : encodedText) {
            encoded.add(vocabulary.indexOf(token));
        }

        return encoded;
    }
    
    public String decode(ArrayList<Integer> tokens) {
        // Decode the text using the vocabulary from front to back
        String decoded = "";
        for (int token : tokens) {
            decoded += vocabulary.get(token);
        }

        return decoded;
    } 
}