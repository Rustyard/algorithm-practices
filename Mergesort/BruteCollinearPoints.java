/* *****************************************************************************
 *  Name: BruteCollinearPoints
 *  Date: May 14th, 2021
 *  Description: Examines 4 points at a time and checks whether they all lie on
 *  the same line segment using brute-force algorithms (time n^4), returning all
 *  such line segments.
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
    private int numberOfSegments;
    private LineSegment[] lineSegments;

    public BruteCollinearPoints(Point[] points) // finds all line segments containing 4 points
    {
        if (points == null) throw new IllegalArgumentException();

        for (Point point : points) {
            if (point == null) throw new IllegalArgumentException();
        }

        numberOfSegments = 0;
        lineSegments = new LineSegment[1];
        for (int p = 0; p < points.length; p++) {
            for (int q = 0; q < points.length; q++) {
                if (q == p) continue;
                if (points[q].compareTo(points[p]) == 0)
                    throw new IllegalArgumentException();

                for (int r = 0; r < points.length; r++) {
                    if (r == q || r == p) continue;
                    if (points[r].compareTo(points[q]) == 0 || points[r].compareTo(points[p]) == 0)
                        throw new IllegalArgumentException();

                    // if first 3 points aren't collinear, no need for the 4th point
                    if (points[p].slopeTo(points[q]) != points[p].slopeTo(points[r])) continue;

                    for (int s = 0; s < points.length; s++) {
                        if (s == r || s == q || s == p) continue;

                        // no dupe point allowed
                        if (points[p].compareTo(points[s]) == 0
                                || points[q].compareTo(points[s]) == 0
                                || points[r].compareTo(points[s]) == 0)
                            throw new IllegalArgumentException();

                        if (points[p].slopeTo(points[q]) == points[p].slopeTo(points[s])) {
                            // if the points are in ascending order, add to the segments
                            if (points[p].compareTo(points[q]) < 0
                                    && points[q].compareTo(points[r]) < 0
                                    && points[r].compareTo(points[s]) < 0) {
                                lineSegments[numberOfSegments++] = new LineSegment(points[p],
                                        points[s]);
                                // resize the array if necessary
                                if (numberOfSegments == lineSegments.length)
                                    resizeSegments(numberOfSegments * 2);
                            }
                        }
                    }
                }
            }
        }

        resizeSegments(numberOfSegments); // shrink the array
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        StdOut.println(collinear.numberOfSegments());
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

    // make segments array resizable so we won't hit java heap space out of memory
    private void resizeSegments(int capacity) {
        LineSegment[] newLines = new LineSegment[capacity];
        System.arraycopy(lineSegments, 0, newLines, 0, numberOfSegments);
        lineSegments = newLines;
    }

    public int numberOfSegments() // the number of line segments
    {
        return numberOfSegments;
    }

    public LineSegment[] segments() // the line segments
    {
        if (numberOfSegments != 0) {
            LineSegment[] copy = new LineSegment[lineSegments.length];
            System.arraycopy(lineSegments, 0, copy, 0, lineSegments.length);
            return copy;
        } else return new LineSegment[0];
    }
}
