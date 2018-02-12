import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class BruteCollinearPoints {
    private LineSegment[] lineSegments = new LineSegment[5];
    private int linesCount = 0;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("points array is null");
        }

        for (int i = 0; i < points.length; i++) {
            for (int j = 0; j < points.length; j++) {
                for (int k = 0; k < points.length; k++) {
                    for (int l = 0; l < points.length; l++) {
                        Point p1 = points[i];
                        Point p2 = points[j];
                        Point p3 = points[k];
                        Point p4 = points[l];

                        //add only unique points
                        if (i == j || i == k || i == l ||
                                j == k || j == l ||
                                k == l) {
                            continue;
                        }

                        if (p1.slopeTo(p2) == p2.slopeTo(p3) && p2.slopeTo(p3) == p3.slopeTo(p4)) {
                            Point[] segment = new Point[4];
                            segment[0] = p1;
                            segment[1] = p2;
                            segment[2] = p3;
                            segment[3] = p4;

                            Arrays.sort(segment);

                            if (p1.compareTo(segment[0]) == 0
                                    && p2.compareTo(segment[1]) == 0
                                    && p3.compareTo(segment[2]) == 0
                                    && p4.compareTo(segment[3]) == 0) {
                                add(new LineSegment(segment[0], segment[3]));
                            }
                        }
                    }
                }
            }
        }

    }

    // the number of line segments
    public int numberOfSegments() {
        return linesCount;
    }

    // the line segments
    public LineSegment[] segments() {
        resize(linesCount);
        return lineSegments;
    }

    private void add(LineSegment lineSegment) {
        lineSegments[linesCount++] = lineSegment;

        if (linesCount == lineSegments.length) {
            resize(lineSegments.length * 2);
        }
    }

    private void resize(int capacity) {
        lineSegments = Arrays.copyOf(lineSegments, capacity);
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
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
