package org.example.controller.models;

import org.example.models.PlayerBoard;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PlayerBoardTest {
    PlayerBoard playerBoard;

    @BeforeEach
    void setup() {
        int gridSize = 5;
        playerBoard = new PlayerBoard(gridSize);
    }

    @Test
    void testMarkAsRevealed(){
        playerBoard.markAsRevealed(1,1,2);
        Assertions.assertEquals(1,playerBoard.getRevealedCells());
        Assertions.assertTrue(playerBoard.isRevealed(1,1));
    }
}
