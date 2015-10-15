/*************************************************************************
 *  Compilation:  javac KdTree.java
 *  Execution:    java KdTree input10K.txt
 *  Dependencies: StdDraw.java Stopwatch.java Point2D.java RectHV.java
 *
 *  Mutable data type that arranges points in BST.
 *
 *************************************************************************/

import java.util.Stack;
/**
 * The mutable data type KdTree uses a 2d-tree to implement the APIs which are
 * for example range the points in a rectangle area or search a nearest point
 * from a query point. The 2d-tree is a generalization of a BST to 2D keys. The 
 * prime advantage of a 2d-tree over a BST is that it supports efficient 
 * implementation of range search and nearest search.
 * 
 * @author Feng Liu
 * @version 08/02/2015
 */
public class KdTree
{
    private Node root;              // root of tree
    private int N;                  // size of tree
    private Stack<Point2D> points;  // range of points
    private Point2D nearestP;       
    private double nearestDist;     

    private static class Node {
        private final Point2D p;   // the point
        private final RectHV rect; // axis-aligned rect corresponding to this node
        private Node lb;            // left/bottom subtree
        private Node rt;            // right/top subtree

        public Node(Point2D p, RectHV rect) {
            this.p = p;
            this.rect = rect;
            lb = null;
            rt = null;
        }
    }

    /**
     * construct an empty set of points 
     */
    public KdTree() {
        root = null;
        N = 0;
        points = null;
        nearestP = null;
        nearestDist = Double.POSITIVE_INFINITY;
    }

    /**
     * is the set empty?
     */
    public boolean isEmpty() {
        return N == 0;
    }

    /**
     * number of points in the set
     */
    public int size() {
        return N;
    }

    /**
     * add the point to the set (if it is not already in the set)
     */
    public void insert(Point2D p) {
        if (p == null) throw new java.lang.NullPointerException();
        root = insertV(root, p, 0, 0, 1, 1);
    }

    /**
     * helper method for insert that compare x of 2 points, and the 
     * inserting point is corresponding to vertical
     */
    private Node insertV(Node x, Point2D p, 
    double xmin, double ymin, double xmax, double ymax) {
        if (x == null) { // recursive until hit null node
            N++;
            RectHV rect = new RectHV(xmin, ymin, xmax, ymax);
            return new Node(p, rect);
        }

        int cmp = Point2D.X_ORDER.compare(x.p, p);
        // p small go left
        if      (cmp > 0) x.lb = insertH(x.lb, p, xmin, ymin, x.p.x(), ymax); 
        // p large go right
        else if (cmp < 0) x.rt = insertH(x.rt, p, x.p.x(), ymin, xmax, ymax); 
        // p equal go right
        else {
            if (x.p.equals(p)) return x; // check if contains the point
            x.rt = insertH(x.rt, p, p.x(), ymin, xmax, ymax); // if same x, go right
        }            

        return x;
    }

    /**
     * helper method for insert that compare y of 2 points, and the 
     * inserting point is corresponding to horizontal
     */
    private Node insertH(Node x, Point2D p, 
    double xmin, double ymin, double xmax, double ymax) {
        if (x == null) { // recursive until hit null node
            N++;
            RectHV rect = new RectHV(xmin, ymin, xmax, ymax);
            return new Node(p, rect);
        }

        int cmp = Point2D.Y_ORDER.compare(x.p, p);
        // p small go left
        if      (cmp > 0) x.lb = insertV(x.lb, p, xmin, ymin, xmax, x.p.y());
        // p large go right
        else if (cmp < 0) x.rt = insertV(x.rt, p, xmin, x.p.y(), xmax, ymax);
        // p equal go right
        else {
            if (x.p.equals(p)) return x; // check if contains the point
            x.rt = insertV(x.rt, p, xmin, p.y(), xmax, ymax); // if same x, go right
        }        

        return x;
    }

    /**
     * does the set contain point p?
     */
    public boolean contains(Point2D p) {
        if (p == null) throw new java.lang.NullPointerException();
        // contains() method to test insert() properly

        return p.equals(containsV(root, p));
    }

