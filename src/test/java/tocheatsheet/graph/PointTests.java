package tocheatsheet.graph;

import org.junit.Assert;
import org.junit.Test;

public class PointTests {

    @Test
    public void visitAllExcept6() {
        int sum = 0;
        int val = 0;
        int[][] grid = new int[20][];
        for (int i = 0; i < grid.length; ++i) {
            grid[i] = new int[20];
            for (int j = 0; j < grid[i].length; ++j, ++val) {
                grid[i][j] = val;
                if (shouldVisit(val)) sum += val;
            }
        }

        final Holder bfsSum = new Holder();
        new Point(0,0, grid).BFS(
                p -> bfsSum.x += p.val(),
                p -> shouldVisit(p.val())
        );
        Assert.assertEquals(sum, bfsSum.x);

        final Holder dfsSum = new Holder();
        new Point(0,0, grid).DFS(
                p -> dfsSum.x += p.val(),
                p -> shouldVisit(p.val())
        );
        Assert.assertEquals(sum, dfsSum.x);
    }

    static boolean shouldVisit(int val) {
        return val != 6;
    }

    static class Holder {
        int x = 0;
    }
}
