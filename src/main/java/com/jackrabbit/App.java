package com.jackrabbit;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

public class App 
{
    private static StringBuilder sb = new StringBuilder();

    public static String mostFrequentPair(List<String> splits) {
        HashMap<String, Integer> freqs = new HashMap<>();
        List<String> pairs = new ArrayList<>();
        for (int i = 0; i < splits.size() - 1; i++) {
            pairs.add(splits.get(i) + splits.get(i+1));
        }
        for (String pair : pairs) {
            if (freqs.containsKey(pair)) {
                freqs.put(pair, freqs.get(pair) + 1);
            } else {
                freqs.put(pair, 1);
            }
        }
        int maxFreq = 0;
        String maxFreqPair = "";
        for (Map.Entry<String, Integer> entry : freqs.entrySet()) {
            if (entry.getValue() > maxFreq) {
                maxFreq = entry.getValue();
                maxFreqPair = entry.getKey();
            }
        }

        return maxFreqPair;
    }

    public static List<String> mergeStep (List<String> splits, String pair) {
        List<String> newSplits = new ArrayList<>();
        for (int i = 0; i < splits.size() - 1; i++) {
            sb.setLength(0);
            sb.append(splits.get(i)).append(splits.get(i+1));
            if (sb.toString().equals(pair)) {
                newSplits.add(pair);
                i++;
            } else {
                newSplits.add(splits.get(i));
            }
        }

        return newSplits;
    }

    public static void main( String[] args )
    {
        String testString = "I ain't got no hugs, or pugs, she's a lug. With many slugs";
        List<String> splits = new ArrayList<>();
        for (int i = 0; i < testString.length(); i++) {
            if (i < testString.length() - 1) {
                splits.add(testString.substring(i, i+1));
            }
        }

        List<String> vocab = new ArrayList<>();
        vocab.add("<|endoftext|>");
        vocab.add("<|UNKNOWN|>");
        vocab.add("<|ENDL|>");
        for (int i = 32; i <= 126; i++) {
            vocab.add(Character.toString((char)i));
        }

        for (int i = 0; i < 10; i++) {
            String mfp = mostFrequentPair(splits);
            vocab.add(mfp);
            splits = mergeStep(splits, mfp);
        }

        System.out.println(vocab);
        System.out.println(testString);
        System.out.println(splits);
    }
}
