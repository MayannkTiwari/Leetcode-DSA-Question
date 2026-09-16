class Solution {
    public int numberOfSets(int n, int k) {
        int MOD = 1000000007;

        long[][] dp = new long[n + 1][k + 1];
        long[][] open = new long[n + 1][k + 1];

        dp[1][0] = 1;

        for (int i = 2; i <= n; i++) {
            for (int j = 0; j <= k; j++) {

                dp[i][j] = (dp[i - 1][j] + open[i - 1][j]) % MOD;

                open[i][j] = open[i - 1][j];

                if (j > 0) {
                    open[i][j] += dp[i - 1][j - 1];
                    open[i][j] %= MOD;

                    open[i][j] += open[i - 1][j - 1];
                    open[i][j] %= MOD;
                }
            }
        }

        return (int)((dp[n][k] + open[n][k]) % MOD);
    }
}