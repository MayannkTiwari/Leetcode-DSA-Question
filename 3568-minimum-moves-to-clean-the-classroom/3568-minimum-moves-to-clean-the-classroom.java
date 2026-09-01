import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;

class Solution {
    public int minMoves(String[] classroom, int energy) {
        int m = classroom.length;
        int n = classroom[0].length();

        Map<Integer, Integer> litterIndex = new HashMap<>();
        int sr = -1, sc = -1;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                char ch = classroom[i].charAt(j);
                if (ch == 'S') {
                    sr = i;
                    sc = j;
                } else if (ch == 'L') {
                    litterIndex.put(i * n + j, litterIndex.size());
                }
            }
        }

        int k = litterIndex.size();
        int fullMask = (1 << k) - 1;
        if (k == 0) return 0;

        int maskCount = 1 << k;
        int energyCount = energy + 1;

        long totalStates = (long) m * n * maskCount * energyCount;
        boolean[] visited = new boolean[(int) totalStates];

        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        ArrayDeque<int[]> queue = new ArrayDeque<>();
        int startIdx = ((sr * n + sc) * maskCount + 0) * energyCount + energy;
        visited[startIdx] = true;
        queue.add(new int[]{sr, sc, 0, energy, 0});

        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int r = cur[0], c = cur[1], mask = cur[2], e = cur[3], moves = cur[4];

            if (mask == fullMask) {
                return moves;
            }
            if (e == 0) {
                continue;
            }

            for (int d = 0; d < 4; d++) {
                int nr = r + dr[d];
                int nc = c + dc[d];
                if (nr < 0 || nr >= m || nc < 0 || nc >= n) continue;

                char ch = classroom[nr].charAt(nc);
                if (ch == 'X') continue;

                int ne = (ch == 'R') ? energy : e - 1;

                int nmask = mask;
                Integer litterBit = litterIndex.get(nr * n + nc);
                if (litterBit != null) {
                    nmask = mask | (1 << litterBit);
                }

                int idx = ((nr * n + nc) * maskCount + nmask) * energyCount + ne;
                if (!visited[idx]) {
                    visited[idx] = true;
                    queue.add(new int[]{nr, nc, nmask, ne, moves + 1});
                }
            }
        }

        return -1;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        System.out.println(sol.minMoves(new String[]{"S.", "XL"}, 2));
        System.out.println(sol.minMoves(new String[]{"LS", "RL"}, 4));
        System.out.println(sol.minMoves(new String[]{"L.S", "RXL"}, 3));
    }
}