package com.andrew.capstone;

public enum CellType {
    AIR(0),
    TREE(1),
    ANIMAL(2),
    WATER(3),
    METEORITE(4);

    private final int value;

    CellType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static CellType fromValue(int value) {
        for (CellType type : values()) {
            if (type.value == value) return type;
        }
        return AIR;
    }
}
