package com.redbadger.codingchallenge;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class RobotControllerTest {

    private List<String> run(String input) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(out));
        try {
            new RobotController().run(new Scanner(input.trim()));
        } finally {
            System.setOut(originalOut);
        }
        String output = out.toString().trim();
        if (output.isEmpty()) return List.of();
        return Arrays.asList(output.split(System.lineSeparator()));
    }

    private String runErr(String input) {
        ByteArrayOutputStream err = new ByteArrayOutputStream();
        PrintStream originalErr = System.err;
        System.setErr(new PrintStream(err));
        try {
            new RobotController().run(new Scanner(input.trim()));
        } finally {
            System.setErr(originalErr);
        }
        return err.toString().trim();
    }

    @Test
    void sampleInput() {
        String input = """
                5 3
                1 1 E
                RFRFRFRF

                3 2 N
                FRRFLLFFRRFLL

                0 3 W
                LLFFFLFLFL
                """;

        List<String> results = run(input);

        assertEquals(3, results.size());
        assertEquals("1 1 E", results.get(0));
        assertEquals("3 3 N LOST", results.get(1));
        assertEquals("2 3 S", results.get(2));
    }

    @Test
    void robotStaysOnGrid() {
        List<String> results = run("5 5\n2 2 N\nFFF");
        assertEquals("2 5 N", results.get(0));
    }

    @Test
    void robotFallsOffGrid() {
        List<String> results = run("5 5\n2 5 N\nF");
        assertEquals("2 5 N LOST", results.get(0));
    }

    @Test
    void scentPreventsSecondRobotFromFallingOff() {
        String input = """
                5 5
                2 5 N
                F
                2 5 N
                F
                """;
        List<String> results = run(input);
        assertEquals("2 5 N LOST", results.get(0));
        assertEquals("2 5 N", results.get(1));
    }

    @Test
    void fourLeftTurnsReturnToOriginalOrientation() {
        List<String> results = run("5 5\n2 2 N\nLLLL");
        assertEquals("2 2 N", results.get(0));
    }

    @Test
    void fourRightTurnsReturnToOriginalOrientation() {
        List<String> results = run("5 5\n2 2 N\nRRRR");
        assertEquals("2 2 N", results.get(0));
    }

    @Test
    void robotStopsExecutingAfterLost() {
        List<String> results = run("5 5\n0 0 S\nFFF");
        assertEquals("0 0 S LOST", results.get(0));
    }

    @Test
    void worldStatePersistsAcrossRobots() {
        String input = "5 5\n3 5 N\nF\n3 5 N\nFRF";
        List<String> results = run(input);
        assertEquals("3 5 N LOST", results.get(0));
        assertEquals("4 5 E", results.get(1));
    }

    @Test
    void singleRobot() {
        List<String> results = run("5 5\n1 1 N\nF");
        assertEquals(1, results.size());
        assertEquals("1 2 N", results.get(0));
    }

    @Test
    void multipleRobots() {
        String input = """
                5 5
                1 1 N
                F
                3 3 E
                FF
                """;
        List<String> results = run(input);
        assertEquals(2, results.size());
        assertEquals("1 2 N", results.get(0));
        assertEquals("5 3 E", results.get(1));
    }

    @Test
    void robotAtGridCornerSurvives() {
        List<String> results = run("5 5\n0 0 S\nL");
        assertEquals("0 0 E", results.get(0));
    }

    // --- validation ---

    @Test
    void invalidGridLineReturnsError() {
        String err = runErr("5 3 N\n1 1 E\nF");
        assertTrue(err.contains("Error: invalid grid coordinates"));
    }

    @Test
    void invalidGridLineProducesNoOutput() {
        List<String> results = run("5 3 N\n1 1 E\nF");
        assertTrue(results.isEmpty());
    }

    @Test
    void robotOutsideGridReturnsError() {
        String err = runErr("5 3\n6 3 N\nF");
        assertTrue(err.contains("Error: initial robot position outside grid"));
    }

    @Test
    void robotOutsideGridProducesNoOutput() {
        List<String> results = run("5 3\n6 3 N\nF");
        assertTrue(results.isEmpty());
    }

    @Test
    void invalidRobotPositionFormatReturnsError() {
        String err = runErr("5 5\n1 1 X\nF");
        assertTrue(err.contains("Error: invalid robot position"));
    }

    @Test
    void invalidInstructionsReturnsError() {
        String err = runErr("5 5\n1 1 N\nLRFX");
        assertTrue(err.contains("Error: invalid instructions"));
    }

    @Test
    void missingInstructionsReturnsError() {
        String err = runErr("5 5\n1 1 N");
        assertTrue(err.contains("Error: missing instructions"));
    }

    @Test
    void positionEqualityUsedForScents() {
        String input = """
                5 5
                0 5 N
                F
                0 5 N
                F
                0 5 N
                F
                """;
        List<String> results = run(input);
        assertEquals("0 5 N LOST", results.get(0));
        assertEquals("0 5 N", results.get(1));
        assertEquals("0 5 N", results.get(2));
    }
}
