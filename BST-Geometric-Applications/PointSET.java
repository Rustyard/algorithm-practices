import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

public class PointSET {
    private SET<Point2D> set;

    public PointSET() {
        set = new SET<>();
    }

    public static void main(String[] args) {

    }

    public boolean isEmpty() {
        return set.isEmpty();
    }

    public int size() {
        return set.size();
    }

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (!contains(p)) set.add(p);
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return set.contains(p);
    }

    public void draw() {
        for (Point2D p : set) {
            p.draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();

        SET<Point2D> resultSet = new SET<>();
        for (Point2D p : set) {
            if (rect.contains(p)) {
                resultSet.add(p);
            }
        }
        return resultSet;
    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();

        Point2D nearestPoint = null;
        double minDistance = 2.0; // largest possible distance is 1.0

        for (Point2D pThis : set) {
            if (p.distanceSquaredTo(pThis) <= minDistance) {
                minDistance = p.distanceSquaredTo(pThis);
                nearestPoint = pThis;
            }
        }

        return nearestPoint;
    }
}
