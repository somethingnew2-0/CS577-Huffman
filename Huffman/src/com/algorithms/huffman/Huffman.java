package com.algorithms.huffman;

import java.util.*;
import java.io.*;

public class Huffman {

	public static void main (String args[]) {
		
		Scanner stdin = null;
		// map to hold words and frequencies of current set of speeches
		BSTDictionary<KeyWord> dictionary = new BSTDictionary<KeyWord>();
		// holds info about all of the speeches
		BSTDictionary<KeyWord> allSpeeches = new BSTDictionary<KeyWord>();
		
		File folder = new File("\\Users\\Kristin\\Documents\\GitHub\\CS577-Huffman\\Huffman\\speechdata");
		File[] listOfFiles = folder.listFiles();
		
		// process all files in speech directory
		for (File inFile : listOfFiles) {
			// check if file exits and can be read
			if (!inFile.exists() || !inFile.canRead()) {
				System.out.println("Improper file: " + inFile.getName());
				System.exit(-1);
			}
			try {
				// open file and parse all words
				stdin = new Scanner(inFile);
			    smooth(dictionary, stdin);
			} catch (FileNotFoundException ex) {
				System.out.println("Unable to find file: " + inFile.getName());
				System.exit(-1);
			}
		}
		
		File inFile = new File("\\Users\\Kristin\\Documents\\GitHub\\CS577-Huffman\\Huffman\\speechdata\\2010_01_27_5706.txt");
		if (!inFile.exists() || !inFile.canRead()) {
			System.out.println("Improper file: " + inFile.getName());
			System.exit(-1);
		}
		try {
			stdin = new Scanner(inFile);
			parseFile(dictionary, stdin);
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to find file: " + inFile.getName());
			System.exit(-1);
		}
		HuffmanTree<String> tree = makeHuffman(dictionary);
		
		System.out.println(dictionary.size());
	}
	
	/*
	 * Create the Huffman code tree using the words in all of the speeches
	 */
	public static HuffmanTree<String> makeHuffman(BSTDictionary<KeyWord> dictionary) {
		
		
		// iterate over all words in the dictionary
		Iterator<KeyWord> it = dictionary.iterator();
		// queue of words sorted by frequency
		PriorityQueue<KeyWord> queue = new PriorityQueue<KeyWord>();
		KeyWord keyword;
		KeyWord keyword2;
		// place all keywords into priority queue, least # occurrences at the head
		while(it.hasNext()) {
			keyword = (KeyWord)it.next();
			queue.add(keyword);
		}
		
		Iterator<KeyWord> itq = queue.iterator();
		// make first node in list the root
		HuffmanNode<String> node = new HuffmanNode<String>();
		keyword = (queue.peek());
		node.setData(keyword.getWord());
			
		while(queue.size() >= 2) {
			keyword = itq.next();
			keyword2 = itq.next();
			
		}
		
		return null;
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
				
				boolean done = isLetter(word);
				
				// if letter was found, increment its value in the dictionary
				if (done) {
					KeyWord keyword;
					try {
						// try inserting new keyword
						keyword = new KeyWord(word.toLowerCase());
						keyword.increment();
						dictionary.insert(keyword);
					} catch (DuplicateKeyException e) {
						// if already in dictionary, do nothing
						;
					}
				}
			} // end outer while
		}
	}
	
	/*
	 * Takes each line in a file and adds each word to the dictionary, or, if already 
	 * present, increments its value.
	 */
	public static void parseFile(BSTDictionary<KeyWord> dictionary, Scanner in) {
		
		while (in.hasNext()) {
			String line = in.next();
			String[] tokens = line.split("[ ]+");
			for (String word : tokens) {
				
				int first = findFirst(word);
				int last = findLast(word, first);
				
				// trim string so it starts and ends with letter/digit
				word = word.substring(first, last + 1);
				
				boolean done = isLetter(word);
				
				// if letter was found, increment its value in the dictionary
				if (done) {
					KeyWord keyword;
					try {
						// try inserting new keyword
						keyword = new KeyWord(word.toLowerCase());
						keyword.increment();
						dictionary.insert(keyword);
					} catch (DuplicateKeyException e) {
						// if already in dictionary, just increment occurences
						keyword = dictionary.lookup(new KeyWord(word.toLowerCase()));
						keyword.increment();
					}
				}
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
	
	/*
	 * Returns true if there is at least one letter in the given word
	 */
	public static boolean isLetter(String word) {
		// make sure there is at least one letter in the word
		boolean done = false;
		int first = 0;
		while (first < word.length() && !done) {
			if (Character.isLetter(word.charAt(first))) {
				done = true;
			}
			else {
				first++;
			}
		} // end while
		return done;
	}
}
