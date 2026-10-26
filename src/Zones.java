/**
 * Missions 4 and 5. The slides: a directed graph. Cells are 0 .. n-1, slide i goes from s.from()[i] to s.to()[i]
 * and takes s.time()[i]. There are no slides from a cell to itself, and the exit can always be reached from the start.
 *
 * Ready for you: Graphs.slidesFrom(s)[u] is the list of the slides that leave cell u, and Graphs.slidesInto(s)[v] is the list
 * of the slides that enter cell v (reversed graph). The lists hold slide numbers i; the cell at the other end is s.to()[i]
 * or s.from()[i].
 */
import java.util.*;

public class Zones {

    /**
     * Mission 4. Kosaraju. Returns zoneOf: zoneOf[cell] = number of the zone, numbers 0 .. zones-1.
     * Number the zones in the order in which the second pass finds them. That order is a topological order
     * of the zone graph: every slide between two zones goes from a smaller number to a larger number.
     *
     * Plan: 1) DFS on the slides, write the cells in the order in which they are finished (use a stack, not recursion);
     * 2) go through the cells from the last finished to the first; if a cell has no zone yet, DFS from it on the reversed
     * slides (slidesInto) and give every cell it reaches the next zone number.
     * Is DAG in Main is 1 when the number of zones equals the number of cells (no cycle), so you do not need a separate check.
     */
    public static int[] kosaraju(Input.Slides s) {
        int n = s.n();
        int[][] fromLists = Graphs.slidesFrom(s);
        boolean[] visited = new boolean[n];
        Deque<Integer> finishOrder = new ArrayDeque<>();

        // Pass 1: Iterative DFS to get finish order
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                Deque<DFSNode> stack = new ArrayDeque<>();
                stack.push(new DFSNode(i, 0));
                visited[i] = true;

                while (!stack.isEmpty()) {
                    DFSNode curr = stack.peek();
                    int u = curr.u;
                    int[] slideIndices = fromLists[u];

                    boolean pushedNew = false;
                    while (curr.edgeIdx < slideIndices.length) {
                        int slideIdx = slideIndices[curr.edgeIdx];
                        curr.edgeIdx++;
                        int v = s.to()[slideIdx];
                        if (!visited[v]) {
                            visited[v] = true;
                            stack.push(new DFSNode(v, 0));
                            pushedNew = true;
                            break;
                        }
                    }
                    if (!pushedNew) {
                        stack.pop();
                        finishOrder.push(u);
                    }
                }
            }
        }

        // Pass 2: Iterative DFS on reversed graph (slidesInto)
        int[] zoneOf = new int[n];
        Arrays.fill(zoneOf, -1);
        int zoneCount = 0;
        int[][] intoLists = Graphs.slidesInto(s);

        while (!finishOrder.isEmpty()) {
            int start = finishOrder.pop();
            if (zoneOf[start] != -1) continue;

            Deque<Integer> compStack = new ArrayDeque<>();
            compStack.push(start);
            zoneOf[start] = zoneCount;

            while (!compStack.isEmpty()) {
                int u = compStack.pop();
                for (int slideIdx : intoLists[u]) {
                    int v = s.from()[slideIdx];
                    if (zoneOf[v] == -1) {
                        zoneOf[v] = zoneCount;
                        compStack.push(v);
                    }
                }
            }
            zoneCount++;
        }

        return zoneOf;
    }

    private static class DFSNode {
        int u;
        int edgeIdx;
        DFSNode(int u, int edgeIdx) {
            this.u = u;
            this.edgeIdx = edgeIdx;
        }
    }

    /**
     * Mission 5. The graph of zones: the edge X to Y costs the cheapest slide from X to Y, walking inside a zone is free.
     * Go through the zones in the order of their numbers (no priority queue). Returns two numbers:
     * [0] flow stages: the number of zones on the longest chain of zones;
     * [1] the cost of the cheapest route from the zone of cell 0 to the zone of the last cell.
     */
    public static long[] routes(Input.Slides s, int[] zoneOf, int zoneCount) {
        // TODO
        return new long[]{0, 0};
    }
}
