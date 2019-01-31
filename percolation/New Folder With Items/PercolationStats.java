/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] fractionPercent;

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n  <= 0 || trials <= 0) throw new java.lang.IllegalArgumentException();

        fractionPercent = new double[trials];

        for (int i = 0; i < trials; i += 1) {
            Percolation prc = new Percolation(n);
            while (!prc.percolates()) {
                prc.open(StdRandom.uniform(1, n + 1),StdRandom.uniform(1, n + 1));
            }
            fractionPercent[i] = prc.numberOfOpenSites() * 1.0 / (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(fractionPercent);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        if (fractionPercent.length == 1) return Double.NaN;
        else return StdStats.stddevp(fractionPercent);
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return 0;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return 0;
    }

    public static void main(String[] args) {
        PercolationStats ps = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        StdOut.println(ps.mean());
        StdOut.println(ps.stddev());
    }
}
