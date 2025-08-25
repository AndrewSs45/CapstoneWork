public class Main {
    static final int[][] innerRingCoords = {
            {-1, -1}, {-1, 0}, {-1, 1},
            {0, -1},  {0, 1},
            {1, -1},  {1, 0},  {1, 1}
    };
    static final int[][] outerRingCoords = {
            {-2, -2}, {-2, -1}, {-2, 0}, {-2, 1}, {-2, 2},
            {-1, -2},           {-1, 2},
            {0, -2},            {0, 2},
            {1, -2},            {1, 2},
            {2, -2}, {2, -1}, {2, 0}, {2, 1}, {2, 2}
    };

    public static void main(String[] args) {
        int[] permissibleWidth = {5, 10, 15, 40, 80};
        int[] permissibleHeight = {5, 10, 15, 40};
        int[] permissibleSpeed = {0, 250, 500, 1000, 4000};
        int[] permissibleDirections = {1, 2, 3, 4};

        int width = 10, height = 10, generations = 20, speed = 500, direction = 1;
        String map = args.length > 0 ? args[0] : "01030#20100#00030##";
        int[] meteoriteCoords = null;

        boolean valid = true;
        java.lang.StringBuilder report = new java.lang.StringBuilder();

        for (String arg : args) {
            String[] parts = arg.split("=");
            if (parts.length != 2) continue;
            String key = parts[0];
            String value = parts[1];

            if (!isNumeric(value) && !key.equals("m") && !key.equals("c")) {
                report.append("El argumento que enviaste '").append(key).append("' no es un numero o coordenada valida.\n");
                valid = false;
                continue;
            }

            switch (key) {
                case "w" -> width = java.lang.Integer.parseInt(value);
                case "h" -> height = java.lang.Integer.parseInt(value);
                case "g" -> generations = java.lang.Integer.parseInt(value);
                case "s" -> speed = java.lang.Integer.parseInt(value);
                case "n" -> direction = java.lang.Integer.parseInt(value);
                case "m" -> map = value;
                case "c" -> {
                    String[] coords = value.split(",");
                    if (coords.length == 2 && isNumeric(coords[0]) && isNumeric(coords[1])) {
                        meteoriteCoords = new int[]{java.lang.Integer.parseInt(coords[0]), java.lang.Integer.parseInt(coords[1])};
                    } else {
                        report.append("Coordenada de meteorito '").append(value).append("' es invalida.\n");
                        valid = false;
                    }
                }
            }
        }

        if (!valid) {
            System.out.print(report);
            System.out.println("No podemos ejecutar el proyecto debido a un error");
            return;
        }

        report.setLength(0);

        report.append("With=[").append(isValidParameter(width, permissibleWidth) ? width : "Invalido").append("]\n");
        if (!isValidParameter(width, permissibleWidth)) valid = false;

        report.append("Height=[").append(isValidParameter(height, permissibleHeight) ? height : "Invalido").append("]\n");
        if (!isValidParameter(height, permissibleHeight)) valid = false;

        report.append("Generations = [").append((generations >= 0 && generations <= 1000) ? generations : "Invalido").append("]\n");
        if (generations < 0 || generations > 1000) valid = false;

        report.append("speed = [").append(isValidParameter(speed, permissibleSpeed) ? speed : "Invalido").append("]\n");
        if (!isValidParameter(speed, permissibleSpeed)) valid = false;

        if (map == null || !isMapSizeValid(map, height, width)) {
            report.append("map = [invalido]\n");
            valid = false;
        } else {
            report.append("map = [").append(map).append("]\n");
        }

        report.append("n = ").append(isValidParameter(direction, permissibleDirections) ? direction : "1 (por defecto)").append("\n");
        if (!isValidParameter(direction, permissibleDirections)) valid = false;

        if (meteoriteCoords != null) {
            report.append("Meteorite Coords = [").append(meteoriteCoords[0]).append(",").append(meteoriteCoords[1]).append("]\n");
        }

        System.out.print(report);

        if (!valid) {
            System.out.println("No se puede ejecutar la simulación debido a parámetros inválidos.");
            return;
        }

        System.out.println("Todos los parametros están correctos :D");

        int[][] grid = parseMap(map, height, width);

        if (meteoriteCoords != null) {
            applyMeteorite(grid, meteoriteCoords[0], meteoriteCoords[1]);
        }

        System.out.println("Gen: 0");
        displayGrid(grid);

        for (int i = 1; i <= generations; i++) {
            System.out.println("Gen: " + i);
            grid = evolveGrid(grid, i, direction);
            displayGrid(grid);

            if (speed > 0) {
                try {
                    java.lang.Thread.sleep(speed);
                } catch (java.lang.InterruptedException e) {
                    System.out.println("Simulation interrupted.");
                    break;
                }
            }
        }
    }

    public static int[][] parseMap(String mapText, int rows, int cols) {
        int[][] grid = new int[rows][cols];
        String[] lines = mapText.split("#");

        for (int r = 0; r < rows; r++) {
            String line = r < lines.length ? lines[r] : "";
            for (int c = 0; c < cols; c++) {
                grid[r][c] = (c < line.length()) ? Character.getNumericValue(line.charAt(c)) : 0;
            }
        }
        return grid;
    }

    public static void displayGrid(int[][] grid) {
        int cols = grid[0].length;
        for (int[] ints : grid) {
            for (int j = 0; j < cols; j++) {
                System.out.print(getSymbol(ints[j]) + "  ");
            }
            System.out.println();
        }
        System.out.println("-".repeat(cols * 4));
    }

    public static String getSymbol(int type) {
        return switch (type) {
            case 1 -> "🌳"; // Árbol
            case 2 -> "🐑"; // Animal
            case 3 -> "💧"; // Agua
            case 4 -> "☄️"; // Meteorito
            default -> "💨"; // Vacío
        };
    }

    public static int[][] evolveGrid(int[][] currentGrid, int currentGeneration, int direction) {
        int rows = currentGrid.length;
        int cols = currentGrid[0].length;
        int[][] nextGenerationGrid = new int[rows][cols];

        for (int r = 0; r < rows; r++) {
            System.arraycopy(currentGrid[r], 0, nextGenerationGrid[r], 0, cols);
        }

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                int currentType = currentGrid[r][c];

                int treesInInnerRing = countNeighbors(currentGrid, r, c, 1, innerRingCoords);
                int animalsInInnerRing = countNeighbors(currentGrid, r, c, 2, innerRingCoords);
                int waterInInnerRing = countNeighbors(currentGrid, r, c, 3, innerRingCoords);
                int treesInOuterRing = countNeighbors(currentGrid, r, c, 1, outerRingCoords);
                int waterInOuterRing = countNeighbors(currentGrid, r, c, 3, outerRingCoords);

                switch (currentType) {
                    case 0:
                        if (treesInInnerRing >= 2) {
                            nextGenerationGrid[r][c] = 1;
                        } else if (currentGeneration % 2 == 0 && animalsInInnerRing == 2) {
                            if ((treesInInnerRing + treesInOuterRing) >= 1 && (waterInInnerRing + waterInOuterRing) >= 1) {
                                nextGenerationGrid[r][c] = 2;
                            }
                        } else if (currentGeneration % 3 == 0) {
                            if (checkWaterInTopRow(currentGrid, r, c)) {
                                nextGenerationGrid[r][c] = 3;
                            }
                        }
                        break;
                    case 1: // Árbol
                        if ((waterInInnerRing + waterInOuterRing) < 1) {
                            nextGenerationGrid[r][c] = 0;
                        }
                        break;
                    case 2: // Animal
                        if ((treesInInnerRing + treesInOuterRing) < 1 || (waterInInnerRing + waterInOuterRing) < 1) {
                            nextGenerationGrid[r][c] = 0;
                        } else {
                            if (currentGeneration % 2 == 0) {
                                int[] nextPos = getNextPosition(r, c, direction);
                                int nextR = nextPos[0];
                                int nextC = nextPos[1];

                                if (isCellValid(nextR, nextC, rows, cols)) {
                                    int nextType = currentGrid[nextR][nextC];
                                    if (nextType == 0 || nextType == 1) {
                                        nextGenerationGrid[r][c] = 0;
                                        nextGenerationGrid[nextR][nextC] = 2;
                                    }
                                }
                            }
                        }
                        break;
                    case 3:
                        break;
                    case 4:
                        nextGenerationGrid[r][c] = 0;
                        break;
                }
            }
        }
        return nextGenerationGrid;
    }
