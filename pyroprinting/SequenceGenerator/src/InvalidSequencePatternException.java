/**
 * An exception that is thrown when an invalid pattern is entered. Patterns should add up to exactly 7 opterons.
 * @author Ryan Hnarakis and Tyler Yero
 *
 */

public class InvalidSequencePatternException extends Exception {

	
	private static final long serialVersionUID = 1L;

	public InvalidSequencePatternException() {
		super();
	}
	
	public InvalidSequencePatternException(String msg) {
		super(msg);
	}
}
