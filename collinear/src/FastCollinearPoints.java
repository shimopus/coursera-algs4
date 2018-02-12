import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class FastCollinearPoints {
    private LineSegment[] lineSegments = new LineSegment[5];
    private int linesCount = 0;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points){
        for (int i = 0; i < points.length; i++) {
            Point p = points[i];

            Point[] slopes = new Point[points.length - 1];
            int k = 0;
            for (int j = 0; j < points.length; j++) {
                if (i != j) {
                    Point q = points[j];
                    slopes[k++] = q;
                }
            }

            Arrays.sort(slopes, p.slopeOrder());

            Point[] segment = null;
            int segmentSize = 0;
            int segmentStart = -1;

            for (int j = 0; j < slopes.length-1; j++) {
                if (p.slopeTo(slopes[j]) == p.slopeTo(slopes[j+1])) {
                    segmentSize++;
                    if (segmentStart < 0) {
                        segmentStart = j;
                    }
                } else if (segmentSize > 0) {
                    segment = Arrays.copyOfRange(slopes, segmentStart, segmentStart+segmentSize+1);
                    segment[segmentSize] = p;
                    break;
                }
            }

            if (segment != null) {
                //do not add duplicates. Only sorted array should be added
                boolean sorted = true;
                for (int j = 0; j < segment.length-1; j++) {
                    Point point = segment[j];
                    if (segment[j].compareTo(segment[j+1]) <= 0) {
                        sorted = false;
                    }
                }

                if (sorted) {
                    add(new LineSegment(segment[0], segment[segmentSize]));
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
            resize(lineSegments.length*2);
        }
    }

    private void resize(int capacity)
    {
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
