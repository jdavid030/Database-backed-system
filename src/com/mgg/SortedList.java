package com.mgg;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This class is the template for the sorted list ADT that is implemented.
 * Has methods such to add, remove, and retrieve. 
 * 
 * @author jarondavid
 *
 * @param <T>
 */
public class SortedList<T> implements Iterable<T> {
	
	private Comparator<T> cmp;
	private int size;
	private T arr[];

	@SuppressWarnings("unchecked")
	public SortedList(Comparator<T> cmp) {
		this.cmp = cmp;
		this.arr =  (T[]) new Object[10];
		this.size = 0;
	}
	
	/**
	 * Gets the size of the list
	 * 
	 * @return
	 */
	public int size() {
		return this.size;
	}
	
	/** 
	 * Adds an object to the list based on the comparator so the order is maintained
	 * 
	 * @param object
	 */
	public void add(T object) {
		int index = 0;
		
		if (this.size == 0){
			this.arr[0] = object;
			this.size++;
			return;
		}
		
		// adds more capacity to the array if it fills up
		if(this.size == this.arr.length - 1) {
			this.arr = Arrays.copyOf(this.arr, this.arr.length + 10);
		}
		
		//find the index where the new object should be inserted
		while(index < this.size) {
			if(this.cmp.compare(object, this.arr[index]) < 0) {
				break;
			}
			index++;
		}
		
		//shifts the list down from the index so the new object can be inserted
		// where it belongs
		for(int i = this.size-1; i>=index; i--) {
			this.arr[i+1] = this.arr[i];
		}
		this.arr[index] = object;
		this.size++;
		return;
	}
	
	/**
	 * This method gets the object in the given index
	 * 
	 * @param index
	 * @return
	 */
	public T get(int index) {
		if(index < 0 || index >= this.size) {
			throw new IndexOutOfBoundsException("Index " + index + " out of bounds");
		}
		return this.arr[index];
	}
	
	/**
	 * This method gets the index that a specific object is located in
	 * 
	 * @param object
	 * @return
	 */
	public int getIndexOf(T object) {
		for(int i=0; i<this.size; i++) {
			if(this.arr[i].equals(object)) {
				return i;
			}
		}
		throw new NoSuchElementException("This element is not in the list");
	}
	
	/**
	 * This method removes the given object from the list.
	 * @param object
	 */
	public void remove(T object) {
		int index = getIndexOf(object);
		
		for(int i=index+1; i<this.size; i++) {
			this.arr[i-1] = this.arr[i];
		}
		this.size--;
	}
	
	/**
	 * This method prints out the elements in the sorted list
	 */
	public String toString() {
		if(this.size == 0) {
			return "empty";
		}
		
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<this.size-1; i++) {
			sb.append(this.arr[i]);
			sb.append(", ");
		}
		sb.append(this.arr[this.size-1]);
		return sb.toString();
	}
	
	/**
	 * Iterable interface is implemented so a for-each loop 
	 * can be used to output the elements in the sorted list ADT.
	 * 
	 * @author jarondavid
	 *
	 * @param <T>
	 */
	public class CustomerIterator implements Iterator<T>{

		int index = 0;
	    SortedList<T> list;
		
		public CustomerIterator(SortedList<T> list) {
			this.list = list;
		}
		@Override
		public boolean hasNext() {
			if(list.size() >= index+1) {
				return true;
			}
			return false;
		}

		@Override
		public T next() {
			return list.get(index++);
		}
		
	}

	@Override
	public Iterator<T> iterator() {
		return new CustomerIterator(this);
	}
	
}
