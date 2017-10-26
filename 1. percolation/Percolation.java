import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation  {

    private static final int SOURCE_INDEX = 0;

    private static int BOTTOM_INDEX;
    
    private boolean[][] openedSite;
   
    private int size;
   
    private WeightedQuickUnionUF ufService;

    private WeightedQuickUnionUF ufServiceWithBottom;
   
    public Percolation(int n)   {
        // create n-by-n grid, with all sites blocked
        if (n <= 0)
            throw new IllegalArgumentException();
        size = n;
        BOTTOM_INDEX = n*n+1;
        ufService = new WeightedQuickUnionUF(n*n+1);
        ufServiceWithBottom = new WeightedQuickUnionUF(n*n+2);
        openedSite = new boolean[n][n];
    }

   public void open(int i, int j) throws IllegalArgumentException {
       checkArguments(i,j);
       // open site (row i, column j) if it is not open already
       if (openedSite[i-1][j-1])
           return;
       openedSite[i-1][j-1] = true;
       unionLeft(i, j);
       unionUp(i, j);
       unionRight(i, j);
       unionDown(i, j);
   }
   
   private void unionLeft(int i, int j) {
       if (j != 1 && openedSite[i-1][j-2]) {
           ufService.union(indexOf(i, j), indexOf(i, j - 1));
           ufServiceWithBottom.union(indexOf(i, j), indexOf(i, j - 1));
       }
   }
   
   private void unionUp(int i, int j) {
       if (i == 1) {
           ufService.union(indexOf(i, j), SOURCE_INDEX);
           ufServiceWithBottom.union(indexOf(i, j), SOURCE_INDEX);
       }
       else if (openedSite[i-2][j-1]) {
           ufService.union(indexOf(i, j), indexOf(i - 1, j));
           ufServiceWithBottom.union(indexOf(i, j), indexOf(i - 1, j));
       }
   }
   
   private void unionRight(int i, int j) {
       if (j != size && openedSite[i-1][j]) {
           ufService.union(indexOf(i, j), indexOf(i, j + 1));
           ufServiceWithBottom.union(indexOf(i, j), indexOf(i, j + 1));
       }
   }
   
   private void unionDown(int i, int j) {
       if (i == size)
           ufServiceWithBottom.union(indexOf(i, j), BOTTOM_INDEX);
       if (i != size && openedSite[i][j-1]) {
           ufService.union(indexOf(i, j), indexOf(i + 1, j));
           ufServiceWithBottom.union(indexOf(i, j), indexOf(i + 1, j));
       }
   }
   
   public boolean isOpen(int i, int j) throws IllegalArgumentException{
       // is site (row i, column j) open?
       checkArguments(i,j);
       return openedSite[i-1][j-1];
   }
   public boolean isFull(int i, int j) throws IllegalArgumentException{
       // is site (row i, column j) full?
       checkArguments(i,j);
       return isOpen(i,j) && ufService.connected(indexOf(i, j), SOURCE_INDEX);
   }

   public boolean percolates() {
       // does the system percolate?
       if (ufServiceWithBottom.connected(SOURCE_INDEX, BOTTOM_INDEX))
           return true;
       return false;
   }
       
   private int indexOf(int i, int j) {
       return (i-1)*size + j;
   }

    private void checkArguments(int i, int j) throws IllegalArgumentException {
        if (i > size || i < 1 || j > size || j < 1)
            throw new IllegalArgumentException();
    }
       

   public static void main(String[] args) { // test client (optional)

   }
}