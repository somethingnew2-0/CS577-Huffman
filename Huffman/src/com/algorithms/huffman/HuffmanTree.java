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
	
}
