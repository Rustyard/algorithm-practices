import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;


public class PercolationStats {
    private static final double CONFIDENCE_95 = 1.96;
    private final double[] fractions;
    private double mean;
    private double stddev;
    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n < 1 || trials < 1) {
            throw new IllegalArgumentException();
        }
        fractions = new double[trials];
        Percolation percolation;
        for (int i = 0; i < trials; i++) {
            fractions[i] = 0;
        }

        for (int i = 0; i < trials; i++) {
            int frac = 0;
            percolation = new Percolation(n);
            while (!percolation.percolates()) {
                // randomly open a site
                int index = StdRandom.uniform(n * n);
                while (percolation.isOpen(index / n + 1, index % n + 1)) {
                    index = StdRandom.uniform(n * n);
                }
                percolation.open(index / n + 1, index % n + 1);
                frac++;
            }
            fractions[i] = (double) frac / (n * n);
        }
        mean();
        stddev();
    }

    // sample mean of percolation threshold
    public double mean() {
        mean = StdStats.mean(fractions);
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        stddev = StdStats.stddev(fractions);
        return stddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return (mean - CONFIDENCE_95 * stddev / Math.sqrt(fractions.length));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return (mean + CONFIDENCE_95 * stddev / Math.sqrt(fractions.length));
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int bigT = Integer.parseInt(args[1]);
        PercolationStats percolationStats = new PercolationStats(n, bigT);
        StdOut.println("mean\t\t\t\t\t= " + percolationStats.mean());
        StdOut.println("stddev\t\t\t\t\t= " + percolationStats.stddev());
        StdOut.println("95% confidence interval = [" + percolationStats.confidenceLo()
            + ", " + percolationStats.confidenceHi() + "]");
    }
}
