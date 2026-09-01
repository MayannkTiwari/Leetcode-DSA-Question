import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;

class Solution {
    public int minMoves(String[] classroom, int energy) {
        int m = classroom.length;
        int n = classroom[0].length();

        Map<Integer, Integer> litterIndex = new HashMap<>();
        int sr = -1, sc = -1;

        // 1. Locate start position and index all litter locations
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
        if (k == 0) return 0; // No litter to collect

        // Check if starting location itself has litter
        int initialMask = 0;
        Integer startLitterBit = litterIndex.get(sr * n + sc);
        if (startLitterBit != null) {
            initialMask |= (1 << startLitterBit);
        }

        // 4-Dimensional Visited Array: [row][col][bitmask][current_energy]
        boolean[][][][] visited = new boolean[m][n][1 << k][energy + 1];

        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        // Queue stores: {row, col, mask, current_energy, moves}
        ArrayDeque<int[]> queue = new ArrayDeque<>();
        
        visited[sr][sc][initialMask][energy] = true;
        queue.add(new int[]{sr, sc, initialMask, energy, 0});

        // 2. BFS State Traversal
        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int r = cur[0], c = cur[1], mask = cur[2], e = cur[3], moves = cur[4];

            // Goal check: All litters collected
            if (mask == fullMask) {
                return moves;
            }

            // Cannot move further if energy is depleted
            if (e == 0) {
                continue;
            }

            for (int d = 0; d < 4; d++) {
                int nr = r + dr[d];
                int nc = c + dc[d];

                // Boundary & Obstacle check
                if (nr < 0 || nr >= m || nc < 0 || nc >= n) continue;
                char ch = classroom[nr].charAt(nc);
                if (ch == 'X') continue;

                // Recharge energy at 'R', otherwise subtract 1 unit
                int ne = (ch == 'R') ? energy : e - 1;

                // Update bitmask if stepping on uncollected litter
                int nmask = mask;
                Integer litterBit = litterIndex.get(nr * n + nc);
                if (litterBit != null) {
                    nmask |= (1 << litterBit);
                }

                if (!visited[nr][nc][nmask][ne]) {
                    visited[nr][nc][nmask][ne] = true;
                    queue.add(new int[]{nr, nc, nmask, ne, moves + 1});
                }
            }
        }

        return -1; // Unable to clean all litters
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        System.out.println(sol.minMoves(new String[]{"S.", "XL"}, 2));  // Output: 2
        System.out.println(sol.minMoves(new String[]{"LS", "RL"}, 4));  // Output: 1
        System.out.println(sol.minMoves(new String[]{"L.S", "RXL"}, 3)); // Output: 3
    }
}