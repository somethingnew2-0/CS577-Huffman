package com.algorithms.huffman;

public class BSTNode<K> {
	// Hold the key
	private K key;
	// Hold the pointer to left child
	private BSTNode<K> left;
	// Hold the pointer to right child
	private BSTNode<K> right;

	/**
	 * Initializes the node.
	 * 
	 * @param key
	 *            the key
	 * @param value
	 *            the value
	 */
	public BSTNode(K key) {
		// initialize key and value with given
		this.key = key;
	}

	public BSTNode(K key, BSTNode<K> left, BSTNode<K> right) {
		this(key);
		
		this.left = left;
		this.right = right;
	}

	/**
	 * Sets the key.
	 * @param the key to set 
	 */
	public void setKey(K key) {
		this.key =  key;
	}

	
	/**
	 * Returns the key.
	 * 
	 * @return the key
	 */
	public K getKey() {
		return key;
	}

	/**
	 * Returns a pointer to the left child.
	 * 
	 * @return the left child
	 * @see #setLeft(BSTNode)
	 */
	public BSTNode<K> getLeft() {
		return left;
	}

	/**
	 * Returns a pointer to the right child.
	 * 
	 * @return the right child
	 * @see #setRight(BSTNode)
	 */
	public BSTNode<K> getRight() {
		return right;
	}

	/**
	 * Sets a pointer to a new left child.
	 * 
	 * @param left
	 *            the new left child.
	 * @see #getLeft()
	 */
	public void setLeft(BSTNode<K> left) {
		this.left = left;
	}

	/**
	 * Sets a pointer to a new right child.
	 * 
	 * @param right
	 *            the new right child
	 * @see #getRight()
	 */
	public void setRight(BSTNode<K> right) {
		this.right = right;
	}

	/**
	 * Returns a string representation of this node, for debugging. The result
	 * is the concatenation of the key and the value, separated by a single
	 * space.
	 * 
	 * @return the string representation
	 */
	public String toString() {
		return key.toString();
	}
}