package org.example.cli;

import org.example.cli.exception.InvalidArgumentException;
import org.example.models.PlayerBoard;

public class CLIGameHelper {
    public static int[] parseCellId(String cellId) throws InvalidArgumentException {
        if (cellId == null || cellId.isBlank() || cellId.length() < 2) {
            throw new InvalidArgumentException("Invalid cell ID format. Cell ID must have a row letter and a column number (e.g., A1).");
        }

        // Parse row character
        char rowChar = Character.toUpperCase(cellId.charAt(0));
        if (rowChar < 'A' || rowChar > 'Z') {
            throw new InvalidArgumentException("Invalid row identifier. Row must be a letter from A to Z.");
        }

        // Parse column number
        int col;
        try {
            col = Integer.parseInt(cellId.substring(1)) - 1;
        } catch (NumberFormatException e) {
            throw new InvalidArgumentException("Invalid column identifier");
        }

        if (col < 0) {
            throw new InvalidArgumentException("Invalid column identifier. Column must be greater than 0.");
        }

        // Calculate row index
        int row = rowChar - 'A';

        return new int[]{row, col};
    }


    public static void printBoard(PlayerBoard playerBoard) {
        int sizeOfGrid = playerBoard.getSize();

        System.out.println("Here is your minefield:");
        System.out.print("  ");
        for (int i = 1; i <= sizeOfGrid; i++) {
            System.out.print(i + " ");
        }
        System.out.println();

        for (int i = 0; i < sizeOfGrid; i++) {
            System.out.print((char) ('A' + i) + " ");
            for (int j = 0; j < sizeOfGrid; j++) {
                if (playerBoard.getCells()[i][j] == -1) {
                    System.out.print("_ "); // Unrevealed cell
                } else {
                    int value = playerBoard.getCells()[i][j];
                    System.out.print(value + " "); // Number revealed
                }
            }
            System.out.println();
        }
    }
}
