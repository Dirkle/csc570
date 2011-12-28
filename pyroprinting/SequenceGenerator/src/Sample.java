import java.util.ArrayList;
import java.util.List;


/**
 * This class represents a sample of 7 opterons
 * 
 * @author Ryan Hnarakis and Tyler Yero
 *
 */
public class Sample {

	public static final int NUMBEROFOPTERONS = 7;
	private List<Opteron> _opterons;
	
	public Sample() {
		_opterons = new ArrayList<Opteron>();
	}
	
	/**
	 * This is the only constructor that should be used since there is no way to add opterons to the sample currently
	 * @param opteronList
	 */
	public Sample(List<Opteron> opteronList) {
		_opterons = opteronList;
	}
	
	public List<Opteron> getOpterons() {
		return _opterons;
	}		
	
	/**
	 * Prints out the 7 opterons (one on each line)
	 * See Opteron.java for how these are printed out.
	 */
	public String toString() {
		
		String sample = "";

		for(int i = 0; i < NUMBEROFOPTERONS; i++) {
			sample += _opterons.get(i).toString() + "\n";
		}
		return sample;
	}
}
