package com.algorithms.huffman;

/**
 * A KeyWord consists of a word and an integer (representing the number of
 * occurrences of the word). A word is a non-empty sequence of characters whose
 * letters are all lower-case.
 * 
 * <p>
 * Bugs: none known
 * 
 * @author Peter Collins
 */
public class KeyWord implements Comparable<KeyWord> {
	private int occurrences; // Representation of the number of occurrences
	private String word; // String representation of lowercase word given

	/**
	 * Constructs a KeyWord with the given word (converted to lower-case) and
	 * zero occurences.
	 */
	public KeyWord(String word) {
		this.word = word;
		this.occurrences = 0;
	}

	/**
	 * Compares the KeyWord with the one given.
	 * 
	 * @param other
	 *            the other Keyword to compare to.
	 * @return 1 if this keyword has a higher number occurrences, 0 if they're
	 *         the same, and -1 if the other keyword has the higher number of
	 *         occurrences
	 */
	@Override
	public int compareTo(KeyWord other) {
		return this.word.compareTo(other.getWord());
	}

	/**
	 * Compares this KeyWord to the specified object.
	 * 
	 * @param other
	 *            object to compare to
	 * @return true is they're the same, otherwise false
	 */
	@Override
	public boolean equals(Object other) {
		// Make sure the other object is a KeyWord before comparing
		if (other instanceof KeyWord) {
			// Compare only the word string
			if (this.word.equals(((KeyWord) other).getWord())) {
				return true;
			}
		}
		return false;

	}

	/**
	 * Returns the number of occurrences for this KeyWord.
	 * 
	 * @return integer of number of occurrences
	 */
	public int getOccurrences() {
		return occurrences;
	}

	/**
	 * Returns the word for this KeyWord.
	 * 
	 * @return string of the word
	 */
	public String getWord() {
		return word;
	}

	/**
	 * Adds one to the number of occurrences for this KeyWord.
	 */
	public void increment() {
		occurrences++;
	}

}
