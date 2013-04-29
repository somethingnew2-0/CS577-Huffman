package com.algorithms.huffman;

import java.util.*;
import java.io.*;

public class Huffman {

	public static void main (String args[]) {

		String folder1 = "\\Users\\Kristin\\Documents\\GitHub\\CS577-Huffman\\Huffman\\speechdata";
		String folder2 = "\\Users\\Kristin\\Documents\\GitHub\\CS577-Huffman\\Huffman\\recent1";
		File outFile = new File("recent1.txt");
		processFiles(folder1, folder2, outFile);
		
		/*folder2 = "\\Users\\Kristin\\Documents\\GitHub\\CS577-Huffman\\Huffman\\recent2";
		outFile = "recent2.txt";
		processFiles(folder1, folder2, outFile);
		
		folder2 = "\\Users\\Kristin\\Documents\\GitHub\\CS577-Huffman\\Huffman\\recent10";
		outFile = "recent10.out";
		processFiles(folder1, folder2, outFile);

		folder2 = "\\Users\\Kristin\\Documents\\GitHub\\CS577-Huffman\\Huffman\\recent100";
		outFile = "recent100.out";
		processFiles(folder1, folder2, outFile);

		folder2 = "\\Users\\Kristin\\Documents\\GitHub\\CS577-Huffman\\Huffman\\recent300";
		outFile = "recent300.out";
		processFiles(folder1, folder2, outFile);

		folder2 = "\\Users\\Kristin\\Documents\\GitHub\\CS577-Huffman\\Huffman\\old2";
		outFile = "old2.out";
		processFiles(folder1, folder2, outFile);

		folder2 = "\\Users\\Kristin\\Documents\\GitHub\\CS577-Huffman\\Huffman\\old10";
		outFile = "old10.out";
		processFiles(folder1, folder2, outFile);

		folder2 = "\\Users\\Kristin\\Documents\\GitHub\\CS577-Huffman\\Huffman\\old100";
		outFile = "old100.out";
		processFiles(folder1, folder2, outFile);

		folder2 = "\\Users\\Kristin\\Documents\\GitHub\\CS577-Huffman\\Huffman\\old300";
		outFile = "old300.out";
		processFiles(folder1, folder2, outFile);

		folder2 = "\\Users\\Kristin\\Documents\\GitHub\\CS577-Huffman\\Huffman\\speechdata";
		outFile = "all.out";
		processFiles(folder1, folder2, outFile);*/

	}


	/*
	 * folderName1 : folder containing all the speeches
	 * folderName2 : folder containing subset of speeches used to build the Huffman Code
	 */
	public static void processFiles (String folderName1, String folderName2, File outFileName)      {
		Scanner stdin = null;
		// map to hold words and frequencies of current set of speeches
		BSTDictionary<KeyWord> dictionary = new BSTDictionary<KeyWord>();
		// huffman tree made based on given subset of speeches
		HuffmanTree<String> tree;
		// map to hold the number of bits that represent each word in huffman
		HashMap<String, Integer> bitsPerWord = new HashMap<String, Integer>();
		// folder containing all speeches to be compressed
		File folder1 = new File(folderName1);
		File[] allSpeeches = folder1.listFiles();

		// process all files in speech directory
		for (File inFile : allSpeeches) {
			// check if file exits and can be read
			if (!inFile.exists() || !inFile.canRead()) {
				System.out.println("Improper file: " + inFile.getName());
				System.exit(-1);
			}//end if
			try {
				stdin = new Scanner(inFile);
				// add all words from all speeches with a frequency of 1
				smooth(dictionary, stdin);
			} catch (FileNotFoundException ex) {
				System.out.println("Unable to find file: " + inFile.getName());
				System.exit(-1);
			}//end try-catch
		}//end for

		double huffmanComp = 0;
		double blockComp = 0;
		int numWords = 0;
		// build huffman code using subset of files
		File folder2 = new File(folderName2);
		File[] listOfFiles = folder2.listFiles();
		// process subset of files to make huffman code
		for (File inFile : listOfFiles) {
			System.out.println(inFile.getName());
			if (!inFile.exists() || !inFile.canRead()) {
				System.out.println("Improper file: " + inFile.getName());
				System.exit(-1);
			}//end if
			try {
				stdin = new Scanner(inFile);
				parseFile(dictionary, stdin);

			} catch (FileNotFoundException ex) {
				System.out.println("Unable to find file: " + inFile.getName());
				System.exit(-1);
			}// end try-catch
		} //end-for
		
		tree = makeHuffman(dictionary);
		bitsPerWord = findBits(tree);

		try {
			PrintWriter out = new PrintWriter(outFileName);
			for (File speech : allSpeeches) {
				// calculate compression ratio for all speeches
				try {
					stdin = new Scanner(speech);
					numWords = countWords(stdin);
					stdin = new Scanner(speech);
				} catch(FileNotFoundException e) {
					System.out.println("Unable to find fine: " + speech.getName());
				}
				huffmanComp = calcHuffmanCompression(stdin, bitsPerWord);
				blockComp = calcBlockCompression(dictionary, numWords);
				double ratio = huffmanComp/blockComp;
				out.append("\n" + ratio);

			} //end-for
		} catch (IOException e) {
			System.out.println("Could not write to file");
		} 
	}


