/*
 * LeetCode 210 - Course Schedule II
 * Approach: Kahn's Algorithm (BFS Topological Sort)
 * Time: O(V + E) | Space: O(V + E)
 */
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class Solution {
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        List<List<Integer>> graph = new ArrayList<>();

        for (int i = 0; i < numCourses; i++) {
            graph.add(new ArrayList<>());
        }

        int[] indegree = new int[numCourses];

        // prerequisite[1] must be completed before prerequisite[0].
        for (int[] prerequisite : prerequisites) {
            int course = prerequisite[0];
            int required = prerequisite[1];

            graph.get(required).add(course);
            indegree[course]++;
        }

        Queue<Integer> queue = new LinkedList<>();

        for (int course = 0; course < numCourses; course++) {
            if (indegree[course] == 0) {
                queue.offer(course);
            }
        }

        int[] order = new int[numCourses];
        int index = 0;

        while (!queue.isEmpty()) {
            int current = queue.poll();
            order[index]++;

            for (int nextCourse : graph.get(current)) {
                indegree[nextCourse]--;

                if (indegree[nextCourse] == 0) {
                    queue.offer(nextCourse);
                }
            }
        }

        // A cycle means not all courses can be completed.
        if (index != numCourses) {
            return new int[0];
        }

        return order;
    }
}
