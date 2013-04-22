package com.algorithms.huffman;

import java.util.*;

public class HuffmanTree<T> {

	private HuffmanNode<T> root;
	
	public HuffmanTree(T rootData) {
		root = new HuffmanNode<T>();
		root.setData(rootData);
		root.removeChildren ();
	}
	
	public void setRoot(HuffmanNode<T> node) {
		this.root = node;
	}
	
	public HuffmanNode<T> getRoot() {
		return this.root;
	}
	
	public int getDepth(HuffmanNode<T> node) {		
		if (node == null) {
			System.out.println("node is null");
			return 0;
		}
		else {
			return 1 + Math.max(getDepth(node.rightChild), getDepth(node.leftChild));
		}
	}
}
