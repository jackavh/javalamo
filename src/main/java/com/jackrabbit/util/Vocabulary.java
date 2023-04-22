package com.jackrabbit.util;

import java.util.HashMap;

public class Vocabulary {
    // Only want to add to the vocabulary if the entry is not already there
    private HashMap<String, Integer> vocab;
    private int index;

    public void add(String token) {
        if (!this.vocab.containsKey(token)) {
            this.vocab.put(token, this.index);
            this.index++;
        }
    }

    public Vocabulary() {
        this.vocab = new HashMap<>();
        this.index = 0;

        // Add the special tokens
        this.add("<|EOT|>"); // End of text
        this.add("<|UNK|>"); // Unknown token
        this.add("<|ENDL|>"); // End of line

        // Add readable ASCII characters
        for (int i = 32; i < 127; i++) {
            this.add(Character.toString((char) i));
        }
    }

    public Vocabulary(String[] tokens) {
        this.vocab = new HashMap<>();
        this.index = 0;

        // Add the special tokens
        this.add("<|EOT|>"); // End of text
        this.add("<|UNK|>"); // Unknown token
        this.add("<|ENDL|>"); // End of line

        // Add tokens
        for (String token : tokens) {
            this.add(token);
        }
    }

    // TODO add basic methods for getting the index of a token, getting the token
    // TODO Possibly add extends HashMap<String, Integer> to make it easier to use
    // TODO add method for getting the size of the vocabulary
}
