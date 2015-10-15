
/**
 * The partition is the most tricky part for its index. To princeton version, 
 * this one lacks a assertion for a sorted array. The usage of shuffle is to
 * ensure that the soring is not fell into the worst case. With random shuffle,
 * every case to turn up with the same probability.
 * 
 * @author Feng Liu
 * @version 8/14/2015
 */
public class MyQuick
{
    public static void sort(Comparable[] a)
    {
        // throw excpetion if array is sorted
        assert !isSorted(a);

        // quick has too much overhead for tiny subarrays
        if (a.length <= 11) {
            insertion(a);
            return;
        }

        StdRandom.shuffle(a);
        sort(a, 0, a.length-1);
    }

    private static void insertion(Comparable[] a) {
        StdOut.println("insertion is invoded");
        for (int i = 1; i < a.length; i++) {
            for (int j = i; j > 0 && less(a[j], a[j-1]); j--) {
                exch(a, j-1, j);
            }
        }
    }

    private static boolean isSorted(Comparable[] a){
        for (int i = 1; i < a.length; i++) {
            if (less(a[i], a[i-1])) return false;
        } 
        return true;
    }

    private static void sort(Comparable[] a, int lo, int hi) {
        if (hi <= lo) return;

        int mid = partition(a, lo, hi);
        sort(a, lo, mid-1);
        sort(a, mid+1, hi);
    }

    private static int partition(Comparable[] a, int lo, int hi) {
        int k = lo;
        int i = lo+1;
        int j = hi;

        while (true) {
            // greater than k in right and less than k in left
            while (less(a[k], a[j])) { //scan until find less than k in right
                j -= 1;
                if (j <= lo) break;
            }

            while (less(a[i], a[k])) { //scan until find greater than k in left
                i += 1;
                if (i >= hi) break; 
                // if i == hi, for sometimes i can be out of bound!
            }

            if (j <= i) { //until i and j cross and the partition is done
                //uses <=, if <, the outer while will be never broken!
                exch(a, k, j); //exch to put k back to the pivot
                break;
            } 

            exch(a, i, j); //exch to make less in left and greater in right
        }

        return j; // return the pivot
    }

    private static boolean less(Comparable x, Comparable y) {
        return x.compareTo(y) < 0;
    }

    private static void exch(Comparable[] a, int i, int j) {
        Comparable temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    public static void main(String args[]) {
        String[] a = StdIn.readAllStrings();
        MyQuick.sort(a);

        for (int i = 0; i < a.length; i++) {
            StdOut.println(a[i]);
        }

        MyQuick.sort(a);
    }
}
