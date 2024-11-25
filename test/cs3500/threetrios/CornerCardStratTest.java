package cs3500.threetrios;

import org.junit.Before;
import org.junit.Test;

import cs3500.threetrios.model.Card;
import cs3500.threetrios.model.SimpleCard;
import cs3500.threetrios.model.Value;
import cs3500.threetrios.strategy.CornerCardStrat;
import cs3500.threetrios.strategy.GameMove;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests for the corner card strategies.
 */
public class CornerCardStratTest {
  private ConditionMockThreeTriosModel conditionMock;
  private TranscriptMockThreeTriosModel transcriptMock;
  private CornerCardStrat strategy;
  private Card testCard;

  @Before
  public void setUp() {
    conditionMock = new ConditionMockThreeTriosModel();
    transcriptMock = new TranscriptMockThreeTriosModel();
    strategy = new CornerCardStrat();
    testCard = new SimpleCard("TestCard", Value.TWO, Value.THREE, Value.FOUR, Value.FIVE);

  }

  @Test
  public void testSelectsBestCorner() {
    // add the test card to the player's hand in the mock model
    conditionMock.addCardToPlayer(testCard);

    // set all legal moves
    conditionMock.setLegalMove(new Coord(0, 0), true);
    conditionMock.setLegalMove(new Coord(1, 1), true);
    conditionMock.setLegalMove(new Coord(0, 1), false);
    conditionMock.setLegalMove(new Coord(1, 0), true);

    GameMove bestMove = strategy.chooseMove(conditionMock);

    assertEquals(new GameMove(testCard, 0, 0), bestMove);
  }

  @Test
  public void testTiebreakerMultipleCorners() {
    // add the test card to the player's hand in the mock model
    SimpleCard testCard2 = new SimpleCard(
            "TestCard", Value.FOUR, Value.FOUR, Value.FOUR, Value.FOUR);
    conditionMock.addCardToPlayer(testCard2);

    // set all legal moves
    conditionMock.setLegalMove(new Coord(0, 0), true);
    conditionMock.setLegalMove(new Coord(1, 1), true);
    conditionMock.setLegalMove(new Coord(0, 1), false);
    conditionMock.setLegalMove(new Coord(1, 0), true);

    GameMove bestMove = strategy.chooseMove(conditionMock);

    assertEquals(new GameMove(testCard2, 0, 0), bestMove);
  }

  // testing pass move is being called correctly within choose move
  @Test
  public void testNoCornersAvailable_PassMove() {
    // add the test card to the player's hand in the mock model
    conditionMock.addCardToPlayer(testCard);

    // set all legal moves
    conditionMock.setLegalMove(new Coord(0, 1), true);
    conditionMock.setLegalMove(new Coord(1, 1), false);
    conditionMock.setLegalMove(new Coord(1, 0), false);
    conditionMock.setLegalMove(new Coord(0, 0), false);

    GameMove bestMove = strategy.chooseMove(conditionMock);

    assertEquals(new GameMove(testCard, 0, 1), bestMove);
  }

  @Test(expected = NullPointerException.class)
  public void testNoAvailableMove() {
    // ensure all cells are set as illegal
    conditionMock.setLegalMove(new Coord(0, 0), false);
    conditionMock.setLegalMove(new Coord(0, 1), false);
    conditionMock.setLegalMove(new Coord(1, 1), false);

    strategy.chooseMove(conditionMock);
  }

  // testing pass move method
  @Test
  public void testPassMoveNoCornersAvailable_PassMove() {
    // add the test card to the player's hand in the mock model
    conditionMock.addCardToPlayer(testCard);

    // set all legal moves
    conditionMock.setLegalMove(new Coord(0, 1), true);
    conditionMock.setLegalMove(new Coord(1, 1), false);
    conditionMock.setLegalMove(new Coord(1, 0), false);
    conditionMock.setLegalMove(new Coord(0, 0), false);

    GameMove bestMove = strategy.choosePassMove(conditionMock);

    assertEquals(new GameMove(testCard, 0, 1), bestMove);
  }

  @Test(expected = NullPointerException.class)
  public void testPassMoveNoAvailableMove() {
    // add the test card to the player's hand in the mock model
    conditionMock.addCardToPlayer(testCard);

    // set all legal moves
    conditionMock.setLegalMove(new Coord(0, 0), false);
    conditionMock.setLegalMove(new Coord(0, 1), false);
    conditionMock.setLegalMove(new Coord(1, 1), false);

    strategy.choosePassMove(conditionMock);
  }

