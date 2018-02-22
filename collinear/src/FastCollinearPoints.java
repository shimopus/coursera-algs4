import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private LineSegment[] lineSegments = new LineSegment[5];
    private SegmentSlope[] lineSlopes = new SegmentSlope[5];
    private int linesCount = 0;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("points array is null");
        }

        for (int i = 0; i < points.length; i++) {
            Point p = points[i];

            if (p == null) throw new IllegalArgumentException("some point in array is null");

            Point[] slopes = new Point[points.length - 1];
            int k = 0;
            for (int j = 0; j < points.length; j++) {
                if (i != j) {
                    Point q = points[j];
                    if (q == null) throw new IllegalArgumentException("some point in array is null");
                    slopes[k++] = q;
                }
            }

            Arrays.sort(slopes, p.slopeOrder());

            int segmentSize = 0;
            int segmentStart = -1;

            for (int j = 0; j < slopes.length; j++) {
                if (j < slopes.length-1 && p.slopeTo(slopes[j]) == p.slopeTo(slopes[j + 1])) {
                    segmentSize++;
                    if (segmentStart < 0) {
                        segmentStart = j;
                    }

                    continue;
                } else if (segmentSize >= 2) {
                    segmentSize++;

                    addSegment(slopes, p, segmentStart, segmentSize);
                }

                segmentStart = -1;
                segmentSize = 0;
            }
        }
    }

    private void addSegment(Point[] slopes, Point p, int segmentStart, int segmentSize) {
        Point[] segment = null;
        segment = Arrays.copyOfRange(slopes, segmentStart, segmentStart + segmentSize + 1);
        segment[segmentSize] = p;
        //do not add duplicates. Only sorted array should be added

        Arrays.sort(segment);
        boolean sorted = true;
        /*for (int j = 0; j < segment.length-1; j++) {
            if (segment[j].compareTo(segment[j+1]) > 0) {
                sorted = false;
                break;
            }
        }*/

        if (sorted) {
            add(segment[0], segment[segmentSize]);
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return linesCount;
    }

    // the line segments
    public LineSegment[] segments() {
        return resizeSegments(linesCount);
    }

    private void add(Point p1, Point p2) {
        LineSegment lineSegment = new LineSegment(p1, p2);
        double lineSlope = p1.slopeTo(p2);
        boolean add = true;
        for (SegmentSlope slope : lineSlopes) {
            if (slope != null && slope.slope == lineSlope && slope.p1.slopeTo(p1) == Double.NEGATIVE_INFINITY) {
                add = false;
                break;
            }
        }

        if (add) {
            lineSlopes[linesCount] = new SegmentSlope(p1, lineSlope);
            lineSegments[linesCount++] = lineSegment;
            if (linesCount == lineSegments.length) {
                lineSegments = resizeSegments(lineSegments.length * 2);
            }
        }
    }

    private LineSegment[] resizeSegments(int capacity) {
        lineSlopes = Arrays.copyOf(lineSlopes, capacity);
        return Arrays.copyOf(lineSegments, capacity);
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
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

    private class SegmentSlope {
        Point p1;
        double slope;

        public SegmentSlope(Point p1, double slope) {
            this.p1 = p1;
            this.slope = slope;
        }
    }
}