	/*
	 * Creates a mapping of words to the number of bits needed to encode them
	 */
	public static HashMap<String, Integer> findBits(HuffmanTree<String> tree) {
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		Queue<HuffmanNode<String>> queue = new LinkedList<HuffmanNode<String>>();
		queue.add(tree.getRoot());
		tree.getRoot().setVisited(true);
		while (!queue.isEmpty()) {
			HuffmanNode<String> node = queue.remove();
			map.put(node.getData(), node.getHeight());
			if (node.leftChild != null && !node.leftChild.visited) {
				node.leftChild.setVisited(true);
				queue.add(node.leftChild);
			}
			if (node.rightChild != null && !node.rightChild.visited) {
				node.rightChild.setVisited(true);
				queue.add(node.rightChild);
			}
		}
		return map;
	}

	/*
	 * Returns number of words in speech to be compressed.
	 */
	public static int countWords(Scanner stdin) {
		int count = 0;
		while (stdin.hasNext()) {
			stdin.next();
			count++;
		}
		return count;
	}

	/*
	 * Calculates the compression using the huffman method
	 */
	public static double calcHuffmanCompression(Scanner stdin, HashMap<String, Integer> map) {
		int size = 0;
		while (stdin.hasNext()) {
			String word = stdin.next();
			try {
				int bits = map.get(word);
				size += bits;
			} catch (NullPointerException e) {
				System.out.println(word);
				System.exit(-1);
			}
		}
		return size;
	}

	/*
	 * Calculates the compression of block method.
	 */
	public static double calcBlockCompression(BSTDictionary<KeyWord> dictionary, int numWords) {
		double size = dictionary.size();
		double blockCompression;

		blockCompression = (double)(Math.log(size)/Math.log(2));
		blockCompression = blockCompression * numWords;

		return blockCompression;
	}

