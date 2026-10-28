
class Solution {
    public int minCost(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        // reachable[i][j][x] means XOR x is possible
        // when reaching cell (i, j).
        boolean[][][] reachable =
            new boolean[m][n][1024];

        reachable[0][0][grid[0][0]] = true;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                if (i == 0 && j == 0) {
                    continue;
                }

                // Come from the cell above.
                if (i > 0) {
                    for (int x = 0; x < 1024; x++) {
                        if (reachable[i - 1][j][x]) {
                            int newXor = x ^ grid[i][j];
                            reachable[i][j][newXor] = true;
                        }
                    }
                }

                // Come from the cell on the left.
                if (j > 0) {
                    for (int x = 0; x < 1024; x++) {
                        if (reachable[i][j - 1][x]) {
                            int newXor = x ^ grid[i][j];
                            reachable[i][j][newXor] = true;
                        }
                    }
                }
            }
        }

        // Find the smallest reachable XOR at the destination.
        for (int x = 0; x < 1024; x++) {
            if (reachable[m - 1][n - 1][x]) {
                return x;
            }
        }

        return -1;
    }
}

