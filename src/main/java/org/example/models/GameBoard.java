package org.example.models;

import lombok.Getter;

import java.util.Random;

import static org.example.utils.GameConstants.COL_OFFSETS;
import static org.example.utils.GameConstants.ROW_OFFSETS;

public final class GameBoard {
    private final GameCell[][] gameCells;
    @Getter
    private final int size;
    @Getter
    private int placedMines = 0;

    public GameBoard(int sizeOfGrid, int numOfMines) {
        this.size = sizeOfGrid;
        this.gameCells = new GameCell[sizeOfGrid][sizeOfGrid];

        // Initialize all cells as not containing bombs with 0 adjacent bombs
        for (int i = 0; i < sizeOfGrid; i++) {
            for (int j = 0; j < sizeOfGrid; j++) {
                gameCells[i][j] = new GameCell(false, 0);
            }
        }

        // Randomly place mines
        placeMines(sizeOfGrid, numOfMines);

        calculateAdjBombs();
    }

    public boolean isBomb(int row, int col) {
        return gameCells[row][col].isBomb();
    }

    public GameCell getCell(int row, int col) {
        return gameCells[row][col];
    }


    private void placeMines(int sizeOfGrid, int numOfMines) {
        Random random = new Random();

        while (placedMines < numOfMines) {
            int row = random.nextInt(sizeOfGrid);
            int col = random.nextInt(sizeOfGrid);

            if (!gameCells[row][col].isBomb()) { // Place a bomb only if the cell is not already a bomb
                gameCells[row][col] = new GameCell(true, 0);
                placedMines++;
            }
        }
    }

    /**
     * Calculate adjacent bomb counts for each cell. this will make sure a smoother game play experience
     * by compromising the initial load time maybe.
     * TODO: Discuss this, This can be changed based on the constrains
     */
    private void calculateAdjBombs() {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (gameCells[row][col].isBomb()) {
                    continue;
                }

                int adjBombCount = 0;
                for (int i = 0; i < ROW_OFFSETS.length; i++) {
                    int newRow = row + ROW_OFFSETS[i];
                    int newCol = col + COL_OFFSETS[i];

                    if (isValidCell(newRow, newCol) && gameCells[newRow][newCol].isBomb()) {
                        adjBombCount++;
                    }
                }

                gameCells[row][col] = new GameCell(false, adjBombCount);
            }
        }
    }

    private boolean isValidCell(int row, int col) {
        return row >= 0 && row < size && col >= 0 && col < size;
    }
}
