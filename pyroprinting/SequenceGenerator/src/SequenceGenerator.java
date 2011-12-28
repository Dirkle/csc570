import java.util.*;

/**
 * This class represents the logic for generating samples.
 * It can generate samples in uniform, histogram, keywordtree or exhaustive mode.
 * 
 * @author Allen Dunlea, Ryan Hnarakis and Tyler Yero
 *
 */
public class SequenceGenerator {

	private List<Opteron> _sequences;
	private HashMap<String, Opteron> _uniformMap;
	private KeywordTree _keywordTree;
	private String outDir = null; //The directory where the output files go.
	private static final int MAXPATTERNSIZE = 7;
	private static final int MINPATTERNSIZE = 0;
	private static final int OPTERONQUANTITY = 7;
	
	/**
	 * The random mode to use, exhaustive is not included because it is not a random mode
	 * 
	 */
	public enum RandomMode {
		Uniform,
		Histogram,
		KeywordTree
	}
	
	public SequenceGenerator(List<Opteron> data) {
		_sequences = data;
	}
	
	/**
	 * Makes sure that the pattern is valid
	 * 
	 * The pattern must add up to 7 total opterons
	 * 
	 *  For example: 3,3,1 is a valid pattern but 3,3,2 is not and 3,3 is not
	 *  
	 * @param pattern
	 * @throws InvalidSequencePatternException
	 */
	private void checkPatternList(List<Integer> pattern) throws InvalidSequencePatternException {
		if (pattern == null) {
			throw new InvalidSequencePatternException("Pattern list cannot be null");
		}
		else if (pattern.size() <= MINPATTERNSIZE) {
			throw new InvalidSequencePatternException("Pattern list cannot have size less than " + MINPATTERNSIZE);
		}
		else if (pattern.size() > MAXPATTERNSIZE) {
			throw new InvalidSequencePatternException("Pattern list cannot have size greater than " + MAXPATTERNSIZE);
		}
		
		int sum = 0;
		
		for (int i = 0; i < pattern.size(); i++) {
			sum += pattern.get(i);
		}
		
		if (sum != OPTERONQUANTITY) {
			throw new InvalidSequencePatternException("Pattern must contain + " + OPTERONQUANTITY + "opterons total");
		}
	}

