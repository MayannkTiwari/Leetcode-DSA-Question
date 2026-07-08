import java.util.*;

class Solution {
    private static final long MOD = 1_000_000_007L;
    private long[] pow10;
    private long[] val, sm;
    private int[] cnt;
    private String s;
    private int n;

    public int[] sumAndMultiply(String s, int[][] queries) {
        this.s = s;
        this.n = s.length();

        pow10 = new long[n + 1];
        pow10[0] = 1;
        for (int i = 1; i <= n; i++) {
            pow10[i] = pow10[i - 1] * 10 % MOD;
        }

        val = new long[4 * Math.max(n, 1)];
        sm  = new long[4 * Math.max(n, 1)];
        cnt = new int[4 * Math.max(n, 1)];

        if (n > 0) {
            build(1, 0, n - 1);
        }

        int[] ans = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            int l = queries[i][0], r = queries[i][1];
            long[] res = query(1, 0, n - 1, l, r); 
            ans[i] = (int) (res[0] * res[1] % MOD);
        }
        return ans;
    }

    private void build(int node, int start, int end) {
        if (start == end) {
            int d = s.charAt(start) - '0';
            if (d != 0) {
                val[node] = d;
                sm[node] = d;
                cnt[node] = 1;
            }
            return;
        }
        int mid = (start + end) / 2;
        build(2 * node, start, mid);
        build(2 * node + 1, mid + 1, end);
        mergeUp(node);
    }

    private void mergeUp(int node) {
        int l = 2 * node, r = 2 * node + 1;
        val[node] = (val[l] * pow10[cnt[r]] + val[r]) % MOD;
        sm[node]  = (sm[l] + sm[r]) % MOD;
        cnt[node] = cnt[l] + cnt[r];
    }

    
    private long[] query(int node, int start, int end, int l, int r) {
        if (r < start || end < l) {
            return new long[]{0, 0, 0};
        }
        if (l <= start && end <= r) {
            return new long[]{val[node], sm[node], cnt[node]};
        }
        int mid = (start + end) / 2;
        long[] left = query(2 * node, start, mid, l, r);
        long[] right = query(2 * node + 1, mid + 1, end, l, r);

        long combinedVal = (left[0] * pow10[(int) right[2]] + right[0]) % MOD;
        long combinedSum = (left[1] + right[1]) % MOD;
        long combinedCnt = left[2] + right[2];
        return new long[]{combinedVal, combinedSum, combinedCnt};
    }

    
    public static void main(String[] args) {
        Solution sol = new Solution();
        int[][] queries = {{0, 7}, {1, 3}, {4, 6}};
        int[] result = sol.sumAndMultiply("10203004", queries);
        System.out.println(Arrays.toString(result)); 
    }
}
