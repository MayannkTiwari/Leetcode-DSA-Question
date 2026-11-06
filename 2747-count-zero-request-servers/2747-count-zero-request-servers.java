class Solution {
    public int[] countServers(int n, int[][] logs, int x, int[] queries) {

        // Sort logs by time
        Arrays.sort(logs, (a, b) -> a[1] - b[1]);

        int m = queries.length;

        int[] ans = new int[m];
        int[] count = new int[n + 1];

        Integer[] indices = new Integer[m];

        for (int i = 0; i < m; i++) {
            indices[i] = i;
        }

        // Sort query indices by query time
        Arrays.sort(indices, (a, b) -> queries[a] - queries[b]);

        int left = 0;
        int right = 0;
        int active = 0;

        for (int idx : indices) {

            int q = queries[idx];

            // Add logs inside the right boundary
            while (right < logs.length && logs[right][1] <= q) {

                int server = logs[right][0];

                if (count[server] == 0) {
                    active++;
                }

                count[server]++;
                right++;
            }

            // Remove logs outside the left boundary
            while (left < right && logs[left][1] < q - x) {

                int server = logs[left][0];

                count[server]--;

                if (count[server] == 0) {
                    active--;
                }

                left++;
            }

            ans[idx] = n - active;
        }

        return ans;
    }
}