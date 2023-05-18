package com.jackrabbit.util;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

public class Batch {
    private INDArray context;
    private INDArray target;

    public Batch(INDArray context, INDArray target) {
        this.context = context;
        this.target = target;
    }

    public INDArray getContext() {
        return this.context;
    }

    public INDArray getTarget() {
        return this.target;
    }

    public void setContext(INDArray context) {
        this.context = context;
    }

    public void setTarget(INDArray target) {
        this.target = target;
    }

    public String toString() {
        return "Context: " + this.context.toString() + "\nTarget: " + this.target.toString();
    }
}
