/* *****************************************************************************
 *  Name: BruteCollinearPoints
 *  Date: May 14th, 2021
 *  Description: Examines 4 points at a time and checks whether they all lie on
 *  the same line segment using brute-force algorithms (time n^4), returning all
 *  such line segments.
 **************************************************************************** */

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
    private int numberOfSegments = 0;
    private LineSegment[] lineSegments;

    public BruteCollinearPoints(Point[] points) // finds all line segments containing 4 points
    {
        if (points == null) throw new IllegalArgumentException();

        lineSegments = new LineSegment[1];
        for (int p = 0; p < points.length; p++) {
            for (int q = 0; q < points.length; q++) {
                if (q == p) continue;
                for (int r = 0; r < points.length; r++) {
                    if (r == q || r == p) continue;
                    // if first 3 points aren't collinear, no need for the 4th point
                    if (points[p].slopeTo(points[q]) != points[p].slopeTo(points[r])) continue;
                    for (int s = 0; s < points.length; s++) {
                        if (s == r || s == q || s == p) continue;
                        if (points[p].slopeTo(points[q]) == points[p].slopeTo(points[s])) {
                            // if the points are in ascending order, add to the segments
                            if (points[p].compareTo(points[q]) < 0
                                    && points[q].compareTo(points[r]) < 0
                                    && points[r].compareTo(points[s]) < 0) {
                                lineSegments[numberOfSegments++] = new LineSegment(points[p], points[s]);
                                // make the array bigger
                                if (numberOfSegments == lineSegments.length) resizeSegments(numberOfSegments * 2);
                            }
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);

        if (StdIn.isEmpty()) return;
        int num = StdIn.readInt();
        Point[] points = new Point[num];
        for (int i = 0; i < num; i++) {
            points[i] = new Point(StdIn.readInt(), StdIn.readInt());
        }

        BruteCollinearPoints solution = new BruteCollinearPoints(points);
        LineSegment[] segments = solution.segments();
        StdOut.println(solution.numberOfSegments());
        for (int i = 0; i < solution.numberOfSegments(); i++) {
            StdOut.println(segments[i].toString());
            segments[i].draw();
        }
    }

    public LineSegment[] segments() // the line segments
    {
        return lineSegments;
    }

    public int numberOfSegments() // the number of line segments
    {
        return numberOfSegments;
    }

    // make segments array resizable so we won't hit java heap space out of memory
    private void resizeSegments(int capacity) {
        LineSegment[] newLines = new LineSegment[capacity];
        System.arraycopy(lineSegments, 0, newLines, 0, lineSegments.length);
        lineSegments = newLines;
    }
}
