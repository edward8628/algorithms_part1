
/**
 * Binary Search algorithm to search a value in a sorted array
 * 
 * @author Feng Liu
 * @version 8/17/2015
 */
public class MyBinarySearch
{
    public static boolean search(Comparable[] a, Comparable target)
    {
        return search(a, target, 0, a.length-1);
    }

    private static boolean search(Comparable[] a, Comparable target, int lo, int hi) {
        if (lo > hi) return false; // >= not work but only > works
        int mid = lo + (hi-lo)/2;  // forgot to divide by 2
        //StdOut.println(mid);
        int cmd = a[mid].compareTo(target);
        if      (cmd > 0) return search(a, target, lo, mid-1); // go to left
        else if (cmd < 0) return search(a, target, mid+1, hi); // go to right
        else              return true;
    }
    
    public static void main(String args[]) {
        Integer[] a = new Integer[100];
        for (int i = 0; i < a.length; i++) {
            a[i] = a.length-1-i;
        }
        
        MyMerge.sort(a);
        
        for (int i = 0; i < a.length; i++) {
            if (!MyBinarySearch.search(a, i)) {
                StdOut.println("error");
                break;
            }
        }
    }
}
