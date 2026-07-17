import java.util.*;

class Solution {
    public int[] gcdValues(int[] nums, long[] queries) {
        int mx = 0;
        for (int x : nums) mx = Math.max(mx, x);

        int[] freq = new int[mx + 1];
        for (int x : nums) freq[x]++;

        long[] cnt = new long[mx + 1];

        for (int g = mx; g >= 1; g--) {
            long c = 0;
            for (int j = g; j <= mx; j += g) {
                c += freq[j];
            }

            cnt[g] = c * (c - 1) / 2;

            for (int j = g * 2; j <= mx; j += g) {
                cnt[g] -= cnt[j];
            }
        }

        long[] pref = new long[mx + 1];
        for (int i = 1; i <= mx; i++) {
            pref[i] = pref[i - 1] + cnt[i];
        }

        int[] ans = new int[queries.length];

        for (int i = 0; i < queries.length; i++) {
            long q = queries[i] + 1;

            int l = 1;
            int r = mx;

            while (l < r) {
                int mid = l + (r - l) / 2;

                if (pref[mid] >= q) {
                    r = mid;
                } else {
                    l = mid + 1;
                }
            }

            ans[i] = l;
        }

        return ans;
    }
}