	/**
	 * This creates a list of all the possible samples that are of the pattern given. Since
	 * the number of samples grows exponentially with the length of the pattern the list is
	 * dumped to a filewhenever it gets too large.
	 * 
	 * @param pattern
	 * @return A list of samples that have not been dumped into the outputfile (exhaustive.out)
	 * @throws InvalidSequencePatternException
	 */
	public SampleList chooseExhaustiveSamples(List<Integer> pattern) throws InvalidSequencePatternException {
		//make sure the pattern is valid
		checkPatternList(pattern);
		
		//indices for the different levels of the opterons
		int opt1, opt2, opt3, opt4, opt5, opt6, opt7;
		
		//counts the number of repeated opterons that are supposed to be added to the sample
		//for instance, if the pattern is 3,3,1 then 3 identical opterons need to be added.
		int repeatedOpts = 0;
		
		//the return list
		SampleList returnList = new SampleList(outDir + "/exhaustive.out");
		
		//The new sample
		List<Opteron> chosenOpterons = new ArrayList<Opteron>(7);
		
		
		/*
		 * I realise that the following section might be difficult to follow and I'm very sorry to whoever
		 * is maintaining this code
		 *
		 *
		 *The code goes down one level (basement level) below the number of distinct opterons that are required in the pattern
		 *and then adds the created sample to the return list
		 *For example: if the pattern is 3,4 then samples will only get added at level 3 and will then break 
		 *             that loop
		 *             
		 *each level is set up as follows:
		 *  1. check if this is the basement level (see above)
		 *     if so, add new sample and break from that level of loop
		 *  2. trim to just the parts of the sample that came from the level
		 *     above. 
		 *     For example: if the pattern is 3,4 and we're in level 2 then I want
		 *                  to make sure that first 3 opteron persist through each
		 *                  iteration of my loop
		 *  3. add the appropriate number of opterons
		 *
		 */
		
		
		//LEVEL 1
		for(opt1 = 0; opt1 < _sequences.size(); opt1++) {
		
			chosenOpterons = new ArrayList<Opteron>(7);
			
			//add new opterons
			for(repeatedOpts = 0; repeatedOpts < pattern.get(0); repeatedOpts++) {
				chosenOpterons.add(_sequences.get(opt1));
			}
			
			
			//LEVEL 2
			for(opt2 = 0; opt2 < _sequences.size(); opt2++) {
				
				
				//check if this is the last entry in the pattern
				if(pattern.size() < 2) {
					//if so then add the new sample
					returnList.add(new Sample(new ArrayList<Opteron>(chosenOpterons)));
					break;
				}
				
				//trim off extras from previous loops
				chosenOpterons = chosenOpterons.subList(0, pattern.get(0));
				
				//otherwise add new opterons
				for(repeatedOpts = 0; repeatedOpts < pattern.get(1); repeatedOpts++) {
					chosenOpterons.add(_sequences.get(opt2));
				}
		
				
				//LEVEL 3
				for(opt3 = 0; opt3 < _sequences.size(); opt3++) {
				
					//check if this is the last entry in the pattern
					if(pattern.size() < 3) {
						
						//if so then add the new sample
						returnList.add(new Sample(new ArrayList<Opteron>(chosenOpterons)));
						break;
					}
					
					//trim from previous loops
					chosenOpterons = chosenOpterons.subList(0, pattern.get(0)+pattern.get(1));
					
					//otherwise add new opterons
					for(repeatedOpts = 0; repeatedOpts < pattern.get(2); repeatedOpts++) {
						chosenOpterons.add(_sequences.get(opt3));
					}
					
					//LEVEL 4
					for(opt4 = 0; opt4 < _sequences.size(); opt4++) {
						
						//check if this is the last entry in the pattern
						if(pattern.size() < 4) {
							//if so then add the new sample
							returnList.add(new Sample(new ArrayList<Opteron>(chosenOpterons)));
							break;
						}
				
						//trim from previous loops
						chosenOpterons = chosenOpterons.subList(0, pattern.get(0)+pattern.get(1)+pattern.get(2));
						
						// add new opterons
						for(repeatedOpts = 0; repeatedOpts < pattern.get(3); repeatedOpts++) {
							chosenOpterons.add(_sequences.get(opt4));
						}
						
						//LEVEL 5
						for(opt5 = 0; opt5 < _sequences.size(); opt5++) {
							
							//check if this is the last entry in the pattern
							if(pattern.size() < 5) {
				
								//if so then add the new sample
								returnList.add(new Sample(new ArrayList<Opteron>(chosenOpterons)));
								break;
							}
					
							//trim from previous loops
							chosenOpterons = chosenOpterons.subList(0, pattern.get(0)+pattern.get(1)+pattern.get(2)+pattern.get(3));
							
							//otherwise add new opterons
							for(repeatedOpts = 0; repeatedOpts < pattern.get(4); repeatedOpts++) {
								chosenOpterons.add(_sequences.get(opt5));
							}
							
							
							//LEVEL 6
							for(opt6 = 0; opt6 < _sequences.size(); opt6++) {
								
								//check if this is the last entry in the pattern
								if(pattern.size() < 6) {
					
									//if so then add the new sample
									returnList.add(new Sample(new ArrayList<Opteron>(chosenOpterons)));
									break;
								}
						
								//trim from previous loops
								chosenOpterons = chosenOpterons.subList(0, pattern.get(0)+pattern.get(1)+pattern.get(2)+pattern.get(3)+pattern.get(4));
								
								//otherwise add new opterons
								for(repeatedOpts = 0; repeatedOpts < pattern.get(5); repeatedOpts++) {
									chosenOpterons.add(_sequences.get(opt6));
								}
								
								
								//LEVEL 7
								for(opt7 = 0; opt7 < _sequences.size(); opt7++) {
									
									//check if this is the last entry in the pattern
									if(pattern.size() < 7) {
						
										//if so then add the new sample
										returnList.add(new Sample(new ArrayList<Opteron>(chosenOpterons)));
										break;
									}
							
									//trim from previous loops
									chosenOpterons = chosenOpterons.subList(0, pattern.get(0)+pattern.get(1)+pattern.get(2)+pattern.get(3)+pattern.get(4)+pattern.get(5));
									//otherwise add new opterons
									for(repeatedOpts = 0; repeatedOpts < pattern.get(6); repeatedOpts++) {
										chosenOpterons.add(_sequences.get(opt7));
									}
								}//END LEVEL 7
							}//END LEVEL 6
						}//END LEVEL 5
					}//END LEVEL 4
				}//END LEVEL 3	
			}//END LEVEL 2					
		}//END LEVEL 1
		return returnList;
	}
	
	/**
	 * Generates random samples in one of the 3 random modes
	 * @param pattern
	 * @param mode can be Uniform, Histogram of KeywordTree
	 * @param returnSize
	 * @return
	 * @throws InvalidSequencePatternException
	 */
	public SampleList generateRandomSamples(List<Integer> pattern, RandomMode mode, int returnSize) throws InvalidSequencePatternException {
		checkPatternList(pattern);
		
		SampleList returnList = null;
		
		switch (mode) {
			case Uniform : returnList = chooseUniformRandomSamples(pattern, returnSize); break; 
			case Histogram : returnList = chooseHistogramRandomSamples(pattern, returnSize); break;
			case KeywordTree : returnList = chooseKeywordTreeRandomSamples(pattern, returnSize); break;
			default : break;
		}				
		returnList.dumpToFile(); //just in case it hasn't already been dumped
		return returnList;
	}

