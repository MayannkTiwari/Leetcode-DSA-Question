import java.util.*;

class Solution {
    public List<Integer> maxActiveSectionsAfterTrade(String s, int[][] queries) {
        int n = s.length();

        // 1. Total '1's in original string
        int totalOnes = 0;
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == '1') totalOnes++;
        }

        // 2. Parse string into blocks of '0's and '1's
        List<Block> blocks = new ArrayList<>();
        int i = 0;
        while (i < n) {
            char c = s.charAt(i);
            int start = i;
            while (i < n && s.charAt(i) == c) i++;
            blocks.add(new Block(c - '0', start, i - 1, blocks.size()));
        }

        int numBlocks = blocks.size();
        int[] blockGain = new int[numBlocks]; // Stores full gain for internal '1' blocks

        // Calculate full gain for internal '1' blocks (if both adjacent '0' blocks exist)
        for (int k = 1; k < numBlocks - 1; k++) {
            Block b = blocks.get(k);
            if (b.type == 1) {
                Block leftZero = blocks.get(k - 1);
                Block rightZero = blocks.get(k + 1);
                blockGain[k] = leftZero.len() + rightZero.len();
            }
        }

        // 3. Build Segment Tree for Range Maximum Query on block gains
        SegmentTree st = new SegmentTree(blockGain);

        // Map each string index to its block index for fast O(1) lookup
        int[] charToBlock = new int[n];
        for (Block b : blocks) {
            for (int idx = b.start; idx <= b.end; idx++) {
                charToBlock[idx] = b.id;
            }
        }

        List<Integer> answer = new ArrayList<>();

        // 4. Process queries in O(log N) time each
        for (int[] q : queries) {
            int l = q[0];
            int r = q[1];

            int maxGain = 0;

            int firstBlockIdx = charToBlock[l];
            int lastBlockIdx = charToBlock[r];

            // Find the first and last '1' block indices strictly inside [l+1, r-1]
            int firstFullOneIdx = -1;
            int lastFullOneIdx = -1;

            int startB = firstBlockIdx;
            if (blocks.get(startB).start < l || blocks.get(startB).type == 0) startB++;
            
            int endB = lastBlockIdx;
            if (blocks.get(endB).end > r || blocks.get(endB).type == 0) endB--;

            // Find first '1' block
            for (int bIdx = startB; bIdx <= Math.min(startB + 2, endB); bIdx++) {
                if (blocks.get(bIdx).type == 1 && blocks.get(bIdx).start > l && blocks.get(bIdx).end < r) {
                    firstFullOneIdx = bIdx;
                    break;
                }
            }

            // Find last '1' block
            for (int bIdx = endB; bIdx >= Math.max(endB - 2, startB); bIdx--) {
                if (blocks.get(bIdx).type == 1 && blocks.get(bIdx).start > l && blocks.get(bIdx).end < r) {
                    lastFullOneIdx = bIdx;
                    break;
                }
            }

            if (firstFullOneIdx != -1) {
                // Check candidate 1: First full '1' block inside range
                maxGain = Math.max(maxGain, computeGain(blocks.get(firstFullOneIdx), l, r, blocks));

                // Check candidate 2: Last full '1' block inside range
                maxGain = Math.max(maxGain, computeGain(blocks.get(lastFullOneIdx), l, r, blocks));

                // Check Candidate 3: Internal '1' blocks whose adjacent '0' blocks are strictly fully inside [l, r]
                int innerStartOne = firstFullOneIdx;
                if (blocks.get(innerStartOne - 1).start < l) innerStartOne += 2;

                int innerEndOne = lastFullOneIdx;
                if (blocks.get(innerEndOne + 1).end > r) innerEndOne -= 2;

                if (innerStartOne <= innerEndOne) {
                    int segmentMax = st.query(1, 0, numBlocks - 1, innerStartOne, innerEndOne);
                    maxGain = Math.max(maxGain, segmentMax);
                }
            }

            answer.add(totalOnes + maxGain);
        }

        return answer;
    }

    private int computeGain(Block b1, int l, int r, List<Block> blocks) {
        Block leftZero = blocks.get(b1.id - 1);
        Block rightZero = blocks.get(b1.id + 1);

        int leftZeros = Math.min(leftZero.end, r) - Math.max(leftZero.start, l) + 1;
        int rightZeros = Math.min(rightZero.end, r) - Math.max(rightZero.start, l) + 1;

        return Math.max(0, leftZeros + rightZeros);
    }

    private static class Block {
        int type, start, end, id;
        Block(int type, int start, int end, int id) {
            this.type = type;
            this.start = start;
            this.end = end;
            this.id = id;
        }
        int len() { return end - start + 1; }
    }

    private static class SegmentTree {
        int[] tree;
        int n;

        SegmentTree(int[] arr) {
            n = arr.length;
            tree = new int[4 * n];
            build(arr, 1, 0, n - 1);
        }

        void build(int[] arr, int node, int start, int end) {
            if (start == end) {
                tree[node] = arr[start];
                return;
            }
            int mid = (start + end) / 2;
            build(arr, 2 * node, start, mid);
            build(arr, 2 * node + 1, mid + 1, end);
            tree[node] = Math.max(tree[2 * node], tree[2 * node + 1]);
        }

        int query(int node, int start, int end, int l, int r) {
            if (r < start || end < l) return 0;
            if (l <= start && end <= r) return tree[node];
            int mid = (start + end) / 2;
            return Math.max(
                query(2 * node, start, mid, l, r),
                query(2 * node + 1, mid + 1, end, l, r)
            );
        }
    }
}