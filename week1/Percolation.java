/****************************************************************************
 *  Compilation:  javac Percolation.java
 *  Execution:  java Percolation < input.txt
 *  Dependencies: WeightedQuickUnionUF.java stdlib.java
 *
 *  Percolation with Weighted Quick Union in Union Find.
 *  
 *  Author      : Feng Liu
 *  Data        : 6/24/2015
 *  Purpose     : The Percolation class simulates the scientific problem 
 *                percolation of the material and calculate the fraction 
 *                of the materials need to be metallic so that the composite 
 *                system is an electrical conductor.
 ****************************************************************************/

public class Percolation {
    private int grid;                    // store the grid size
    private int virtualTop;              // virtualTop to prevent brute force
    private int virtualBottom;           // virtualBottom same as top
    private boolean[] openSite;          // store whether the site is open or not
    private WeightedQuickUnionUF uf;     // create an object of WeightedQuickUnionUF
    private WeightedQuickUnionUF ufPerc; // create one more object to treat backwash

    // create N-by-N grid, with all sites blocked
    public Percolation(int N) {
        if (N <= 0) throw new IllegalArgumentException("N out of bounds");

        grid = N;
        openSite = new boolean[grid*grid];
        virtualTop = grid*grid;                     //index is N*N
        virtualBottom = grid*grid+1;                 //index is N*N+1
        uf = new WeightedQuickUnionUF(grid*grid + 2); //N*N + 2 for virtual sites
        ufPerc = new WeightedQuickUnionUF(grid*grid + 2); // for percolation
    }

    // to test the input row and input column is valid, and not out of bounds
    private void validate(int i, int j) {
        if (i <= 0 || i > grid) 
            throw new IndexOutOfBoundsException("row index i out of bounds");
        if (j <= 0 || j > grid) 
            throw new IndexOutOfBoundsException("column index j out of bounds");
    }

    //return the index 0 to N^2-1 index in 1D that generate from from inputs i and j.
    private int xyTo1D(int i, int j) {
        validate(i, j);
        return (j-1)+grid*(i-1);
    }

    // open site (row i, column j) if it is not open already
    public void open(int i, int j) {
        int site = xyTo1D(i, j);

        if (!isOpen(i, j)) {
            openSite[site] = true;

            //union to the virtualTop
            if (i == 1) {
                uf.union(site, virtualTop);
                ufPerc.union(site, virtualTop);
            }

            //union to the virtualBottom
            if (i == grid) {
                ufPerc.union(site, virtualBottom);
            }

            //right of the site
            if (j != grid) {
                if (isOpen(i, j+1)) {
                    int siteRight = xyTo1D(i, j+1);
                    uf.union(site, siteRight);
                    ufPerc.union(site, siteRight);
                }
            }

            //left of the site
            if (j != 1) {
                if (isOpen(i, j-1)) {
                    int siteLeft = xyTo1D(i, j-1);
                    uf.union(site, siteLeft);
                    ufPerc.union(site, siteLeft);
                }
            }

            //up of the site
            if (i != 1) {
                if (isOpen(i-1, j)) {
                    int siteUp = xyTo1D(i-1, j);
                    uf.union(site, siteUp);
                    ufPerc.union(site, siteUp);
                }
            }

            //down of the site
            if (i != grid) {
                if (isOpen(i+1, j)) {
                    int siteDown = xyTo1D(i+1, j);
                    uf.union(site, siteDown);
                    ufPerc.union(site, siteDown);
                }
            }
        }
    }

    // is site (row i, column j) open?
    public boolean isOpen(int i, int j) {
        return openSite[xyTo1D(i, j)];
    }

    // is site (row i, column j) full?
    public boolean isFull(int i, int j) {
        int site = xyTo1D(i, j);

        if (isOpen(i, j)) {
            if (uf.connected(virtualTop, site)) {
                return true;
            }
        }

        return false;
    }

    // does the system percolate?
    public boolean percolates() {

        if (ufPerc.connected(virtualTop, virtualBottom)) return true;

        return false;
    }

    // test client (optional)
    public static void main(String[] args) {
        int N = StdIn.readInt();
        Percolation perc = new Percolation(N);
        while (!StdIn.isEmpty()) {
            int i = StdIn.readInt();
            int j = StdIn.readInt();
            StdOut.println(i + " " + j);
            if (perc.isOpen(i, j)) continue;
            perc.open(i, j);
        }
    }
}
