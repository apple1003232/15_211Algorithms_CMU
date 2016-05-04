/**
 ******************************************************************************
 *                    HOMEWORK, 15-211
 ******************************************************************************
 *                    Amortized Dictionary
 ******************************************************************************
 *
 * Implementation of an Amortized Array-Based Dictionary data structure.
 *
 * This data structure supports duplicates and does *NOT* support storage of
 * null references.
 *
 * Notes:
 * 		-It is *highly* recommended that you begin by reading over all the methods,
 *       all the comments, and all the code that has already been written for you.
 *
 * 		-the specifications provided are to help you understand what the methods
 *       are supposed to accomplish.
 * 		-they are *NOT* descriptions for how you should implement the methods.
 * 		-See the lab documentation & recitation notes for implementation details.
 *
 * 		-Some of the helper methods specify a runtime cost; make sure your
 *       implementation meets that requirement.
 * 		-(Also, obviously, if the lecture notes and/or the lab documentation specifies
 *       a runtime cost for a method, you need to pay attention to that).
 *
 *
 * User ID(s):
 *
 *****************************************************************************/


import static java.util.Arrays.binarySearch;
import java.util.Arrays;


public class Dictionary<E extends Comparable<E>>  implements DictionaryInterface<E>
{
	/*
	 * Keeps track of the number of elements in the dictionary.
	 * Take a look at the implementation of size()
	 */
	private int size;
	/*
	 * The head reference to the linked list of Nodes.
	 * Take a look at the Node class.
	 */
	private Node head;

	/**
	 * Creates an empty dictionary.
	 */
	public Dictionary()
	{
		size = 0;
		head = null;
	}

	/**
	 * Adds e to the dictionary, thus making contains(e) true.
	 * Increments size to ensure size() is correct.
	 */
	public void add(E e)
	{
		if(e == null)
		{
			throw new NullPointerException("Error passing null object to add");
		}

		/*
		 * Your code goes here...
		 */
		
		Comparable[] arr = new Comparable[1];
		
		arr[0] = e;
		
		Node node = new Node(0, arr, null);
		if(head == null)
			head = node;
		else{
			node.next = head;
			head = node;
		}
		
		size ++;
		
		mergeDown();

//		throw new RuntimeException("You need to implement this method!");
	}

	/**
	 * Removes one instance of e from the dictionary, if the item is in the dictionary.
	 * Otherwise, it does nothing.
	 */
	public void remove(E e)
	{
		if(e == null)
		{
			throw new NullPointerException("Error passing null object to remove");
		}

		/*
		 * Your code goes here...
		 */
		if(!this.contains(e)) return;
		if(head.contains(e)){
			int k = head.power;
			if(k == 0){
				head = head.next;
				size --;
				return;
			}
			Comparable[] arr = new Comparable[power(2, k) - 1];
			int i = 0, j = 0;
			while(i < arr.length && j < head.array.length){
				if(head.array[j].equals(e)){
					j++;
					continue;
				}
				arr[i] = head.array[j];
				i ++;
				j ++;
			}
			java.util.Queue<Comparable[]> q = splitUp(arr, k);
			head = head.next;
			while(!q.isEmpty()){
				Node node = new Node(k-1, q.remove(), head);
				head = node;
				k --;
			}
			size --;
			return;
		}
			
		Node tmp = head.next;
		while(tmp != null){
			if(tmp.contains(e)){
				for(int i = 0; i < tmp.array.length; i++){
					if(tmp.array[i].equals(e)){
						for(int j = i; j < tmp.array.length - 1; j++){
							tmp.array[j] = tmp.array[j + 1];
						}
						break;
					}
				}
				int big = head.array.length - 1;
				if(tmp.array[tmp.array.length - 2].compareTo(head.array[big]) <= 0){
					tmp.array[tmp.array.length - 1] = head.array[big];
				}else{
					for(int i = 0; i < tmp.array.length - 1; i++){
						if(tmp.array[i].compareTo(head.array[big]) > 0){
							for(int j = tmp.array.length - 1; j > i; j--)
								tmp.array[j] = tmp.array[j - 1];
							tmp.array[i] = head.array[big];
							break;
						}
					}
				}
				this.remove((E)head.array[big]);
				return;
			}
			tmp = tmp.next;
		}

		throw new RuntimeException("You need to implement this method!");
	}

