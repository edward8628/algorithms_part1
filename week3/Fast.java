/*************************************************************************
 * Name: Feng Liu
 * Date : 7/11/2014
 * Email: shenjian8628@gmail.com
 *
 * Compilation:  javac Fast.java
 * Execution: java Fast input6.txt
 * Dependencies: StdDraw.java StdOut.java StdIn.java
 *
 * Description: Search line segments based on sorting in polar order
 *
 *************************************************************************/

import java.util.Arrays;

/**
 * Search line segments in a plane containing multiple
 * points based on array sorting in the polar order. 
 * Print and plot the line segment that is 4 or more points in 
 * a collinear line. 
 * 
 *  @author Feng Liu
 *  @date 7/11/2015
 */
public class Fast {
    public static void main(String[] args) {
        // rescale coordinates and turn on animation mode
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        //StdDraw.show(0);
        StdDraw.setPenRadius(0.01);     // make the points a bit larger

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
        for (int i = 0; i < N; i++) {
            int n = 1;                          //counter of same slope
            Arrays.sort(point, i, N);           //fixed for drawTo method
            Arrays.sort(point, i, N, point[i].SLOPE_ORDER);
            //StdOut.println(point[i].toString() 
            //    + " " + point[i].slopeTo(point[i])); //print origin

            slopescan:
            for (int j = i + 1; j < N; j++) {
                //compare j w/ j-1, as j
                double headSlope = point[i].slopeTo(point[j]);
                double tailSlope = point[i].slopeTo(point[j-1]); //as j-1

                //StdOut.println(point[j].toString() 
                //    + " " + headSlope + " " + i + " " + j); //print point j

                // compare headSlope(j) and tailSlope(j-1) for same slope
                if (headSlope == tailSlope) {
                    n++;
                    //StdOut.println("n = " + n); // counter of same slope
                } 
                //reset counter if it is not line segment
                else if (headSlope != tailSlope && n < 3) 
                    n = 1;

                // handle the normal case
                if (headSlope != tailSlope && n >= 3) {
                    //handle all case of printing agian
                    for (int k = 0; k < i; k++) {
                        if (tailSlope == point[i].slopeTo(point[k])) {
                            //StdOut.println("caught the same slope");
                            n = 1;
                            continue slopescan;
                        }
                    }

                    StdOut.print(point[i].toString());
                    for (int k = n; k >= 1; k--) {
                        StdOut.print(" -> " + point[j-k].toString());
                    }
                    //StdIn.readString();
                    point[i].drawTo(point[j-1]);
                    StdOut.println();
                    n = 1;
                }

                // handle the out of bound case
                else if (point.length - 1 <= j && n >= 3) {
                    //handle all case of printing again
                    for (int k = 0; k < i; k++) {
                        if (tailSlope == point[i].slopeTo(point[k])) {
                            //StdOut.println("caught the same slope");
                            n = 1;
                            continue slopescan;
                        }
                    }

                    StdOut.print(point[i].toString());
                    for (int k = n - 1; k >= 0; k--) {
                        StdOut.print(" -> " + point[j-k].toString());
                    }
                    //StdIn.readString();
                    point[i].drawTo(point[j]);
                    StdOut.println();
                    break;
                }
            }
            //StdDraw.show(0);
            //StdOut.println();
        }

        StdDraw.show(0);
        StdDraw.setPenRadius();
    }
}
