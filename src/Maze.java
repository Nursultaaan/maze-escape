import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Missions 1 and 2. Building the maze out of the walls.
 * A wall k joins the cells w.a()[k] and w.b()[k] and costs w.cost()[k]. Indexes start from 0,
 * the line number in walls.txt is the index + 1. The cells are connected by DisjointSet.
 */
public class Maze {

    /**
     * Mission 1. Take the walls in the order of the file. Break a wall when its two cells are in different sets.
     * Returns the indexes of the broken walls, in the order of breaking.
     */
    public static int[] breakWalls(Input.Walls w) {
        int numWalls = w.a().length;
        int maxCell = 0;
        for (int k = 0; k < numWalls; k++) {
            if (w.a()[k] > maxCell) maxCell = w.a()[k];
            if (w.b()[k] > maxCell) maxCell = w.b()[k];
        }

        DisjointSet dsu = new DisjointSet(maxCell + 1);
        List<Integer> broken = new ArrayList<>();

        for (int k = 0; k < numWalls; k++) {
            if (dsu.union(w.a()[k], w.b()[k])) {
                broken.add(k);
            }
        }

        int[] result = new int[broken.size()];
        for (int i = 0; i < broken.size(); i++) {
            result[i] = broken.get(i);
        }
        return result;
    }

    /**
     * Mission 2. Kruskal: the same, but take the walls from the cheapest to the most expensive.
     * Returns the indexes of the broken walls.
     */
    public static int[] cheapestMaze(Input.Walls w) {
        int numWalls = w.a().length;
        int maxCell = 0;
        Integer[] indices = new Integer[numWalls];
        for (int k = 0; k < numWalls; k++) {
            indices[k] = k;
            if (w.a()[k] > maxCell) maxCell = w.a()[k];
            if (w.b()[k] > maxCell) maxCell = w.b()[k];
        }

        // Sort wall indices by their cost
        Arrays.sort(indices, Comparator.comparingInt(k -> w.cost()[k]));

        DisjointSet dsu = new DisjointSet(maxCell + 1);
        List<Integer> broken = new ArrayList<>();

        for (int k : indices) {
            if (dsu.union(w.a()[k], w.b()[k])) {
                broken.add(k);
            }
        }

        int[] result = new int[broken.size()];
        for (int i = 0; i < broken.size(); i++) {
            result[i] = broken.get(i);
        }
        return result;
    }

    /**
     * Mission 2. Prim with a priority queue. Returns the total cost of the cheapest maze.
     * It must be equal to the total cost of cheapestMaze.
     */
    public static long primCost(Input.Walls w) {
        int numWalls = w.a().length;
        int maxCell = 0;
        for (int k = 0; k < numWalls; k++) {
            if (w.a()[k] > maxCell) maxCell = w.a()[k];
            if (w.b()[k] > maxCell) maxCell = w.b()[k];
        }
        int numCells = maxCell + 1;

        // Build adjacency list
        List<List<Neighbor>> adj = new ArrayList<>(numCells);
        for (int i = 0; i < numCells; i++) {
            adj.add(new ArrayList<>());
        }

        for (int k = 0; k < numWalls; k++) {
            int u = w.a()[k];
            int v = w.b()[k];
            int cost = w.cost()[k];
            adj.get(u).add(new Neighbor(v, cost));
            adj.get(v).add(new Neighbor(u, cost));
        }

        boolean[] visited = new boolean[numCells];
        PriorityQueue<Neighbor> pq = new PriorityQueue<>(Comparator.comparingInt(n -> n.cost));

        long totalCost = 0;
        int visitedCount = 0;

        // Start from vertex 0
        visited[0] = true;
        visitedCount++;
        for (Neighbor n : adj.get(0)) {
            pq.add(n);
        }

        while (!pq.isEmpty() && visitedCount < numCells) {
            Neighbor edge = pq.poll();
            int u = edge.to;
            if (visited[u]) continue;

            visited[u] = true;
            visitedCount++;
            totalCost += edge.cost;

            for (Neighbor next : adj.get(u)) {
                if (!visited[next.to]) {
                    pq.add(next);
                }
            }
        }

        return totalCost;
    }

    private static class Neighbor {
        int to;
        int cost;

        Neighbor(int to, int cost) {
            this.to = to;
            this.cost = cost;
        }
    }
}
