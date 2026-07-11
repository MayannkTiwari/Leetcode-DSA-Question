import java.util.*;

class Solution {
    public int countCompleteComponents(int n, int[][] edges) {

        ArrayList<Integer>[] graph = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int[] edge : edges) {
            graph[edge[0]].add(edge[1]);
            graph[edge[1]].add(edge[0]);
        }

        boolean[] visited = new boolean[n];
        int answer = 0;

        for (int i = 0; i < n; i++) {

            if (!visited[i]) {

                int[] info = dfs(i, graph, visited);

                int nodes = info[0];
                int edgesCount = info[1] / 2;

                if (edgesCount == nodes * (nodes - 1) / 2) {
                    answer++;
                }
            }
        }

        return answer;
    }

    private int[] dfs(int node, ArrayList<Integer>[] graph, boolean[] visited) {

        visited[node] = true;

        int nodes = 1;
        int degreeSum = graph[node].size();

        for (int next : graph[node]) {

            if (!visited[next]) {

                int[] child = dfs(next, graph, visited);

                nodes += child[0];
                degreeSum += child[1];
            }
        }

        return new int[]{nodes, degreeSum};
    }
}