// Acá generamos el código para el meteorito
    public static void applyMeteorite(int[][] grid, int r, int c) {
        int rows = grid.length;
        int cols = grid[0].length;
        if (isCellValid(r, c, rows, cols)) {
            for (int[] coord : innerRingCoords) {
                int newR = r + coord[0];
                int newC = c + coord[1];
                if (isCellValid(newR, newC, rows, cols)) {
                    grid[newR][newC] = 0;
                }
            }
            grid[r][c] = 4;
        }
    }

    private static int countNeighbors(int[][] grid, int r, int c, int type, int[][] coords) {
        int count = 0;
        int rows = grid.length;
        int cols = grid[0].length;
        for (int[] coord : coords) {
            int newR = r + coord[0];
            int newC = c + coord[1];
            if (isCellValid(newR, newC, rows, cols) && grid[newR][newC] == type) {
                count++;
            }
        }
        return count;
    }

    private static boolean checkWaterInTopRow(int[][] grid, int r, int c) {
        int rows = grid.length;
        int cols = grid[0].length;
        for (int i = -1; i <= 1; i++) {
            int newR = r - 1;
            int newC = c + i;
            if (isCellValid(newR, newC, rows, cols) && grid[newR][newC] == 3) {
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

    public static boolean isNumeric(String str) {
        if (str == null || str.isEmpty()) return false;
        for (char ch : str.toCharArray()) {
            if (!java.lang.Character.isDigit(ch)) return false;
        }
        return true;
    }

    public static boolean isValidParameter(int value, int[] allowed) {
        for (int v : allowed) {
            if (v == value) return true;
        }
        return false;
    }

    public static boolean isMapSizeValid(String mapText, int rows, int cols) {
        if (mapText.equalsIgnoreCase("rnd")) return true;
        String[] lines = mapText.split("#");
        if (lines.length > rows) return false;
        for (String line : lines) {
            if (line.length() > cols) return false;
        }
        return true;
    }

    private static boolean isCellValid(int r, int c, int rows, int cols) {
        return r >= 0 && r < rows && c >= 0 && c < cols;
    }
}