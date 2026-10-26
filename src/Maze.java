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
        // TODO
        return new int[0];
    }

    /**
     * Mission 2. Prim with a priority queue. Returns the total cost of the cheapest maze.
     * It must be equal to the total cost of cheapestMaze.
     */
    public static long primCost(Input.Walls w) {
        // TODO
        return 0;
    }
}
