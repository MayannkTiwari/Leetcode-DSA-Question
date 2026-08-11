class Solution {
    public int missingInteger(int[] nums) {

        // Find sum of longest sequential prefix
        int sum = nums[0];

        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == nums[i - 1] + 1) {
                sum += nums[i];
            } else {
                break;
            }
        }

        // Find the smallest missing number >= sum
        boolean[] present = new boolean[101];

        for (int num : nums) {
            present[num] = true;
        }

        while (sum <= 100 && present[sum]) {
            sum++;
        }

        return sum;
    }
}