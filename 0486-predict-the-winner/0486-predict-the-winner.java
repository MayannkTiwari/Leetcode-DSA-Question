class Solution {

    int[][] dp;

    public boolean predictTheWinner(int[] nums) {

        int n = nums.length;
        dp = new int[n][n];

        for (int i = 0; i < n; i++) {
            Arrays.fill(dp[i], Integer.MIN_VALUE);
        }

        return solve(nums, 0, n - 1) >= 0;
    }

    private int solve(int[] nums, int left, int right) {

        if (left == right) {
            return nums[left];
        }

        if (dp[left][right] != Integer.MIN_VALUE) {
            return dp[left][right];
        }

        int takeLeft = nums[left] - solve(nums, left + 1, right);
        int takeRight = nums[right] - solve(nums, left, right - 1);

        dp[left][right] = Math.max(takeLeft, takeRight);

        return dp[left][right];
    }
}