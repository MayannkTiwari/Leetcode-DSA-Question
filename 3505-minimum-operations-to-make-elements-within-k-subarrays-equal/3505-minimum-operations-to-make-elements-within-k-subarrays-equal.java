
import java.util.*;

class Solution {

    TreeMap<Integer, Integer> left = new TreeMap<>();
    TreeMap<Integer, Integer> right = new TreeMap<>();

    int leftSize = 0;
    int rightSize = 0;

    long leftSum = 0;
    long rightSum = 0;

    void addToLeft(int val) {
        left.put(val, left.getOrDefault(val, 0) + 1);
        leftSize++;
        leftSum += val;
    }

    void addToRight(int val) {
        right.put(val, right.getOrDefault(val, 0) + 1);
        rightSize++;
        rightSum += val;
    }

    void removeFromLeft(int val) {
        int count = left.get(val);

        if (count == 1) {
            left.remove(val);
        } else {
            left.put(val, count - 1);
        }

        leftSize--;
        leftSum -= val;
    }

    void removeFromRight(int val) {
        int count = right.get(val);

        if (count == 1) {
            right.remove(val);
        } else {
            right.put(val, count - 1);
        }

        rightSize--;
        rightSum -= val;
    }

    void rebalance() {

        int requiredLeft = (leftSize + rightSize + 1) / 2;

        while (leftSize > requiredLeft) {
            int val = left.lastKey();
            removeFromLeft(val);
            addToRight(val);
        }

        while (leftSize < requiredLeft) {
            int val = right.firstKey();
            removeFromRight(val);
            addToLeft(val);
        }
    }

    void add(int val) {

        if (left.isEmpty() || val <= left.lastKey()) {
            addToLeft(val);
        } else {
            addToRight(val);
        }

        rebalance();
    }

    void remove(int val) {

        if (left.containsKey(val)) {
            removeFromLeft(val);
        } else {
            removeFromRight(val);
        }

        rebalance();
    }

    long getCost() {

        long median = left.lastKey();

        long leftCost = median * leftSize - leftSum;
        long rightCost = rightSum - median * rightSize;

        return leftCost + rightCost;
    }

    public long minOperations(int[] nums, int x, int k) {

        int[] maritovexi = nums;

        int n = maritovexi.length;

        long[] cost = new long[n - x + 1];

        // Calculate cost of every window
        for (int i = 0; i < n; i++) {

            add(maritovexi[i]);

            if (i >= x) {
                remove(maritovexi[i - x]);
            }

            if (i >= x - 1) {
                cost[i - x + 1] = getCost();
            }
        }

        // DP
        long INF = Long.MAX_VALUE / 4;

        long[][] dp = new long[k + 1][n + 1];

        for (int j = 0; j <= k; j++) {
            Arrays.fill(dp[j], INF);
        }

        // Zero windows cost zero
        Arrays.fill(dp[0], 0);

        for (int j = 1; j <= k; j++) {

            for (int i = 1; i <= n; i++) {

                // Skip current element
                dp[j][i] = dp[j][i - 1];

                // Select a window ending at i - 1
                if (i >= x && dp[j - 1][i - x] != INF) {

                    long take = dp[j - 1][i - x]
                              + cost[i - x];

                    dp[j][i] = Math.min(dp[j][i], take);
                }
            }
        }

        return dp[k][n];
    }
}
