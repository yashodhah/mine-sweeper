package org.example.utils;

import org.example.controller.GameController;
import org.example.exceptions.GameInitializationException;
import org.example.models.GameBoard;
import org.example.models.PlayerBoard;

public class GameHandler {

    private GameHandler(){}

    public static GameController initGame(int sizeOfGrid, int numOfMines) throws GameInitializationException {
        if (sizeOfGrid <= 0 || sizeOfGrid > 20) {
            throw new GameInitializationException("Invalid grid size, Grid size should be between 1 and 20");
        }

        if (numOfMines < 0 || numOfMines > sizeOfGrid * sizeOfGrid * 0.35) {
            throw new GameInitializationException("Invalid number of mines. number of mines should not exceed 35% than the grid size");
        }

        GameBoard gameBoard = new GameBoard(sizeOfGrid, numOfMines);
        PlayerBoard playerBoard = new PlayerBoard(gameBoard.getSize());

        return new GameController(gameBoard, playerBoard);
    }
}
