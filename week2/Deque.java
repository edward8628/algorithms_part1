/****************************************************************************
 *  Compilation:  javac Deque.java
 *  Execution:  java Deque < input.txt
 *  Dependencies: StdIn.java StdOut.java
 *
 *  Double-Ended Queue or Deque.
 *
 *  % java Deque < input2.txt
 *  
 ****************************************************************************/

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;

/**
 *  The Deque or Double-Ended Queue class represents adding or removing to or
 *  from either end of the stack or queue like data structure using 
 *  double-linked list of generic items. It supports add frist, add last, 
 *  remove frist and remove last operations. In addition, it supports 
 *  the itorator through the items in FIFO order.
 * 
 *  @author Feng Liu
 *  @date 7/4/2015
 */
public class Deque<Item> implements Iterable<Item> {
    private Node<Item> first;       // the head of the deque
    private Node<Item> last;        // the end of the deque
    private int size;               // the current item in the deque

    // uses static nested class Node to save 8 bytes per Node.
    private static class Node<Item> {
        private Item item;          // item
        private Node<Item> next;    // its next node
        private Node<Item> prev;    // its previous node
    }

    /** 
     * construct an empty deque
     */
    public Deque() {
        first = null;
        last = null;
        size = 0;
    }

    /**
     * is the deque empty?
     */
    public boolean isEmpty() {
        return first == null && last == null;
    }

    /**
     * return the number of items on the deque
     */ 
    public int size() {
        return size;
    }

    /** 
     * add the item to the front
     */
    public void addFirst(Item item) {
        if (item == null) throw new java.lang.NullPointerException();
        Node<Item> oldFirst = first;
        first = new Node<Item>();
        first.item = item;
        first.next = null;
        first.prev = oldFirst;
        size++;

        if (last == null) last = first;
        else oldFirst.next = first;
    }

    /**
     * add the item to the end
     */
    public void addLast(Item item) {
        if (item == null) throw new java.lang.NullPointerException();
        Node<Item> oldLast = last;
        last = new Node<Item>();
        last.item = item;
        last.prev = null;
        last.next = oldLast;
        size++;

        if (first == null) first = last;
        else oldLast.prev = last;
    }

    /**
     * remove and return the item from the front
     */ 
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException(); 
        Item item = first.item;

        if (first.prev == null) { // for loitering?
            first = null;
            last = null;
            size--;
            return item;
        } 

        first = first.prev;
        first.next = null;
        size--;

        return item;
    }

    /**
     * remove and return the item from the end
     */
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException(); 
        Item item = last.item;

        if (last.next == null) { // for loitering?
            last = null;
            first = null;
            size--;
            return item;
        }

        last = last.next;
        last.prev = null;
        size--;

        return item;
    }

    public Iterator<Item> iterator() {
        return new ListIterator<Item>(first);
    }

    /**
     * return an iterator over items in order from front to end
     */ 
    private class ListIterator<Item> implements Iterator<Item> {
        private Node<Item> current;
        private int currentSize;

        public ListIterator(Node<Item> first) {
            current = first;
            currentSize = size;
        }

        public boolean hasNext() { 
            if (currentSize != size) throw new ConcurrentModificationException();
            return current != null; 
        }

        public void remove() { throw new UnsupportedOperationException(); }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            if (currentSize != size) throw new ConcurrentModificationException();
            Item item = current.item;
            current = current.prev;
            return item;
        }
    }

    // unit testing
    public static void main(String[] args) {
    }
}
