package org.example.models;

import lombok.Getter;

/**
 * Player viewer of the board
 * Using to mark the player interactions
 */
@Getter
public class PlayerBoard {
    private final int[][] cells;
    private final int size;
    private int revealedCells = 0;

    public PlayerBoard(int size) {
        this.size = size;
        this.cells = new int[size][size];

        // Initialize with all cells unrevealed (-1)
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                cells[i][j] = -1;
            }
        }
    }

    public boolean isRevealed(int row, int col) {
        return cells[row][col] > -1;
    }

    /**
     * Mark player board cell as revealed
     * @param row row number
     * @param col column number
     * @param adjBombCount number of adjacent bombs
     */
    public void markAsRevealed(int row, int col, int adjBombCount) {
        revealedCells += 1;
        cells[row][col] = adjBombCount;
    }
}
