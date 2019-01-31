/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[] sites;
    private int N;
    private WeightedQuickUnionUF uf;
    private int openedSites;
    private boolean percolates;

    public Percolation(int n)  { // create n-by-n grid, with all sites blocked
        if (n <= 0) throw new IllegalArgumentException("n must be larger than 0");
        N = n;
        uf = new WeightedQuickUnionUF(n * n + 2);
        openedSites = 0;
        sites = new boolean[n * n + 2];
        percolates = false;
    }

    private int twoToOne(int row, int col) {
        return (row - 1) * N + col;
    }

    private void validateIndex(int row, int col) {
        if (row < 1 || row > N || col < 1 || col > N) throw new IllegalArgumentException("illegal row or col!");
    }


    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        validateIndex(row, col);

        int arrayIndex = twoToOne(row, col);
        if (sites[arrayIndex]) return;
        sites[twoToOne(row, col)] = true;
        openedSites += 1;

        if (row == 1) {
            uf.union(0, arrayIndex);
        }
        unionWithNeighbs(row, col);

        if (row == N) {
            if (!percolates) uf.union(arrayIndex, sites.length - 1);
        }

        if (uf.connected(0, sites.length - 1)) percolates = true;
    }

    // union the given site with its neighbors that are opened.
    private void unionWithNeighbs(int row, int col) {
        if (row - 1 >= 1 && isOpen(row - 1, col)) {
            uf.union(twoToOne(row, col), twoToOne(row - 1, col));
        }
        if (row + 1 <= N && isOpen(row + 1, col)) {
            uf.union(twoToOne(row, col), twoToOne(row + 1, col));
        }
        if (col + 1 <= N && isOpen(row, col + 1)) {
            uf.union(twoToOne(row, col), twoToOne(row, col + 1));
        }
        if (col - 1 >= 1 && isOpen(row, col - 1)) {
            uf.union(twoToOne(row, col), twoToOne(row, col - 1));
        }
    }

    public boolean isOpen(int row, int col) {  // is site (row, col) open?
        validateIndex(row, col);
        return sites[twoToOne(row, col)];
    }

    public boolean isFull(int row, int col)  {  // is site (row, col) full?
        validateIndex(row, col);
        return uf.connected(0, twoToOne(row, col));
    }

    public int numberOfOpenSites()  {   // number of open sites
        return openedSites;
    }

    public boolean percolates() {    // does the system percolate?
        return percolates;
    }

    public static void main(String[] args) {
        Percolation p = new Percolation(4);
        p.validateIndex(4, 4);
    }
}
