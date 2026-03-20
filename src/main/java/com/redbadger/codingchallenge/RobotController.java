package com.redbadger.codingchallenge;

import java.util.Scanner;
import java.util.regex.Pattern;

public class RobotController {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        new RobotController().run(scanner);
    }

    private static final Pattern GRID_PATTERN = Pattern.compile("\\d+ \\d+");
    private static final Pattern POSITION_PATTERN = Pattern.compile("\\d+ \\d+ [NSEW]");
    private static final Pattern INSTRUCTIONS_PATTERN = Pattern.compile("[LRF]+");

    private static final Command LEFT_COMMAND = new LeftCommand();
    private static final Command RIGHT_COMMAND = new RightCommand();
    private static final Command FORWARD_COMMAND = new ForwardCommand();

    public void run(Scanner scanner) {
        String gridLine = scanner.nextLine().trim();
        if (!GRID_PATTERN.matcher(gridLine).matches()) {
            System.err.println("Error: invalid grid coordinates: " + gridLine);
            return;
        }
        String[] gridParts = gridLine.split("\\s+");
        int maxX = Integer.parseInt(gridParts[0]);
        int maxY = Integer.parseInt(gridParts[1]);

        World world = new World(maxX, maxY);
        WorldState worldState = new WorldState(world);

        while (scanner.hasNextLine()) {
            String robotPositionLine = scanner.nextLine().trim();

            if (robotPositionLine.isEmpty()) {
                continue;
            }

            String instructionsLine = null;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (!line.isEmpty()) {
                    instructionsLine = line;
                    break;
                }
            }

            if (instructionsLine == null) {
                System.err.println("Error: missing instructions for robot at " + robotPositionLine);
                return;
            }

            if (!POSITION_PATTERN.matcher(robotPositionLine).matches()) {
                System.err.println("Error: invalid robot position: " + robotPositionLine);
                return;
            }

            if (!INSTRUCTIONS_PATTERN.matcher(instructionsLine).matches()) {
                System.err.println("Error: invalid instructions: " + instructionsLine);
                return;
            }

            Robot robot = createRobot(robotPositionLine);
            if (!world.isInside(robot.getRobotPosition().getPosition())) {
                System.err.println("Error: initial robot position outside grid: " + robotPositionLine);
                return;
            }
            worldState.addRobot(robot);

            executeInstructions(robot, instructionsLine, worldState);
        }

        worldState.getRobots().forEach(System.out::println);
    }

    private Robot createRobot(String robotPositionLine) {
        String[] parts = robotPositionLine.split("\\s+");

        int x = Integer.parseInt(parts[0]);
        int y = Integer.parseInt(parts[1]);
        Orientation orientation = Orientation.valueOf(parts[2]);

        Position position = new Position(x, y);
        RobotPosition robotPosition = new RobotPosition(position, orientation);

        return new Robot(robotPosition);
    }

    private void executeInstructions(Robot robot, String instructions, WorldState worldState) {
        for (char instruction : instructions.toCharArray()) {
            if (robot.isLost()) {
                break;
            }

            Command command = toCommand(instruction);
            command.execute(robot, worldState);
        }
    }

    private Command toCommand(char instruction) {
        return switch (instruction) {
            case 'L' -> LEFT_COMMAND;
            case 'R' -> RIGHT_COMMAND;
            case 'F' -> FORWARD_COMMAND;
            default -> throw new IllegalArgumentException("Unknown instruction: " + instruction);
        };
    }
}
