import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

/**
 * Many times our list of samples was getting too large (mainly with exhaustive sampling)
 * and we wanted a way to easily dump all the samples to a file without changing the
 * structure of the program. In order to do this we created a SampleList as an extension of
 * ArrayList<Sample>. It works exactly the same as ArrayList<Sample> except that when a 
 * sample is added past the MAXSAMPLES the ArrayList is output to the file and the entire
 * ArrayList is cleared. 
 * @author Allen Dunlea and Ryan Hnarakis
 *
 */
public class SampleList extends ArrayList<Sample>{

	/**
	 * The max number of samples that can be added before the contents of the arraylist are dumped to file.
	 * The samples will not be dumped to file until a sample is added past the max number of samples.
	 */
	public static final long MAXSAMPLES = 100;  //This is just a guess
	
	private static final long serialVersionUID = 1L;
	private File outFile = null;
	
	public File getOutFile() {
		return outFile;
	}

	/**
	 * Sets the dump file. Creates a new file if none has been created.
	 * @param dumpFile
	 */
	public void setOutFile(File dumpFile) {
		outFile = dumpFile;
		
		if(!outFile.exists()) {
			try {
				outFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

	/**
	 * Creates a new sample list and sets the dump file.
	 * @param dumpFile
	 */
	public SampleList(File dumpFile) {
		super();	
		setOutFile(dumpFile);	
	}
	
	/**
	 * Creates a new sample list and sets the dump file.
	 * @param dumpFile
	 */
	public SampleList(String dumpFile) {
		super();
		setOutFile(new File(dumpFile));

	}
	
	/**
	 * If you use this constructor do not forget to set the dump file if you plan on going over MAXSAMPLES or dumping to file
	 */
	public SampleList() {
		super();
	}
	
	/**
	 * Dumps the contents of the arraylist to file f and then clears the arraylist.
	 * @param f
	 */
	public void dumpToFile(File f) {
		
		PrintStream out = null;
		try {
			out = new PrintStream(f);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(Sample s: this){
			
			out.println(s);
		}
		out.close();
		this.clear();
	}
	
	@Override
	/**
	 * If the arraylist.size() is greater than or equal to MAXSAMPLES then the contents of the arraylist are dumped to file
	 * before adding the new sample.
	 */
	public boolean add(Sample s) {
	
		if(super.size() >= MAXSAMPLES) {
			//System.out.println("Dumping samples to file");
			dumpToFile(outFile);
		}
		
		return super.add(s);
	}

	/**
	 * Only call this method if the dumpfile has been set
	 */
	public void dumpToFile() {
		dumpToFile(outFile);
	}

	
	
}
