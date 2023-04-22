package com.jackrabbit.util;

import java.util.ArrayList;
import java.util.Hashtable;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * BytePairEncoder
 * 
 * This class is responsible tokenization of a given text file.
 * Begins with pretokenization, then tokenization, then output.
 */
public class BytePairEncoder {
    private final int DICT_SIZE = 200;
    private final int MAX_ITERATIONS = 1000;

    // Vocabulary of tokens
    private Hashtable<String, String> vocabulary = new Hashtable<>();

    public BytePairEncoder() {
        // Initialize vocabulary
        
    }

    // Out of a set of two character Strings, eg. "ab," find and return the most frequent pair
    public String computeFrequencies(ArrayList<String> splits) {
        Hashtable<String, Integer> frequencies = new Hashtable<>();

        // Sum frequencies of each pair
        for (String split : splits) {
            if (frequencies.containsKey(split)) {
                frequencies.put(split, frequencies.get(split) + 1);
            } else {
                frequencies.put(split, 1);
            }
        }

        // Compute max frequency
        int maxFrequency = 0;
        String maxFrequencyPair = "";
        for (String pair : frequencies.keySet()) {
            if (frequencies.get(pair) > maxFrequency) {
                maxFrequency = frequencies.get(pair);
                maxFrequencyPair = pair;
            }
        }

        return maxFrequencyPair;
    }

    public ArrayList<PreToken> loadPreTokens(String filePath) {
        // List of PreToken objects to be parsed from .txt file
        ArrayList<PreToken> preTokens = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            // Temporary holder of line data
            String line;
            String delimiter = " ";
            
            int currentIndex = 0;

            while ((line = br.readLine()) != null) {
                // Splits the line into an array list of words
                String[] words = line.split(delimiter);
                for (String word : words) {
                    // Creates a new PreToken object and adds it to the list
                    PreToken preToken = new PreToken(word, currentIndex, currentIndex + word.length()-1); // -1, dont count for space
                    preTokens.add(preToken);
                    currentIndex += word.length() + 1; // +1, count for space
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Debug
        for (PreToken preToken : preTokens) {
            System.out.println(preToken);
        }

        return preTokens;
    }
}
