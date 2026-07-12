package com.andrew.capstone;

public class ArgumentParser {

    private static final int[] PERMISSIBLE_WIDTH = {5, 10, 15, 40, 80};
    private static final int[] PERMISSIBLE_HEIGHT = {5, 10, 15, 40};
    private static final int[] PERMISSIBLE_SPEED = {0, 250, 500, 1000, 4000};
    private static final int[] PERMISSIBLE_DIRECTIONS = {1, 2, 3, 4};

    private int width = 10;
    private int height = 10;
    private int generations = 20;
    private int speed = 500;
    private int direction = 1;
    private String map = "01030#20100#00030##";
    private int[] meteoriteCoords;
    private final StringBuilder report = new StringBuilder();
    private boolean valid = true;

    public ArgumentParser(String[] args) {
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
                case "w" -> width = Integer.parseInt(value);
                case "h" -> height = Integer.parseInt(value);
                case "g" -> generations = Integer.parseInt(value);
                case "s" -> speed = Integer.parseInt(value);
                case "n" -> direction = Integer.parseInt(value);
                case "m" -> map = value;
                case "c" -> {
                    String[] coords = value.split(",");
                    if (coords.length == 2 && isNumeric(coords[0]) && isNumeric(coords[1])) {
                        meteoriteCoords = new int[]{Integer.parseInt(coords[0]), Integer.parseInt(coords[1])};
                    } else {
                        report.append("Coordenada de meteorito '").append(value).append("' es invalida.\n");
                        valid = false;
                    }
                }
            }
        }
    }

    public boolean validate() {
        report.setLength(0);
        report.append("With=[").append(isValid(width, PERMISSIBLE_WIDTH) ? width : "Invalido").append("]\n");
        if (!isValid(width, PERMISSIBLE_WIDTH)) valid = false;

        report.append("Height=[").append(isValid(height, PERMISSIBLE_HEIGHT) ? height : "Invalido").append("]\n");
        if (!isValid(height, PERMISSIBLE_HEIGHT)) valid = false;

        report.append("Generations = [").append((generations >= 0 && generations <= 1000) ? generations : "Invalido").append("]\n");
        if (generations < 0 || generations > 1000) valid = false;

        report.append("speed = [").append(isValid(speed, PERMISSIBLE_SPEED) ? speed : "Invalido").append("]\n");
        if (!isValid(speed, PERMISSIBLE_SPEED)) valid = false;

        if (map == null || !isMapSizeValid(map, height, width)) {
            report.append("map = [invalido]\n");
            valid = false;
        } else {
            report.append("map = [").append(map).append("]\n");
        }

        report.append("n = ").append(isValid(direction, PERMISSIBLE_DIRECTIONS) ? direction : "1 (por defecto)").append("\n");
        if (!isValid(direction, PERMISSIBLE_DIRECTIONS)) valid = false;

        if (meteoriteCoords != null) {
            report.append("Meteorite Coords = [").append(meteoriteCoords[0]).append(",").append(meteoriteCoords[1]).append("]\n");
        }

        return valid;
    }

    public String getReport() {
        return report.toString();
    }

    public boolean isValid() {
        return valid;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getGenerations() {
        return generations;
    }

    public int getSpeed() {
        return speed;
    }

    public int getDirection() {
        return direction;
    }

    public String getMap() {
        return map;
    }

    public int[] getMeteoriteCoords() {
        return meteoriteCoords;
    }

    private static boolean isNumeric(String str) {
        if (str == null || str.isEmpty()) return false;
        for (char ch : str.toCharArray()) {
            if (!Character.isDigit(ch)) return false;
        }
        return true;
    }

    private static boolean isValid(int value, int[] allowed) {
        for (int v : allowed) {
            if (v == value) return true;
        }
        return false;
    }

    private static boolean isMapSizeValid(String mapText, int rows, int cols) {
        if (mapText.equalsIgnoreCase("rnd")) return true;
        String[] lines = mapText.split("#");
        if (lines.length > rows) return false;
        for (String line : lines) {
            if (line.length() > cols) return false;
        }
        return true;
    }
}
