package com.jackrabbit;

import com.jackrabbit.util.BytePairEncoder;
import com.jackrabbit.util.Batch;
import com.jackrabbit.util.Pair;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

import java.util.ArrayList;

import org.nd4j.autodiff.samediff.SDVariable;
import org.nd4j.autodiff.samediff.SameDiff;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.api.buffer.DataType;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.indexing.NDArrayIndex;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;

public class Prototype {

    // Loads a string from a .txt file
    public static String loadStringFromFile(String path) {
        try {
            return new String(Files.readAllBytes(Paths.get(path)));
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
    
    // Generates a random array of integers, int[size] with values in [min, max)
    public static int[] generateRandomArray(int size, int min, int max) {
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = Nd4j.getRandom().nextInt(min, max);
        }
        return arr;
    }

    public static Pair<INDArray, INDArray> getBatch(INDArray data) {
        int batchSize = 4;
        int blockSize = 8;
        INDArray context = Nd4j.create(batchSize, blockSize);
        INDArray target = Nd4j.create(batchSize, blockSize);
        // This method call assumes data is a row vector
        int[] offsets = generateRandomArray(batchSize, 0, data.columns());
        
        
        
        for (int i = 0; i < 4; i++) {
            context.putRow(i, data.get(NDArrayIndex.interval(offsets[i], offsets[i] + blockSize)));
            // targets are one index to the right of context
            target.putRow(i, data.get(NDArrayIndex.interval(offsets[i] + 1, offsets[i] + 1 + blockSize)));
        }

        return new Pair<>(context, target);
    }
    
    public static void main(String[] args) {
        // Setup code
        SameDiff sd = SameDiff.create();
        String vocab = loadStringFromFile("testVocab.txt");
        BytePairEncoder bp = new BytePairEncoder(vocab);
        Nd4j.getRandom().setSeed(8008135);

        // Load shakespeare.txt
        String text = loadStringFromFile("src/main/java/com/jackrabbit/data/shakespeare.txt");
        // TODO: Remove this after we're ready to do the whole dataset
        text = text.substring(0, 1000);

        // Training the BytePairEncoder on the first 1000 characters of the text
        // bp.train(text.substring(0, 1000));
        // For this prototype, import testVocab.txt

        // Splitting text into training and validation sets
        int n = (int)(text.length() * 0.9);
        String trainString = text.substring(0, n);
        String valString = text.substring(n);
        INDArray trainData = Nd4j.create(bp.encode(trainString));
        INDArray valData = Nd4j.create(bp.encode(valString));

        // System.out.println(trainData);
        // System.out.println(trainData.shapeInfoToString());

        Pair<INDArray, INDArray> batch = getBatch(trainData);
        INDArray xb = batch.first;
        INDArray yb = batch.second;

        Bigram bg = new Bigram(bp.getVocabSize());
        SDVariable logits = bg.forward(xb, yb);

        System.out.println(bg.sd.summary());

        /* Code setup for demo
        * 
        // Test prediction setup
        int blockSize = 8;
        INDArray x = trainData.get(NDArrayIndex.interval(0, blockSize));
        INDArray y = trainData.get(NDArrayIndex.interval(1, blockSize + 1));
        System.out.println(x);

        // How the prediction should look for batchDimension of 1
        for (int i = 0; i < blockSize; i++) {
            INDArray context = x.get(NDArrayIndex.interval(0, i+1));
            INDArray target = y.get(NDArrayIndex.point(i));
            System.out.print("With ");
            System.out.print(context);
            System.out.print(" as context, predict ");
            System.out.println(target);
        } */
    }
}
