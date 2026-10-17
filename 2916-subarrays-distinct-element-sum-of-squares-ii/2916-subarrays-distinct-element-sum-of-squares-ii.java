
class Solution {
    private static final long MOD = 1_000_000_007L;

    private int n;
    private long[] sum;
    private long[] sumSquares;
    private long[] lazy;

    public int sumCounts(int[] nums) {
        n = nums.length;

        sum = new long[4 * n];
        sumSquares = new long[4 * n];
        lazy = new long[4 * n];

        int[] last = new int[100001];
        java.util.Arrays.fill(last, -1);

        long answer = 0;

        for (int r = 0; r < n; r++) {
            int previous = last[nums[r]];

            // All subarrays starting after the previous
            // occurrence gain one distinct element.
            update(1, 0, n - 1, previous + 1, r);

            answer = (answer + sumSquares[1]) % MOD;
            last[nums[r]] = r;
        }

        return (int) answer;
    }

    private void update(
            int node, int left, int right,
            int ql, int qr) {

        if (ql > right || qr < left) {
            return;
        }

        if (ql <= left && right <= qr) {
            apply(node, left, right);
            return;
        }

        pushDown(node, left, right);

        int mid = left + (right - left) / 2;

        update(node * 2, left, mid, ql, qr);
        update(node * 2 + 1, mid + 1, right, ql, qr);

        sum[node] =
            (sum[node * 2] + sum[node * 2 + 1]) % MOD;

        sumSquares[node] =
            (sumSquares[node * 2] +
             sumSquares[node * 2 + 1]) % MOD;
    }

    private void apply(int node, int left, int right) {
        long len = right - left + 1L;
        long increment = lazy[node];

        // Add one to each distinct count in this segment.
        sumSquares[node] = (
            sumSquares[node]
            + 2L * sum[node] % MOD
            + len
        ) % MOD;

        sum[node] = (sum[node] + len) % MOD;
        lazy[node] = (lazy[node] + 1) % MOD;
    }

    private void pushDown(int node, int left, int right) {
        if (lazy[node] == 0 || left == right) {
            return;
        }

        int mid = left + (right - left) / 2;
        long increment = lazy[node];

        applyWithValue(node * 2, left, mid, increment);
        applyWithValue(node * 2 + 1, mid + 1, right, increment);

        lazy[node] = 0;
    }

    private void applyWithValue(
            int node, int left, int right, long increment) {

        long len = right - left + 1L;

        sumSquares[node] = (
            sumSquares[node]
            + 2L * increment % MOD * sum[node] % MOD
            + increment * increment % MOD * len % MOD
        ) % MOD;

        sum[node] = (sum[node] + increment * len) % MOD;
        lazy[node] = (lazy[node] + increment) % MOD;
    }
}
