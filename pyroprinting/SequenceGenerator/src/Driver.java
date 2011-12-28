import java.io.FileNotFoundException;
import java.util.*;

/**
 * This is the entry way into the program
 * @author Allen Dunlea, Ryan Hnarakis and Tyler Yero
 *
 */
public class Driver {

	public static void main(String[] args) throws FileNotFoundException,
			InvalidSequencePatternException {

		//See the README for an explanations of the 7 arguments
		if (args.length != 7) {

			System.err.println("Invalid arguments : [directory in] [directory out] [primer sequence] [number of samples to generate] [generation mode: (K)eyword,(H)istogram, (U)niform, (E)xhaustive]  [length to snip (~97-104 bases)] [pattern (Ex. 4,1,1,1)] .");
			System.err.println("\tExample: SampleOriginalFiles output GGAACCTGCGGTTGGATCAC 104 K 6 3,3,1");
			System.err.println("\tExample: SampleOriginalFiles output GGAACCTGCGGTTGGATCAC 104 E 0 2,3,2");
			System.exit(1);
		} else {
			//set up file parser
			FileParser fp = new FileParser(args[0]);
			fp.setPrimer(args[2]);
			fp.setSequenceLength(Integer.parseInt(args[3]));
			
			//set up sequence generator
			System.out.println("Parsing files...");
			SequenceGenerator gen = new SequenceGenerator(fp.ParseSequences());
			gen.setOutDirectory(args[1]);
			System.out.println("Done parsing.");

			// Distribution pattern for selecting samples. Must add up to 7.
			String[] ratios = args[6].split(",");
			List<Integer> pattern = new ArrayList<Integer>();
			for(String r : ratios) {
				pattern.add(Integer.parseInt(r));
			}
			
			switch(args[4].toLowerCase().charAt(0)) {
				//histogram
				case 'h':
				List<Sample> randomSamples = gen.generateRandomSamples(pattern,
						SequenceGenerator.RandomMode.Histogram, Integer.parseInt(args[5]));
				System.out.println("-----------------\nPrinting N samples selected from the database by histogram method:");
				int n = randomSamples.size();
				for (int i = 0; i < n; i++)
					System.out.println(randomSamples.get(i));
				break;
				
				//uniform
				case 'u':
				randomSamples = gen.generateRandomSamples(pattern, SequenceGenerator.RandomMode.Uniform, Integer.parseInt(args[5]));
				System.out.println("-----------------\nPrinting N samples uniformily selected from the database:");
				n = randomSamples.size();
				for (int i = 0; i < n; i++)
					System.out.println(randomSamples.get(i));
				break;
				
				//keyword tree
				case 'k':
				randomSamples = gen.generateRandomSamples(pattern, SequenceGenerator.RandomMode.KeywordTree, Integer.parseInt(args[5]));
				System.out.println("-----------------\nPrinting N samples selected from the database by keyword probability:");
				n = randomSamples.size();
				for (int i = 0; i < n; i++)
					System.out.println(randomSamples.get(i));
				break;
				
				//exhaustive
				case 'e':
				gen.chooseExhaustiveSamples(pattern);
				break;
				
				default:
				System.err.println("Please enter a valid generation mode (Keyword, Histogram, Uniform or Exhaustive.");
				break;
			}
		}
	}
}
