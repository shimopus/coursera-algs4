import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] series;

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("n or trails are less or equal to 0");
        }

        series = new double[trials];

        for (int i = 0; i < trials; i++) {
            series[i] = (double)initializePercolation(n).numberOfOpenSites() / (double)(n*n);
        }
    }

    private Percolation initializePercolation(int n) {
        Percolation perc = new Percolation(n);

        while (!perc.percolates()) {
            perc.open(StdRandom.uniform(1, n + 1), StdRandom.uniform(1, n + 1));
        }

        return perc;
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(series);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(series);
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return StdStats.min(series);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return StdStats.max(series);
    }

    // test client (described below)
    public static void main(String[] args) {
        int n;

        int T;

        if (args.length == 2) {
            n = Integer.parseInt(args[0]);
            T = Integer.parseInt(args[1]);
        } else {
            n = StdIn.readInt();
            T = StdIn.readInt();
        }
        PercolationStats percolationStats = new PercolationStats(n, T);

        StdOut.printf("mean                    = %1f \n", new Object[] {Double.valueOf(percolationStats.mean())});
        StdOut.printf("stddev                  = %1f \n", new Object[] {Double.valueOf(percolationStats.stddev())});
        StdOut.print("95% confidence interval = [" +
                (percolationStats.mean() - (1.96 * percolationStats.stddev() / Math.sqrt(T))) +
                ", " +
                (percolationStats.mean() + (1.96 * percolationStats.stddev() / Math.sqrt(T))) +
                "]"
        );
    }
}
