/**
 * Mission 3. Escape: DFS from cell 0 to the last cell (rows * cols - 1) in the maze of Mission 1.
 * Two neighbouring cells are connected when the wall between them is broken.
 * The neighbours are tried in this order: up, right, down, left (cell - cols, cell + 1, cell + cols, cell - 1).
 * The ready method Graphs.open(w, broken) gives you the open neighbours of every cell, in no particular order:
 * to visit them in the required order, go through the four cells above one by one and take those that are in the list.
 * The program must not overflow the stack on a maze of 1000 by 1000 cells.
 */
import java.util.*;

public class Escape {

    /**
     * path: the cells of the way out, from 0 to the last cell.
     * visited: how many cells are marked as visited at the moment the exit is marked (the start and the exit are counted).
     * deadEnds: how many cells of the maze have exactly one open neighbour.
     */
    public record Result(int[] path, int visited, int deadEnds) {}

    public static Result escape(Input.Walls w, int[] broken) {
        int rows = w.rows();
        int cols = w.cols();
        int numCells = rows * cols;
        int exit = numCells - 1;

        // Get open neighbors for each cell using the ready Graphs.open method
        int[][] openNeighbors = Graphs.open(w, broken);

        // Fast lookup matrix for open connections
        boolean[][] isOpen = new boolean[numCells][numCells];
        for (int u = 0; u < numCells; u++) {
            for (int v : openNeighbors[u]) {
                isOpen[u][v] = true;
            }
        }

        // Count dead ends: cells that have exactly one open neighbor (start and exit count too)
        int deadEnds = 0;
        for (int i = 0; i < numCells; i++) {
            if (openNeighbors[i].length == 1) {
                deadEnds++;
            }
        }

        // Iterative DFS with parent tracking for path reconstruction
        int[] parent = new int[numCells];
        Arrays.fill(parent, -1);
        boolean[] visited = new boolean[numCells];

        Deque<Integer> stack = new ArrayDeque<>();

        stack.push(0);
        visited[0] = true;
        int visitedCount = 1; // start cell is counted

        boolean exitReached = false;

        // Strict order of directions: up, right, down, left
        int[] directions = {-cols, 1, cols, -1};

        while (!stack.isEmpty() && !exitReached) {
            int curr = stack.peek();

            if (curr == exit) {
                exitReached = true;
                break;
            }

            boolean foundNeighbor = false;

            // Try neighbors in the required order: up, right, down, left
            for (int dir : directions) {
                int next = curr + dir;

                // Boundary checks for horizontal moves to prevent wrapping around rows
                if (dir == 1 && curr % cols == cols - 1) continue;
                if (dir == -1 && curr % cols == 0) continue;

                if (next >= 0 && next < numCells && isOpen[curr][next] && !visited[next]) {
                    visited[next] = true;
                    visitedCount++;
                    parent[next] = curr;
                    stack.push(next);
                    foundNeighbor = true;

                    if (next == exit) {
                        exitReached = true;
                    }
                    break;
                }
            }

            if (!foundNeighbor) {
                stack.pop(); // Backtrack if no unvisited open neighbors
            }
        }

        // Reconstruct path from exit to start
        List<Integer> pathList = new ArrayList<>();
        int curr = exit;
        while (curr != -1) {
            pathList.add(curr);
            curr = parent[curr];
        }
        Collections.reverse(pathList);

        int[] path = new int[pathList.size()];
        for (int i = 0; i < pathList.size(); i++) {
            path[i] = pathList.get(i);
        }

        return new Result(path, visitedCount, deadEnds);
    }
}
