/**
 * My own Quick Union - Union Find
 * 
 * @author Feng Liu
 * @version 7/41/2015
 */
public class MyWeightedQuickUnionUF
{
    private int[] array;        // store the parent of each item
    private int[] size;         // the size of each tree
    private int N;              // number of item

    /**
     * Constructor for objects of class MyQuickFindUF
     */
    public MyWeightedQuickUnionUF(int N)
    {
        this.N = N;
        array = new int[N];
        size = new int[N];
        for (int i = 0; i < N; i++) {
            array[i] = i;
            size[i] = 1;
        }
    }

    /**
     * Check the two items whether is connected
     */
    public boolean connected(int item1, int item2) {
        return find(item1) == find(item2);
    }
    
    /**
     * return the size of this array
     */
    public int size() {
        return N;
    }

    /**
     * Find the parent of a item
     */
    public int find(int item) {
        while (item != array[item]) {
            item = array[item];
        }
        return item;
    }

    /**
     * Union 2 items that are not connected yet
     */
    public void union(int item1, int item2) {
        int i = find(item1);
        int j = find(item2);

        //check whether 2 items are already connected
        //if(connected(item1, item2)) return; // this one is redundant
        if (i == j) return;

        //size of item1 > item2, item2 has item1's parent
        if (size[i] > size[j]) {
            array[j] = array[i];
            size[i] = size[i] + size[j] + 1;
        }
        //size of item2 > item1, item1 has item2's parent
        else if (size[i] < size[j]) {
            array[i] = array[j];
            size[j] = size[i] + size[j] + 1;
        }
        //equal size and the 1st item has 2nd item's parent
        else {
            array[i] = array[j];
            size[j] = size[i] + size[j] + 1;
        } 
    }

    /**
     * Print the parents of each index
     */
    public void print() {
        for(int i = 0; i < N; i++) {
            StdOut.println(array[i]);
        }
    }

    public static void main(String[] args) {
        MyWeightedQuickUnionUF mydata = new MyWeightedQuickUnionUF(10);
        mydata.union(3, 4);
        mydata.union(4, 5);
        mydata.union(0, 1);
        mydata.union(0, 9);
        mydata.union(9, 2);
        mydata.print();
    }
}
