package tocheatsheet.graph;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

// An OO representation of an array based graph
public class Point {
    final int i;
    final int j;
    final int[][] grid;
    public Point(int i, int j, int[][] grid) {
        this.i = i;
        this.j = j;
        this.grid = grid;
    }

    int val() {
        return grid[i][j];
    }
    void val(int v) {
        grid[i][j] = v;
    }

    List<Point> next() {
        return new ArrayList<Point>(4) {{
             if (i > 0)                  add(new Point(i - 1, j, grid));
             if (i < grid.length    - 1) add(new Point(i + 1, j, grid));
             if (j > 0)                  add(new Point(i,j - 1, grid));
             if (j < grid[i].length - 1) add(new Point(i,j + 1, grid));
        }};
    }

    void BFS(Consumer<Point> onVisit, Function<Point, Boolean> shouldVisit) { BFS(this, onVisit, shouldVisit); }
    static void BFS(Point origin, Consumer<Point> onVisit, Function<Point, Boolean> shouldVisit) {
        final Set<Point> queued = new HashSet<>();
        final Queue<Point> q = new LinkedList<>();
        q.add(origin);
        queued.add(origin);
        while (!q.isEmpty()) {
            final Point p = q.poll();
            onVisit.accept(p);
            for (final Point n : p.next()) {
                if (shouldVisit.apply(n) && queued.add(n)) {
                    q.add(n);
                }
            }
        }
    }

    void DFS(Consumer<Point> onVisit, Function<Point, Boolean> shouldVisit) { DFS(this, onVisit, shouldVisit); }
    static void DFS(Point origin, Consumer<Point> onVisit, Function<Point, Boolean> shouldVisit) {
        final Set<Point> queued = new HashSet<>();
        final Stack<Point> q = new Stack<>();
        q.add(origin);
        queued.add(origin);
        while (!q.isEmpty()) {
            final Point p = q.pop();
            onVisit.accept(p);
            for (final Point n : p.next()) {
                if (shouldVisit.apply(n) && queued.add(n)) {
                    q.push(n);
                }
            }
        }
    }

    @Override
    public int hashCode() {
        return i ^ (j << 16);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null || !(o instanceof Point)) return false;
        final Point other = (Point)o;
        return i == other.i && j == other.j;
    }

    @Override
    public String toString() {
        return "(" + i + ", " + j + ")";
    }
}
