/**
 * Unin test methods taking String as inputs
 */

public class TestDeque
{
    private Deque<String> deck;
    private String[] inputs;

    //constructor
    public TestDeque() {
        int size = StdIn.readInt();
        inputs = new String[size];

        for (int i = 0; i < size; i++) {
            inputs[i] = StdIn.readString();
        }
    }

    /**
     * This unit test is used to test following methods:
     * isEmpty(), size(), addFirst() and removeFirst()
     */
    public void addRemoveFirst() {
        deck = new Deque<String>();

        StdOut.println("The size is " + deck.size());
        StdOut.println("Add string to the frist");
        for (int i = 0; i < inputs.length; i++) {
            deck.addFirst(inputs[i]);
        }

        StdOut.println("The size is " + deck.size());
        StdOut.println("Remove string from the frist");
        while (!deck.isEmpty()) {
            String str = deck.removeFirst();
            StdOut.println(str);
        }

        StdOut.println("The size is " + deck.size());
        StdOut.println("The deque is emptied? " + deck.isEmpty());
        StdOut.println();
        deck = null;
    }

    /**
     * This unit test is used to test following methods:
     * isEmpty(), size(), addLast() and removeLast()
     */
    public void addRemoveLast() {
        deck = new Deque<String>();

        StdOut.println("The size is " + deck.size());
        StdOut.println("Add string to the last");
        for (int i = 0; i < inputs.length; i++) {
            deck.addLast(inputs[i]);
        }

        StdOut.println("The size is " + deck.size());
        StdOut.println("Remove string from the last");
        while (!deck.isEmpty()) {
            String str = deck.removeLast();
            StdOut.println(str);
        }

        StdOut.println("The size is " + deck.size());
        StdOut.println("The deque is emptied? " + deck.isEmpty());
        StdOut.println();
        deck = null;
    }

    /**
     * This unit test is used to test 
     * the intermixed add and remove from first and last
     */
    public void intermixed() {
        deck = new Deque<String>();

        StdOut.println("The size is " + deck.size());
        StdOut.println("Add string to the first when even, otherwise when odd");
        //9 7 5 3 1 2 4 6 8 10
        for (int i = 0; i < inputs.length; i++) {
            if (i % 2 == 0) deck.addFirst(inputs[i]); //when even
            else deck.addLast(inputs[i]);           // when odd
        }

        StdOut.println("The size is " + deck.size());
        StdOut.println("Remove string from the last and then first");
        //10 9 8 7 6 5 4 3 2 1
        while (!deck.isEmpty()) {
            String str = deck.removeLast();
            StdOut.println(str);
            str = deck.removeFirst();
            StdOut.println(str);
        }

        StdOut.println();
        StdOut.println("The size is " + deck.size());
        StdOut.println("Add string to the first when even, otherwise when odd");
        for (int i = 0; i < inputs.length; i++) {
            if (i % 2 == 0) deck.addFirst(inputs[i]); //even
            else deck.addLast(inputs[i]); //odd
        }

        StdOut.println("The size is " + deck.size());
        StdOut.println("Remove string from the frist and then last");
        //9 10 7 8 6 5 3 4 1 2 
        while (!deck.isEmpty()) {
            String str = deck.removeFirst();
            StdOut.println(str);
            str = deck.removeLast();
            StdOut.println(str);
        }

        StdOut.println("The size is " + deck.size());
        StdOut.println("The deque is emptied? " + deck.isEmpty());
        StdOut.println();
        deck = null;
    }

    /**
     * This unit test is used to test 
     * the iterator operation when only one foreach
     */
    public void iterator1() {
        deck = new Deque<String>();

        StdOut.println("The size is " + deck.size());
        StdOut.println("Add string to the first when even, otherwise when odd");
        //9 7 5 3 1 2 4 6 8 10
        for (int i = 0; i < inputs.length; i++) {
            if (i % 2 == 0) deck.addFirst(inputs[i]); //when even
            else deck.addLast(inputs[i]);           // when odd
        }

        StdOut.println("Iterator the deque");
        for (String str : deck) StdOut.println(str);

        StdOut.println("The size is " + deck.size());
        StdOut.println("The deque is emptied? " + deck.isEmpty());
        StdOut.println();
        deck = null;
    }