  @Test
  public void testTranscriptSelectsCorner() {
    // add the test card to the player's hand in the mock model
    transcriptMock.addCardToPlayer(testCard);

    // set all legal moves
    transcriptMock.setLegalMove(new Coord(0, 0), true);
    transcriptMock.setLegalMove(new Coord(0, 1), true);
    transcriptMock.setLegalMove(new Coord(0, 2), true);
    transcriptMock.setLegalMove(new Coord(1, 0), true);
    transcriptMock.setLegalMove(new Coord(1, 1), true);
    transcriptMock.setLegalMove(new Coord(1, 2), true);
    transcriptMock.setLegalMove(new Coord(2, 0), true);
    transcriptMock.setLegalMove(new Coord(2, 1), true);
    transcriptMock.setLegalMove(new Coord(2, 2), true);

    GameMove bestMove = strategy.chooseMove(transcriptMock);

    assertTrue(transcriptMock.getInspectedCoords().contains(new Coord(0, 0)));
    assertTrue(transcriptMock.getInspectedCoords().contains(new Coord(0, 2)));
    assertTrue(transcriptMock.getInspectedCoords().contains(new Coord(2, 0)));
    assertTrue(transcriptMock.getInspectedCoords().contains(new Coord(2, 2)));

    assertEquals(new GameMove(testCard, 0, 2), bestMove);
  }

  @Test
  public void testTranscriptTieBreakerMultipleCorners() {
    // add the test card to the player's hand in the mock model
    SimpleCard testCard2 = new SimpleCard(
            "TestCard", Value.FOUR, Value.FOUR, Value.FOUR, Value.FOUR);
    transcriptMock.addCardToPlayer(testCard2);

    // set all legal moves
    transcriptMock.setLegalMove(new Coord(0, 0), true);
    transcriptMock.setLegalMove(new Coord(0, 1), true);
    transcriptMock.setLegalMove(new Coord(0, 2), true);
    transcriptMock.setLegalMove(new Coord(1, 0), true);
    transcriptMock.setLegalMove(new Coord(1, 1), true);
    transcriptMock.setLegalMove(new Coord(1, 2), true);
    transcriptMock.setLegalMove(new Coord(2, 0), true);
    transcriptMock.setLegalMove(new Coord(2, 1), true);
    transcriptMock.setLegalMove(new Coord(2, 2), true);

    GameMove bestMove = strategy.chooseMove(transcriptMock);

    assertTrue(transcriptMock.getInspectedCoords().contains(new Coord(0, 0)));
    assertTrue(transcriptMock.getInspectedCoords().contains(new Coord(0, 2)));
    assertTrue(transcriptMock.getInspectedCoords().contains(new Coord(2, 0)));
    assertTrue(transcriptMock.getInspectedCoords().contains(new Coord(2, 2)));

    assertEquals(new GameMove(testCard2, 0, 0), bestMove);
  }

  @Test
  public void testTranscriptNoCornersAvailablePassMove() {
    // add the test card to the player's hand in the mock model
    transcriptMock.addCardToPlayer(testCard);

    // set all legal moves
    transcriptMock.setLegalMove(new Coord(0, 0), false);
    transcriptMock.setLegalMove(new Coord(0, 1), true);
    transcriptMock.setLegalMove(new Coord(0, 2), false);
    transcriptMock.setLegalMove(new Coord(1, 0), true);
    transcriptMock.setLegalMove(new Coord(1, 1), true);
    transcriptMock.setLegalMove(new Coord(1, 2), true);
    transcriptMock.setLegalMove(new Coord(2, 0), false);
    transcriptMock.setLegalMove(new Coord(2, 1), true);
    transcriptMock.setLegalMove(new Coord(2, 2), false);

    GameMove passMove = strategy.chooseMove(transcriptMock);

    assertTrue(transcriptMock.getInspectedCoords().contains(new Coord(0, 0)));
    assertTrue(transcriptMock.getInspectedCoords().contains(new Coord(0, 2)));
    assertTrue(transcriptMock.getInspectedCoords().contains(new Coord(2, 0)));
    assertTrue(transcriptMock.getInspectedCoords().contains(new Coord(2, 2)));

    assertEquals(new GameMove(testCard, 0, 1), passMove);
  }

  @Test
  public void testTieBreakerPositionAndCardIndex() {
    // add two cards to the player's hand
    Card firstCard = new SimpleCard("FirstCard", Value.TWO, Value.THREE, Value.FOUR, Value.FIVE);
    Card secondCard = new SimpleCard("SecondCard", Value.TWO, Value.THREE, Value.FOUR, Value.FIVE);
    conditionMock.addCardToPlayer(firstCard);
    conditionMock.addCardToPlayer(secondCard);

    // set up legal moves
    conditionMock.setLegalMove(new Coord(0, 0), true);
    conditionMock.setLegalMove(new Coord(0, 1), false);
    conditionMock.setLegalMove(new Coord(1, 0), true);
    conditionMock.setLegalMove(new Coord(1, 1), true);

    GameMove bestMove = strategy.chooseMove(conditionMock);

    assertEquals(new GameMove(firstCard, 0, 0), bestMove);
  }
}