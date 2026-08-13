class Solution {
    private static class Node {
        int maxLen;
        int prefLen;
        int suffLen;
        char prefChar;
        char suffChar;

        Node(int maxLen, int prefLen, int suffLen, char prefChar, char suffChar) {
            this.maxLen = maxLen;
            this.prefLen = prefLen;
            this.suffLen = suffLen;
            this.prefChar = prefChar;
            this.suffChar = suffChar;
        }

        Node() {}
    }

    private static class SegmentTree {
        private final int n;
        private final Node[] tree;

        public SegmentTree(String s) {
            this.n = s.length();
            this.tree = new Node[4 * n];
            build(s, 1, 0, n - 1);
        }

        private Node merge(Node left, Node right, int leftSize, int rightSize) {
            Node res = new Node();
            res.prefChar = left.prefChar;
            res.suffChar = right.suffChar;

            // Prefix length calculation
            res.prefLen = left.prefLen;
            if (left.prefLen == leftSize && left.suffChar == right.prefChar) {
                res.prefLen += right.prefLen;
            }

            // Suffix length calculation
            res.suffLen = right.suffLen;
            if (right.suffLen == rightSize && left.suffChar == right.prefChar) {
                res.suffLen += left.suffLen;
            }

            // Max length calculation
            res.maxLen = Math.max(left.maxLen, right.maxLen);
            if (left.suffChar == right.prefChar) {
                res.maxLen = Math.max(res.maxLen, left.suffLen + right.prefLen);
            }

            return res;
        }

        private void build(String s, int node, int start, int end) {
            if (start == end) {
                char c = s.charAt(start);
                tree[node] = new Node(1, 1, 1, c, c);
                return;
            }
            int mid = start + (end - start) / 2;
            build(s, 2 * node, start, mid);
            build(s, 2 * node + 1, mid + 1, end);
            tree[node] = merge(tree[2 * node], tree[2 * node + 1], mid - start + 1, end - mid);
        }

        public void update(int node, int start, int end, int idx, char ch) {
            if (start == end) {
                tree[node] = new Node(1, 1, 1, ch, ch);
                return;
            }
            int mid = start + (end - start) / 2;
            if (idx <= mid) {
                update(2 * node, start, mid, idx, ch);
            } else {
                update(2 * node + 1, mid + 1, end, idx, ch);
            }
            tree[node] = merge(tree[2 * node], tree[2 * node + 1], mid - start + 1, end - mid);
        }

        public int getMax() {
            return tree[1].maxLen;
        }
    }

    public int[] longestRepeating(String s, String queryCharacters, int[] queryIndices) {
        int k = queryIndices.length;
        SegmentTree st = new SegmentTree(s);
        int[] ans = new int[k];

        for (int i = 0; i < k; i++) {
            st.update(1, 0, s.length() - 1, queryIndices[i], queryCharacters.charAt(i));
            ans[i] = st.getMax();
        }

        return ans;
    }
}