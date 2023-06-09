/*******************************************************************************
 *
 *
 *
 * This program and the accompanying materials are made available under the
 * terms of the Apache License, Version 2.0 which is available at
 * https://www.apache.org/licenses/LICENSE-2.0.
 *  See the NOTICE file distributed with this work for additional
 *  information regarding copyright ownership.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 *
 * SPDX-License-Identifier: Apache-2.0
 ******************************************************************************/

package com.jackrabbit;

import org.nd4j.autodiff.functions.DifferentialFunction;
import org.nd4j.autodiff.samediff.SDVariable;
import org.nd4j.autodiff.samediff.SameDiff;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * This is a simple example introducing the basic concepts of SameDiff:
 * - The SameDiff class
 * - Variables
 * - Functions
 * - Getting arrays
 * - Executing forward pass
 *
 * @author Alex Black
 */
public class Ex1_SameDiff_Basics {

    public static void main(String[] args) {


        //The starting point for using SameDiff is creating a SameDiff instance via the create method.
        //The SameDiff class has many methods for creating variables and functions
        SameDiff sd = SameDiff.create();

        /*
        Variables can be added to a graph in a number of ways.
        You can think of variables as "holders" of an n-dimensional array - specifically, an ND4J INDArray
        First, let's create a variable based on a specified array, with the name "myVariable"
        */
        INDArray values = Nd4j.ones(3,4);
        SDVariable variable = sd.var("myVariable", values);

        //We can then perform operations on the variable:
        SDVariable plusOne = variable.add(1.0);                               //Name: automatically generated as "add"
        SDVariable mulTen = variable.mul("mulTen", 10.0);        //Name: Defined to be "mulTen"
        //Note that most operations have method overloads with and without a user-specified name - i.e., add(double) vs. add(String,double)

        //Let's inspect the graph. We currently have 3 variables, with names "myVariable", "add", and "mulTen",
        // and two functions - our "add 1.0" and our "multiply by 10" functions. These are shown in the summary:
        System.out.println(sd.summary());
        System.out.println("===================================");

        //We can also get the variables directly:
        List<SDVariable> allVariables = sd.variables();
        System.out.println("Variables: " + allVariables);
        for(SDVariable var : allVariables){
            long[] varShape = var.getShape();
            System.out.println(var.name() + " - shape " + Arrays.toString(varShape));
        }

        /*
        We can also inspect the individual functions.
        You can think of functions as operations that map input variables (arrays) to new variables (arrays)
        Functions have 0 or more inputs (usually 1 or more) and 1 or more outputs
         */
        DifferentialFunction[] ops = sd.ops();
        for(DifferentialFunction df : ops){
            SDVariable[] inputsToFunction = df.args();      //Inputs are also known as "args" or "arguments" for a function
            SDVariable[] outputsOfFunction = df.outputVariables();
            System.out.println("Op: " + df.opName() + ", inputs: " + Arrays.toString(inputsToFunction) + ", outputs: " +
                Arrays.toString(outputsOfFunction));
        }

        //Now, let's execute the graph forward pass on the mulTen variable.
        INDArray variableArr, mulTenArr, plusOneArr;
        //We are passing in any empty map since this graph does not need any inputs for the forward pass
        sd.output(Collections.<String,INDArray>emptyMap(), "mulTen");
        variableArr = variable.getArr();               //We can get arrays directly from the variables
        mulTenArr = sd.getArrForVarName("mulTen");     //Or also by name, from the Samediff instance

        System.out.println("===================================");
        System.out.println("Initial variable values:\n" + variableArr);
        System.out.println("'mulTen' values:\n" + mulTenArr);

        //We can also do the forward pass by calling eval on the SDVariable directly.
        //Note that this will clear the sd graph and set all it's variable arrays that are not on the path of the forward pass to null
        plusOneArr = plusOne.eval();
        variableArr = variable.getArr();               //We can get arrays directly from the variables
        mulTenArr = sd.getArrForVarName("mulTen");     //Or also by name, from the Samediff instance
        System.out.println("===================================");
        System.out.println("Initial variable values:\n" + variableArr);
        System.out.println("'plusOne' values:\n" + plusOneArr);
        System.out.println("'mulTen' values:\n" + mulTenArr);

        //That's it - see the next example for calculating gradients
    }

}
