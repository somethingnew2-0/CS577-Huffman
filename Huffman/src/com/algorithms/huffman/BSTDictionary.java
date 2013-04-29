package com.algorithms.huffman;

import java.util.Iterator;

/**
 * BSTDictionary implements a binary search tree (BST) implementation of a
 * Dictionary.
 * 
 * <p>
 * Bugs: none known
 * 
 * @author Peter Collins
 */
public class BSTDictionary<K extends Comparable<K>> {
	private BSTNode<K> root; // the root node
	private int numItems; // the number of items in the dictionary

	/**
	 * Contructs a new BSTDictionary with no items in it
	 */
	public BSTDictionary() {
		numItems = 0;
	}

	public void insert(K key) throws DuplicateKeyException {
		root = insert(root, key);
	}

	/**
	 * Private method to recursively search where to insert the given K in the
	 * given tree
	 * 
	 * @param n
	 *            the tree to insert the key into
	 * @param key
	 *            the key to insert
	 * @return the new root of the given tree n
	 */
	private BSTNode<K> insert(BSTNode<K> n, K key) throws DuplicateKeyException {
		// Base cases
		// We've found the spot to put the key, increment the number of items,
		// and add the key to the tree
		if (n == null) {
			numItems++;
			return new BSTNode<K>(key, null, null);
		}

		// If the key is already in the tree throw a DuplicateKeyException
		if (n.getKey().equals(key)) {
			throw new DuplicateKeyException();
		}

		// Recursive cases
		if (key.compareTo(n.getKey()) < 0) {
			// add k to the left subtree
			n.setLeft(insert(n.getLeft(), key));
			return n;
		} else {
			// add k to the right subtree
			n.setRight(insert(n.getRight(), key));
			return n;
		}
	}

	public K lookup(K key) {
		return lookup(root, key);
	}

	/**
	 * Private method to recursively search for the given key in the given tree
	 * 
	 * @param n
	 *            root of tree to search through
	 * @param key
	 *            to search for
	 * @return the key found in the tree
	 */
	private K lookup(BSTNode<K> n, K key) {
		// Base cases
		// Not found in the tree, return null
		if (n == null) {
			return null;
		}
		// Found return the key searched for
		if (n.getKey().equals(key)) {
			return n.getKey();
		}

		// Recursive cases
		if (key.compareTo(n.getKey()) < 0) {
			// key < this node's key; look in left subtree
			return lookup(n.getLeft(), key);
		} else {
			// key > this node's key; look in right subtree
			return lookup(n.getRight(), key);
		}
	}

	public boolean delete(K key) {
		// Is the key in the tree
		if (lookup(key) != null) {
			// If it is delete it recursively and update numItems to reflect the
			// change
			root = delete(root, key);
			numItems--;
			return true;
		}
		// If not return false, nothing was changed
		return false;
	}

	/**
	 * Private method to recursively search for the given key to delete in the
	 * given tree
	 * 
	 * @param n
	 *            the root of the tree to search through
	 * @param key
	 *            to search for in the tree and delete
	 * @return the new root of the given tree n
	 */
	private BSTNode<K> delete(BSTNode<K> n, K key) {
		// Base cases
		// If the tree is null return null
		if (n == null) {
			return null;
		}

		// Recursive cases
		// We've found the key!
		if (key.equals(n.getKey())) {
			// n is the node to be removed
			// If there are no children return null
			if (n.getLeft() == null && n.getRight() == null) {
				return null;
			}
			// If there is only one child return the non-null one
			if (n.getLeft() == null) {
				return n.getRight();
			}
			if (n.getRight() == null) {
				return n.getLeft();
			}

			// If we get here, then n has 2 children, find the smallest by
			// inorder successor
			// and return that as the new root
			K smallVal = smallest(n.getRight());
			n.setKey(smallVal);
			n.setRight(delete(n.getRight(), smallVal));

			return n;
		}
		// Go left if the key is less then the current root
		else if (key.compareTo(n.getKey()) < 0) {
			n.setLeft(delete(n.getLeft(), key));
			return n;
		}
		// Else go right if the key is greater then the current root
		else {
			n.setRight(delete(n.getRight(), key));
			return n;
		}
	}

	/**
	 * Private method to find the smallest key in the given tree
	 * 
	 * @param n
	 *            the root node to search for the smallest node in
	 * @return the smallest key in the subtree rooted at n
	 */
	private K smallest(BSTNode<K> n) {
		if (n == null) {
			return null;
		}
		if (n.getLeft() == null) {
			return n.getKey();
		} else {
			return smallest(n.getLeft());
		}
	}

	public Iterator<K> iterator() {
		return new BSTDictionaryIterator<K>(root);
	}

	public boolean isEmpty() {
		return numItems == 0;
	}

	public int size() {
		return numItems;
	}

}
