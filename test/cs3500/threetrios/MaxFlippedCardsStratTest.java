package cs3500.threetrios;

import org.junit.Before;
import org.junit.Test;
import java.io.FileWriter;
import java.io.IOException;
import cs3500.threetrios.model.Card;
import cs3500.threetrios.model.SimpleCard;
import cs3500.threetrios.model.Value;
import cs3500.threetrios.strategy.GameMove;
import cs3500.threetrios.strategy.MaxFlippedCardsStrat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Class to test the max flipped cards.
 */
public class MaxFlippedCardsStratTest {
  private ConditionMockThreeTriosModel conditionMock;
  private TranscriptMockThreeTriosModel transcriptMock;
  private MaxFlippedCardsStrat strategy;
  private Card testCard;

  @Before
  public void setUp() {
    conditionMock = new ConditionMockThreeTriosModel();
    transcriptMock = new TranscriptMockThreeTriosModel();
    strategy = new MaxFlippedCardsStrat();
    testCard = new SimpleCard("TestCard", Value.TWO, Value.THREE, Value.FOUR, Value.FIVE);

    // add test card to the player's hand in the mock model
    conditionMock.addCardToPlayer(testCard);
    transcriptMock.addCardToPlayer(testCard);

  }

  @Test
  public void testSelectsMaxFlips() {
    // set up legal moves
    conditionMock.setLegalMove(new Coord(0, 0), true);
    conditionMock.setLegalMove(new Coord(0, 1), false);
    conditionMock.setLegalMove(new Coord(1, 0), true);
    conditionMock.setLegalMove(new Coord(1, 1), true);

    // assign flip counts
    conditionMock.setFlipCount(new Coord(0, 0), 2);
    conditionMock.setFlipCount(new Coord(0, 1), 0);
    conditionMock.setFlipCount(new Coord(1, 0), 1);
    conditionMock.setFlipCount(new Coord(1, 1), 3);

    GameMove bestMove = strategy.chooseMove(conditionMock);

    assertEquals(new GameMove(testCard, 1, 1), bestMove);
  }

  @Test
  public void testTiebreakerUpperLeft() {
    // set up legal moves
    conditionMock.setLegalMove(new Coord(0, 0), true);
    conditionMock.setLegalMove(new Coord(0, 1), false);
    conditionMock.setLegalMove(new Coord(1, 0), true);
    conditionMock.setLegalMove(new Coord(1, 1), true);

    // assign flip counts
    conditionMock.setFlipCount(new Coord(0, 0), 2);
    conditionMock.setFlipCount(new Coord(0, 1), 0);
    conditionMock.setFlipCount(new Coord(1, 0), 1);
    conditionMock.setFlipCount(new Coord(1, 1), 2);

    GameMove bestMove = strategy.chooseMove(conditionMock);

    assertEquals(new GameMove(testCard, 0, 0), bestMove);
  }

  // testing pass move is being called correctly within choose move
  @Test
  public void testPassMoveWithinChooseMove() {
    // set up legal moves
    conditionMock.setLegalMove(new Coord(0, 0), true);
    conditionMock.setLegalMove(new Coord(0, 1), false);
    conditionMock.setLegalMove(new Coord(1, 0), true);
    conditionMock.setLegalMove(new Coord(1, 1), true);

    // assign flip counts
    conditionMock.setFlipCount(new Coord(0, 0), 0);
    conditionMock.setFlipCount(new Coord(0, 1), 0);
    conditionMock.setFlipCount(new Coord(1, 0), 0);
    conditionMock.setFlipCount(new Coord(1, 1), 0);

    GameMove bestMove = strategy.chooseMove(conditionMock);

    assertEquals(new GameMove(testCard, 0, 0), bestMove);
  }

  @Test(expected = NullPointerException.class)
  public void testNoAvailableMoveInChooseMove() {
    // set up legal moves
    conditionMock.setLegalMove(new Coord(0, 0), false);
    conditionMock.setLegalMove(new Coord(1, 0), false);
    conditionMock.setLegalMove(new Coord(1, 1), false);

    strategy.chooseMove(conditionMock);
  }

