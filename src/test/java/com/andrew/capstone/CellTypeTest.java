package com.andrew.capstone;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CellTypeTest {

    @Test
    void testValues() {
        assertEquals(0, CellType.AIR.getValue());
        assertEquals(1, CellType.TREE.getValue());
        assertEquals(2, CellType.ANIMAL.getValue());
        assertEquals(3, CellType.WATER.getValue());
        assertEquals(4, CellType.METEORITE.getValue());
    }

    @Test
    void testFromValue() {
        assertEquals(CellType.AIR, CellType.fromValue(0));
        assertEquals(CellType.TREE, CellType.fromValue(1));
        assertEquals(CellType.ANIMAL, CellType.fromValue(2));
        assertEquals(CellType.WATER, CellType.fromValue(3));
        assertEquals(CellType.METEORITE, CellType.fromValue(4));
    }

    @Test
    void testFromValueInvalid() {
        assertThrows(IllegalArgumentException.class, () -> CellType.fromValue(99));
    }

    @Test
    void testGetSymbol() {
        assertNotNull(CellType.AIR.getSymbol());
        assertNotNull(CellType.TREE.getSymbol());
        assertNotNull(CellType.ANIMAL.getSymbol());
        assertNotNull(CellType.WATER.getSymbol());
        assertNotNull(CellType.METEORITE.getSymbol());
    }
}
