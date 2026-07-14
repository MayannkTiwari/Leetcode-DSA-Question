import java.util.Arrays;

class Solution {

    static final int MOD = 1_000_000_007;
    int[][][] dp;

    public int subsequencePairCount(int[] nums) {

        dp = new int[nums.length][201][201];

        for (int[][] x : dp) {
            for (int[] y : x) {
                Arrays.fill(y, -1);
            }
        }

        return solve(nums, 0, 0, 0);
    }

    private int solve(int[] nums, int index, int gcd1, int gcd2) {

        if (index == nums.length) {
            if (gcd1 != 0 && gcd2 != 0 && gcd1 == gcd2)
                return 1;
            return 0;
        }

        if (dp[index][gcd1][gcd2] != -1)
            return dp[index][gcd1][gcd2];

        long ans = 0;

        
        ans += solve(nums, index + 1,
                gcd(gcd1, nums[index]),
                gcd2);


        ans += solve(nums, index + 1,
                gcd1,
                gcd(gcd2, nums[index]));

        
        ans += solve(nums, index + 1,
                gcd1,
                gcd2);

        ans %= MOD;

        return dp[index][gcd1][gcd2] = (int) ans;
    }

    private int gcd(int a, int b) {
        if (a == 0) return b;
        if (b == 0) return a;
        return gcd(b, a % b);
    }
}