    /**
     * This unit test is used to test 
     * the iterator operation when multiple foreach
     */
    public void iterator2() {
        deck = new Deque<String>();

        StdOut.println("The size is " + deck.size());
        StdOut.println("Add string to the first when even, otherwise when odd");
        //9 7 5 3 1 2 4 6 8 10
        for (int i = 0; i < inputs.length; i++) {
            if (i % 2 == 0) deck.addFirst(inputs[i]); //when even
            else deck.addLast(inputs[i]);           // when odd
        }

        StdOut.println("Iterator the deque");
        for (String str1 : deck) 
            for (String str2 : deck)
                StdOut.println(str1 + " " + str2);

        StdOut.println("The size is " + deck.size());
        StdOut.println("The deque is emptied? " + deck.isEmpty());
        StdOut.println();
        deck = null;        
    }

    /**
     * This unit test is used to test 
     * the currentrent modification exception, should throw exception
     */
    public void concurrent() {
        deck = new Deque<String>();

        StdOut.println("The size is " + deck.size());
        StdOut.println("Add string to the first when even, otherwise when odd");
        //9 7 5 3 1 2 4 6 8 10
        for (int i = 0; i < inputs.length; i++) {
            if (i % 2 == 0) deck.addFirst(inputs[i]); //when even
            else deck.addLast(inputs[i]);           // when odd
        }

        // should throw exception
        StdOut.println("Test ConcurrentModificationException");
        for (String str : deck) {
            StdOut.println(str);
            deck.removeFirst();
        }

        StdOut.println();
        deck = null;
    }

    /**
     * This unit test is used to test 
     * the empty queue when remove from queue, should throw exception
     */
    public void testEmpty2() {
        deck = new Deque<String>();

        StdOut.println("The size is " + deck.size());
        StdOut.println("Add string to the first when even, otherwise when odd");
        //9 7 5 3 1 2 4 6 8 10
        for (int i = 0; i < inputs.length; i++) {
            if (i % 2 == 0) deck.addFirst(inputs[i]); //when even
            else deck.addLast(inputs[i]);           // when odd
        }

        StdOut.println("The size is " + deck.size());
        StdOut.println("Remove all from the deque");
        while (!deck.isEmpty()) {
            String str = deck.removeLast();
            StdOut.println(str);
        }

        StdOut.println("The size is " + deck.size());
        StdOut.println("And remove one more time");
        // should throw exception
        String str = deck.removeLast();
        StdOut.println();
        deck = null;
    }

    /**
     * This unit test is used to test 
     * the empty queue when add string and then remove immediately 
     */
    public void testEmpty1() {
        deck = new Deque<String>();

        StdOut.println("The size is " + deck.size());
        StdOut.println("Add string then remove imediately");
        //9 7 5 3 1 2 4 6 8 10
        for (int i = 0; i < inputs.length; i++) {
            //add first when even, and add last when odd
            if (i % 2 == 0) deck.addFirst(inputs[i]);
            else deck.addLast(inputs[i]);
            StdOut.println("The size is " + deck.size());
            //remove last when even and remove frist when odd
            if (i % 2 == 0) {
                String str = deck.removeLast();
                StdOut.println(str);
            }
            else {
                String str = deck.removeFirst();
                StdOut.println(str);
            }
            StdOut.println("The size is " + deck.size());
        }

        StdOut.println("The size is " + deck.size());
        StdOut.println();
        deck = null;
    }
    
    public Iterable<String> list() {
        deck = new Deque<String>();
        for (int i = 0; i < inputs.length; i++) {
            deck.addFirst(inputs[i]);
        }
        return deck;
    }

    public static void main(String[] args) {
        TestDeque test1 = new TestDeque();

        test1.addRemoveFirst();
        test1.addRemoveLast();
        test1.intermixed();
        test1.iterator1();
        test1.iterator2();
        test1.testEmpty1();
        //test1.concurrent(); // throw exception
        test1.testEmpty2(); // throw exception

    }
}
