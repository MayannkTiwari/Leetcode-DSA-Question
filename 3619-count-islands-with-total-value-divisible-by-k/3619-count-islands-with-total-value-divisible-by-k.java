
class Solution {
    public int countIslands(int[][] grid, int k) {
        int m = grid.length;
        int n = grid[0].length;
        int count = 0;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] > 0) {
                    long sum = dfs(grid, i, j, m, n);

                    if (sum % k == 0) {
                        count++;
                    }
                }
            }
        }

        return count;
    }

    private long dfs(int[][] grid, int r, int c, int m, int n) {
        if (r < 0 || r >= m || c < 0 || c >= n
                || grid[r][c] == 0) {
            return 0;
        }

        long sum = grid[r][c];
        grid[r][c] = 0; // Mark as visited

        sum += dfs(grid, r - 1, c, m, n); // Up
        sum += dfs(grid, r + 1, c, m, n); // Down
        sum += dfs(grid, r, c - 1, m, n); // Left
        sum += dfs(grid, r, c + 1, m, n); // Right

        return sum;
    }
}
