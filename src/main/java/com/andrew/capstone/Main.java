package com.andrew.capstone;

public class Main {

    public static void main(String[] args) {
        ArgumentParser parser = new ArgumentParser(args);

        if (!parser.isValid()) {
            System.out.print(parser.getReport());
            System.out.println("No podemos ejecutar el proyecto debido a un error");
            return;
        }

        if (!parser.validate()) {
            System.out.print(parser.getReport());
            System.out.println("No se puede ejecutar la simulación debido a parámetros inválidos.");
            return;
        }

        System.out.print(parser.getReport());
        System.out.println("Todos los parametros están correctos :D");

        if (parser.getMap() == null) {
            System.out.println("No se puede ejecutar la simulación debido a que el mapa es nulo.");
            return;
        }
        Grid grid = Grid.parse(parser.getMap(), parser.getHeight(), parser.getWidth());

        int[] meteorite = parser.getMeteoriteCoords();
        if (meteorite != null) {
            grid.applyMeteorite(meteorite[0], meteorite[1]);
        }

        System.out.println("Gen: 0");
        DisplayUtil.display(grid);

        for (int i = 1; i <= parser.getGenerations(); i++) {
            System.out.println("Gen: " + i);
            grid = grid.evolve(i, parser.getDirection());
            DisplayUtil.display(grid);

            if (parser.getSpeed() > 0) {
                try {
                    Thread.sleep(parser.getSpeed());
                } catch (InterruptedException e) {
                    System.out.println("Simulation interrupted.");
                    break;
                }
            }
        }
    }
}
