
class Solution {
    public int numOfSubarrays(int[] arr) {

        int mod = 1_000_000_007;

        int odd = 0;
        int even = 1;

        int prefix = 0;
        int ans = 0;

        for (int num : arr) {

            prefix = (prefix + num) % 2;

            if (prefix == 0) {
                ans = (ans + odd) % mod;
                even++;
            } 
            else {
                ans = (ans + even) % mod;
                odd++;
            }
        }

        return ans;
    }
}
