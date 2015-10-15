/****************************************************************************
 *  Compilation:  javac PercolationStats.java
 *  Execution:  java PercolationStats N T
 *  Dependencies: Percolation.java stdlib.java
 *
 *  Percolation to calculate the Statistics with inputs N and T.
 *  
 *  Author      : Feng Liu
 *  Data        : 6/25/2015
 *  Purpose     : The PercolationStats class helps to calculate the related 
 *                statistics like mean, standard deviation and 95% confidence
 *                interval to validate the threshold of the system. The system
 *                will generate random number with StdRandom.java.
 ****************************************************************************/

public class PercolationStats {
    private int grid;                   // store the grid size
    private int times;                  // store the times that need to repeat
    private double mean;                // mean
    private double stddev;              // the standard of this simulation
    private double confidenceLo;        // the confidence interval lower bound
    private double confidenceHi;        // the confidence interval higher bound

    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T) {
        if (N <= 0) throw new IllegalArgumentException("N out of bounds ");
        if (T <= 0) throw new IllegalArgumentException("T out of bounds");

        grid = N;
        times = T;
        double[] fraction = new double[times];

        //fraction
        for (int i = 0; i < times; i++) {
            Percolation perc = new Percolation(grid);
            double counter = 0;

            while (!perc.percolates()) {
                int x = StdRandom.uniform(1, grid+1);
                int y = StdRandom.uniform(1, grid+1);
                if (!perc.isOpen(x, y)) {
                    perc.open(x, y);
                    counter++;
                }
            }

            fraction[i] = counter/(grid*grid);
        }
        
        //mean
        mean = StdStats.mean(fraction);

        //stddev
        stddev = StdStats.stddev(fraction);

        confidenceLo = mean-(1.96*stddev/Math.sqrt(times));
        confidenceHi = mean+(1.96*stddev/Math.sqrt(times));
    }

    // sample mean of percolation threshold
    public double mean() {
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return stddev;
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return confidenceLo;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return confidenceHi;
    }

    // test client (described below)
    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);

        PercolationStats percStat = new PercolationStats(N, T);

        double mean = percStat.mean();
        double stddev = percStat.stddev();
        double confidenceLo = percStat.confidenceLo();
        double confidenceHi = percStat.confidenceHi();

        StdOut.println("mean                    = " + mean);
        StdOut.println("stddev                  = " + stddev);
        StdOut.println("95% confidence interval = " + confidenceLo+" "+confidenceHi);
    }
}