package org.example.controller.cli;

import org.example.cli.CLIGameHelper;
import org.example.cli.exception.InvalidArgumentException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CLIGameHelperTest {
    @Test
    void testParseCellId_ValidInput() throws InvalidArgumentException {
        int[] result = CLIGameHelper.parseCellId("A1");
        assertArrayEquals(new int[]{0, 0}, result, "A1 should map to [0, 0].");

        result = CLIGameHelper.parseCellId("C3");
        assertArrayEquals(new int[]{2, 2}, result, "C3 should map to [2, 2].");

        result = CLIGameHelper.parseCellId("D4");
        assertArrayEquals(new int[]{3, 3}, result, "D4 should map to [3, 3].");
    }


    @Test
    void testParseCellId_InvalidFormat() {
        // Missing column number
        assertThrows(InvalidArgumentException.class, () -> CLIGameHelper.parseCellId("A"), "Cell ID without a column should throw an exception.");

        // Missing row character
        assertThrows(InvalidArgumentException.class, () -> CLIGameHelper.parseCellId("1"), "Cell ID without a row should throw an exception.");

        // Non-numeric column
        assertThrows(InvalidArgumentException.class, () -> CLIGameHelper.parseCellId("A*"), "Non-numeric column should throw an exception.");
    }

    @Test
    void testParseCellId_OutOfRangeColumn() {
        // Negative or zero column number
        assertThrows(InvalidArgumentException.class, () -> CLIGameHelper.parseCellId("A0"), "Column number 0 is invalid and should throw an exception.");

        assertThrows(InvalidArgumentException.class, () -> CLIGameHelper.parseCellId("A-1"), "Negative column number should throw an exception.");
    }
}
