import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.TreeSet;

/**
 * Created by ponomarevandrew on 29.09.17.
 */
public class PointSET {

    private TreeSet<Point2D> set;

    // construct an empty set of points
    public PointSET() {
        set = new TreeSet<Point2D>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return set.isEmpty();
    }

    // number of points in the set
    public int size() {
        return set.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("argument Point2D is null");
        }
        set.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("argument Point2D is null");
        }
        return set.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(.01);

        for (final Point2D p : set) {
            p.draw();
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException("argument RectHV is null");
        }
        Iterable<Point2D>  result = new TreeSet<Point2D>();
        for (final Point2D p : set) {
            if (rect.contains(p)) {
                set.add(p);
            }
        }
        return result;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("argument Point2D is null");
        }
        Point2D resultPoint = null;
        double minDistance = Double.MAX_VALUE;
        if (set == null) {
            return resultPoint;
        }
        for (final Point2D pp : set) {
            if (pp.distanceTo(p) < minDistance) {
                resultPoint = pp;
            }
        }
        return resultPoint;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {

    }
}