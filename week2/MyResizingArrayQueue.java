import java.util.Iterator;
import java.util.NoSuchElementException;

/*************************************************************************
 *  Compilation:  javac MyResizingArrayQueue.java
 *  Execution:    java MyResizingArrayQueue < input.txt
 *
 *  A generic stack, implemented using a linked list. Each stack
 *  element is of type Item.
 *  
 *  % more input.txt 
 *  to be or not to - be - - that - - - is
 *
 *  % java MyResizingArrayQueue < input.txt
 *  to be or not to be (2 left on stack)
 *
 *************************************************************************/
public class MyResizingArrayQueue<Item> implements Iterable<Item>
{
    private Item[] queue;
    private int N;      // number of elements on queue
    private int head;       // index of enqueue
    private int tail;       // index of dequeue

    /**
     * Construct an generic empty queue
     */
    public MyResizingArrayQueue()
    {
        //cast needed since no generic array creation in Java
        head = 0;
        tail = 0;
        N = 0;
        queue = (Item[]) new Object[2];
    }

    /**
     * Resize the array
     */
    private void resize(int newSize) {
        Item[] newQueue = (Item[]) new Object[newSize];
        for (int i = 0; i < N; i++) {
            newQueue[i] = queue[(head + i) % queue.length];
        }
        queue = newQueue;
        head = 0;
        tail = N;
    }

    /**
     * Add item to the last of queue
     */
    public void enqueue(Item item) {
        if (N == queue.length) resize(queue.length*2);
        queue[tail] = item;
        N++;
        tail++;
        if (tail == queue.length) tail = 0;             // wrap-around
    }

    /**
     * Remove item from the frist of queue
     */
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        Item item = queue[head];
        queue[head] = null;
        N--;
        head++;
        if (head == queue.length) head = 0;             // wrap-around
        if (N > 0 && N <= queue.length/4) resize(queue.length/2);

        return item;
    }

    public Iterator<Item> iterator() { return new arrayIterator(); }

    private class arrayIterator implements Iterator<Item> {
        private int i = 0;
        public boolean hasNext() { return i < N; }

        public void remove() { throw new UnsupportedOperationException(); }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = queue[(i + head) % queue.length];        
            i++;
            return item;
        }
    }

    /**
     * Return the number of element currently in the queue
     */
    public int size() {
        return N;
    }

    /**
     * Return the result whether queue is empty
     */
    public boolean isEmpty(){
        return N == 0;
    }

    /**
     * Unit test
     */
    public static void main(String[] args) {
        MyResizingArrayQueue<String> q = new MyResizingArrayQueue<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-")) q.enqueue(item);
            else if (!q.isEmpty()) StdOut.print(q.dequeue() + " ");
        }

        StdOut.println("(" + q.size() + " left on queue)");

        for (String str : q) {
            StdOut.println(str);
        }
        
        assert 1 == 0;

    }
}
