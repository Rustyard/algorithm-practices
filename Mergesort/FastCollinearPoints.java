/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class FastCollinearPoints {
    private int numberOfSegments;
    private LineSegment[] lineSegments;

    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();
        for (Point point : points) {
            if (point == null) throw new IllegalArgumentException();
        }

        numberOfSegments = 0;
        double currentSlope;
        Point currentPoint;
        int sameSlopePoints;

        lineSegments = new LineSegment[1];
        for (Point point : points) {
            currentPoint = point;

            Point[] sortedPoints = new Point[points.length]; // a temporary array to be sorted
            System.arraycopy(points, 0, sortedPoints, 0, points.length);
            Arrays.sort(sortedPoints, currentPoint.slopeOrder());

            currentSlope = sortedPoints[0].slopeTo(currentPoint);
            sameSlopePoints = 0;
            Point startingPointMin = sortedPoints[0];
            Point endingPointMax = sortedPoints[0];
            for (int j = 0; j < sortedPoints.length; j++) {
                if (sortedPoints[j].compareTo(currentPoint) == 0 && j != 0)
                    throw new IllegalArgumentException();

                // count the points with same slope to current point
                if (currentSlope == sortedPoints[j].slopeTo(currentPoint)) {
                    sameSlopePoints++;
                    if (startingPointMin.compareTo(sortedPoints[j]) > 0) {
                        startingPointMin = sortedPoints[j]; // make the starting point the smallest
                    }
                    if (endingPointMax.compareTo(sortedPoints[j]) < 0) {
                        endingPointMax = sortedPoints[j]; // make the ending point the biggest
                    }
                    if (j == sortedPoints.length - 1 && sameSlopePoints >= 3
                            && startingPointMin.compareTo(currentPoint) < 0
                            && endingPointMax.compareTo(currentPoint)
                            <= 0) { // corner case: vertical line
                        lineSegments[numberOfSegments++] = new LineSegment(startingPointMin,
                                currentPoint);
                        if (numberOfSegments == lineSegments.length)
                            resizeSegmentArray(numberOfSegments * 2);
                    }
                } else {
                    // if more than 3 points have same slope to current point i, they are collinear.
                    // more: check if the starting point is the smallest, last point is less than current point,
                    // otherwise there will be dupes.
                    if (sameSlopePoints >= 3 && startingPointMin.compareTo(currentPoint) < 0
                            && endingPointMax.compareTo(currentPoint) <= 0) {
                        lineSegments[numberOfSegments++] = new LineSegment(startingPointMin,
                                currentPoint);
                        // double resize the array if it's full
                        if (numberOfSegments == lineSegments.length)
                            resizeSegmentArray(numberOfSegments * 2);
                    }
                    sameSlopePoints = 1;
                    currentSlope = sortedPoints[j].slopeTo(currentPoint);
                    startingPointMin = sortedPoints[j];
                    endingPointMax = sortedPoints[j];
                }
            }
        }

        resizeSegmentArray(numberOfSegments); // shrink the array
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        StdOut.println(collinear.numberOfSegments());
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

    private void resizeSegmentArray(int capacity) {
        LineSegment[] newarray = new LineSegment[capacity];
        System.arraycopy(lineSegments, 0, newarray, 0, numberOfSegments);
        lineSegments = newarray;
    }

    public int numberOfSegments()        // the number of line segments
    {
        return numberOfSegments;
    }

    public LineSegment[] segments()                // the line segments
    {
        if (numberOfSegments != 0) {
            LineSegment[] copy = new LineSegment[lineSegments.length];
            System.arraycopy(lineSegments, 0, copy, 0, lineSegments.length);
            return copy;
        } else return new LineSegment[0];
    }
}
