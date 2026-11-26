/*
 * LeetCode 685 - Redundant Connection II
 * Approach: Detect a two-parent conflict, then use Union-Find
 * to identify the edge that must be removed.
 * Time: O(n * alpha(n)) | Space: O(n)
 */
class Solution {
    private int[] parent;

    public int[] findRedundantDirectedConnection(int[][] edges) {
        int n = edges.length;
        int[] firstParentEdge = new int[n + 1];
        int[] secondParentEdge = new int[n + 1];

        // Step 1: Find a node with two incoming edges, if one exists.
        for (int i = 0; i < n; i++) {
            int from = edges[i][0];
            int to = edges[i][1];

            if (secondParentEdge[to] != 0) {
                // This is the second incoming edge to 'to'.
                // Store its index; remember the earlier edge index too.
                return checkWithTwoParentEdges(edges, firstParentEdge[to], i);
            }

            if (firstParentEdge[to] != 0) {
                secondParentEdge[to] = i;
            } else {
                firstParentEdge[to] = i;
            }
        }

        // No node has two parents. Find the edge that creates a cycle.
        return findCycleEdge(edges, -1);
    }

    private int[] checkWithTwoParentEdges(int[][] edges, int earlierIndex,
                                           int laterIndex) {
        // First, skip the later edge. If there is no cycle, it is redundant.
        int[] result = findCycleEdge(edges, laterIndex);

        if (result.length == 0) {
            return edges[laterIndex];
        }

        // Otherwise, the earlier edge must be removed.
        return edges[earlierIndex];
    }

    private int[] findCycleEdge(int[][] edges, int skippedIndex) {
        int n = edges.length;
        parent = new int[n + 1];

        for (int i = 1; i <= n; i++) {
            parent[i] = i;
        }

        for (int i = 0; i < n; i++) {
            if (i == skippedIndex) {
                continue;
            }

            int from = edges[i][0];
            int to = edges[i][1];

            int rootFrom = find(from);
            int rootTo = find(to);

            if (rootFrom == rootTo) {
                return edges[i];
            }

            parent[rootTo] = rootFrom;
        }

        return new int[0];
    }

    private int find(int node) {
        if (parent[node] != node) {
            parent[node] = find(parent[node]);
        }

        return parent[node];
    }
}
