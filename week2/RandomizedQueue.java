/****************************************************************************
 *  Compilation:  javac RandomizedQueue.java
 *  Execution:  java RandomizedQueue < input2.txt
 *  Dependencies: StdIn.java StdOut.java StdRandom.java
 *
 *  Randomized Queue in FIFO order
 *
 *  % java RandomizedQueue < input2.txt
 *  
 ****************************************************************************/

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;

/**
 *  The randomized queue class represents the queue data structure except 
 *  randomly removing item from the queue which uses generic item. It 
 *  supports the iterator throw the items in random order and enqueue
 *  and dequeue operations.
 * 
 *  @author Feng Liu
 *  @date 7/4/2015
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] queue;   // array list of queue
    private int N;          // number of elements currently on queue

    /**
     * Construct an empty randomized queue
     */
    public RandomizedQueue() {
        N = 0;
        queue = (Item[]) new Object[2];
    }

    /**
     * Is the queue empty?
     */ 
    public boolean isEmpty() {
        return N == 0;
    }

    /**
     * Return the number of items on the queue
     */
    public int size() {
        return N;
    }

    /**
     * Shrink or expand the array size
     */
    private void resize(int newSize) {
        Item[] temp = (Item[]) new Object[newSize];
        for (int i = 0; i < N; i++) {
            temp[i] = queue[i];
        }
        queue = temp;
    }

    /**
     * Add the item
     */
    public void enqueue(Item item) {
        if (item == null) throw new java.lang.NullPointerException();
        if (N == queue.length) resize(N*2); //resize the array at beginning
        queue[N] = item;
        N++;
    }

    /**
     * Remove and return a random item
     */
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Queue is empty");
        int index = StdRandom.uniform(N); // random 0 to N-1
        Item item = queue[index];
        if (item == null) throw new NoSuchElementException();
        N--;
        queue[index] = queue[N];        // fill the removed spot
        queue[N] = null;                // void loitering
        if (N > 0 && N == queue.length/4) resize(queue.length/2);

        return item;
    }

    /**
     * Return (but do not remove) a random item
     */ 
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException("Queue is empty");
        int index = StdRandom.uniform(N); // random number 0 to N-1
        Item item = queue[index];
        if (item == null) throw new NoSuchElementException();
        return item;
    }

    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    /**
     * Return an independent iterator over items in random order
     */ 
    private class ArrayIterator implements Iterator<Item> {
        private int i;
        private int currentN;
        private int[] shuffle;

        public ArrayIterator() {
            i = 0;                      // index of array list
            currentN = N;               // for exception
            shuffle = new int[N];
            for (int j = 0; j < N; j++) {
                shuffle[j] = j;
            }
            StdRandom.shuffle(shuffle);
            //StdRandom.shuffle(queue, 0, N-1); // why print twice?
        }

        public boolean hasNext() {
            if (currentN != N) throw new ConcurrentModificationException();
            return i < N;
        }

        public void remove() { throw new UnsupportedOperationException(); }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            if (currentN != N) throw new ConcurrentModificationException();
            Item item = queue[shuffle[i]];
            i++;
            return item;
        }
    }

    // unit testing
    public static void main(String[] args) {
    } 
}
