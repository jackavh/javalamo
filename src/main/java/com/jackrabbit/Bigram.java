package com.jackrabbit;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.indexing.NDArrayIndex;

import java.util.HashMap;
import java.util.Map;

import org.nd4j.autodiff.samediff.SDVariable;
import org.nd4j.autodiff.samediff.SameDiff;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.api.buffer.DataType;
import org.nd4j.linalg.ops.transforms.Transforms;

public class Bigram {
    public SameDiff sd;
    
    public Bigram(int vocabSize) {
        sd = SameDiff.create();
        // Create an embedding table of size vocabSize x vocabSize
        SDVariable embeddingTable = sd.var("embeddingTable", Nd4j.randn(DataType.FLOAT, vocabSize, vocabSize));

        // Create placeholder variables for context and target
        SDVariable context = sd.placeHolder("context", DataType.INT32, -1); // B*T
        SDVariable target = sd.placeHolder("target", DataType.INT32, -1);   // B*T

        // DEBUG VARS
        SDVariable debug = sd.var("debug", Nd4j.ones(DataType.INT32, 2));

        // Get logits by mapping the context indices to embeddings (see getEmbedding)
        SDVariable logits = getEmbedding(debug, embeddingTable); // B*T, C
        //SDVariable logits = getEmbedding(context, embeddingTable); // B*T, C

        // Get the loss by comparing the logits to the target indices
        SDVariable logLoss = sd.loss.logLoss("logLoss", target, logits);
        // TODO: Figure out how tf this stupid class works
        // How to get a scalar loss out of logits of shape B*T, C
        // Compared against the target indices, which are B*T, 1?

        INDArray emb, dbg, ctemb, loss;
        emb = embeddingTable.getArr();
        dbg = debug.getArr();
        ctemb = logits.getArr();
        loss = logLoss.getArr();
        System.out.println("embedding table");
        System.out.println("========");
        System.out.println(emb);
        System.out.println("debug");
        System.out.println("========");
        System.out.println(dbg);
        System.out.println("context embeddings");
        System.out.println("========");
        System.out.println(ctemb);
        System.out.println("loss");
        System.out.println("========");
        System.out.println(loss);
    }

    public SDVariable forward(INDArray idx, INDArray targets) {
        Map<String, INDArray> inputs = new HashMap<>();
        inputs.put("context", idx);
        inputs.put("target", targets);  
        
        return new SDVariable();
    }

    // Returns the embedding for the given indices (idx)
    public SDVariable getEmbedding(SDVariable _idx, SDVariable embeddingTable) {
        // Get the INDArray for _idx
        INDArray idx = _idx.getArr();
        
        // If idx is a matrix, flatten it to a row vector
        if (idx.rank() > 1) {
            // -1 means "infer the size of this dimension"
            idx = idx.reshape(1, -1);
        }
        // Create an array to hold the embeddings
        INDArray[] embedArrays = new INDArray[(int)idx.length()];

        // Get the embedding for each index
        for (int i = 0; i < idx.length(); i++) {
            embedArrays[i] = embeddingTable.getArr().getRow(idx.getInt(i));
        }

        // Stack the embeddings into a matrix
        return sd.var("logits", Nd4j.vstack(embedArrays));
    }

    public static void main(String[] args) {
        Bigram bg = new Bigram(5);
    }
}
