# Saturday 4/1/2023

Researched some of the different dataset options and found that Cornell's Movie Corpus is not a good fit for this project. The corpus is broken into multiple convoluded .json files that would be headache inducing to format into a good training set. After considering other options, I found the **WikiQACorpus** and decided this is a much better fit. This corpus contains question/answer pairs across a wide range of subjects, and is already formatted in some .tsv files which will be much easier to parse for the project. In addition, the data was generated to specifically used for NLP projects, so this dataset is the best fit

# Sunday 4/2/2023

Got the dev environment ready, and set up Maven to manage Deeplearning4J and other dependencies as they pop up. In the background I am researching with a two pronged approach. On one end, I am reading and re-reading the Transformer paper, "Attention is All You Need," while trying my best to understand the super complex technical jargon. On the other end, I am watching a nice lecture series by Andrej Karpathy building from the ground up, very intuitive and simple. I am finding that the eureka moments are abundant with this method. Finally, I am reading through relevant pages of the Deeplearning4J documentation to get some understanding of future implementations.

# Wednesday 4/5/2023

Implemented some data processing. First, I found a source for using ALL of wikipedia as a training set. Using this data involved getting WikiExtractor, a python library working. Luckily I have a Macbook because it did not work on Windows. Then, implemented final_pass.py which does finishing touches on the output from WikiExtractor. This is where lemming will be implemented among other data cleaning operations. It also splits the output into multiple files, which will be good for memory because the Wikipedia dataset is 20Gb.

# Thursday 4/20/2023

Wowzers, this ship is running behind schedule because I didn't work on it at all over spring break. Too much fun having was being had. Back to the groove, beginning implementation of a Byte Pair Encoder in Java today. It's about half done as it stands, but that's before optimization. If I want it to run on the full 133 Gb Wikipedia corpus it will need to be faaaast.
