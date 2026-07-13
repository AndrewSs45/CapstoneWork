package com.andrew.capstone;

public enum CellType {
    AIR(0),
    TREE(1),
    ANIMAL(2),
    WATER(3),
    METEORITE(4);

    private final int value;
    private final String symbol;

    CellType(int value) {
        this.value = value;
        this.symbol = String.valueOf(value);
    }

    public int getValue() {
        return value;
    }

    public String getSymbol() {
        return symbol;
    }

    public static CellType fromValue(int value) {
        for (CellType type : values()) {
            if (type.value == value) return type;
        }
        throw new IllegalArgumentException("Unknown cell type value: " + value);
    }
}
