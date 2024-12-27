package org.example.controller.controller;

import org.example.controller.GameController;
import org.example.models.GameBoard;
import org.example.models.PlayerBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameControllerTest {
    private GameController gameController;
    private GameBoard gameBoard;
    private PlayerBoard playerBoard;

    @BeforeEach
    void setup() {
        int gridSize = 5;
        int numOfMines = 3;
        gameBoard = new GameBoard(gridSize, numOfMines);
        playerBoard = new PlayerBoard(gridSize);
        gameController = new GameController(gameBoard, playerBoard);
    }

    @Test
    void testRevealABombCell() {
        // Simulate a bomb location
        int[] cellCord = TestHelper.getBombCell(gameBoard);
        // Assert bomb is revealed
        assertTrue(gameController.revealCell(cellCord[0], cellCord[1]), "Expected a bomb at the given cell.");
    }

    @Test
    void testRevealSafeCell() {

        int[] cellCord = TestHelper.getSafeCell(gameBoard);

        // Assert safe cell is revealed without a bomb
        assertFalse(gameController.revealCell(cellCord[0], cellCord[1]), "Safe cell should not trigger a bomb.");
        assertTrue(playerBoard.isRevealed(cellCord[0], cellCord[1]), "Safe cell should be marked as revealed.");
    }

    @Test
    void shouldThrowExceptionWhenTryToRevealInvalidCell() {
        int gridSize = gameBoard.getSize();

        assertThrows(IllegalArgumentException.class, () -> gameController.revealCell(-1, 0));
        assertThrows(IllegalArgumentException.class, () -> gameController.revealCell(0, -1));
        assertThrows(IllegalArgumentException.class, () -> gameController.revealCell(gridSize, 0));
        assertThrows(IllegalArgumentException.class, () -> gameController.revealCell(0, gridSize));
    }

    @Test
    void testRevealAnAlreadyRevealedCell() {
        int[] cellCord = TestHelper.getSafeCell(gameBoard);

        // Reveal the cell once
        assertFalse(gameController.revealCell(cellCord[0], cellCord[1]));

        // Try to reveal it again
        assertFalse(gameController.revealCell(cellCord[0], cellCord[1]), "Revealing an already revealed cell should return false.");
    }

    @Test
    void testRevealSafeCellsAutomaticReveals() {
        int[] cellCord= TestHelper.getCellWithNoAdjBombs(gameBoard);
        int row = cellCord[0];
        int col= cellCord[1];

        assertFalse(gameController.revealCell(row, col), "Expected automatic reveals for a 0-adjacent-bomb cell.");

        // Check surrounding cells are revealed
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if (i >= 0 && i < gameBoard.getSize() && j >= 0 && j < gameBoard.getSize()) {
                    assertTrue(playerBoard.isRevealed(i, j), "Adjacent cells should be revealed.");
                }
            }
        }
    }

    private static class TestHelper {
        static int[] getBombCell(GameBoard gameBoard){
            int bombRow = -1, bombCol = -1;
            for (int i = 0; i < gameBoard.getSize(); i++) {
                for (int j = 0; j < gameBoard.getSize(); j++) {
                    if (gameBoard.isBomb(i, j)) {
                        bombRow = i;
                        bombCol = j;
                        break;
                    }
                }
                if (bombRow != -1) break;
            }

            return new int[]{bombRow, bombCol};
        }

        static int[] getSafeCell(GameBoard gameBoard){
            int safeRow = -1, safeCol = -1;
            for (int i = 0; i < gameBoard.getSize(); i++) {
                for (int j = 0; j < gameBoard.getSize(); j++) {
                    if (!gameBoard.isBomb(i, j)) {
                        safeRow = i;
                        safeCol = j;
                        break;
                    }
                }
                if (safeRow != -1) break;
            }

            return new int[]{safeRow, safeCol};
        }

        static int[] getCellWithNoAdjBombs(GameBoard gameBoard){
            int zeroBombRow = -1, zeroBombCol = -1;
            for (int i = 0; i < gameBoard.getSize(); i++) {
                for (int j = 0; j < gameBoard.getSize(); j++) {
                    if (!gameBoard.isBomb(i, j) && gameBoard.getCell(i, j).adjBombs() == 0) {
                        zeroBombRow = i;
                        zeroBombCol = j;
                        break;
                    }
                }
                if (zeroBombRow != -1) break;
            }

            return new int[]{zeroBombRow, zeroBombCol};

        }
    }
}
