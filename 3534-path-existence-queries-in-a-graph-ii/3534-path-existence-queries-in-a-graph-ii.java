import java.util.*;

class Solution {
    public int[] pathExistenceQueries(int n, int[] nums, int maxDiff, int[][] queries) {
        Integer[] order = new Integer[n];
        for (int i = 0; i < n; i++) order[i] = i;
        Arrays.sort(order, (a, b) -> nums[a] - nums[b]);

        int[] sortedVal = new int[n];
        int[] pos = new int[n]; // pos[originalNode] = index in sorted order
        for (int i = 0; i < n; i++) {
            sortedVal[i] = nums[order[i]];
            pos[order[i]] = i;
        }

        int[] farthest = new int[n];
        int j = 0;
        for (int i = 0; i < n; i++) {
            if (j < i) j = i;
            while (j + 1 < n && sortedVal[j + 1] - sortedVal[i] <= maxDiff) j++;
            farthest[i] = j;
        }

        int[] compEnd = new int[n];
        int i = 0;
        while (i < n) {
            int end = farthest[i];
            int k = i;
            while (k <= end) {
                if (farthest[k] > end) end = farthest[k];
                k++;
            }
            for (int t = i; t < k; t++) compEnd[t] = k - 1;
            i = k;
        }

        int LOG = 1;
        while ((1 << LOG) < n) LOG++;
        LOG += 1;
        int[][] up = new int[LOG][n];
        up[0] = farthest.clone();
        for (int k = 1; k < LOG; k++) {
            for (int idx = 0; idx < n; idx++) {
                up[k][idx] = up[k - 1][up[k - 1][idx]];
            }
        }

        // 5. Answer queries
        int[] ans = new int[queries.length];
        for (int qi = 0; qi < queries.length; qi++) {
            int u = queries[qi][0], v = queries[qi][1];
            if (u == v) {
                ans[qi] = 0;
                continue;
            }
            int p = pos[u], q = pos[v];
            if (p > q) { int tmp = p; p = q; q = tmp; }

            if (compEnd[p] < q) {
                ans[qi] = -1;
                continue;
            }

            int cur = p, steps = 0;
            for (int k = LOG - 1; k >= 0; k--) {
                if (up[k][cur] < q) {
                    cur = up[k][cur];
                    steps += (1 << k);
                }
            }
            ans[qi] = steps + 1;
        }

        return ans;
    }
}