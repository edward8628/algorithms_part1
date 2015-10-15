/*************************************************************************
 * Name: Feng Liu
 * Date : 7/11/2014
 * Email: shenjian8628@gmail.com
 *
 * Compilation:  javac Point.java
 * Execution: 
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

import java.util.Comparator;
/**
 *  data type for points in the plane that supports system sorting
 *  and a alternate sorting based on slope order.
 * 
 *  @author Feng Liu
 *  @date 7/11/2015
 */
public class Point implements Comparable<Point> {
    //compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new BySlope();
    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    //one Comparator for each point (not static)
    private class BySlope implements Comparator<Point> {
        public int compare(Point a, Point b) {
            double slopeA = slopeTo(a); // slope btw this point and point a
            double slopeB = slopeTo(b); // slope btw this point and point b

            //StdOut.println("(" + x + ", " + y + ")");
            //StdOut.println("(" + a.x + ", " + a.y + ")" + ", slopeA: " + slopeA);
            //StdOut.println("(" + b.x + ", " + b.y + ")" + ", slopeB: " + slopeB);

            if (slopeA > slopeB) return 1;
            else if (slopeA < slopeB) return -1;
            else return 0;
        }
    }

    /**
     * create the point (x, y)
     */
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    /**
     * plot this point to standard drawing
     */
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    /**
     * draw line between this point and that point to standard drawing
     */
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * slope between this point and that point, (y1-y0)/(x1-x0)
     */
    public double slopeTo(Point that) {
        /* YOUR CODE HERE */
        if (that.y == this.y && that.x != this.x) {
            //StdOut.println("horizontal line");
            return +0; 
        }
        else if (that.x == this.x && that.y != this.y) {
            //StdOut.println("vertical line");
            return Double.POSITIVE_INFINITY;
        }
        else if (that.x == this.x && that.y == this.y) {
            //StdOut.println("degenerate line segment");
            return Double.NEGATIVE_INFINITY;
        }

        return (double) (that.y-this.y)/(that.x-this.x);
    }

    /**
     * is this point lexicographically smaller than that one?
     * comparing y-coordinates and breaking ties by x-coordinates
     */
    public int compareTo(Point that) {
        /* YOUR CODE HERE */
        //(point0 < point1 if and only if either y0 < y1 or if y0 = y1 and x0 < x1)
        if (this.y < that.y) return -1;
        else if (this.y > that.y) return 1;
        else if (this.y == that.y) {
            if (this.x < that.x) return -1;
            else if (this.x > that.x) return 1;
            else return 0;
        }
        else return 0;
    }

    /**
     * return string representation of this point
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    // unit test
    public static void main(String[] args) {
        /* YOUR CODE HERE */
    }
}