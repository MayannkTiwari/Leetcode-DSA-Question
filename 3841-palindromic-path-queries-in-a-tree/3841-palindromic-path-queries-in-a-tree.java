import java.util.*;

class Solution {
    List<Integer>[] graph;
    int[] parent, depth, size, heavy, head, pos;
    int[] tree;
    int timer;

    public List<Boolean> palindromePath(
        int n, int[][] edges, String s, String[] queries
    ) {
        graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int[] e : edges) {
            graph[e[0]].add(e[1]);
            graph[e[1]].add(e[0]);
        }

        parent = new int[n];
        depth = new int[n];
        size = new int[n];
        heavy = new int[n];
        head = new int[n];
        pos = new int[n];

        Arrays.fill(heavy, -1);

        dfs(0, -1);
        decompose(0, 0);

        tree = new int[4 * n];
        char[] chars = s.toCharArray();

        for (int i = 0; i < n; i++) {
            updateTree(1, 0, n - 1, pos[i],
                       1 << (chars[i] - 'a'));
        }

        List<Boolean> ans = new ArrayList<>();

        for (String query : queries) {
            String[] parts = query.split(" ");

            if (parts[0].equals("update")) {
                int u = Integer.parseInt(parts[1]);
                char c = parts[2].charAt(0);

                if (chars[u] != c) {
                    chars[u] = c;
                    updateTree(1, 0, n - 1, pos[u],
                               1 << (c - 'a'));
                }
            } else {
                int u = Integer.parseInt(parts[1]);
                int v = Integer.parseInt(parts[2]);

                int mask = queryPath(u, v);
                ans.add(Integer.bitCount(mask) <= 1);
            }
        }

        return ans;
    }

    int dfs(int u, int p) {
        parent[u] = p;
        size[u] = 1;
        int maxSize = 0;

        for (int v : graph[u]) {
            if (v == p) continue;

            depth[v] = depth[u] + 1;
            int childSize = dfs(v, u);
            size[u] += childSize;

            if (childSize > maxSize) {
                maxSize = childSize;
                heavy[u] = v;
            }
        }

        return size[u];
    }

    void decompose(int u, int h) {
        head[u] = h;
        pos[u] = timer++;

        if (heavy[u] != -1) {
            decompose(heavy[u], h);
        }

        for (int v : graph[u]) {
            if (v == parent[u] || v == heavy[u]) continue;
            decompose(v, v);
        }
    }

    void updateTree(int node, int left, int right,
                    int index, int value) {
        if (left == right) {
            tree[node] = value;
            return;
        }

        int mid = left + (right - left) / 2;

        if (index <= mid) {
            updateTree(node * 2, left, mid, index, value);
        } else {
            updateTree(node * 2 + 1, mid + 1, right,
                       index, value);
        }

        tree[node] = tree[node * 2] ^ tree[node * 2 + 1];
    }

    int queryTree(int node, int left, int right,
                  int ql, int qr) {
        if (ql > right || qr < left) return 0;

        if (ql <= left && right <= qr) {
            return tree[node];
        }

        int mid = left + (right - left) / 2;

        return queryTree(node * 2, left, mid, ql, qr)
             ^ queryTree(node * 2 + 1, mid + 1, right,
                         ql, qr);
    }

    int queryPath(int u, int v) {
        int mask = 0;

        while (head[u] != head[v]) {
            if (depth[head[u]] < depth[head[v]]) {
                int temp = u;
                u = v;
                v = temp;
            }

            mask ^= queryTree(1, 0, pos.length - 1,
                              pos[head[u]], pos[u]);

            u = parent[head[u]];
        }

        if (depth[u] > depth[v]) {
            int temp = u;
            u = v;
            v = temp;
        }

        mask ^= queryTree(1, 0, pos.length - 1, pos[u], pos[v]);

        return mask;
    }
}