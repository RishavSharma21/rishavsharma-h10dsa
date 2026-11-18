/*
 * LeetCode 133 - Clone Graph
 * Approach: Depth-First Search (DFS) + HashMap
 * Time: O(V + E) | Space: O(V)
 *
 * LeetCode provides the Node class definition.
 */
import java.util.HashMap;
import java.util.Map;

class Solution {
    private Map<Node, Node> visited = new HashMap<>();

    public Node cloneGraph(Node node) {
        if (node == null) {
            return null;
        }

        if (visited.containsKey(node)) {
            return visited.get(node);
        }

        Node clone = new Node(node.val);
        visited.put(node, clone);

        for (Node neighbor : node.neighbors) {
            clone.neighbors.add(cloneGraph(neighbor));
        }

        return clone;
    }
}
