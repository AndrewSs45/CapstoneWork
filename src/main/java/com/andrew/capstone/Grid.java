package com.andrew.capstone;

import com.andrew.capstone.CellType;

public class Grid {

    private static final int[][] INNER_RING = {
        {-1, -1}, {-1, 0}, {-1, 1},
        {0, -1},           {0, 1},
        {1, -1},  {1, 0},  {1, 1}
    };

    private static final int[][] OUTER_RING = {
        {-2, -2}, {-2, -1}, {-2, 0}, {-2, 1}, {-2, 2},
        {-1, -2},                     {-1, 2},
        {0, -2},                      {0, 2},
        {1, -2},                      {1, 2},
        {2, -2}, {2, -1}, {2, 0}, {2, 1}, {2, 2}
    };

    private final int[][] cells;
    private final int rows;
    private final int cols;

    public Grid(int[][] cells) {
        this.cells = cells;
        this.rows = cells.length;
        this.cols = cells[0].length;
    }

    public static Grid parse(String mapText, int rows, int cols) {
        int[][] grid = new int[rows][cols];
        String[] lines = mapText.split("#");
        for (int r = 0; r < rows; r++) {
            String line = r < lines.length ? lines[r] : "";
            for (int c = 0; c < cols; c++) {
                grid[r][c] = (c < line.length()) ? Character.getNumericValue(line.charAt(c)) : 0;
            }
        }
        return new Grid(grid);
    }

    public Grid evolve() {
        return evolve(0, 1);
    }

    public Grid evolve(int generation, int direction) {
        int[][] next = new int[rows][cols];
        for (int r = 0; r < rows; r++) {
            System.arraycopy(cells[r], 0, next[r], 0, cols);
        }

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                CellType currentType = CellType.fromValue(cells[r][c]);
                int treesInInner = countNeighbors(r, c, CellType.TREE.getValue(), INNER_RING);
                int animalsInInner = countNeighbors(r, c, CellType.ANIMAL.getValue(), INNER_RING);
                int waterInInner = countNeighbors(r, c, CellType.WATER.getValue(), INNER_RING);
                int treesInOuter = countNeighbors(r, c, CellType.TREE.getValue(), OUTER_RING);
                int waterInOuter = countNeighbors(r, c, CellType.WATER.getValue(), OUTER_RING);

                switch (currentType) {
                    case AIR:
                        if (treesInInner >= 2) {
                            next[r][c] = CellType.TREE.getValue();
                        } else if (generation % 2 == 0 && animalsInInner == 2) {
                            if ((treesInInner + treesInOuter) >= 1 && (waterInInner + waterInOuter) >= 1) {
                                next[r][c] = CellType.ANIMAL.getValue();
                            }
                        } else if (generation % 3 == 0) {
                            if (checkWaterAbove(r, c)) {
                                next[r][c] = CellType.WATER.getValue();
                            }
                        }
                        break;
                    case TREE:
                        if ((waterInInner + waterInOuter) < 1) {
                            next[r][c] = CellType.AIR.getValue();
                        }
                        break;
                    case ANIMAL:
                        if ((treesInInner + treesInOuter) < 1 || (waterInInner + waterInOuter) < 1) {
                            next[r][c] = CellType.AIR.getValue();
                        } else if (generation % 2 == 0) {
                            int[] nextPos = getNextPosition(r, c, direction);
                            int nr = nextPos[0];
                            int nc = nextPos[1];
                            if (isValid(nr, nc)) {
                                int nextType = cells[nr][nc];
                                if (nextType == CellType.AIR.getValue() || nextType == CellType.TREE.getValue()) {
                                    next[r][c] = CellType.AIR.getValue();
                                    next[nr][nc] = CellType.ANIMAL.getValue();
                                }
                            }
                        }
                        break;
                    case WATER:
                        break;
                    case METEORITE:
                        next[r][c] = CellType.AIR.getValue();
                        break;
                }
            }
        }
        return new Grid(next);
    }

    public void applyMeteorite(int r, int c) {
        if (isValid(r, c)) {
            for (int[] coord : INNER_RING) {
                int nr = r + coord[0];
                int nc = c + coord[1];
                if (isValid(nr, nc)) {
                    cells[nr][nc] = CellType.AIR.getValue();
                }
            }
            cells[r][c] = CellType.METEORITE.getValue();
        }
    }

    public int getCell(int r, int c) {
        return cells[r][c];
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public int[][] getCells() {
        return cells;
    }

    private int countNeighbors(int r, int c, int type, int[][] coords) {
        int count = 0;
        for (int[] coord : coords) {
            int nr = r + coord[0];
            int nc = c + coord[1];
            if (isValid(nr, nc) && cells[nr][nc] == type) {
                count++;
            }
        }
        return count;
    }

    private boolean checkWaterAbove(int r, int c) {
        for (int i = -1; i <= 1; i++) {
            int nr = r - 1;
            int nc = c + i;
            if (isValid(nr, nc) && cells[nr][nc] == 3) {
                return true;
            }
        }
        return false;
    }

    private static int[] getNextPosition(int r, int c, int direction) {
        return switch (direction) {
            case 1 -> new int[]{r, c + 1};
            case 2 -> new int[]{r + 1, c};
            case 3 -> new int[]{r, c - 1};
            case 4 -> new int[]{r - 1, c};
            default -> new int[]{r, c};
        };
    }

    private boolean isValid(int r, int c) {
        return r >= 0 && r < rows && c >= 0 && c < cols;
    }
}
