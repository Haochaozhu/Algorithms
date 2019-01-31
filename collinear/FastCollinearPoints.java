/* *****************************************************************************
A faster, sorting-based solution. Remarkably, it is possible to solve the problem much
faster than the brute-force solution described above.
Given a point p, the following method determines whether
p participates in a set of 4 or more collinear points.

Think of p as the origin.
For each other point q, determine the slope it makes with p.
Sort the points according to the slopes they makes with p.
Check if any 3 (or more) adjacent points in the sorted order have equal
slopes with respect to p. If so, these points, together with p, are collinear.
Applying this method for each of the n points in turn yields an efficient algorithm
to the problem. The algorithm solves the problem because points that have
equal slopes with respect to p are collinear, and sorting brings such points together.
The algorithm is fast because the bottleneck operation is sorting.
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class FastCollinearPoints {
    private List<LineSegment> segments;
    private Point[] copy;


    // Valid input and make a copy of the input. Sort the copy
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

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        validArgument(points);
        int N = copy.length;
        segments = new LinkedList<>();
        for (int i = 0; i < N; i += 1) {
            Arrays.sort(copy);
            Arrays.sort(copy, copy[i].slopeOrder());

            for (int p = 0, first = 1, last = 2; last < copy.length; last++) {
                while (last < copy.length
                        && Double.compare(copy[p].slopeTo(copy[first]),
                                          copy[p].slopeTo(copy[last])) == 0) {
                    last += 1;
                }
                if (last - first >= 3 && copy[p].compareTo(copy[first]) < 0) {
                    segments.add(new LineSegment(copy[p], copy[last - 1]));
                }
                first = last;
            }
        }

    }

    // the number of line segments
    public int numberOfSegments() {
        return segments.size();
    }

    // the line segments
    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[0]);
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}

