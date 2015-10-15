/*************************************************************************
 * Compilation:  javac Board.java
 * Execution: java Board puzzle11.txt
 * Dependencies: StdOut.java
 * 
 * % java Board puzzle11.txt
 *
 *************************************************************************/
import java.util.Stack;

/**
 *  The Board class provides data type with N*N blocks filling integers
 *  and the method to compute manhattan priority and hamming priority for 
 *  A* search. The Board class also provides the method to search the 
 *  current Board's neighbor Boards and the method to find its one of 
 *  the twin boards.
 * 
 *  @author Feng Liu
 *  @date 7/18/2015
 *  @email shenjian8628@gmail.com
 */
public class Board {
    private final int N;            //dimension of board
    private final int[][] blocks;   //2D blocks
    private final int zeroPos;      //cache 0 position to save resource
    private final int manhattan;        //cache Manhattan priority

    /**
     * construct a board from an N-by-N array of blocks
     * (where blocks[i][j] = block in row i, column j) 
     */ 
    public Board(int[][] blocks) {
        N = blocks[0].length;
        this.blocks = new int[N][N];

        //creat array and find zero postion
        int zero = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                this.blocks[i][j] = blocks[i][j];
                if (this.blocks[i][j] == 0) zero = to1D(i, j);
            }
        }
        zeroPos = zero; // final is not allowed in a loop

        //find the Manhattan priority
        int moves = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (blocks[i][j] != to1D(i, j) + 1 && blocks[i][j] != 0) {
                    int j1 = (blocks[i][j]-1) % N; // j of right position
                    int i1 = (blocks[i][j]-1-j1) / N; // i of right position
                    moves = moves + Math.abs(j - j1) + Math.abs(i - i1);
                }
            }
        }
        manhattan = moves;
        // no need hamming and since disable, running time behalf
    }

    /**
     * convert 2D array to 1D array
     * if N = 3 and indice 0-8, (2,2) = 2*3+2 = 8, (2,1) = 2*3+1 = 7
     */
    private int to1D(int i, int j) {
        return i*N+j;
    }

    /**
     * convert 1D array to xy dimenstion and return array
     */
    private int[] to2D(int index) {
        int[] xy = new int[2];
        xy[1] = index % N; // index j
        xy[0] = (index - xy[1]) / N; // index i
        return xy;
    }

    /**
     * board dimension N
     */
    public int dimension() {
        return N;
    }

    /**
     * number of blocks out of place
     */
    public int hamming() {
        int counter = 0;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (i == N-1 && j == N-1) break;
                if (blocks[i][j] != to1D(i, j) + 1) counter++;
            }
        }

        return counter;
    }

    /**
     * sum of Manhattan distances between blocks and goal
     */
    public int manhattan() {
        return manhattan;
    }

    /**
     * is this board the goal board?
     */
    public boolean isGoal() {
        if (manhattan == 0) return true;
        return false;
    }

    /**
     * a board that is obtained by exchanging two adjacent 
     * blocks in the same row 
     */
    public Board twin() {
        int [][] twin = new int[N][N];
        copy(twin, null, null, null);

        for (int i = 0; i < N; i++) {
            if (twin[i][0] != 0 && twin[i][1] != 0) {
                exch(twin, i, 0, i, 1);
                break;
            }
        }

        return new Board(twin);
    }

    /**
     * does this board equal y?
     */
    public boolean equals(Object y) {
        if (y == this) return true; // i dont know this
        if (y == null) return false;
        if (this.getClass() != y.getClass()) return false;

        Board that = (Board) y;
        if (this.N != that.N) return false;
        if (this.zeroPos != that.zeroPos) return false;
        if (this.manhattan != that.manhattan) return false;

        //instead of Arrays.equal()
        for (int i = 0; i < N; i++) 
            for (int j = 0; j < N; j++)
                if (blocks[i][j] != that.blocks[i][j]) return false;

        return true;
    }

    /**
     * eachange the value i1, j1 with i, j
     */
    private void exch(int[][] blocks, int i1, int j1, int i2, int j2) {
        int temp = blocks[i1][j1];
        blocks[i1][j1] = blocks[i2][j2];
        blocks[i2][j2] = temp;
    }

    /**
     * copy the array to a new created 2D array
     */
    private void copy(int[][] blocks1, int[][] blocks2, int[][] blocks3, int[][] blocks4) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (blocks1 != null)
                    blocks1[i][j] = this.blocks[i][j];
                if (blocks2 != null)
                    blocks2[i][j] = this.blocks[i][j];
                if (blocks3 != null)
                    blocks3[i][j] = this.blocks[i][j];
                if (blocks4 != null)
                    blocks4[i][j] = this.blocks[i][j];
            }
        }
    }

    /**
     * all neighboring boards reture as a stack
     */
    public Iterable<Board> neighbors() {
        Stack<Board> st = new Stack<Board>();
        int [][] newBlocks1 = new int[N][N];
        int [][] newBlocks2 = new int[N][N];
        int [][] newBlocks3 = new int[N][N];
        int [][] newBlocks4 = new int[N][N];
        copy(newBlocks1, newBlocks2, newBlocks3, newBlocks4);

        int[] xy = to2D(zeroPos);
        int j = xy[1];
        int i = xy[0];

        // zero right
        if (j >= 0 && j < N-1) {
            exch(newBlocks1, i, j, i, j+1);
            Board neighbor = new Board(newBlocks1);
            st.push(neighbor);    
        }              

        // zero below 
        if (i >= 0 && i < N-1) {
            exch(newBlocks2, i+1, j, i, j);
            Board neighbor = new Board(newBlocks2);
            st.push(neighbor);     
        }

        //zero left
        if (j > 0 && j < N) {
            exch(newBlocks3, i, j, i, j-1);
            Board neighbor = new Board(newBlocks3);
            st.push(neighbor);    
        }

        // zero above
        if (i > 0 && i < N) {
            exch(newBlocks4, i-1, j, i, j);
            Board neighbor = new Board(newBlocks4);
            st.push(neighbor);    
        }        

        return st;
    }

    /**
     * string representation of this board (in the output 
     * format specified below)
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", blocks[i][j]));
            }
            s.append("\n");
        }

        if (false) { // debug
            s.append("\nhamming " + hamming());
            s.append("\nmanhattan " + manhattan);
            s.append("\n");
        }

        return s.toString();
    }

    // unit tests (not graded)
    public static void main(String[] args) {
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];

        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++) 
                blocks[i][j] = in.readInt();

        Board initial = new Board(blocks);
        Board twin = initial.twin();
        StdOut.println("The initial board\n" + initial);
        StdOut.println("The twin board\n" + twin);

        for (Board neighbors : initial.neighbors()) {
            StdOut.println("The neighbors\n" + neighbors);
        }

        int[][] blocks1 = new int[][]
            {{8, 3, 5}, {0, 2, 1}, {7, 6, 4}};
        int[][] blocks2 = new int[][]
            {{2, 8, 4}, {0, 5, 3}, {6, 1, 7}};
        Board equal1 = new Board(blocks1);
        Board equal2 = new Board(blocks2);
        StdOut.println(equal1.equals(equal2));

        int[][] blocks3 = new int[][]
            {{6, 0, 7, 8}, {14, 4, 12, 2}, {15, 5, 11, 13}, {3, 1, 9, 10}};
        int[][] blocks4 = new int[][]
            {{7, 0, 14, 1}, {12, 8, 5, 2}, {15, 10, 13, 4}, {9, 11, 3, 6}};
        Board equal3 = new Board(blocks3);
        Board equal4 = new Board(blocks4);
        StdOut.println(equal3.equals(equal4));
    }
}