    /**
     * helper method for contains that compare x of 2 points, and the 
     * checked point is corresponding to vertical
     */
    private Point2D containsV(Node x, Point2D p) {
        if (x == null) return null; // recursive method return until null Node

        int cmp = Point2D.X_ORDER.compare(x.p, p);
        if      (cmp > 0) return containsH(x.lb, p); // p small go left
        else if (cmp < 0) return containsH(x.rt, p); // p large go right
        else {
            if (x.p.equals(p)) return x.p; // found same point
            else return containsH(x.rt, p); // x==p, p go right
        }
    }

    /**
     * helper method for contains that compare y of 2 points, and the 
     * checked point is corresponding to horizontal
     */
    private Point2D containsH(Node x, Point2D p) {
        if (x == null) return null;      

        int cmp = Point2D.Y_ORDER.compare(x.p, p);
        if      (cmp > 0) return containsV(x.lb, p); // p small go left
        else if (cmp < 0) return containsV(x.rt, p); // p large go right
        else {
            if (x.p.equals(p)) return x.p; // found same point
            else return containsV(x.rt, p); // p equal go right
        }
    }

    /**
     * draw all points to standard draw
     */
    public void draw() {
        drawV(root);
    }

    /**
     * helper method to draw the point with whether vertical
     */
    private void drawV(Node x) {
        if (x == null) return; // return when hit the null Node

        drawH(x.lb);
        drawH(x.rt);

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(.01);
        x.p.draw();

        StdDraw.setPenRadius();
        StdDraw.setPenColor(StdDraw.RED);
        Point2D vLow = new Point2D(x.p.x(), x.rect.ymin());
        Point2D vHigh = new Point2D(x.p.x(), x.rect.ymax());
        //StdOut.println("Draw vertical line low " + vLow.toString());
        //StdOut.println("Draw vertical line high " + vHigh.toString());

        vLow.drawTo(vHigh);
        vHigh = null; // redundant?
        vLow = null;
    }

    /**
     * helper method to draw the point with whether horizaontal line
     */
    private void drawH(Node x) {
        if (x == null) return; // return when hit the null Node

        drawV(x.lb);
        drawV(x.rt);

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(.01);
        x.p.draw();

        StdDraw.setPenRadius();
        StdDraw.setPenColor(StdDraw.BLUE);
        Point2D hLeft = new Point2D(x.rect.xmin(), x.p.y());
        Point2D hRight = new Point2D(x.rect.xmax(), x.p.y());
        //StdOut.println("Draw horizontal line left " + hLeft.toString());
        //StdOut.println("Draw horizontal line right " + hRight.toString());

        hLeft.drawTo(hRight);
        hLeft = null;
        hRight = null;
    }

    /**
     * all points that are inside the rectangle
     */ 
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new java.lang.NullPointerException();
        //start at root
        points = new Stack<Point2D>();
        range(root, rect);

