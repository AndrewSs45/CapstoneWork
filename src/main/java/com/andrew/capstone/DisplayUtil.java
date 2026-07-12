package com.andrew.capstone;

public final class DisplayUtil {

    private DisplayUtil() {
    }

    public static void display(Grid grid) {
        int cols = grid.getCols();
        for (int r = 0; r < grid.getRows(); r++) {
            for (int c = 0; c < cols; c++) {
                System.out.print(getSymbol(grid.getCell(r, c)) + "  ");
            }
            System.out.println();
        }
        System.out.println("-".repeat(cols * 4));
    }

    public static String getSymbol(int type) {
        return switch (type) {
            case 1 -> "🌳";
            case 2 -> "🐑";
            case 3 -> "💧";
            case 4 -> "☄️";
            default -> "💨";
        };
    }
}
