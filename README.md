# Ecosystem Simulator

A Conway's Game-of-Life inspired ecosystem simulator built in Java. Trees, animals, water, and meteorites interact across generations according to configurable rules.

## Features

- **Trees** 🌳 — grow and spread; die without water
- **Animals** 🐑 — move on even generations, reproduce near trees + water
- **Water** 💧 — essential for survival; spreads downward on multiples of 3 generations
- **Meteorites** ☄️ — destroy everything in their blast radius
- **Random maps** via the `m=rnd` option
- Configurable grid size, speed, generations, and animal movement direction

## Usage

```bash
# Run with default parameters (10x10, 20 generations)
java -jar capstone-work-1.0.0.jar

# Custom parameters
java -jar capstone-work-1.0.0.jar w=15 h=15 g=50 s=250 n=2 m=01030#20100#00030##
```

### Parameters

| Flag | Description | Allowed Values |
|------|-------------|----------------|
| `w` | Width | 5, 10, 15, 40, 80 |
| `h` | Height | 5, 10, 15, 40 |
| `g` | Generations | 0 – 1000 |
| `s` | Speed (ms delay) | 0, 250, 500, 1000, 4000 |
| `n` | Direction (1=right, 2=down, 3=left, 4=up) | 1, 2, 3, 4 |
| `m` | Map string or `rnd` for random | `#`-delimited rows |
| `c` | Meteorite coordinates | `row,col` |

### Map format

Rows are separated by `#`. Each character is a cell type:
- `0` = Air
- `1` = Tree
- `2` = Animal
- `3` = Water

Example: `01030#20100#00030##`

### Run with Maven

```bash
mvn clean compile exec:java
```

## Build

```bash
mvn clean package
java -jar target/capstone-work-1.0.0.jar
```

## Requirements

- Java 17+
- Apache Maven 3.8+

## Acknowledgements

- [Jhampo](https://www.youtube.com/watch?v=WNfaLMjsqF8) — Conway's Game of Life in Java tutorial
- [Chemaclass](https://github.com/Chemaclass/juego-de-la-vida) — reference implementation
- [W3Schools](https://www.w3schools.com/java/default.asp) — Java reference