        return points;
    }

    /**
     * helper method to find the set of point inside the range recursively
     */
    private void range(Node x, RectHV rect) {
        if (x == null || !x.rect.intersects(rect)) return;
        //if does intersects then search both subtree, if not then only one subtree
        range(x.lb, rect);
        range(x.rt, rect);
        if (rect.contains(x.p)) points.push(x.p);
    }

    /**
     * a nearest neighbor in the set to point p; null if the set is empty
     * start from root and return the nearest point.
     */ 
    public Point2D nearest(Point2D p) {
        if (p == null) throw new java.lang.NullPointerException();
        if (root == null) return null; // handling the empty case

        nearestP = null;
        nearestDist = Double.POSITIVE_INFINITY;
        nearestV(root, p);

        //         StdOut.println("KdTree");
        //         StdOut.println("nearestDist is " + nearestDist);
        //         StdOut.println("nearestP is " + nearestP.toString()
        //             + " query point is " + p.toString());

        return nearestP;
    }

    /**
     * helper method to find nearest point that considerting every branch without
     * considerting (2)
     */
    private void nearest(Node x, Point2D p) {
        if (x == null || nearestDist < x.rect.distanceSquaredTo(p)) return;

        double currentDist = x.p.distanceSquaredTo(p);
        if (nearestDist >= currentDist) {
            nearestP = x.p;
            nearestDist = currentDist;
        }

        nearest(x.rt, p); 
        nearest(x.lb, p);
    }

    /**
     * helper method to find the nearest point in vertical section recursively, 
     * this helps to consider the next searching call is called either from 
     * left/bottom or right/top first.
     * 
     * (1) if nearest p < distance of query and a node rect, stop go further
     * (2) go down same side subtree first, this may stop go down the second subtree
     */
    private void nearestV(Node x, Point2D p) {
        if (x == null || nearestDist < x.rect.distanceSquaredTo(p)) return;

        // update the nearest
        double currentDist = x.p.distanceSquaredTo(p);
        //         StdOut.println("nearestDist is " + nearestDist);
        //         StdOut.println("currentDist is " + currentDist);
        if (nearestDist >= currentDist) {
            nearestP = x.p;
            nearestDist = currentDist;
        }

        //with 1 and 2 now running more way faster than only considering 1
        //right or left for vertical node
        int cmd = Point2D.X_ORDER.compare(x.p, p);
        if (cmd > 0) {
            // for 1
            //StdOut.println("nearestV query point is at left of node");
            nearestH(x.lb, p);
            nearestH(x.rt, p); 
        }
        else if (cmd < 0) {
            //StdOut.println("nearestV query point is at right of node");
            nearestH(x.rt, p);
            nearestH(x.lb, p);
        }
        else {
            //StdOut.println("nearestV query point is at same vertical of node");
            nearestH(x.rt, p); 
            nearestH(x.lb, p);
        }
    }

    /**
     * helper method to find the nearest point recursively, start from root
     * and return the nearest point. see nearestV
     */
    private void nearestH(Node x, Point2D p) {
        if (x == null || nearestDist < x.rect.distanceSquaredTo(p)) return;

        // update the nearest
        double currentDist = x.p.distanceSquaredTo(p);
        //         StdOut.println("nearestDist is " + nearestDist);
        //         StdOut.println("currentDist is " + currentDist);
        if (nearestDist >= currentDist) {
            nearestP = x.p;
            nearestDist = currentDist;
        }

        //for 2
        //top or bottom for horizontal node
        int cmd = Point2D.Y_ORDER.compare(x.p, p);
        if (cmd > 0) {
            //StdOut.println("nearestH query point is at bottom of node");
            nearestV(x.lb, p);
            nearestV(x.rt, p); 
        }
        else if (cmd < 0) {
            //StdOut.println("nearestH query point is at top of node");
            nearestV(x.rt, p); 
            nearestV(x.lb, p);
        }
        else {
            //StdOut.println("nearestH query point is at same horizontal of node");
            nearestV(x.rt, p); 
            nearestV(x.lb, p);
        }
    }

    public Iterable<Point2D> levelOrder() {
        Queue<Point2D> points = new Queue<Point2D>();
        Queue<Node> queue = new Queue<Node>();
        queue.enqueue(root);
        while (!queue.isEmpty()) {
            Node x = queue.dequeue();
            if (x == null) continue;
            points.enqueue(x.p);
            queue.enqueue(x.lb);
            queue.enqueue(x.rt);
        }
        return points;
    }

    //
    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);

        StdDraw.clear();
        StdDraw.show(0);

        // initialize the data structures with N points from standard input
        KdTree kdtree = new KdTree();
        Stopwatch watch = new Stopwatch();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
        }
        double timeMath = watch.elapsedTime();
        StdOut.println("insert time " + timeMath);
        StdOut.println("The size of kdtree is " + kdtree.size());

        // draw the points
        kdtree.draw();
        StdDraw.show();

        // rectangle range is GREEN
        //         RectHV rect = new RectHV(0, 0, .5, .5);
        //         StdDraw.setPenRadius(0.01);
        //         StdDraw.setPenColor(StdDraw.GREEN);
        //         rect.draw();

        // points inside range is BLUE
        //         StdDraw.setPenColor(StdDraw.BLUE);
        //         watch = new Stopwatch();
        //         for (Point2D p : kdtree.range(rect))
        //             p.draw(); // this cause the slow
        //         timeMath = watch.elapsedTime();
        //         StdOut.println("range time " + timeMath);

        for (Point2D p : kdtree.levelOrder()) {
            StdOut.println(p.toString());
        }
    }
}