	/*
	 * Create the Huffman code tree using the words in a certain set of speeches
	 */
	public static HuffmanTree<String> makeHuffman(BSTDictionary<KeyWord> dictionary) {

		// iterate over all words in the dictionary
		Iterator<KeyWord> it = dictionary.iterator();
		// queue of words sorted by frequency
		PriorityQueue<HuffmanNode<String>> queue = new PriorityQueue<HuffmanNode<String>>();
		HuffmanNode<String> node1, node2;
		KeyWord keyword;
		// place all keywords into priority queue, least # occurrences at the head
		while(it.hasNext()) {
			// turn them into huffman nodes
			keyword = (KeyWord)it.next();
			HuffmanNode<String> node = new HuffmanNode<String>();
			node.setData(keyword.getWord());
			node.setFreq(keyword.getOccurrences());
			node.setVisited(false);
			node.removeChildren();
			queue.add(node);
		}

		// make first node in list the root
		HuffmanTree<String> tree = new HuffmanTree(queue.peek());
		double sum;
		HuffmanNode<String> newNode;

		// make sure at least 2 nodes left in the queue
		while(queue.size() > 2) {
			node1 = queue.poll();
			node2 = queue.poll();
			// take the sum of their two frequencies
			sum = node1.getFreq() + node2.getFreq();
			// make a new internal node with this new frequency
			newNode =  new HuffmanNode<String>();
			newNode.setFreq(sum);
			// add the other two nodes as its children
			tree.addLeft(newNode, node1);
			tree.addRight(newNode, node2);
			node1.setParent(newNode);
			node2.setParent(newNode);
			queue.add(newNode);
		}

		node1 = queue.poll();
		node2 = queue.poll();
		sum = node1.getFreq() + node2.getFreq();
		// make a new internal node with this new frequency
		newNode =  new HuffmanNode<String>();
		newNode.setFreq(sum);
		// add the other two nodes as its children
		tree.addLeft(newNode, node1);
		tree.addRight(newNode, node2);
		node1.setParent(newNode);
		node2.setParent(newNode);
		tree.setRoot(newNode);

		return tree;
	}

	/*
	 * Add one to the count for every word in all the speeches
	 */
	public static void smooth(BSTDictionary<KeyWord> dictionary, Scanner in) {
		while (in.hasNext()) {
			String line = in.next();
			String[] tokens = line.split("[ ]+");

			for (String word : tokens) {

				int first = findFirst(word);
				int last = findLast(word, first);

				// trim string so it starts and ends with letter/digit
				word = word.substring(first, last + 1);

				// increment value in dictionary
				KeyWord keyword;
				try {
					// try inserting new keyword
					keyword = new KeyWord(word.toLowerCase());
					keyword.increment();						dictionary.insert(keyword);
				} catch (DuplicateKeyException e) {
					// if already in dictionary, do nothing
					;
				}
			} // end outer while
		}
	}

	/*
	 * Takes each line in a file and adds each word to the dictionary, or, if already 
	 * present, increments its value.
	 */
	//TODO: decide whether to use percentage or count of occurrences
	public static void parseFile(BSTDictionary<KeyWord> dictionary, Scanner in) {

		while (in.hasNext()) {
			String line = in.next();
			String[] tokens = line.split("[ ]+");
			for (String word : tokens) {

				int first = findFirst(word);
				int last = findLast(word, first);

				// trim string so it starts and ends with letter/digit
				word = word.substring(first, last + 1);

				KeyWord keyword;
				try {
					// try inserting new keyword
					keyword = new KeyWord(word.toLowerCase());
					keyword.increment();						dictionary.insert(keyword);
				} catch (DuplicateKeyException e) {
					// if already in dictionary, just increment occurences
					keyword = dictionary.lookup(new KeyWord(word.toLowerCase()));
					keyword.increment();
				}//end try-catch
			} // end outer while
		}
	}

	/*
	 * Finds position of first letter or digit in word
	 */
	public static int findFirst(String word) {
		// find index of first digit/letter
		boolean done = false;
		int first = 0;
		while (first < word.length() && !done) {
			if (Character.isDigit(word.charAt(first)) || Character.isLetter(word.charAt(first))) {
				done = true;
			}
			else {
				first++;
			}
		} // end while

		return first;
	}

	/*
	 * Finds position of last letter or digit in word
	 */
	public static int findLast(String word, int first) {
		// find index of last digit/letter
		boolean done = false;
		int last = word.length() - 1;
		while (last > first && !done) {
			if (Character.isDigit(word.charAt(last)) || Character.isLetter(word.charAt(last))) {
				done = true;
			}
			else {
				last--;
			}
		} // end while
		return last;
	}
}
