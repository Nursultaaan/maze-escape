# Assignment 3. Maze Escape

Student ID: 250846
Name: Sabidoldanov Nursultan 
Group: SE-2512
Maze size (rows x columns): 9 x 8

This file is the report. Fill it in as you go. How to start: `START_HERE.md`. The task: `docs/Assignment3.pdf`.

## Program output

The 13 lines your program prints on your data (`java -cp out Main`):

    Broken: 71
    Kept: 56
    Last broken wall: 102
    MST cost: 21666
    Heaviest broken wall: 902
    Path length: 17
    DFS visited: 30
    Dead ends: 22
    Zones: 9
    Largest zone: 14
    Is DAG: 0
    Flow stages: 6
    Fastest route cost: 10

My maze:

![maze](output/maze.png)

## Questions

Answer in your own words, two or three sentences each.

Mission 0. What do find and union do, and what makes them fast in your code?
find locates the root representative of a set, while union merges two separate sets together. 
They run in near-constant time because of path compression

Mission 1. Why is Broken always R*C - 1, and Kept always (R-1)*(C-1)?
To connect all cells without loops, we always need to break exactly $R \times C - 1$ walls, leaving the remaining $(R-1)(C-1)$ walls standing.

Mission 2. My MST cost from Kruskal and from Prim, and why they are equal:
Both Kruskal and Prim find the cheapest way to connect the maze. Their total costs match because all wall prices are unique.

Mission 3. Why is there exactly one path between two cells? How did you avoid a stack overflow on a huge maze?
There is exactly one path between any two points because the maze forms a tree. To prevent crashes on huge $1000 \times 1000$ grids recursion was replaced with an explicit stack (Deque). 

Mission 4. Why does the second pass of Kosaraju go on the reversed graph? Why do the slides have no cycle exactly when Zones equals the number of cells?
Kosaraju's second pass uses the reversed graph to precisely isolate the strongly connected components (zones). If the number of zones equals the total number of cells it proves there are no cyclic loops in the slides.

Mission 5. Why is it enough to go through the zones in the order of their numbers? What does this pass cost?
Zones are already numbered in a strict chain order, so we don't need a priority queue. This final pass runs efficiently in linear time costing $O(\text{zones} + \text{slides})$

## AI use

The syllabus requires you to say which AI tools you used and for what. Write the tool (ChatGPT, Copilot, other) and what you asked it: explain an idea, find an error, review code. Write "none" if you did not use any. If you took code or text from a book or a website, give the source here.
Gemini was used to help review code logic, explain algorithmic concepts.


## Timing (Mission 7)

Milliseconds, 5 runs each, from `java -cp out Bench`.

| maze | Kruskal | Prim |
|---|---|---| 
| 100 x 100 |14 |12 |
| 300 x 300 |86 |83 |
| 1000 x 1000 |1279 |1991 |

What do the numbers say about how the time grows? Two or three sentences.
As the maze size increases, the running time grows larger for both algorithms. On smaller mazes, both Kruskal and Prim are lightning-fast and perform similarly. However, on a massive $1000 \times 1000$ maze, Kruskal is faster because its underlying group operations stay very lightweight, while Prim's queue gets heavier and slower as it handles a huge number of connections.

## Complexity

n is the number of cells, m the number of walls (or slides). Fill in every algorithm you wrote.

| algorithm | time | space | assumptions |
|---|---|---|---|
| DSU find and union | O(α(n)) per operation | O(n) | Path compression and union by size/rank are used |
| Kruskal | O(m log m) | O(n + m) | Edges must be sorted by cost |
| Prim | O(m log n) | O(n + m) | Uses a binary priority queue |
| DFS | O(n + m) | O(n) | Uses an explicit iterative stack (Deque) to prevent overflow |
| Kosaraju | O(n + m) | O(n + m) | Two passes over the directed graph with a stack |
| Route in the zone graph | O(zones + slides) | O(zones + slides) | Zones are pre-sorted in topological order |

## Before you submit

- data/walls.txt, data/slides.txt and output/maze.png are in the repository.
- `java tools/Check.java` shows all examples right.
- This README is filled in, the program output and the section AI use included.
- The work is pushed to main (at least one commit per mission) and the repository is public.
- The link is attached in Moodle.
