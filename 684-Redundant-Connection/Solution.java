/*
 * LeetCode 684 - Redundant Connection
 * Approach: Disjoint Set Union (Union-Find) with path compression
 * Time: O(n * alpha(n)) | Space: O(n)
 */
class Solution {
    private int[] parent;
    private int[] rank;

    public int[] findRedundantConnection(int[][] edges) {
        int n = edges.length;
        parent = new int[n + 1];
        rank = new int[n + 1];

        for (int i = 1; i <= n; i++) {
            parent[i] = i;
        }

        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];

            // If both vertices already have the same root,
            // this edge creates a cycle.
            if (find(u) == find(v)) {
                return edge;
            }

            union(u, v);
        }

        return new int[0];
    }

    private int find(int node) {
        if (parent[node] != node) {
            parent[node] = find(parent[node]);
        }

        return parent[node];
    }

    private void union(int u, int v) {
        int rootU = find(u);
        int rootV = find(v);

        if (rootU == rootV) {
            return;
        }

        if (rank[rootU] < rank[rootV]) {
            parent[rootU] = rootV;
        } else if (rank[rootU] > rank[rootV]) {
            parent[rootV] = rootU;
        } else {
            parent[rootV] = rootU;
            rank[rootU]++;
        }
    }
}
