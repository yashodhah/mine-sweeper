package org.example.controller;

import lombok.Getter;
import org.example.models.GameBoard;
import org.example.models.PlayerBoard;

import static org.example.utils.GameConstants.COL_OFFSETS;
import static org.example.utils.GameConstants.ROW_OFFSETS;

/**
 * Game controller is where the actual game play happens,
 * It is keeping track of the user interactions for a particular
 * GameBoard against a PlayerBoard
 *
 * @see org.example.models.GameBoard
 * @see org.example.models.PlayerBoard
 */
public class GameController {
    private final GameBoard gameBoard;

    @Getter
    private final PlayerBoard playerBoard;

    public GameController(GameBoard gameBoard, PlayerBoard playerBoard) {
        this.gameBoard = gameBoard;
        this.playerBoard = playerBoard;
    }

    /**
     * Reveal the cell by row and column
     *
     * @param row row number
     * @param col column number
     * @return {@code true} if found a bomb while revealing the cell
     */
    public boolean revealCell(int row, int col) {
        if (isOutOfBound(row, col)) {
            throw new IllegalArgumentException(" cell coordinates are  out of bounds.");
        }

        // Check if the cell is already revealed
        if (playerBoard.isRevealed(row, col)) {
            return false;
        }

        // TODO: Handle first reveal
        // Reveal the cell
        if (gameBoard.isBomb(row, col)) {
            return true;
        } else {
            revealSafeCells(row, col);
            return false;
        }
    }

    public boolean isGameWon() {
        return gameBoard.getPlacedMines() + playerBoard.getRevealedCells() == gameBoard.getSize() * gameBoard.getSize();
    }


    /**
     * Reveal safe cells recursively
     *
     * @param row
     * @param col
     */
    private void revealSafeCells(int row, int col) {
        if (isOutOfBound(row, col)) {
            return;
        }

        if (playerBoard.isRevealed(row, col)) {
            return; // Exit early if already revealed
        }

        int adjBombs = gameBoard.getCell(row, col).adjBombs();
        playerBoard.markAsRevealed(row, col, adjBombs);

        if (adjBombs == 0) {
            for (int i = 0; i < ROW_OFFSETS.length; i++) {
                revealSafeCells(row + ROW_OFFSETS[i], col + COL_OFFSETS[i]);
            }
        }
    }

    private boolean isOutOfBound(int row, int col) {
        return row < 0 || row >= gameBoard.getSize() || col < 0 || col >= gameBoard.getSize();
    }
}
