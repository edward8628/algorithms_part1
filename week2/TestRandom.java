/**
 * Unin test methods taking String as inputs
 */

public class TestRandom
{
    private RandomizedQueue<String> randq;
    private String[] inputs;

    //constructor
    public TestRandom() {
        int size = StdIn.readInt();
        inputs = new String[size];

        for (int i = 0; i < size; i++) {
            inputs[i] = StdIn.readString();
        }
    }

    /**
     * This unit test is used to test following methods:
     * isEmpty(), size(), enqueue() and dequeue()
     */
    public void addRemove() {
        randq = new RandomizedQueue<String>();

        StdOut.println("The size is " + randq.size());
        StdOut.println("Add string to the queue");
        for (int i = 0; i < inputs.length; i++) {
            randq.enqueue(inputs[i]);
        }

        StdOut.println("The size is " + randq.size());
        StdOut.println("Remove string from the queue at random");
        while (!randq.isEmpty()) {
            String str = randq.dequeue();
            StdOut.println(str);
        }

        StdOut.println("The size is " + randq.size());
        StdOut.println("The RandomizedQueue is emptied? " + randq.isEmpty());
        StdOut.println();
        randq = null;
    }

    /**
     * This unit test is used to test 
     * the empty random queue when add string and then remove immediately
     */
    public void testEmpty1() {
        randq = new RandomizedQueue<String>();

        StdOut.println("The size is " + randq.size());
        StdOut.println("Add string then remove immediately");
        for (int i = 0; i < inputs.length; i++) {
            //add the string
            randq.enqueue(inputs[i]);
            StdOut.println("After adding, the size is " + randq.size());
            //remove immediately
            String str = randq.dequeue();
            StdOut.println("Remove " + str + " and the size is " + randq.size());
        }

        StdOut.println("The RandomizedQueue is emptied? " + randq.isEmpty());
        StdOut.println();
        randq = null;
    }

    /**
     * This unit test is used to test 
     * the iterator operation when only one foreach
     */
    public void iterator1() {
        randq = new RandomizedQueue<String>();

        StdOut.println("The size is " + randq.size());
        StdOut.println("Add string to the queue");
        for (int i = 0; i < inputs.length; i++) {
            randq.enqueue(inputs[i]);
        }

        StdOut.println("Iterator of RandomizedQueue");
        for (String str : randq) StdOut.println(str);

        StdOut.println("The size is " + randq.size());
        StdOut.println("The RandomizedQueue is emptied? " + randq.isEmpty());
        StdOut.println();
        randq = null;
    }

    /**
     * This unit test is used to test 
     * the iterator operation when multiple foreach
     */
    public void iterator2() {
        randq = new RandomizedQueue<String>();

        StdOut.println("The size is " + randq.size());
        StdOut.println("Add string to the queue");
        for (int i = 0; i < inputs.length; i++) {
            randq.enqueue(inputs[i]);
        }

        StdOut.println("Iterator of RandomizedQueue");
        for (String str1 : randq) 
            for (String str2 : randq)
                StdOut.println(str1 + " " + str2);

        StdOut.println("The size is " + randq.size());
        StdOut.println("The RandomizedQueue is emptied? " + randq.isEmpty());
        StdOut.println();
        randq = null;        
    }

    /**
     * This unit test is used to test 
     * the currentrent modification exception, should throw exception
     */
    public void concurrent() {
        randq = new RandomizedQueue<String>();

        StdOut.println("The size is " + randq.size());
        StdOut.println("Add string to the queue");
        for (int i = 0; i < inputs.length; i++) {
            randq.enqueue(inputs[i]);
        }

        StdOut.println("Test ConcurrentModificationException");
        for (String str : randq) {
            StdOut.println(str);
            str = randq.dequeue();
        }

        StdOut.println();
        randq = null;
    }

    /**
     * This unit test is used to test 
     * the empty queue when remove from queue, should throw exception
     */
    public void testEmpty2() {
        randq = new RandomizedQueue<String>();

        StdOut.println("The size is " + randq.size());
        StdOut.println("Add string to the queue");
        for (int i = 0; i < inputs.length; i++) {
            randq.enqueue(inputs[i]);
        }

        StdOut.println("The size is " + randq.size());
        StdOut.println("Remove string from the queue at random");
        while (!randq.isEmpty()) {
            String str = randq.dequeue();
            StdOut.println(str);
        }

        StdOut.println("The size is " + randq.size());
        StdOut.println("The RandomizedQueue is emptied? " + randq.isEmpty());
        String str = randq.dequeue(); // should throw exception
        StdOut.println();
        randq = null;
    }

    public void sample() {
        randq = new RandomizedQueue<String>();

        StdOut.println("The size is " + randq.size());
        StdOut.println("Add string to the queue");
        for (int i = 0; i < inputs.length; i++) {
            randq.enqueue(inputs[i]);
        }

        StdOut.println("Sample the queue at random and should not change size");
        for(int i = 0; i < randq.size(); i++) {
            StdOut.println(randq.sample());
        }

        StdOut.println("The size is " + randq.size());
        StdOut.println("The RandomizedQueue is emptied? " + randq.isEmpty());
        StdOut.println();
        randq = null;
    }

    public static void main(String[] args) {
        TestRandom test1 = new TestRandom();

        test1.addRemove();
        test1.testEmpty1();
        test1.iterator1();
        test1.iterator2();
        test1.sample();
        //test1.concurrent(); // throw exception
        test1.testEmpty2(); // throw exception
    }
}
