/*
 * LeetCode 685 - Redundant Connection II
 * Approach: Handle a two-parent conflict, then use Union-Find
 * to detect a directed cycle in the remaining edges.
 * Time: O(n * alpha(n)) | Space: O(n)
 */
class Solution {
    private int[] parent;

    public int[] findRedundantDirectedConnection(int[][] edges) {
        int n = edges.length;
        int[] incomingEdge = new int[n + 1];

        int earlierEdge = -1;
        int laterEdge = -1;

        // Step 1: Check whether a node has two incoming edges.
        for (int i = 0; i < n; i++) {
            int to = edges[i][1];

            if (incomingEdge[to] == 0) {
                // Store index + 1 because 0 means no edge was stored.
                incomingEdge[to] = i + 1;
            } else {
                earlierEdge = incomingEdge[to] - 1;
                laterEdge = i;
                break;
            }
        }

        // Step 2: Run Union-Find, skipping the later edge if there
        // was a two-parent conflict.
        parent = new int[n + 1];

        for (int i = 1; i <= n; i++) {
            parent[i] = i;
        }

        for (int i = 0; i < n; i++) {
            if (i == laterEdge) {
                continue;
            }

            int from = edges[i][0];
            int to = edges[i][1];

            int rootFrom = find(from);
            int rootTo = find(to);

            if (rootFrom == rootTo) {
                if (earlierEdge != -1) {
                    return edges[earlierEdge];
                }

                return edges[i];
            }

            parent[rootTo] = rootFrom;
        }

        return edges[laterEdge];
    }

    private int find(int node) {
        if (parent[node] != node) {
            parent[node] = find(parent[node]);
        }

        return parent[node];
    }
}
