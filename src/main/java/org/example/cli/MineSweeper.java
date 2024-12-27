package org.example.cli;

import org.example.cli.exception.InvalidArgumentException;
import org.example.controller.GameController;
import org.example.exceptions.GameInitializationException;
import org.example.utils.GameHandler;

import java.util.Scanner;

import static org.example.cli.CLIGameHelper.parseCellId;
import static org.example.cli.CLIGameHelper.printBoard;

public class MineSweeper {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            GameController gameController = getGameController(scanner);
            playGame(gameController, scanner);
        }
    }

    private static GameController getGameController(Scanner scanner) {
        System.out.println("Welcome to Minesweeper!");

        GameController gameController;

        while (true) {
            // Prompt user for grid size
            System.out.print("Enter the size of the grid (e.g., 4 for a 4x4 grid, max 20): ");
            int gridSize = scanner.nextInt();
            scanner.nextLine();

            // Prompt user for number of mines
            System.out.print("Enter the number of mines to place on the grid (maximum is " +
                    (int) (gridSize * gridSize * 0.35) + "): ");
            int mineCount = scanner.nextInt();
            scanner.nextLine();

            if (!isValidInput(gridSize, mineCount)) {
                System.out.println("Invalid input. Grid size must be between 1 and 20, and mines must not exceed " +
                        (int) (gridSize * gridSize * 0.35) + ".");
                continue;
            }

            try {
                System.out.println("Preparing game field with mines... Be careful when you walk out there!");
                gameController = GameHandler.initGame(gridSize, mineCount);
                printBoard(gameController.getPlayerBoard());
                break;
            } catch (GameInitializationException exception) {
                System.out.println(exception.getMessage());
            }
        }

        return gameController;
    }

    private static void playGame(GameController gameController, Scanner scanner) {
        while (true) {
            System.out.print("Select a square to reveal (e.g., A1, or type 'Q' to quit): ");
            String selectedCell = scanner.nextLine();

            if (selectedCell.equalsIgnoreCase("Q")) {
                System.out.println("Thanks for playing Minesweeper! Goodbye!");
                System.exit(0);
            }

            try {
                int[] rowCol = parseCellId(selectedCell);
                boolean isBomb = gameController.revealCell(rowCol[0], rowCol[1]);

                if (isBomb) {
                    System.out.println("Oh no, you hit a mine! Game over.");
                    System.out.println("Press any key to play again...");
                    scanner.nextLine();
                    break;
                } else if (gameController.isGameWon()) {
                    System.out.println("Congratulations, you have won the game!");
                    System.out.println("Press any key to play again...");
                    scanner.nextLine();
                    break;
                } else {
                    printBoard(gameController.getPlayerBoard());
                }
            } catch (InvalidArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static boolean isValidInput(int gridSize, int mineCount) {
        int maxMines = (int) (gridSize * gridSize * 0.35);
        return gridSize > 0 && gridSize <= 20 && mineCount >= 0 && mineCount <= maxMines;
    }
}
