
import java.util.*;

class Solution {
    public int minimumChanges(String s, int k) {

        int n = s.length();

        // Step 1: Precompute factors
        List<Integer>[] factors = new ArrayList[n + 1];

        for (int i = 0; i <= n; i++) {
            factors[i] = new ArrayList<>();
        }

        for (int d = 1; d < n; d++) {
            for (int len = 2 * d; len <= n; len += d) {
                factors[len].add(d);
            }
        }

        // Step 2: Calculate minimum cost for every substring
        int[][] cost = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {

                int len = j - i + 1;
                int minCost = n;

                for (int d : factors[len]) {

                    int changes = 0;

                    for (int offset = 0; offset < d; offset++) {

                        int left = i + offset;
                        int right = j - d + 1 + offset;

                        while (left < right) {

                            if (s.charAt(left) != s.charAt(right)) {
                                changes++;
                            }

                            left += d;
                            right -= d;
                        }
                    }

                    minCost = Math.min(minCost, changes);
                }

                cost[i][j] = minCost;
            }
        }

        // Step 3: DP
        // dp[i][p] = minimum changes to divide
        // first i characters into p parts

        int INF = 1000;

        int[][] dp = new int[n + 1][k + 1];

        for (int[] row : dp) {
            Arrays.fill(row, INF);
        }

        dp[0][0] = 0;

        for (int i = 1; i <= n; i++) {

            for (int p = 1; p <= k; p++) {

                for (int j = i - 1; j >= 0; j--) {

                    // Every part must have length >= 2
                    if (i - j < 2) {
                        continue;
                    }

                    if (dp[j][p - 1] == INF) {
                        continue;
                    }

                    dp[i][p] = Math.min(
                        dp[i][p],
                        dp[j][p - 1] + cost[j][i - 1]
                    );
                }
            }
        }

        return dp[n][k];
    }
}
