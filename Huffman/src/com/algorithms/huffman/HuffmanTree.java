package com.algorithms.huffman;

import java.util.*;

public class HuffmanTree<T> {

	private HuffmanNode<T> root;
	private int height;
	
	public HuffmanTree(T rootData) {
		root = new HuffmanNode<T>();
		root.setData(rootData);
		root.removeChildren();
	}
	
	public void setRoot(HuffmanNode<T> node) {
		this.root = node;
	}
	
	public HuffmanNode<T> getRoot() {
		return this.root;
	}
	
	public int getHeight(){
		return this.height;
	}
	
	public void addRight(HuffmanNode<T> parent, HuffmanNode<T> child) {
		if (parent.leftChild == null && parent.rightChild == null) {
			this.height++;
		}
		child.setHeight(this.height);
		parent.rightChild = child;
	}
	
	public void addLeft(HuffmanNode<T> parent, HuffmanNode<T> child) {
		if (parent.leftChild == null && parent.rightChild == null) {
			this.height++;
		}
		child.setHeight(this.height);
		parent.leftChild = child;
	}
}
