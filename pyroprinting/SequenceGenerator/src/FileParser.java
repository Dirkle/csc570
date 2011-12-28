import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parses a directory for all sequences
 * @author Allen Dunlea
 *
 */


public class FileParser {
	private static final boolean debug = false;
	private static final String PRIMER16s = "GGAACCTGCGGTTGGATCAC";
	private static final String PRIMER5s = "CGTGAGGCTTAACCTT";
	
	private static final int LENGTH16s = 104;
	private static final int LENGTH5s = 96;
	
	private File _directory;
	
	private String primer = "";
	private int length = 0;
	
	public FileParser(String directoryName) {
		_directory = new File(directoryName);	
	}
	
	public FileParser() {
		
	}
	
	/**
	 * Parses a file for all opterons
	 * 
	 * adapted from Alrin's code (Polyparse.java)
	 * @param in
	 * @return
	 * @throws FileNotFoundException
	 */
	public  ArrayList<Opteron> ParseFile(String in) {
		//breaks sequences in files into an arraylist of strings
		
		Scanner fileData = null; //The scanner I use to read data from the file
		Opteron tempOp = null; //A temporary opteron i use before I add it to the list
		ArrayList<Opteron> opterons = new ArrayList<Opteron>(); //the return
	      
	      if (in == null || in.equals("")) {
	         System.err.println("No file selected to parse");
	         return null;
	      }
	
	      try {
	         fileData = new Scanner(new BufferedReader(new FileReader(in)));
	      }
	      catch (FileNotFoundException e) {
	         System.err.println("Could not find file " + in +
	          ", verify that file exists.");
	      }
	
	      //Check for the DNA notation in the file that needs to be dropped
	      //from the string
	      String newSeq = "";

	      
	      while (fileData.hasNextLine()) {
	    	  
	         //analyze file line by line
	         if (debug) System.out.println("parsing line in data");
	         String currentLine = fileData.nextLine();
	         if (debug) System.out.println("currentLine is: " + currentLine);
	
	         //ignore whitespace
	         if (currentLine.equals("")) {
	        	 continue;
	         }
	         
	         //currentLine starts with ">" if it is a description and not dna
	         else if (currentLine.startsWith(">")) {
	            
	            //Trim sequence and add it to temp opteron
	            if(newSeq.contains(primer)) {
					tempOp.setSequence(
							newSeq.substring(
									newSeq.indexOf(primer) + primer.length(), 
									newSeq.indexOf(primer) + primer.length() + length));
				}
	            
	            //the first '>' encountered will be at the top of the file so this is necessary
	            if(tempOp != null) {
	            	opterons.add(tempOp);
	            }
	            
	            newSeq = "";
	            
	            //Make a new opteron
	            tempOp = new Opteron();	           
	            tempOp.setOpteronName(currentLine); //TODO might just want a substring of the current line
				tempOp.setSampleName(in.substring(in.lastIndexOf('/') + 1)); //So I only get the file name (not full path)
	         }
	         
	         //if currentLine is not a description then it is dna
	         else {
	        	 newSeq += currentLine;
	         }
	      }
	      
	
	      //add last opteron
	      if(newSeq.contains(primer)) {
				tempOp.setSequence(
						newSeq.substring(
								newSeq.indexOf(primer) + primer.length(), 
								newSeq.indexOf(primer) + primer.length() + length));
			}
	      opterons.add(tempOp);

		return opterons;
	}
	
	
	
	/**
	 * Parses each file in a directory and returns an arraylist of opterons
	 * @param directory
	 * @return
	 * @throws FileNotFoundException
	 */
	public  ArrayList<Opteron> ParseDirectory(File directory) {
	
		ArrayList<Opteron> database = new ArrayList<Opteron>();
		String filename[] = directory.list();
		
	
		for (int i = 0; i < filename.length; i++) {
			
			database.addAll(ParseFile(directory + "/" + filename[i]));
			
			if(debug) {
		
				System.out.println(ParseFile(directory + "/" + filename[i]));
			
			}
		}				
	
		// Print parsed data
		int n = database.size();
		if(debug) {
			for(int i = 0; i < n ; i++)
				System.out.println( database.get( i ) );
		}
	 
		return database;
		
	}
	
	/**
	 * Takes a string instead of a file, acts exactly the same.
	 * @param directoryName
	 * @return
	 * @throws FileNotFoundException
	 */
	public  ArrayList<Opteron> ParseDirectory(String directoryName) throws FileNotFoundException {
		return ParseDirectory (new File(directoryName));
	}
	
	
	/**
	 * Left this method in so that the functionality would not change from the original
	 * Only call this method if you have already set the directory
	 * @return
	 * @throws FileNotFoundException
	 */
	public ArrayList<Opteron> ParseSequences() throws FileNotFoundException {
		return ParseDirectory(_directory);
		
	}



	/**
	 * 
	 * @param primer the primer to set
	 */
	public void setPrimer(String primer) {
		this.primer = primer;
	}



	/**
	 * 
	 * @return the primer
	 */
	public String getPrimer() {
		return primer;
	}



	/**
	 * 
	 * @param length the length to set
	 */
	public void setSequenceLength(int length) {
		this.length = length;
	}



	/**
	 * @return the length
	 */
	public int getSequenceLength() {
		return length;
	}
	
	
	
	
	
	
	
}
