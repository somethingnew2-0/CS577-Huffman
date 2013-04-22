package com.algorithms.huffman;

import java.util.ArrayList;
import java.util.List;

/*
 * Class allows modification of all information about nodes
 */
public class HuffmanNode<T> implements Comparable {
	
		private T data;
		private double frequency;
		private HuffmanNode<T> parent;
		private List<HuffmanNode<T>> children;
		
		public void setData(T data) {
			this.data = data;
		}
		
		public void setFreq(double freq) {
			this.frequency = freq;
		}
		
		public T getData() {
			return this.data;
		}
		
		public double getFreq() {
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
		
		public int compareTo(Object other) {
			if (other instanceof HuffmanNode) {
				other = (HuffmanNode<T>)other;
			
				if (this.frequency < ((HuffmanNode<T>) other).getFreq()) {
					return 1;
				}
				else if (this.frequency > ((HuffmanNode<T>) other).getFreq()) {
					return -1;
				}
				else {
					return 0;
				}
			} else {
				return 0;
			}
		}
	
}