  // testing pass move method
  @Test
  public void testPassMove() {
    // set up legal moves
    conditionMock.setLegalMove(new Coord(0, 0), true);
    conditionMock.setLegalMove(new Coord(0, 1), false);
    conditionMock.setLegalMove(new Coord(1, 0), true);
    conditionMock.setLegalMove(new Coord(1, 1), true);

    // assign flip counts
    conditionMock.setFlipCount(new Coord(0, 0), 0);
    conditionMock.setFlipCount(new Coord(0, 1), 0);
    conditionMock.setFlipCount(new Coord(1, 0), 0);
    conditionMock.setFlipCount(new Coord(1, 1), 0);

    GameMove bestMove = strategy.choosePassMove(conditionMock);

    assertEquals(new GameMove(testCard, 0, 0), bestMove);
  }

  @Test(expected = NullPointerException.class)
  public void testNoAvailableMove() {
    // set up legal moves
    conditionMock.setLegalMove(new Coord(0, 0), false);
    conditionMock.setLegalMove(new Coord(1, 0), false);
    conditionMock.setLegalMove(new Coord(1, 1), false);

    strategy.choosePassMove(conditionMock);
  }

  // this test makes the file for submission (testing simplest strategy, max cards flipped)
  @Test
  public void testMaxFlippedCardsStratInspectsAllCells() {
    transcriptMock.setLegalMove(new Coord(0, 0), true);
    transcriptMock.setLegalMove(new Coord(0, 1), true);
    transcriptMock.setLegalMove(new Coord(0, 2), true);
    transcriptMock.setLegalMove(new Coord(1, 0), true);
    transcriptMock.setLegalMove(new Coord(1, 1), true);
    transcriptMock.setLegalMove(new Coord(1, 2), true);
    transcriptMock.setLegalMove(new Coord(2, 0), true);
    transcriptMock.setLegalMove(new Coord(2, 1), true);
    transcriptMock.setLegalMove(new Coord(2, 2), true);

    transcriptMock.setFlipCount(new Coord(0, 0), 1);
    transcriptMock.setFlipCount(new Coord(0, 1), 2);
    transcriptMock.setFlipCount(new Coord(0, 2), 1);
    transcriptMock.setFlipCount(new Coord(1, 0), 3);
    transcriptMock.setFlipCount(new Coord(1, 1), 1);
    transcriptMock.setFlipCount(new Coord(1, 2), 2);
    transcriptMock.setFlipCount(new Coord(2, 0), 0);
    transcriptMock.setFlipCount(new Coord(2, 1), 1);
    transcriptMock.setFlipCount(new Coord(2, 2), 0);

    GameMove bestMove = strategy.chooseMove(transcriptMock);

    // verify that all coordinates in the 3x3 grid were inspected
    for (int row = 0; row < 3; row++) {
      for (int col = 0; col < 3; col++) {
        assertTrue(transcriptMock.getInspectedCoords().contains(new Coord(row, col)));
      }
    }

    assertEquals(new GameMove(testCard, 1, 0), bestMove);

    try (FileWriter writer = new FileWriter("strategy-transcript.txt")) {
      for (Coord coord : transcriptMock.getInspectedCoords()) {
        writer.write("Checked: (" + coord.getRow() + ", " + coord.getCol() + ")\n");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testTranscriptMaxFlippedCardsStratTieBreaker() {
    // set up the grid with legal moves
    transcriptMock.setLegalMove(new Coord(0, 0), true);
    transcriptMock.setLegalMove(new Coord(0, 1), true);
    transcriptMock.setLegalMove(new Coord(0, 2), true);
    transcriptMock.setLegalMove(new Coord(1, 0), true);
    transcriptMock.setLegalMove(new Coord(1, 1), true);
    transcriptMock.setLegalMove(new Coord(1, 2), true);
    transcriptMock.setLegalMove(new Coord(2, 0), true);
    transcriptMock.setLegalMove(new Coord(2, 1), true);
    transcriptMock.setLegalMove(new Coord(2, 2), true);

    // assign flip counts
    transcriptMock.setFlipCount(new Coord(0, 0), 1);
    transcriptMock.setFlipCount(new Coord(0, 1), 3);
    transcriptMock.setFlipCount(new Coord(0, 2), 2);
    transcriptMock.setFlipCount(new Coord(1, 0), 1);
    transcriptMock.setFlipCount(new Coord(1, 1), 3);
    transcriptMock.setFlipCount(new Coord(1, 2), 2);
    transcriptMock.setFlipCount(new Coord(2, 0), 1);
    transcriptMock.setFlipCount(new Coord(2, 1), 0);
    transcriptMock.setFlipCount(new Coord(2, 2), 1);

    GameMove bestMove = strategy.chooseMove(transcriptMock);

    for (int row = 0; row < 3; row++) {
      for (int col = 0; col < 3; col++) {
        assertTrue(transcriptMock.getInspectedCoords().contains(new Coord(row, col)));
      }
    }

    assertEquals(new GameMove(testCard, 0, 1), bestMove);
  }

  @Test
  public void testTranscriptMaxFlippedCardsStratPassMove() {
    // set up legal moves
    transcriptMock.setLegalMove(new Coord(0, 0), true);
    transcriptMock.setLegalMove(new Coord(0, 1), true);
    transcriptMock.setLegalMove(new Coord(0, 2), true);
    transcriptMock.setLegalMove(new Coord(1, 0), true);
    transcriptMock.setLegalMove(new Coord(1, 1), true);
    transcriptMock.setLegalMove(new Coord(1, 2), true);
    transcriptMock.setLegalMove(new Coord(2, 0), true);
    transcriptMock.setLegalMove(new Coord(2, 1), true);
    transcriptMock.setLegalMove(new Coord(2, 2), true);

    // assign flip counts
    transcriptMock.setFlipCount(new Coord(0, 0), 0);
    transcriptMock.setFlipCount(new Coord(0, 1), 0);
    transcriptMock.setFlipCount(new Coord(0, 2), 0);
    transcriptMock.setFlipCount(new Coord(1, 0), 0);
    transcriptMock.setFlipCount(new Coord(1, 1), 0);
    transcriptMock.setFlipCount(new Coord(1, 2), 0);
    transcriptMock.setFlipCount(new Coord(2, 0), 0);
    transcriptMock.setFlipCount(new Coord(2, 1), 0);
    transcriptMock.setFlipCount(new Coord(2, 2), 0);

    GameMove passMove = strategy.chooseMove(transcriptMock);

    for (int row = 0; row < 3; row++) {
      for (int col = 0; col < 3; col++) {
        assertTrue(transcriptMock.getInspectedCoords().contains(new Coord(row, col)));
      }
    }

    assertEquals(new GameMove(testCard, 0, 0), passMove);
  }

  @Test
  public void testTieBreakerPositionAndCardIndex() {
    // add 2 new cards
    Card firstCard = new SimpleCard("FirstCard", Value.TWO, Value.THREE, Value.FOUR, Value.FIVE);
    Card secondCard = new SimpleCard("SecondCard", Value.TWO, Value.THREE, Value.FOUR, Value.FIVE);
    conditionMock.addCardToPlayer(firstCard);
    conditionMock.addCardToPlayer(secondCard);

    // set legal moves
    conditionMock.setLegalMove(new Coord(0, 0), true);
    conditionMock.setLegalMove(new Coord(0, 1), false);
    conditionMock.setLegalMove(new Coord(1, 0), true);
    conditionMock.setLegalMove(new Coord(1, 1), true);

    // assign flip counts
    conditionMock.setFlipCount(new Coord(0, 0), 3);
    conditionMock.setFlipCount(new Coord(0, 1), 0);
    conditionMock.setFlipCount(new Coord(1, 0), 3);
    conditionMock.setFlipCount(new Coord(1, 1), 3);

    GameMove bestMove = strategy.chooseMove(conditionMock);

    assertEquals(new GameMove(testCard, 0, 0), bestMove);
  }

  @Test
  public void testPassMoveWhenNoValidMovesAvailableUsingFirstCard() {
    // add a single card to the player's hand
    Card firstCard = new SimpleCard("FirstCard", Value.TWO, Value.THREE, Value.FOUR, Value.FIVE);
    conditionMock.addCardToPlayer(firstCard);

    // set up the grid with all moves marked illegal except the top-left cell
    conditionMock.setLegalMove(new Coord(0, 0), true);
    conditionMock.setLegalMove(new Coord(0, 1), false);
    conditionMock.setLegalMove(new Coord(1, 0), true);
    conditionMock.setLegalMove(new Coord(1, 1), true);

    // set flip count
    conditionMock.setFlipCount(new Coord(0, 0), 0);
    conditionMock.setFlipCount(new Coord(0, 1), 0);
    conditionMock.setFlipCount(new Coord(1, 0), 0);
    conditionMock.setFlipCount(new Coord(1, 1), 0);

    GameMove passMove = strategy.chooseMove(conditionMock);

    assertEquals(new GameMove(testCard, 0, 0), passMove);
  }

}
