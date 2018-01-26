import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] map;
    private WeightedQuickUnionUF quickUnion;
    private int numberOfOpenedSites = 0;
    private int n;
    private int virtTopIndex;
    private int virtBottomIndex;

    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        quickUnion = new WeightedQuickUnionUF(n*n + 2 /*virtual nodes 1-top, 2-bottom*/);
        this.n = n;
        virtTopIndex = n*n;
        virtBottomIndex = n*n+1;

        map = new boolean[n][n];
        for (int row = 0; row < map.length; row++) {
            boolean[] col = map[row];
            for (int i = 0; i < col.length; i++) {
                col[i] = false;
                if (row == 0) {
                    quickUnion.union(getIndexForQuickUnion(row, i), virtTopIndex);
                }
                if (row == n-1) {
                    quickUnion.union(getIndexForQuickUnion(row, i), virtBottomIndex);
                }
            }
        }
    }

    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        row--;
        col--;

        map[col][row] = true;
        numberOfOpenedSites++;

        if (col != 0 && map[col-1][row]) {
            quickUnion.union(getIndexForQuickUnion(row, col), getIndexForQuickUnion(row, col-1));
        }
        if (col < n-1 && map[col+1][row]) {
            quickUnion.union(getIndexForQuickUnion(row, col), getIndexForQuickUnion(row, col+1));
        }
        if (row != 0 && map[col][row-1]) {
            quickUnion.union(getIndexForQuickUnion(row, col), getIndexForQuickUnion(row-1, col));
        }
        if (row < n-1 && map[col][row+1]) {
            quickUnion.union(getIndexForQuickUnion(row, col), getIndexForQuickUnion(row+1, col));
        }
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        row--;
        col--;
        return map[col][row];
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        row--;
        col--;
        return isOpen(row+1, col+1) && quickUnion.connected(virtTopIndex, getIndexForQuickUnion(row, col));
    }

    // number of open sites
    public int numberOfOpenSites() {
        return numberOfOpenedSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return quickUnion.connected(virtTopIndex, virtBottomIndex);
    }

    private int getIndexForQuickUnion(int row, int col) {
        return row * n + col;
    }

    // test client (optional)
    public static void main(String[] args) {

    }
}
