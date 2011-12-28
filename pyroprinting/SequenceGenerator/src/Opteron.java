/**
 * This class represents an opteron. An opteron is a sequence of Nucleotides (ATCG).
 * 
 * @author Ryan Hnarakis and Tyler Yero
 *
 */
public class Opteron {

	private String _sequence; //The actual sequence
	private String _opteronName; //the name of the opteron
	private String _sampleName; //the name of the sample this opteron was taken from
	
	public Opteron() {
		
	}
	
	public Opteron (String seq, String op, String samp) {
		_sequence = seq;
		_opteronName = op;
		_sampleName = samp;
	}
	
	public String getOpteronName() {
		return _opteronName;
	}
	
	public String getSequence() {
		return _sequence;
	}
	
	public String getSampleName() {
		return _sampleName;
	}
	
	public void setOpteronName(String op) {
		_opteronName = op;
	}
	
	public void setSequence(String seq) {
		_sequence = seq;
	}
	
	public void setSampleName(String samp) {
		_sampleName = samp;
	}
	
	/**
	 * Prints out the opteron as string of the form
	 * #getSampleName()
	 * #getOpteronName()
	 * getSequenceName()
	 */
	public String toString() {
		return "#" + _sampleName + "\n#" + _opteronName + "\n" + _sequence;
	
	}
}
