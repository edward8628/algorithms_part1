/****************************************************************************
 *  Compilation:  javac Subset.java
 *  Execution:  echo A B C D E F G I | java Subset 3
 *  Dependencies: StdIn.java StdOut.java
 *
 *  Client Test Subset.
 *  
 *  % echo A B C D E F G I | java Subset 3
 *  C
 *  G
 *  A
 ****************************************************************************/
 
/**
 *  The Subset represents the client test to either Deque or RandomizedQueue
 *  and takes a command-line integer k. It reads in a sequeunce of N Strings
 *  from standard input using StdIn.readString() and prints out exactly k of
 *  them uniformly at random. Each item from the sequence can be printed at
 *  most of once, where 0 <= k <= N.
 * 
 *  @author Feng Liu
 *  @date 7/4/2015
 */
public class Subset {
    public static void main(String[] args) {
        RandomizedQueue<String> randq = new RandomizedQueue<String>();
        int k = Integer.parseInt(args[0]);      // randomly print k of items

        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            randq.enqueue(item);
        }

        if (k > 0 && k <= randq.size()) {
            for (int i = 0; i < k; i++) {
                String item = randq.dequeue();
                StdOut.println(item);
            }
        }
    }
}
