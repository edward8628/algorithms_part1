/*************************************************************************
 * Compilation:  javac Solver.java
 * Execution: java Solver puzzle.txt
 * Dependencies: StdOut.java
 * 
 * % java Board puzzle03.txt
 * 2
 *  2  0 
 *  1  3
 *
 * 2
 *  0  2 
 *  1  3 
 * 2
 *  1  2 
 *  0  3 
 *
 * 2
 *  1  2 
 *  3  0 
 * 
 * Minimum number of moves = 3
 *************************************************************************/

import java.util.Stack;
import java.util.Comparator;
/**
 *  The Solver class provides the algorithm and methods to find the 
 *  shortest solution path of a puzzle N*N blocks provided by Board class.
 *  This class can also detect the board is whether solvable.
 * 
 *  @author Feng Liu
 *  @date 7/18/2015
 *  @email shenjian8628@gmail.com
 */
public class Solver {
    private SearchNode best;              // bset search note
    private MinPQ<SearchNode> pq;        // minimum priority queue
    private boolean isSolvable;         // is this board solvable?

    private static final class SearchNode {
        private static final Comparator<SearchNode> PRIORITY_ORDER = new ByPriority();
        private final Board board;  // this current board
        private final int moves;    // the move made so far
        private final int manhattan;  //manhattan of the current board
        //private final int hamming;  // hamming of the current board
        private final int priority; // manhattan priority
        private SearchNode previous;   // previous Search Note
        private final boolean isTwin;   //

        private static class ByPriority implements Comparator<SearchNode> {
            public int compare(SearchNode a, SearchNode b) {
                if (a.priority > b.priority) return 1;
                else if (a.priority < b.priority) return -1;
                else if (a.priority == b.priority) {
                    if (a.manhattan > b.manhattan) return 1;
                    else if (a.manhattan < b.manhattan) return -1;
                    //else if (a.manhattan == b.manhattan) {
                    //if (a.manhattan > b.manhattan) return 1;
                    //else if (a.manhattan < b.manhattan) return -1;
                    //}
                }

                return 0;
            }
        }

        private SearchNode(Board board, int moves, boolean isTwin) {
            this.board = board;
            this.moves = moves;
            this.previous = null;   
            manhattan = board.manhattan();
            //hamming = board.hamming();
            priority = moves + manhattan;
            this.isTwin = isTwin;
        }
    }

    /**
     * find a solution to the initial board (using the A* algorithm) 
     */    
    public Solver(Board initial) {
        if (initial == null) throw new java.lang.NullPointerException();

        best = new SearchNode(initial, 0, false); //make initial search node
        pq = new MinPQ<SearchNode>(SearchNode.PRIORITY_ORDER); //make priority queue  
        //StdOut.println("PRIORITY BOARD\n" + toString(best));

        SearchNode twin = new SearchNode(initial.twin(), 0, true); 
        pq.insert(twin); //push twin board to pq
        //StdOut.println("PRIORITY BOARD\n" + toString(twin));

        while (!best.board.isGoal()) {
            //find neighbors and insert to priority queue
            neighborSearch:
            for (Board neighbor : best.board.neighbors()) {
                //prevent same previous search node
                SearchNode check = best.previous;
                while (check != null) {
                    if (neighbor.equals(check.board)) continue neighborSearch;
                    check = check.previous;
                }

                SearchNode neighborSearchNode = 
                    new SearchNode(neighbor, best.moves+1, best.isTwin);
                neighborSearchNode.previous = best;
                pq.insert(neighborSearchNode);
                //StdOut.println("neighbor board\n" + toString(neighborSearchNode));
            }

            //if (best.moves == 12) break; // debug
            //StdIn.readString(); //debug

            best = pq.delMin();         // next priority search note
            //StdOut.println("PRIORITY BOARD\n" + toString(best));
        }

        if (best.isTwin) isSolvable = false;
        else isSolvable = true;
    }

    /**
     * printing format for debugger
     */
    private String toString(SearchNode sn) {
        StringBuilder s = new StringBuilder();

        s.append(sn.board);
        s.append("\nmoves " + sn.moves);
        s.append("\nmanhattan " + sn.manhattan);
        //s.append("\nhamming " + sn.hamming);
        s.append("\npriority " + sn.priority);
        s.append("\nis win " + sn.isTwin);
        s.append("\n");

        return s.toString();
    }

    /**
     * is the initial board solvable?
     */
    public boolean isSolvable() {
        return isSolvable;
    }

    /**
     * min number of moves to solve initial board; -1 if unsolvable   
     */
    public int moves() {
        if (!isSolvable()) return -1;
        return best.moves; // the latest solution search node
    }

    /**
     * sequence of boards in a shortest solution; null if unsolvable
     */
    public Iterable<Board> solution() {
        if (!isSolvable()) return null;

        Stack<Board> st = new Stack<Board>();
        Stack<Board> stReverse = new Stack<Board>();
        SearchNode solution = best;

        while (solution.previous != null) {
            st.push(solution.board);
            solution = solution.previous;
        }
        st.push(solution.board);

        //fixed the reverse order
        while (!st.isEmpty()) {
            stReverse.push(st.pop());
        }

        return stReverse;
    }

    /**
     * solve a slider puzzle (given below)
     */
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];

        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++) 
                blocks[i][j] = in.readInt();

        Board initial = new Board(blocks);

        // solve the puzzle
        Stopwatch swMath = new Stopwatch(); 
        Solver solver = new Solver(initial);
        double timeMath = swMath.elapsedTime();
        StdOut.println("time " + timeMath);

        // print solution to standard output
        if (!solver.isSolvable()) StdOut.println("No solution possible");
        else {
            //for (Board board : solver.solution()) StdOut.println(board);
            StdOut.println("Minimum number of moves = " + solver.moves()); 
        } 
    }
}
