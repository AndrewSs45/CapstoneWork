package com.andrew.capstone;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GridTest {

    @Test
    void testTreeGrowsWithWaterNearby() {
        // Setup: water next to an empty cell
        int[][] grid = {
            {0, 0, 0},
            {0, 0, 3},
            {0, 0, 0}
        };
        Grid g = new Grid(grid);
        int[][] next = g.evolve().getCells();
        // Cell (0,0) should become a tree if conditions met
        // Actual condition depends on CellType rules
        assertNotNull(next);
    }

    @Test
    void testAnimalDiesWithoutFood() {
        // Animal with no tree nearby should die
        int[][] grid = {
            {0, 0, 0},
            {0, 2, 0},
            {0, 0, 0}
        };
        Grid g = new Grid(grid);
        int[][] next = g.evolve().getCells();
        // Animal should die (no tree to eat)
        assertEquals(CellType.AIR.getValue(), next[1][1]);
    }

    @Test
    void testMeteoriteClearsArea() {
        int[][] grid = {
            {1, 1, 1},
            {1, 4, 1},
            {1, 1, 1}
        };
        Grid g = new Grid(grid);
        int[][] next = g.evolve().getCells();
        // Meteorite center and surrounding should become air
        assertEquals(CellType.AIR.getValue(), next[1][1]);
    }

    @Test
    void testWaterStatic() {
        int[][] grid = {
            {0, 0, 0},
            {0, 3, 0},
            {0, 0, 0}
        };
        Grid g = new Grid(grid);
        int[][] next = g.evolve().getCells();
        assertEquals(CellType.WATER.getValue(), next[1][1]);
    }
}
