import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
   
   private int size;
   
   private int trials;
   
   private double[] results;
   
   private double mean;
   
   private double stddev;
   
   public PercolationStats(int n, int trials) {
        // perform trials independent experiments on an n-by-n grid
       checkArguments(n, trials);
       size = n;
       this.trials = trials;
       results = new double[trials];
       for (int i = 0; i < trials; i++) {
           results[i] = runExperiment();
       }
       mean = StdStats.mean(results);
       stddev = StdStats.stddev(results);
   }
   
   private double runExperiment() {
       Percolation perc = new Percolation(size);
       int i = 0;
       int j = 0; 
       int count = 0;
       while (!perc.percolates()) {
           i = StdRandom.uniform(1, size+1);
           j = StdRandom.uniform(1, size+1);
           if (!perc.isOpen(i, j)) {
               perc.open(i, j);
               count++;
           }
       }
      double result = (double) count/(size*size);
       return result;
   }
   
   public double mean() {
       // sample mean of percolation threshold
       return mean;
   }
   
   private void calcMean() {
       double sum = 0;
       for (int i = 0; i < trials; i++)
           sum = sum + results[i];
       mean = sum/trials;
   }
   
   public double stddev() {
       // sample standard deviation of percolation threshold
       return stddev;
   }
   
   private void calcStddev() {
       int sum = 0;
       for (int i = 0; i < trials; i++)
           sum += (results[i] - mean)*(results[i] - mean);
       stddev = sum/(trials-1);
   }
       
   
   public double confidenceLo() {
       // low  endpoint of 95% confidence interval
       return mean - 1.96* stddev / Math.sqrt(trials);
   }
   public double confidenceHi() {
       // high endpoint of 95% confidence interval
       return mean + 1.96* stddev / Math.sqrt(trials);
   }

    private void checkArguments(int n, int trials) throws IllegalArgumentException {
        if (n < 1 || trials < 1)
            throw new IllegalArgumentException();
    }
  
   
   public static void main(String[] args) {
       // test client (described below)
       int n = Integer.parseInt(args[0]);
       int t = Integer.parseInt(args[1]);
       PercolationStats percolationStats = new PercolationStats(n, t);
       System.out.println(percolationStats.mean());
       System.out.println(percolationStats.stddev());
       System.out.println(percolationStats.confidenceLo());
       System.out.println(percolationStats.confidenceHi());
   }
}