	/**
	 * Returns true if the dictionary contains an element equal to e, otherwise- false.
	 */
	public boolean contains(E e)
	{
		if(e == null)
		{
			throw new NullPointerException("Error passing null object to contain");
		}

		/*
		 * Your code goes here...
		 */
		Node tmp = head;
		while(tmp != null){
			if(tmp.contains(e))
				return true;
			tmp = tmp.next;
		}
		return false;

//		throw new RuntimeException("You need to implement this method!");
	}

	/**
	 * Returns the number of elements in the dictionary equal to e.
	 * This is logically equivalent to the number of times remove(e) needs to be performed
	 * in order for contains(e) to be false.
	 */
	public int frequency(E e)
	{
		if(e == null)
		{
			throw new NullPointerException("Error checking frequency of null object");
		}

		/*
		 * Your code goes here...
		 */
		Node tmp = head;
		int freq = 0;
		while(tmp != null){
			if(tmp.contains(e))
				freq += tmp.frequency(e);
			tmp = tmp.next;
		}
		return freq;

//		throw new RuntimeException("You need to implement this method!");
	}

	/**
	 * Returns the size of the dictionary.
	 */
	public int size()
	{
		return size;
	}

	/**
	 * Returns a helpful string representation of the dictionary.
	 */
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append(size);
		sb.append("\n");
		Node tmp = head;
		while(tmp != null)
		{
			sb.append(tmp.power);
			sb.append(" : ");
			sb.append(tmp.toString());
			sb.append("\n");
			tmp = tmp.next;
		}
		return sb.toString();
	}

	/**
	 * Starting with the smallest array, mergeDown() merges arrays of the same size together until
	 * all the arrays have different size.
	 *
	 * This is very useful for implementing add(e)!
	 */
	private void mergeDown()
	{
		/*
		 * Your code goes here...
		 */
		Node tmp = head;
		while(tmp.next != null){
			if(tmp.array.length == tmp.next.array.length){
				Comparable[] arr = merge(tmp.array, tmp.next.array);
				tmp.array = arr;
				tmp.power ++;
				tmp.next = tmp.next.next;
				continue;
			}
			tmp = tmp.next;
		}

//		throw new RuntimeException("You need to implement this method!");
	}


	/**
	 * Assumes a is sorted.
	 *
	 * Returns the number of elements of a equal to item.
	 *
	 * This is needed for Node's frequency(e).
	 *
	 * Complexity: O(log(a.length) + frequency(item))
	 */
	@SuppressWarnings("unchecked")
	public static int frequency(Comparable[] a, Comparable item)
	{
		/*
		 * Your code goes here...
		 */
		int left = 0, right = a.length - 1;
		int freq = 0;
		while(left <= right){
			int mid = (left + right)/2;
			if(a[mid].equals(item)){
				while(a[mid].equals(item)){
					mid --;
					if(mid < 0)
						break;
				}
				mid ++;
				while(a[mid].equals(item)){
					freq ++;
					mid ++;
					if(mid > a.length - 1)
						break;
				}
				return freq;
			}
			if(a[mid].compareTo(item) < 0){
				left = mid + 1;
				continue;
			}else{
				right = mid;
				continue;
			}
		}
		return freq;
//		throw new RuntimeException("You need to implement this method!");
	}


	/**
	 * When a and b are sorted arrays, merge(a,b) returns a sorted array
	 * that has length (a.length+b.length) than contains the elements
	 * of a and the elements of b.
	 *
	 * This is useful for implementing the mergeDown() method.
	 *
	 * Complexity: O(a.length + b.length)
	 */
	@SuppressWarnings("unchecked")
	public static Comparable[] merge(Comparable[] a, Comparable[] b)
	{
		/*
		 * Your code goes here...
		 */
		Comparable[] res = new Comparable[a.length + b.length];
		int i = 0, j = 0;
		while(i < a.length && j < b.length){
			if(a[i].compareTo(b[j]) < 0){
				res[i + j] = a[i];
				i ++;
				continue;
			}else{
				res[i + j] = b[j];
				j ++;
				continue;
			}
		}
		if(i >= a.length && j < b.length){
			while(j < b.length){
				res[i + j] = b[j];
				j ++;
			}
		}else{
			while( i < a.length){
				res[i + j] = a[i];
				i ++;
			}
		}
		return res;

//		throw new RuntimeException("You need to implement this method!");
	}

	/**
	 * Returns base^exponent.  This is useful for implementing splitUp(a,k)
	 */
	private static int power(int base, int exponent)
	{
		return (int)(Math.pow(base, exponent));
	}

	/**
	 * Assumes a.length >= 2^k - 1, for the given k.
	 *
	 * Splits the first (2^k -1) elements of a up into k-1 sorted arrays of
	 * length 2^(k-1), 2^(k-2), ..., 2, 1.
	 * Returns a Queue of these arrays (in the above order, i.e. the one with
	 * length 2^(k-1) is at the front).
	 *
	 * This is useful for implementing remove(e).
	 *
	 * Complexity: O(a.length)
	 */
	@SuppressWarnings("unchecked")
	public static java.util.Queue<Comparable[]> splitUp(Comparable[] a, int k)
	{
		/*
		 * We'll just use a LinkedList as a Queue in this fashion.  Take a look at the
		 * API for the java.util.Queue interface.
		 */

		java.util.Queue<Comparable[]> q = new java.util.LinkedList<Comparable[]>();

		/*
		 * Your code goes here...
		 */
		int p = 0;
		while(k > 0){
			int pp = 0;
			Comparable[] arr = new Comparable[power(2, k-1)];
			while(pp < arr.length){
				arr[pp] = a[p];
				pp ++;
				p ++;
//				if(pp == power(2, k-1))
//					break;
			}
			q.add(arr);
			k --;
		}
		return q;
//		throw new RuntimeException("You need to implement this method!");
	}

	/**
	 * Implementation of the underlying array-based data structure.
	 *
	 * Each Node:
	 * 			-knows k, its "power"
	 * 			-has myArray, a sorted array of 2^k elements
	 * 			-knows myNext, the next Node in the linked list of Nodes
	 *
	 * You do *NOT* need to change this class.
	 * It is, however, very important that you understand how it works.
	 * You may add additional methods, although you have been provided with sufficient
	 * functionality needed to implement the dictionary.
	 */
	@SuppressWarnings("unchecked")
	private static class Node
	{
		private int power;
		private Comparable[] array;
		private Node next;

		/**
		 * Creates an Node with the specified parameters.
		 */
		public Node(int power, Comparable[] array, Node next)
		{
			this.power = power;
			this.array = array;
			this.next = next;
		}

		/**
		 * Returns 	-1, if there is no element in the array equal to e
		 * 			 k, otherwise, where array[k] is equal to e
		 */
		public int indexOf(Comparable e)
		{
			return binarySearch(array, e);
		}

		/**
		 * Returns index of the search key, if it is contained in the array;
		 * otherwise, (-(insertion point) - 1)
		 */
		public boolean contains(Comparable e)
		{
			return indexOf(e) > -1;
		}

		/**
		 * Returns the number of elements in the array equal to e
		 */
		public int frequency(Comparable e)
		{
			return Dictionary.frequency(array, e);
		}

		/**
		 * Returns a useful representation of this Node.  (Note how this is used by Dictionary's toString()).
		 */
		public String toString()
		{
			return java.util.Arrays.toString(array);
		}
	}
	
//	public static void main(String[] args){
//		Dictionary<Integer> d = new Dictionary<Integer>();
//		d.add(1);
//		d.add(1);
//		d.add(2);
//		d.add(2);
//		d.add(3);
//		d.add(4);
//		System.out.println(d);
////		int freq = d.frequency(1);
////		d.remove(1);
////		d.remove(1);
////		d.remove(3);
////		System.out.println(d.frequency(1));
//		System.out.println(d);
////		d.remove(3);
////		d.remove(2);
////		d.remove(1);
////		d.remove(2);
//		d.add(6);
//		d.add(6);
//		d.add(10);
//		d.add(3);
//		d.remove(10);
//		d.remove(3);
//		d.remove(3);
//		System.out.println(d);
//	}

}


