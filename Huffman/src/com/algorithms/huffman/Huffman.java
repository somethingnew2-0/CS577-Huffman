package com.algorithms.huffman;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Huffman {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner in = null; // for input from text file
		PrintStream out = null; // for output to html file
		// dictionary used to store keywords
		BSTDictionary<KeyWord> dictionary = new BSTDictionary<KeyWord>();

		// Make sure the input file exists and can be read
		try {
			File inFile = new File(args[0]);
			if (!inFile.exists() || !inFile.canRead()) {
				System.err.println("Error: cannot access file " + args[0]);
				System.exit(1);
			}

			in = new Scanner(inFile);
		} catch (FileNotFoundException e) {
			System.err.println("Error: cannot access file " + args[0]);
			System.exit(1);
		}

//		// Make sure we can write to the given output filename
//		try {
//			// write to given file name
//			File outFile = new File(args[1]);
//			// warn if it exists
//			if (outFile.exists()) {
//				System.err.println("Warning: file " + args[1]
//						+ " already exists, will be overwritten");
//			}
//			// stop if we can't write to the file
//			if (outFile.exists() && !outFile.canWrite()) {
//				System.err.println("Error: cannot write to file " + args[1]);
//				System.exit(1);
//			}
//
//			// open the file to write to
//			out = new PrintStream(outFile);
//
//		} catch (FileNotFoundException e) {
//			// catch if we can't write
//			System.err.println("Error: cannot write to file " + args[1]);
//			System.exit(1);
//		}

		// Process the input file line by line
		// Note: the code below just prints out the words contained in each
		// line. You will need to replace that code with code to generate
		// the dictionary of KeyWords.
		while (in.hasNext()) {
			String line = in.nextLine();
			List<String> words = parseLine(line);

			// Iterate through all the word on this line
			KeyWord keyword;
			for (String word : words) {
				try {
					// Try inserting the keyword
					keyword = new KeyWord(word.toLowerCase());
					keyword.increment();
					dictionary.insert(keyword);
				} catch (DuplicateKeyException e) {
					// If the keyword is already in the dictionary just
					// increment the occurrences
					keyword = dictionary
							.lookup(new KeyWord(word.toLowerCase()));
					keyword.increment();
				}
			}

		} // end while
	}
	
	private static List<String> parseLine(String line) {
		String[] tokens = line.split("[ ]+");
		ArrayList<String> words = new ArrayList<String>();
		for (int i = 0; i < tokens.length; i++) { // for each word

			// find index of first digit/letter
			boolean done = false;
			int first = 0;
			String word = tokens[i];
			while (first < word.length() && !done) {
				if (Character.isDigit(word.charAt(first))
						|| Character.isLetter(word.charAt(first)))
					done = true;
				else
					first++;
			}

			// find index of last digit/letter
			int last = word.length() - 1;
			done = false;
			while (last > first && !done) {
				if (Character.isDigit(word.charAt(last))
						|| Character.isLetter(word.charAt(last)))
					done = true;
				else
					last--;
			}

			// trim from beginning and end of string so that is starts and
			// ends with a letter or digit
			word = word.substring(first, last + 1);

			// make sure there is at least one letter in the word
			done = false;
			first = 0;
			while (first < word.length() && !done)
				if (Character.isLetter(word.charAt(first)))
					done = true;
				else
					first++;
			if (done)
				words.add(word);
		}

		return words;
	}

}
