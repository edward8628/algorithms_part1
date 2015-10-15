
/**
 * I had a wrong stucture to do the merge sort in first place. I fixed the methods 
 * after with the consideration of the lecture. The recursion didn't cost me too 
 * much but the mechanism in merge. However, it uses a auxiliary array to help 
 * the merging and makes it not in palce. This version is lacked of a assersion 
 * for sorted array to complete version. To enable assertion, use "java-algs4 
 * -enableassertions MyMerge < tiny.txt"
 * 
 * @author Feng Liu
 * @version 08/14/2015
 */
public class MyMerge
{
    private static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {
        for (int k = lo; k < hi+1; k++) {
            aux[k] = a[k];
        }

        int i = lo;
        int j = mid+1;

        for (int k = lo; k < hi+1; k++) {
            if (i > mid) { //take care the left over
                a[k] = aux[j];
                j += 1;
            }
            else if (j > hi) { //take case the left ove
                a[k] = aux[i];
                i += 1;
            }
            else if (less(aux[i], aux[j])) { // left array wins and moves to aux
                a[k] = aux[i];
                i += 1;
            }
            else { // right array wins or equals and moves to aux
                a[k] = aux[j];
                j += 1;
            }
        }
    }

    public static void sort(Comparable[] a) {
        assert !isSorted(a); // throw excption if array is sorted
        // has too much overhead for tiny subarrays
        if (a.length <= 7) {
            insertion(a);
            return;
        }

        Comparable[] aux = new Comparable[a.length];
        sort(a, aux, 0, a.length-1);
    }

    private static void sort(Comparable[] a, Comparable[] aux, int lo, int hi) {
        if (hi <= lo) return; // this right? and why?

        int mid = lo + (hi - lo)/2; // why this like magic?
        sort(a, aux, lo, mid);
        sort(a, aux, mid+1, hi);
        merge(a, aux, lo, mid, hi);
    }

    private static boolean less(Comparable x, Comparable y) {
        return x.compareTo(y) < 0; // if x is less, -1 and return true
    }

    private static void insertion(Comparable[] a) {
        StdOut.println("insertion is invoked");
        for (int i = 1; i < a.length; i++) {
            //error for array out of bound. the j>=0 has to be before
            for (int j = i; j > 0 && less(a[j], a[j-1]); j--) {
                exch(a, j-1, j);
            }
        }
    }

    private static void exch(Comparable[] a, int i, int j) {
        Comparable temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    private static boolean isSorted(Comparable[] a) {
        for (int i = 1; i < a.length; i++) {
            if (less(a[i], a[i-1])) return false;
        }
        return true;
    }

    public static void main(String args[]) {
        String[] a = StdIn.readAllStrings();
        MyMerge.sort(a);

        for (int i = 0; i < a.length; i++) {
            StdOut.println(a[i]);
        }

        MyMerge.sort(a); // test assertion
    }

}
