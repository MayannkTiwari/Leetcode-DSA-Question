import java.util.Arrays;

class Solution {
    public int findTheDistanceValue(int[] arr1, int[] arr2, int d) {

        Arrays.sort(arr2);

        int count = 0;

        for (int num : arr1) {

            int left = 0;
            int right = arr2.length;

            // Find first element >= num - d
            while (left < right) {
                int mid = left + (right - left) / 2;

                if (arr2[mid] < num - d) {
                    left = mid + 1;
                } else {
                    right = mid;
                }
            }

            // Check whether an element lies in [num-d, num+d]
            if (left == arr2.length || arr2[left] > num + d) {
                count++;
            }
        }

        return count;
    }
}