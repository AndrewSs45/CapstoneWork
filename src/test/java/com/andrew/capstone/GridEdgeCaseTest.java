package com.andrew.capstone;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GridEdgeCaseTest {

    @Test
    void testEvolveEmptyGrid() {
        Grid g = new Grid(new int[0][0]);
        Grid next = g.evolve();
        assertEquals(0, next.getRows());
        assertEquals(0, next.getCols());
    }

    @Test
    void testEvolveSingleCellAir() {
        int[][] grid = {{0}};
        Grid g = new Grid(grid);
        Grid next = g.evolve();
        assertEquals(0, next.getCell(0, 0));
    }

    @Test
    void testEvolveSingleCellTreeNoWaterAbove() {
        int[][] grid = {{1}};
        Grid g = new Grid(grid);
        Grid next = g.evolve();
        assertEquals(0, next.getCell(0, 0));
    }

    @Test
    void testTreeSurvivesWithWaterOuterRing() {
        int[][] grid = {
            {0, 0, 0, 0, 0},
            {0, 1, 1, 1, 0},
            {0, 1, 1, 1, 0},
            {0, 1, 1, 1, 0},
            {3, 0, 0, 0, 0}
        };
        Grid g = new Grid(grid);
        Grid next = g.evolve();
        assertEquals(1, next.getCell(2, 2));
    }

    @Test
    void testAnimalMovesDirection1() {
        int[][] grid = {
            {0, 0, 0, 0},
            {0, 1, 0, 0},
            {0, 2, 0, 3},
            {0, 0, 0, 0}
        };
        Grid g = new Grid(grid);
        Grid next = g.evolve(2, 1);
        assertTrue(next.getCell(2, 2) == 2 || next.getCell(2, 1) == 2);
    }

    @Test
    void testMeteoriteAtEdge() {
        int[][] grid = {
            {1, 0, 0},
            {0, 0, 0},
            {0, 0, 0}
        };
        Grid g = new Grid(grid);
        g.applyMeteorite(0, 0);
        assertEquals(4, g.getCell(0, 0));
    }

    @Test
    void testApplyMeteoriteOutOfBounds() {
        int[][] grid = {{1, 1}, {1, 1}};
        Grid g = new Grid(grid);
        g.applyMeteorite(-1, -1);
        assertEquals(1, g.getCell(0, 0));
    }

    @Test
    void testParseEmptyMap() {
        Grid g = Grid.parse("", 3, 3);
        assertEquals(0, g.getCell(0, 0));
        assertEquals(0, g.getCell(2, 2));
    }

    @Test
    void testParseTruncatedLines() {
        Grid g = Grid.parse("12", 3, 3);
        assertEquals(1, g.getCell(0, 0));
        assertEquals(2, g.getCell(0, 1));
        assertEquals(0, g.getCell(0, 2));
        assertEquals(0, g.getCell(1, 0));
    }

    @Test
    void testParseRndKeyword() {
        Grid g = Grid.parse("rnd", 2, 2);
        assertEquals(0, g.getCell(0, 0));
    }

    @Test
    void testEvolveWithDifferentDirections() {
        int[][] grid = {
            {0, 0, 0, 0, 0},
            {0, 1, 1, 1, 0},
            {0, 1, 2, 1, 3},
            {0, 1, 1, 1, 0},
            {0, 0, 0, 0, 0}
        };
        Grid g = new Grid(grid);
        Grid result1 = g.evolve(2, 2);
        assertNotNull(result1);
    }

    @Test
    void testEvolveGenerationZero() {
        int[][] grid = {
            {0, 0, 0},
            {0, 2, 0},
            {3, 0, 3}
        };
        Grid g = new Grid(grid);
        Grid next = g.evolve(0, 1);
        assertNotNull(next);
    }

    @Test
    void testMeteoriteClearsInnerRing() {
        int[][] grid = {
            {1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1}
        };
        Grid g = new Grid(grid);
        g.applyMeteorite(2, 2);
        assertEquals(4, g.getCell(2, 2));
        assertEquals(0, g.getCell(1, 1));
        assertEquals(0, g.getCell(1, 2));
        assertEquals(0, g.getCell(2, 1));
        assertEquals(0, g.getCell(2, 3));
        assertEquals(0, g.getCell(3, 2));
        assertEquals(1, g.getCell(0, 0));
    }
}
