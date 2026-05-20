# 8-Puzzle Solver

A Java implementation of a Best-First search algorithm that solves the classic 8-puzzle sliding tile problem.

## How It Works

The solver uses a **Best-First / Uniform Cost Search** strategy:

- Maintains an open list (priority queue) ordered by cumulative cost `g`
- Expands the lowest-cost state at each step
- Tracks visited states in a closed list to avoid cycles
- Reconstructs and returns the full solution path when the goal is reached

## Input Format

The program reads two board states from standard input — the **initial state** and the **goal state**.

Each board is a 9-character string where `0` represents the blank tile:

```
123456780   # initial state
012345678   # goal state
```

## Example

```bash
javac src/*.java -d out
java -cp out Main
# input: 123456780 012345678
```

Output: each intermediate board state followed by the total cost.

## Project Structure

```
8puzzle/
└── src/
    ├── Main.java         # Entry point — reads input and prints solution path
    ├── BestFirst.java    # Search algorithm (open/closed lists, path reconstruction)
    ├── Board.java        # Board representation, move generation, cost
    ├── Ilayout.java      # Interface for puzzle layouts
    └── PuzzleUnitTests.java  # Unit tests
```

## Tech Stack

- **Java** — no external dependencies
