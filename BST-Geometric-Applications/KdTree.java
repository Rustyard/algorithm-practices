import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
    private Node root;
    private int size;

    public KdTree() {
        root = null;
        size = 0;
    }

    public static void main(String[] args) {

    }

    public boolean isEmpty() {
        return root == null;
    }

    public int size() {
        return size;
    }

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (contains(p)) return;
        root = insert(root, p, true, 0.0, 0.0, 1.0, 1.0);
        size++;
    }

    private Node insert(Node x, Point2D p, boolean compareX,
                        double xmin, double ymin,
                        double xmax, double ymax) {
        if (x == null) {
            RectHV rect = new RectHV(xmin, ymin, xmax, ymax);
            return new Node(p, rect);
        }

        boolean cmp;
        if (compareX) cmp = p.x() < x.p.x();
        else cmp = p.y() < x.p.y();

        if (cmp) {
            if (compareX)
                x.lb = insert(x.lb, p, !compareX, xmin, ymin, x.p.x(), ymax);
            else
                x.lb = insert(x.lb, p, !compareX, xmin, ymin, xmax, x.p.y());
        } else {
            if (compareX)
                x.rt = insert(x.rt, p, !compareX, x.p.x(), ymin, xmax, ymax);
            else
                x.rt = insert(x.rt, p, !compareX, xmin, x.p.y(), xmax, ymax);
        }

        return x;
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (isEmpty()) return false;
        return get(root, p, true) != null;
    }

    private Node get(Node x, Point2D p, boolean compareX) {
        if (x == null) return null;
        boolean cmp;
        if (compareX) cmp = p.x() < x.p.x();
        else cmp = p.y() < x.p.y();

        if (p.compareTo(x.p) == 0) return x; // equal case

        if (cmp) return get(x.lb, p, !compareX);
        else return get(x.rt, p, !compareX);
    }

    public void draw() {
        if (!isEmpty())
            draw(root, true);
    }

    private void draw(Node x, boolean vertical) {
        if (x == null) return;

        x.p.draw();

        // draw line
        if (vertical) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius(0.01);
            StdDraw.line(x.p.x(), x.rect.ymin(), x.p.x(), x.rect.ymax());
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius(0.01);
            StdDraw.line(x.rect.xmin(), x.p.y(), x.rect.xmax(), x.p.y());
        }

        draw(x.lb, !vertical);
        draw(x.rt, !vertical);
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        SET<Point2D> resultSet = new SET<>();
        range(root, resultSet, rect);
        return resultSet;
    }

    private void range(Node x, SET<Point2D> resultSet, RectHV rect) {
        if (x == null) return;
        if (rect.contains(x.p)) resultSet.add(x.p);
        if (x.lb != null && rect.intersects(x.lb.rect)) range(x.lb, resultSet, rect);
        if (x.rt != null && rect.intersects(x.rt.rect)) range(x.rt, resultSet, rect);
    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (isEmpty()) return null;
        return nearest(p, root, root.p);
    }

    private Point2D nearest(Point2D p, Node x, Point2D currentNearest) {
        double distance = p.distanceSquaredTo(x.p);
        if (distance < p.distanceSquaredTo(currentNearest))
            currentNearest = x.p;

        if (x.lb != null && x.rt != null) {
            double lDistance = x.lb.rect.distanceSquaredTo(p);
            double rDistance = x.rt.rect.distanceSquaredTo(p);
            if (lDistance < rDistance) {
                if (lDistance < distance)
                    currentNearest = nearest(p, x.lb, currentNearest);
                if (rDistance < distance)
                    currentNearest = nearest(p, x.rt, currentNearest);
            } else {
                if (rDistance < distance)
                    currentNearest = nearest(p, x.rt, currentNearest);
                if (lDistance < distance)
                    currentNearest = nearest(p, x.lb, currentNearest);
            }
        } else if (x.lb != null && x.lb.rect.distanceSquaredTo(p) < distance)
            currentNearest = nearest(p, x.lb, currentNearest);
        else if (x.rt != null && x.rt.rect.distanceSquaredTo(p) < distance)
            currentNearest = nearest(p, x.rt, currentNearest);
        return currentNearest;
    }

    private static class Node {
        private Point2D p;
        private RectHV rect; // axis-aligned rectangle
        private Node lb; // left/bottom
        private Node rt; // right/top

        private Node(Point2D p, RectHV rect) {
            this.p = p;
            this.rect = rect;
            this.lb = null;
            this.rt = null;
        }
    }
}
