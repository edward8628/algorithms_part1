/*************************************************************************
 *  Compilation:  javac MyLinkedQueue.java
 *  Execution:    java MyLinkedQueue < input.txt
 *
 *  A generic stack, implemented using a linked list. Each stack
 *  element is of type Item.
 *  
 *  % more input.txt 
 *  to be or not to - be - - that - - - is
 *
 *  % java MyLinkedQueue < input.txt
 *  to be or not to be (2 left on stack)
 *  
 *************************************************************************/

import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyLinkedQueue<Item> implements Iterable<Item> {
    private Node<Item> first, last; // beginning and end of queue
    private int size;           // number of elements in queue

    private static class Node<Item>{
        private Item item;
        private Node<Item> next;
    }

    public MyLinkedQueue() {
        first = null;
        last = null;
        size = 0;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public int size() {
        return size;
    }

    public void enqueue(Item item) {
        Node<Item> oldLast = last;        //temp  
        last = new Node<Item>();          //new node
        last.item = item;           //new node item is new item
        last.next = null;           //new node next points to nothing
        if(isEmpty()) first = last; //the first node
        else oldLast.next = last;   //in second stage, first and oldLast is same node
        size++;
    }

    public Item dequeue() {
        Item item = first.item;
        first = first.next;
        if(isEmpty()) last = null;  // to avoid loitering
        size--;
        return item;
    }

    public Iterator<Item> iterator() { return new ListIterator<Item>(first);}

    private class ListIterator<Item> implements Iterator<Item> {
        private Node<Item> current;

        public ListIterator(Node<Item> first) {
            current = first;
        }

        public boolean hasNext() { return current != null; }

        public void remove() {throw new UnsupportedOperationException(); }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    public static void main(String[] args) {
        MyLinkedQueue<String> s = new MyLinkedQueue<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-")) s.enqueue(item);
            else if (!s.isEmpty()) StdOut.print(s.dequeue() + " ");
        }

        StdOut.println();
        for (String str : s) {
            StdOut.println(str);
        }

        StdOut.println("(" + s.size() + " left on queue)");
    }
}