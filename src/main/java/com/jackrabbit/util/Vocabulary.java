package com.jackrabbit.util;

import java.util.HashMap;
import java.util.Set;
import java.util.Map;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Vocabulary class stores a mapping of vocab strings and their codes
 * Adding an entry to a Vocabulary object will automatically assign it a code
 * which is the index of the entry in the HashMap, starting at 0
 */
public class Vocabulary {
    // Only want to add to the vocabulary if the entry is not already there
    private HashMap<String, Integer> vocab;
    private int index;
    private int mergeOffset;

    // Special tokens
    private static final String BOT = "<|BOT|>"; // Beginning of text
    private static final String EOT = "<|EOT|>"; // End of text
    private static final String UNK = "<|UNK|>"; // Unknown token
    private static final String ENDL = "<|ENDL|>"; // End of line

    public Vocabulary() {
        this.vocab = new HashMap<>();
        this.index = 0;

        // Add the special tokens
        this.add(BOT);
        this.add(EOT);
        this.add(UNK);
        this.add(ENDL);

        // Add readable ASCII characters by default
        for (int i = 32; i < 127; i++) {
            this.add(Character.toString((char) i));
        }

        this.mergeOffset = this.index;
    }

    // Overload constructor to allow for adding tokens from a list
    public Vocabulary(String[] tokens) {
        this.vocab = new HashMap<>();
        this.index = 0;

        // Add the special tokens
        this.add(BOT);
        this.add(EOT);
        this.add(UNK);
        this.add(ENDL);

        // Add tokens
        for (String token : tokens) {
            this.add(token);
        }

        this.mergeOffset = this.index;
    }

    public void add(String token) {
        if (!this.vocab.containsKey(token)) {
            this.vocab.put(token, this.index);
            this.index++;
        }
    }

    public Integer get(String token) {
        if (this.vocab.containsKey(token)) {
            return this.vocab.get(token);
        } else {
            System.err.println("Token not found in vocabulary: " + token);
            return this.vocab.get(UNK);
        }
    }

    public Set<String> mergeSet() {
        return this.vocab.entrySet()
                .stream()
                .filter(ent -> ent.getValue() >= this.mergeOffset)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }

    public void toJson(String filename) {
        // TODO: Implement
    }

    public void loadFromJson(String filename) {
        // TODO: Implement
    }

    // Below methods are just wrappers for HashMap methods
    public Set<String> keySet() {
        return this.vocab.keySet();
    }

    public Collection<Integer> values() {
        return this.vocab.values();
    }

    public Set<Map.Entry<String, Integer>> entrySet() {
        return this.vocab.entrySet();
    }

    public boolean containsKey(String token) {
        return this.vocab.containsKey(token);
    }

    public void remove(String token) {
        this.vocab.remove(token);
    }

    public int size() {
        return this.vocab.size();
    }

    @Override
    public String toString() {
        return this.vocab.toString();
    }
}