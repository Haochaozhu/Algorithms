/* *****************************************************************************
 *  Brute force. Write a program BruteCollinearPoints.java that
 *  examines 4 points at a time and checks whether they all lie on the same
 *  line segment, returning all such line segments. To check whether the
 *  4 points p, q, r, and s are collinear, check whether the three slopes
 *  between p and q, between p and r, and between p and s are all equal.
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


public class BruteCollinearPoints {
    private int numberofSegments;
    private LineSegment[] segments;
    private Point[] copy;

    private void validArgument(Point[] points) {
        if (points == null) throw new IllegalArgumentException();
        copy = points.clone();
        for (int i = 0; i < copy.length; i += 1) {
            if (copy[i] == null) throw new IllegalArgumentException();
        }

        Arrays.sort(copy);
        for (int i = 1; i < copy.length; i += 1) {
            if (copy[i] == null) throw new IllegalArgumentException();
            if (copy[i].compareTo(copy[i - 1]) == 0) throw new IllegalArgumentException();
        }
    }

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        validArgument(points);
        List<LineSegment> lineSegmentList = new LinkedList<>();
        int N = points.length;
        for (int p = 0; p < N - 3; p += 1) {
            Point a = copy[p];
            for (int q = p + 1; q < N - 2; q += 1) {
                Point b = copy[q];
                double slopeAB = a.slopeTo(b);
                for (int r = q + 1; r < N - 1; r += 1) {
                    Point c = copy[r];
                    double slopeAC = a.slopeTo(c);
                    if (slopeAB == slopeAC) {
                        for (int s = r + 1; s < N; s += 1) {
                            Point d = copy[s];
                            double slopeAD = a.slopeTo(d);
                            if (slopeAB == slopeAD) lineSegmentList.add(new LineSegment(a, d));
                        }
                    }
                }
            }
        }
        segments = lineSegmentList.toArray(new LineSegment[0]);
    }

    // the number of line segments
    public int numberOfSegments() {
        return segments.length;
    }

    // the line segments
    public LineSegment[] segments() {
        return segments.clone();
    }

    public static void main(String[] args) {
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
        // FastCollinearPoints collinear = new FastCollinearPoints(points);
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
