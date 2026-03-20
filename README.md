# Red Badger Coding Challenge

## Overview

A solution to the Martian Robots problem. Robots navigate a rectangular grid on Mars following a sequence of instructions (L, R, F). Robots that move off the grid are marked LOST and leave a scent that prevents future robots from falling off at the same position.

## Prerequisites

- Java 21
- Maven

## Building

```bash
mvn clean package
```

Produces `target/coding-challenge.jar`.

## Running

```bash
# Using the JAR (Linux/macOS)
java -jar target/coding-challenge.jar < input.txt

# Using the JAR (PowerShell)
Get-Content input.txt | java -jar target/coding-challenge.jar

# Using compiled classes directly (PowerShell)
javac -d out (Get-ChildItem src/main/java/com/redbadger/codingchallenge/*.java).FullName
Get-Content input.txt | java -cp out com.redbadger.codingchallenge.RobotController
```

## Input Format

```
5 3
1 1 E
RFRFRFRF

3 2 N
FRRFLLFFRRFLL

0 3 W
LLFFFLFLFL
```

- First line: upper-right coordinates of the grid (lower-left is always 0 0)
- Then for each robot: starting position + orientation, followed by instruction string
- Instructions: `L` (turn left), `R` (turn right), `F` (move forward)

## Output Format

```
1 1 E
3 3 N LOST
2 3 S
```

One line per robot showing final position and orientation. `LOST` is appended if the robot fell off the grid.

## Testing

```bash
mvn test
```

## Approach

The solution is modelled around the following classes:

- `Position` — immutable (x, y) coordinate
- `Orientation` — enum (N, E, S, W)
- `RobotPosition` — immutable combination of position and orientation; produces new instances via `afterLeft()`, `afterRight()`, `afterForward()`
- `World` — immutable grid boundaries
- `WorldState` — mutable shared state; tracks scents left by lost robots and processes forward movement
- `Robot` — holds current `RobotPosition` and lost flag
- `Command` — interface implemented by `LeftCommand`, `RightCommand`, and `ForwardCommand`; new instruction types can be added without modifying existing code
- `RobotController` — parses input, drives execution, prints results

## Design Decisions

- `World` intentionally does not expose `maxX`/`maxY` — neither `Robot` nor `RobotController` need to know the grid dimensions; boundary checking is encapsulated within `World` and `WorldState`.

- Multiple robots may occupy the same position simultaneously; the spec places no restriction on this.

- The `Command` interface makes the instruction set extensible — new commands can be added by implementing the interface without touching existing code. That said, commands are still coupled to a `Robot` and `WorldState`, so they are not general-purpose; they operate within the assumption of a robot moving through a grid world.
