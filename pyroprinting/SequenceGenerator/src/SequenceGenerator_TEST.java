import java.util.ArrayList;
import java.util.List;

/**
 * I designed this class to test the exhaustive sequence generator. It's kind of a beast.
 * @author Allen Dunlea
 *
 */
public class SequenceGenerator_TEST {

	
	public static void main (String[] args) {
		
		
		testExhaustive ();
	}

	private static void testExhaustive() {
		String testFileName = "../SequenceGenerator/SampleOriginalFiles/15-E.coli-ED1a-16s.txt";
		List<Integer> pattern = new ArrayList<Integer>();
		SampleList samples = null;
		FileParser fp = new FileParser();
		
		//THIS CANNOT HANDLE MORE THAN 4 YET
		pattern.add(2);
		pattern.add(2);
		pattern.add(1);
		pattern.add(2);
		//pattern.add(1);


		System.out.println("Parsing files...");
		SequenceGenerator gen = new SequenceGenerator(fp.ParseFile(testFileName));
		gen.setOutDirectory("../SequenceGenerator/output");
		System.out.println("Done parsing.");

		
		
		try {
			samples = gen.chooseExhaustiveSamples(pattern);
		} catch (InvalidSequencePatternException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		samples.dumpToFile();
		System.out.println("-----------------\nPrinting N samples exhautively selected from the database:");
		
		
	/*	int n = samples.size();
		for (int i = 0; i < n; i++)
			System.out.println(samples.get(i));*/
		
	}
}