	/**
	 * 
	 * Dumps output to keyword.out
	 * @param pattern
	 * @param returnSize
	 * @return
	 */
	private SampleList chooseKeywordTreeRandomSamples(List<Integer> pattern, int returnSize) {
		// Create keyword tree
		CreateKeywordTree();
		
		SampleList returnList = new SampleList(outDir + "/keyword.out");
		for (int i = 0; i < returnSize; i++) {	
			Sample oneSample = createKeywordTreeRandomSample(pattern);

			returnList.add(oneSample);
		}		
		
		return returnList;
	}

	private Sample createKeywordTreeRandomSample(List<Integer> pattern) {

		int numberOfOpteronsToChoose = pattern.size();
		List<Opteron> randomList = new ArrayList<Opteron>();
		List<Opteron> sampleList = new ArrayList<Opteron>();
		
		for (int i = 0; i < numberOfOpteronsToChoose; i++) {

			Opteron temp = new Opteron();
			temp.setSequence(_keywordTree.getRandomKeyword());
			temp.setSampleName("Keyword Sample");
			temp.setOpteronName("Keyword Opteron");
			
			randomList.add(temp);
		}
		
		for (int j = 0; j < pattern.size(); j++) {
			for (int k = 0; k < pattern.get(j); k++) {
				sampleList.add(randomList.get(j));
			}
		}
		
		assert(sampleList.size() == 7);

		return new Sample(sampleList);
	}

	private void CreateKeywordTree() {
		_keywordTree = new KeywordTree();
		
		for (int i = 0; i < _sequences.size(); i++) {			
			_keywordTree.insert(_sequences.get(i).getSequence());
		}			
	}
	
	/**
	 * dumps output to histogram.out
	 * @param pattern
	 * @param returnSize
	 * @return
	 */
	private SampleList chooseHistogramRandomSamples(List<Integer> pattern, int returnSize) {
		SampleList returnList = new SampleList(outDir + "/histogram.out");
		
		for (int i = 0; i < returnSize; i++) {	
			Sample oneSample = createHistogramRandomSample(pattern);

			returnList.add(oneSample);
		}		
		
		return returnList;
	}
	
	private Sample createHistogramRandomSample(List<Integer> pattern) {
		
		int numberOfOpteronsToChoose = pattern.size();
		List<Opteron> randomList = new ArrayList<Opteron>();
		List<Opteron> sampleList = new ArrayList<Opteron>();
		Random rand = new Random();
		
		for (int i = 0; i < numberOfOpteronsToChoose; i++) {
			randomList.add(_sequences.get(rand.nextInt(_sequences.size())));
		}
		
		for (int j = 0; j < pattern.size(); j++) {
			for (int k = 0; k < pattern.get(j); k++) {
				sampleList.add(randomList.get(j));
			}
		}
		
		assert(sampleList.size() == 7);

		return new Sample(sampleList);
	}
	
	/**
	 * Dumps output to uniform.out
	 * @param pattern
	 * @param returnSize
	 * @return
	 */
	private SampleList chooseUniformRandomSamples(List<Integer> pattern, int returnSize) {
	  CreateUniformMap();
      SampleList returnList = new SampleList(outDir + "/uniform.out"); //set the out file for the uniform samples
		
		for (int i = 0; i < returnSize; i++) {	
			Sample oneSample = createUniformRandomSample(pattern);

			returnList.add(oneSample);
		}		
		
		return returnList;
	}

   private Sample createUniformRandomSample(List<Integer> pattern) {
		
		int numberOfOpteronsToChoose = pattern.size();
		List<Opteron> randomList = new ArrayList<Opteron>();
		List<Opteron> sampleList = new ArrayList<Opteron>();
		Random rand = new Random();
		String[] arr2 = new String[1000];
		String[] arr = _uniformMap.keySet().toArray(arr2);
		
		for (int i = 0; i < numberOfOpteronsToChoose; i++) {
			int randomIndex = rand.nextInt(_uniformMap.entrySet().size());
			Opteron temp = new Opteron();
			temp.setSequence(arr[randomIndex]);
			temp.setSampleName(_uniformMap.get(arr[randomIndex]).getSampleName());
			temp.setOpteronName(_uniformMap.get(arr[randomIndex]).getOpteronName());
			randomList.add(temp);			
		}
		
		for (int j = 0; j < pattern.size(); j++) {
			for (int k = 0; k < pattern.get(j); k++) {
				sampleList.add(randomList.get(j));
			}
		}
		
		assert(sampleList.size() == 7);

		return new Sample(sampleList);
	}

	private void CreateUniformMap() {	
		_uniformMap = new HashMap<String, Opteron>();
		
		for (int i = 0; i < _sequences.size(); i++) {
			Opteron current = _sequences.get(i);
			
			if (!_uniformMap.containsKey(current.getSequence())) {
				_uniformMap.put(current.getSequence(), current);
			}
		}
	}

	/**
	 * @param outDir the outDir to set
	 */
	public void setOutDirectory(String outDir) {
		this.outDir = outDir;
	}

	/**
	 * @return the outDir
	 */
	public String getOutDirectory() {
		return outDir;
	}
}
