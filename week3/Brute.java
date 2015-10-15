/*************************************************************************
 * Name: Feng Liu
 * Date : 7/11/2014
 * Email: shenjian8628@gmail.com
 *
 * Compilation:  javac Brute.java
 * Execution: java Brute input6.txt
 * Dependencies: StdDraw.java StdOut.java StdIn.java
 *
 * Description: Search line segments in brute method
 *
 *************************************************************************/

import java.util.Arrays;

/**
 * Search line segments in a plane containing multiple
 * points in brute way using 4 for-loops to scan the array
 * Print and plot the line segment that is 4 points in 
 * a collinear line. 
 * 
 *  @author Feng Liu
 *  @date 7/11/2015
 */
public class Brute {
    public static void main(String[] args) {
        // rescale coordinates and turn on animation mode
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);
        StdDraw.setPenRadius(0.01);  // make the points a bit larger

        String filename = args[0];      // read in the input
        In in = new In(filename);
        int N = in.readInt();           // size of the set of points
        Point[] point = new Point[N];   // array of slope

        //StdOut.println("Before Sorted");
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            point[i] = new Point(x, y);
            point[i].draw();
            //StdOut.println(point[i].toString());
        }

        //StdOut.println();
        //StdOut.println("After Sorted");
        Arrays.sort(point);
        // scan the array for line segments
        for (int i = 0; i < N-3; i++) {
            Point origin = point[i];

            for (int j = i+1; j < N-2; j++) {
                double slopeJ = origin.slopeTo(point[j]);

                for (int k = j+1; k < N-1; k++) {
                    double slopeK = origin.slopeTo(point[k]);

                    if (slopeJ == slopeK) {
                        for (int n = k+1; n < N; n++) {
                            double slopeN = origin.slopeTo(point[n]);
                            //StdOut.println(i + "" + j + "" + k + "" + n);
                            if (slopeN == slopeK) {
                                StdOut.print(point[i].toString() + "->" 
                                    + point[j].toString() + "->" 
                                    + point[k].toString() + "->" 
                                    + point[n].toString());
                                StdOut.println();
                                origin.drawTo(point[n]);
                            }
                        }
                    }
                }
            }
        }

        StdDraw.show(0);
        StdDraw.setPenRadius();
    }
}