
class Solution {
    public int uniquePaths(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int MOD = 1_000_000_007;

        // dp[j][0]: paths arriving from the left
        // dp[j][1]: paths arriving from above
        int[][] dp = new int[n + 1][2];

        dp[1][0] = 1;
        dp[1][1] = 1;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int left = dp[j][0];
                int up = dp[j + 1][1];

                if (grid[i][j] == 1) {
                    dp[j + 1][0] = up;
                    dp[j + 1][1] = left;
                } else {
                    int ways = (left + up) % MOD;
                    dp[j + 1][0] = ways;
                    dp[j + 1][1] = ways;
                }
            }
        }

        return dp[n][0];
    }
}
