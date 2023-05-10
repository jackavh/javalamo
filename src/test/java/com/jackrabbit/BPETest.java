package com.jackrabbit;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.Before;
// Import beforeeach

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Unit test for byte pair encoding class
 */
public class BPETest {
    private BytePairEncoder encoder;

    @Before
    public void setup() {
        encoder = new BytePairEncoder("This is a test text.");
    }

    @Test
    public void testGetVocabSize() {
        assertEquals(140, encoder.getVocabSize());
    }

    @Test
    public void testSetVocabSize() {
        encoder.setVocabSize(150);
        assertEquals(150, encoder.getVocabSize());
    }

    @Test
    public void testGetText() {
        assertEquals("This is a test text.", encoder.getText());
    }

    @Test
    public void testSetText() {
        encoder.setText("New test text.");
        assertEquals("New test text.", encoder.getText());
    }

    @Test
    public void testInitializeVocabulary() {
        ArrayList<String> vocab = encoder.getVocabulary();
        assertEquals(95, vocab.size());
        assertEquals("A", vocab.get(65-32));
        assertEquals("B", vocab.get(66-32));
        assertEquals("C", vocab.get(67-32));
    }

    @Test
    public void testEncode() {
        if (encoder.getText().length() == 0) {
            encoder.setText("This is a test text.");
        }
        ArrayList<Integer> encoded = encoder.encode();
        System.out.println(encoded);
        assertEquals(true, encoded.size() > 0);
    }

    @Test
    public void testDecode() {
        if (encoder.getText().length() == 0) {
            encoder.setText("This is a test text.");
        }
        ArrayList<Integer> encoded = encoder.encode();
        String decoded = encoder.decode(encoded);
        assertEquals("This is a test text.", decoded);
    }

    @Test
    public void testEncodeDecode() {
        String originalText = "This is a more complex text for testing the encoding and decoding functionality.";
        BytePairEncoder complexEncoder = new BytePairEncoder(originalText);
        ArrayList<Integer> encoded = complexEncoder.encode();
        String decoded = complexEncoder.decode(encoded);
        assertEquals(originalText, decoded);
    }

    @Test
    public void testSerializeAndImportSerializedVocabulary() {
        String serializedVocab = encoder.serializeVocabulary();
        BytePairEncoder newEncoder = new BytePairEncoder("");
        newEncoder.importSerializedVocabulary(serializedVocab);
        assertEquals(encoder.getVocabulary(), newEncoder.getVocabulary());
    }
}
