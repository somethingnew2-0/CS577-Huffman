package com.algorithms.huffman;

import java.util.ArrayList;
import java.util.List;

/*
 * Class allows modification of all information about nodes
 */
public class HuffmanNode<T> {
	
		private T data;
		private int frequency;
		private HuffmanNode<T> parent;
		private List<HuffmanNode<T>> children;
		
		public void setData(T data) {
			this.data = data;
		}
		
		public void setFreq(int freq) {
			this.frequency = freq;
		}
		
		public T getData() {
			return this.data;
		}
		
		public int getFreq() {
			return this.frequency;
		}
		
		public void setParent(HuffmanNode<T> parent) {
			this.parent = parent;
		}
		
		public void addChild(HuffmanNode<T> child) {
			this.children.add(child);
		}
		
		public void removeChild(HuffmanNode<T> child) {
			this.children.remove(child);
		}
		
		public void removeChildren() {
			this.children = new ArrayList<HuffmanNode<T>>();
		}
	
}
