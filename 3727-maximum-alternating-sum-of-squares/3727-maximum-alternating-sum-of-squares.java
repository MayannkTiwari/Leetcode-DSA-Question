import java.util.Arrays;

class Solution {
    public long maxAlternatingSum(int[] nums) {
        int n = nums.length;
        long[] squares = new long[n];

        for (int i = 0; i < n; i++) {
            squares[i] = (long) nums[i] * nums[i];
        }

        Arrays.sort(squares);

        long answer = 0;
        int mid = n / 2;

        for (int i = 0; i < mid; i++) {
            answer -= squares[i];
        }

        for (int i = mid; i < n; i++) {
            answer += squares[i];
        }

        return answer;
    }
}