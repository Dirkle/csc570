import java.util.Random;

/**
 * This is a class used to represent the Keyword Tree data structure. It allows for the easy insertion of
 * new strings, and it has a public method for obtaining new strings based on the probability
 * of the tree.
 * @author Tyler Yero
 *
 */
public class KeywordTree {
	private class KeywordNode {
		public char letter = ' ';
		public int count;
//		public int height;
		public int level;
		public KeywordNode nodeA;
		public KeywordNode nodeT;
		public KeywordNode nodeC;
		public KeywordNode nodeG;
	}

	private KeywordNode root;
	private KeywordNode last;
	
	public KeywordTree() {
		root = new KeywordNode();
//		root.height = 0;
		root.level = 0;
	}

	public void insert(String seq) {
		for (int i = 0; i < seq.length(); i++) {
			insert(seq.charAt(i), i + 1);
		}
		
		last = null;
	}

	private void insert(char item, int lvl) {	
		if (last == null) {
			last = root;
		}
		
		insert(item, lvl, last);
	}

	private KeywordNode insert(char item, int lvl, KeywordNode treeroot) {
		if (treeroot == null) {
			treeroot = new KeywordNode();
			treeroot.letter = item;
			treeroot.count = 1;
			treeroot.level = lvl;
			last = treeroot;
		} else {
			if (item == treeroot.letter && lvl == treeroot.level) {
				treeroot.count++;
				last = treeroot;
			} else if (item == 'a' || item == 'A') {
				treeroot.nodeA = insert(item, lvl, treeroot.nodeA);
			} else if (item == 't' || item == 'T') {
				treeroot.nodeT = insert(item, lvl, treeroot.nodeT);
			} else if (item == 'c' || item == 'C') {
				treeroot.nodeC = insert(item, lvl, treeroot.nodeC);
			} else if (item == 'g' || item == 'G') {
				treeroot.nodeG = insert(item, lvl, treeroot.nodeG);
			}
		}

//		int height1 = Math.max(height(treeroot.nodeA), height(treeroot.nodeT));
//		int height2 = Math.max(height(treeroot.nodeC), height(treeroot.nodeG));
//		treeroot.height = Math.max(height1, height2) + 1;

		return treeroot;
	}

	public String getRandomKeyword() {
		return getRandomKeyword(root).trim();
	}

	private String getRandomKeyword(KeywordNode node) {
		int totalCount = 0;

		if (node.nodeA != null) {
			totalCount += node.nodeA.count;
		}

		if (node.nodeT != null) {
			totalCount += node.nodeT.count;
		}

		if (node.nodeC != null) {
			totalCount += node.nodeC.count;
		}

		if (node.nodeG != null) {
			totalCount += node.nodeG.count;
		}

		if (totalCount == 0) {
			return node.letter + "";
		}

		Random rand = new Random();
		int chosenNum = rand.nextInt(totalCount);
		int counter = 0;

		if (node.nodeA != null) {
			counter += node.nodeA.count;
			if (chosenNum < counter) {
				return node.letter + getRandomKeyword(node.nodeA);
			}
		}

		if (node.nodeT != null) {
			counter += node.nodeT.count;
			if (chosenNum < counter) {
				return node.letter + getRandomKeyword(node.nodeT);
			}
		}

		if (node.nodeC != null) {
			counter += node.nodeC.count;
			if (chosenNum < counter) {
				return node.letter + getRandomKeyword(node.nodeC);
			}
		}

		if (node.nodeG != null) {
			return node.letter + getRandomKeyword(node.nodeG);
		}

		return node.letter + "";
	}

//	public int height() {
//		return height(root);
//	}

//	private int height(KeywordNode x) {
//		int answer;
//
//		if (x != null) {
//			answer = x.height;
//		} else {
//			answer = -1;
//		}
//
//		return answer;
//	}

	public void print() {
		print(root, ""); // call private method for recursion
	}

	private void print(KeywordNode node, String indent) // parameters are node
														// and indent
	{
		if (node != null) {
			System.out.println(indent + node.letter + " " + node.count);
			print(node.nodeA, indent + "   ");
			print(node.nodeT, indent + "   ");
			print(node.nodeC, indent + "   ");
			print(node.nodeG, indent + "   ");
		}
	}
}