class Solution {
    int n;
    int[] suffix;
    Integer[][] dp;

    public int stoneGameII(int[] piles) {
        n = piles.length;

        suffix = new int[n + 1];

        for (int i = n - 1; i >= 0; i--) {
            suffix[i] = suffix[i + 1] + piles[i];
        }

        dp = new Integer[n][n + 1];

        return solve(0, 1);
    }

    private int solve(int i, int m) {

        // Can take all remaining piles
        if (i >= n) {
            return 0;
        }

        if (2 * m >= n - i) {
            return suffix[i];
        }

        if (dp[i][m] != null) {
            return dp[i][m];
        }

        int best = 0;

        for (int x = 1; x <= 2 * m; x++) {

            int opponent = solve(i + x, Math.max(m, x));

            int current = suffix[i] - opponent;

            best = Math.max(best, current);
        }

        return dp[i][m] = best;
    }
}