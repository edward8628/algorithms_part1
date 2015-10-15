/*************************************************************************
 *  Compilation:  javac PointSET.java
 *  Execution:    java PointSET input10K.txt
 *  Dependencies: StdDraw.java Point2D.java RectHV.java
 *
 *  Mutable data type that arranges points in TreeSet.
 *
 *************************************************************************/

import java.util.TreeSet;
import java.util.Stack;

/**
 * A mutable data type PointSET.java that represents a set of 
 * points in the unit square. Implement the APIs like range search
 * and nearest point search by using a java.util.TreeSet BST. 
 * This is a brute-force implememntation.
 * 
 * @author Feng Liu
 * @version 08/02/2015
 */
public class PointSET
{
    private TreeSet<Point2D> set; // this is red-black BST

    /**
     * construct an empty set of points 
     */
    public PointSET() {
        //using a TreeSet
        set = new TreeSet<Point2D>();
    }

    /**
     * is the set empty?
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * number of points in the set
     */
    public int size() {
        return set.size();
    }

    /**
     * add the point to the set (if it is not already in the set)
     */
    public void insert(Point2D p) {
        if (p == null) throw new java.lang.NullPointerException();
        set.add(p);
    }

    /**
     * does the set contain point p?
     */
    public boolean contains(Point2D p) {
        if (p == null) throw new java.lang.NullPointerException();
        return set.contains(p);
    }

    /**
     * draw all points to standard draw
     */
    public void draw() {
        for (Point2D p: set) 
            p.draw();
    }

    /**
     * all points that are inside the rectangle
     */
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new java.lang.NullPointerException();
        Stack<Point2D> range = new Stack<Point2D>();

        for (Point2D p: set) 
            if (rect.contains(p)) range.push(p);

        return range;
    }

    /**
     * a nearest neighbor in the set to point p; null if the set is empty
     */
    public Point2D nearest(Point2D p) {
        if (p == null) throw new java.lang.NullPointerException();

        Point2D nearestP = null;
        double nearestDist = Double.POSITIVE_INFINITY;

        for (Point2D points: set) {
            double currentDist = points.distanceSquaredTo(p);
            if (currentDist <= nearestDist) {
                nearestDist = currentDist;
                nearestP = points;
            }
        }

        //         StdOut.println("PointSET");
        //         StdOut.println("nearestDist is " + nearestDist);
        //         StdOut.println("nearestP is " + nearestP.toString() 
        //             + " query point is " + p.toString());

        return nearestP;
    }

    // unit test
    public static void main(String[] args) {

    }
}
