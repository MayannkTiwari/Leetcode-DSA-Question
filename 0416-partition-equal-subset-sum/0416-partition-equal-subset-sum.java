
class Solution {
    public boolean canPartition(int[] nums) {
        int n = nums.length;
        int total = 0;

        for (int i=0;i<nums.length;i++) {
            total += nums[i];
        }

        if (total % 2 != 0) {
            return false;
        }

        int target = total / 2;
        boolean[][] dp = new boolean[n + 1][target + 1];

        for (int i = 0; i <= n; i++) {
            dp[i][0] = true;
        }

        for (int i = 1; i <= n; i++) {
            for (int s = 1; s <= target; s++) {
                dp[i][s] = dp[i - 1][s];

                if (s >= nums[i - 1]) {
                    dp[i][s] = dp[i][s] || dp[i - 1][s - nums[i - 1]];
                }
            }
        }

        return dp[n][target];
    }
}
