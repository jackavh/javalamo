package com.jackrabbit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class App 
{
    public static void main( String[] args )
    {
        BytePairEncoder bpe = new BytePairEncoder();
        ArrayList<String> splits = new ArrayList<>(Arrays.asList("ag", "ag", "be", "te", "ag"));
        System.out.println(bpe.computeFrequencies(splits));
    }    
}
