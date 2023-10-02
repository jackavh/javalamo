# javalamo - Java Language Model

## Project Timeline

### Week 1 

~~1. Select from [[Potential Datasets]] after researching options (I'm leaning towards Cornell's movie dialogue corpus)~~

	Selected WikiQACorpus, and the Wikipedia dump dataset for pre-training

2. Study Deeplearning4J and the tensor datastructure

~~3. Research language model architectures and choose one that could be implemented within the timeframe~~

	Committed to a basic n-Gram language model based on maximum likelihood estimation

4. Start brainstorming how that model could be implemented in Java (Flow charts, drawings, etc)

### Week 2

5. Continue researching and developing an implementation plan

~~6. Choose a tokenization technique~~
	
	Committed to Byte-Pair encoding.
	
~~7. Choose and begin processing the dataset,~~
	
	1. Removing article encodings
	2. Removing special characters
	3. Performing Byte-Pair encoding 
	
8. Split the dataset into training, validation, and testing sets

### Week 3 & 4

9. Implement support classes that will build the language model

10. Always keep the greater architecture in mind

11. Implement the language model

### Week 5

12. Train the model on the dataset that was prepared

13. Make sure to avoid overfitting with validation

14. Experiment with hyperparameters and different configurations for better performance (different loss functions, batch size, etc.)

### Week 6

15. Create the console interface so that the user can interact with the now-trained model

16. Write and test some demo prompts for the presentation

### Week 7

17. Make any final adjustments

18. Write up the final report

19. Presentation!
