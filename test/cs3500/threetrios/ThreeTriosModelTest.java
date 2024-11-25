package cs3500.threetrios;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import cs3500.threetrios.model.Card;
import cs3500.threetrios.model.CardFileParser;
import cs3500.threetrios.model.Cell;
import cs3500.threetrios.model.Color;
import cs3500.threetrios.model.Direction;
import cs3500.threetrios.model.GameCell;
import cs3500.threetrios.model.GameGrid;
import cs3500.threetrios.model.Grid;
import cs3500.threetrios.model.GridFileParser;
import cs3500.threetrios.model.player.AIPlayer;
import cs3500.threetrios.model.player.HumanPlayer;
import cs3500.threetrios.model.Model;
import cs3500.threetrios.model.player.Player;
import cs3500.threetrios.model.SimpleCard;
import cs3500.threetrios.model.ThreeTriosModel;
import cs3500.threetrios.model.Value;
import cs3500.threetrios.strategy.CornerCardStrat;
import cs3500.threetrios.strategy.GameStrategy;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Class that contains all testing that has to do with the model.
 */
public class ThreeTriosModelTest {
  // models
  protected Model hasSeededRandom;
  protected Model noSeededRandom;

  // some players
  protected Player redHumanPlayer;
  protected Player blueHumanPlayer;

  // AI player
  protected Player testAIPlayer;
  protected GameStrategy gameStrat;

  protected GameCell cellWithHole;
  protected GameCell cellWithoutHole;
  // some grids
  protected Grid gridWithNoHoles;
  protected Grid gridWithHoles;

  // some cards
  protected Card ratCard;
  protected Card oxCard;
  protected Card tigerCard;
  protected Card rabbitCard;
  protected Card dragonCard;
  protected Card snakeCard;
  protected Card horseCard;
  protected Card goatCard;
  protected Card monkeyCard;
  protected Card roosterCard;
  protected Card dogCard;
  protected Card pigCard;

  protected List<Card> deck;

  @Before
  public void init() {
    // initializing models with seeded and non-seeded randoms
    this.hasSeededRandom = new ThreeTriosModel(new Random(47));
    this.noSeededRandom = new ThreeTriosModel();

    this.cellWithHole = new GameCell(true);
    this.cellWithoutHole = new GameCell(false);

    // initializing cards
    this.ratCard = new SimpleCard("rat", Value.ACE, Value.ONE, Value.TWO, Value.THREE);
    this.oxCard = new SimpleCard("ox", Value.ACE, Value.ONE, Value.TWO, Value.THREE);
    this.tigerCard = new SimpleCard("tiger", Value.TWO, Value.ONE, Value.FIVE, Value.SIX);
    this.rabbitCard = new SimpleCard("rabbit", Value.FOUR, Value.NINE, Value.ACE, Value.THREE);
    this.dragonCard = new SimpleCard("dragon", Value.ACE, Value.ACE, Value.TWO, Value.ONE);
    // purposely making snake card's values equal to the rat cards
    this.snakeCard = new SimpleCard("snake", Value.ACE, Value.ONE, Value.TWO, Value.THREE);
    this.horseCard = new SimpleCard("horse", Value.TWO, Value.EIGHT, Value.TWO, Value.THREE);
    this.goatCard = new SimpleCard("goat", Value.ACE, Value.SIX, Value.FOUR, Value.SEVEN);
    this.monkeyCard = new SimpleCard("monkey", Value.ACE, Value.ACE, Value.ACE, Value.ACE);
    this.roosterCard = new SimpleCard("rooster", Value.ONE, Value.ONE, Value.ONE, Value.ONE);
    this.dogCard = new SimpleCard("dog", Value.NINE, Value.EIGHT, Value.SEVEN, Value.SIX);
    this.pigCard = new SimpleCard("pig", Value.ACE, Value.FOUR, Value.THREE, Value.TWO);

    this.deck = List.of(ratCard, oxCard, tigerCard, rabbitCard, dragonCard, horseCard, goatCard,
            monkeyCard, roosterCard, dogCard);

    this.redHumanPlayer = new HumanPlayer(List.of(), Color.RED);
    this.blueHumanPlayer = new HumanPlayer(List.of(), Color.BLUE);

    // AI player
    this.gameStrat = new CornerCardStrat();
    this.testAIPlayer = new AIPlayer(hasSeededRandom, Color.RED, gameStrat);


    // initializing grids
    boolean[][] noHolesLayout = {
            {false, false, false},
            {false, false, false},
            {false, false, false}
    };

    this.gridWithNoHoles = new GameGrid(3, 3, noHolesLayout);

    boolean[][] holesLayout = {
            {false, true, false},
            {true, false, true},
            {false, true, false}
    };

    this.gridWithHoles = new GameGrid(3, 3, holesLayout);
  }

  // testing the model
  // exceptions to be thrown
  // when random is null
  @Test (expected = IllegalArgumentException.class)
  public void shouldThrowIllegalArgumentRandomNull() {
    Model model = new ThreeTriosModel(null);
  }

  // testing game start with a seeded random
  @Test
  public void testStartGameWithSeededRandom() {
    hasSeededRandom.startGame(deck, true, gridWithNoHoles);
    assertTrue(hasSeededRandom.hasStarted());
    assertEquals(hasSeededRandom.getGrid(), this.gridWithNoHoles);
    assertEquals(redHumanPlayer.getColor(), hasSeededRandom.getCurrentPlayer().getColor());
  }

  // when the deck size is incorrect
  @Test (expected = IllegalArgumentException.class)
  public void testStartGameFailsDueToDeckSize() {
    hasSeededRandom.startGame(
            List.of(ratCard, oxCard, tigerCard, rabbitCard, dragonCard, snakeCard),
            true, gridWithNoHoles);
  }

  // test gameStart when game has already been started
  @Test (expected = IllegalStateException.class)
  public void shouldThrowIllegalStateGameAlreadyStarted() {
    this.hasSeededRandom.startGame(this.deck, true, this.gridWithNoHoles);
    this.hasSeededRandom.startGame(this.deck, false, this.gridWithNoHoles);
  }

  // test when cards are null
  @Test (expected = IllegalArgumentException.class)
  public void shouldThrowIllegalArgumentCardsNull() {
    this.hasSeededRandom.startGame(null, true, this.gridWithNoHoles);
  }

  // when the deck of cards is not unique
  @Test (expected = IllegalArgumentException.class)
  public void shouldThrowIllegalArgumentCardsNotUnique() {
    List<Card> sampleDeck = List.of(this.ratCard, this.oxCard, this.tigerCard, this.rabbitCard,
            this.dragonCard, this.horseCard, this.goatCard,
            this.monkeyCard, this.roosterCard, this.ratCard);
    this.hasSeededRandom.startGame(sampleDeck, true, this.gridWithNoHoles);
  }

  // test placingPhase method
  // when the game has not started
  @Test (expected = IllegalStateException.class)
  public void shouldReturnIllegalStatePlacingPhaseGameNotStarted() {
    this.hasSeededRandom.placingPhase(this.monkeyCard, 0, 0);
  }

  // when the game has ended
  @Test (expected = IllegalStateException.class)
  public void shouldReturnIllegalStatePlacingPhaseGameEnded() {
    hasSeededRandom.startGame(deck, true, gridWithNoHoles);

    hasSeededRandom.takeTurn(oxCard, 0, 1);
    hasSeededRandom.takeTurn(ratCard, 0, 0);
    hasSeededRandom.takeTurn(tigerCard, 1, 0);
    hasSeededRandom.takeTurn(dragonCard, 0, 2);
    hasSeededRandom.takeTurn(dogCard, 1, 2);
    hasSeededRandom.takeTurn(horseCard, 1, 1);
    hasSeededRandom.takeTurn(monkeyCard, 2, 1);
    hasSeededRandom.takeTurn(goatCard, 2, 0);
    hasSeededRandom.takeTurn(rabbitCard, 2, 2);

    hasSeededRandom.placingPhase(horseCard, 0, 0);
  }

  // places a card
  @Test
  public void testPlacingPhase() {
    hasSeededRandom.startGame(deck, true, gridWithNoHoles);
    hasSeededRandom.placingPhase(oxCard, 0, 0);
    assertTrue(hasSeededRandom.getGrid().getCells()[0][0].hasCard());
    assertEquals(oxCard, hasSeededRandom.getGrid().getCard(0, 0));
  }

  // places a card when the card already exists in the same spot
  @Test (expected = IllegalArgumentException.class)
  public void testPlacingPhaseCardAlreadyExists() {
    hasSeededRandom.startGame(deck, true, gridWithNoHoles);
    hasSeededRandom.placingPhase(oxCard, 0, 0);
    hasSeededRandom.placingPhase(tigerCard, 0, 0);
  }

  // places a card where there is a hole
  @Test (expected = IllegalArgumentException.class)
  public void shouldThrowIllegalStatePlacingPhaseHole() {
    List<Card> sampleDeck = List.of(this.ratCard, this.oxCard, this.tigerCard, this.rabbitCard,
            this.dragonCard, this.monkeyCard);
    this.hasSeededRandom.startGame(sampleDeck, true, gridWithHoles);
    this.hasSeededRandom.placingPhase(ratCard, 0, 1);
  }

  // test battlingPhase method
  // when game has not started
  @Test (expected = IllegalStateException.class)
  public void testGameNotStartedBattling() {
    this.hasSeededRandom.battlingPhase(0, 0);
  }

  // when the game has ended
  @Test (expected = IllegalStateException.class)
  public void shouldReturnIllegalStateBattlingGameEnded() {
    hasSeededRandom.startGame(deck, true, gridWithNoHoles);

    hasSeededRandom.takeTurn(oxCard, 0, 1);
    hasSeededRandom.takeTurn(ratCard, 0, 0);
    hasSeededRandom.takeTurn(tigerCard, 1, 0);
    hasSeededRandom.takeTurn(dragonCard, 0, 2);
    hasSeededRandom.takeTurn(dogCard, 1, 2);
    hasSeededRandom.takeTurn(horseCard, 1, 1);
    hasSeededRandom.takeTurn(monkeyCard, 2, 1);
    hasSeededRandom.takeTurn(goatCard, 2, 0);
    hasSeededRandom.takeTurn(rabbitCard, 2, 2);

    hasSeededRandom.battlingPhase(0, 0);
  }

  // battling another card
  @Test
  public void testBattlingPhase() {
    hasSeededRandom.startGame(deck, true, gridWithNoHoles);
    hasSeededRandom.placingPhase(rabbitCard, 0, 0);
    hasSeededRandom.battlingPhase(0, 0);

    assertEquals(Color.RED,
            hasSeededRandom.getCardOwnerColor(hasSeededRandom.getGrid().getCard(0, 0)));
  }

  // test takeTurn method
  // when game has not started yet
  @Test (expected = IllegalStateException.class)
  public void shouldThrowIllegalStateTakeTurnGameNotStarted() {
    this.hasSeededRandom.takeTurn(oxCard, 0, 1);
  }

  // when game has ended
  @Test (expected = IllegalStateException.class)
  public void shouldReturnIllegalStateTakeTurnGameEnded() {
    hasSeededRandom.startGame(deck, true, gridWithNoHoles);

    hasSeededRandom.takeTurn(oxCard, 0, 1);
    hasSeededRandom.takeTurn(ratCard, 0, 0);
    hasSeededRandom.takeTurn(tigerCard, 1, 0);
    hasSeededRandom.takeTurn(dragonCard, 0, 2);
    hasSeededRandom.takeTurn(dogCard, 1, 2);
    hasSeededRandom.takeTurn(horseCard, 1, 1);
    hasSeededRandom.takeTurn(monkeyCard, 2, 1);
    hasSeededRandom.takeTurn(goatCard, 2, 0);
    hasSeededRandom.takeTurn(rabbitCard, 2, 2);

    hasSeededRandom.takeTurn(horseCard, 0, 0);
  }

  // when player does not have the card in hand
  @Test (expected = IllegalArgumentException.class)
  public void shouldReturnIllegalArgumentTakeTurnCardNotInHand() {
    this.hasSeededRandom.startGame(deck, true, gridWithNoHoles);
    this.hasSeededRandom.takeTurn(pigCard, 0, 1);
  }

  // when the move is invalid
  @Test (expected = IllegalArgumentException.class)
  public void shouldReturnIllegalArgumentTakeTurnInvalidMove() {
    this.hasSeededRandom.startGame(deck, true, gridWithNoHoles);
    this.hasSeededRandom.takeTurn(oxCard, 0, 5);
  }

  // takes a turn with a valid move
  @Test
  public void testTakeTurnWithValidMove() {
    hasSeededRandom.startGame(deck, true, gridWithNoHoles);
    hasSeededRandom.placingPhase(oxCard, 0, 0);
    hasSeededRandom.placingPhase(dogCard, 0, 1);
    hasSeededRandom.battlingPhase(0, 0);
    Color expectedOwnerColor = hasSeededRandom.getCurrentPlayer().getColor();
    assertEquals(expectedOwnerColor, hasSeededRandom.getCardOwnerColor(dogCard));
  }

  // test isGameOver method
  // tests when the game has not started but the method has been called
  @Test (expected = IllegalStateException.class)
  public void testIsGameOverException() {
    this.hasSeededRandom.isGameOver();
  }

  // tests that GameOver returns true when game is over
  @Test
  public void testIsGameOver() {
    hasSeededRandom.startGame(deck, true, gridWithNoHoles);
    assertFalse(hasSeededRandom.isGameOver());

    hasSeededRandom.takeTurn(oxCard, 0, 1);
    hasSeededRandom.takeTurn(ratCard, 0, 0);
    hasSeededRandom.takeTurn(tigerCard, 1, 0);
    hasSeededRandom.takeTurn(dragonCard, 0, 2);
    hasSeededRandom.takeTurn(dogCard, 1, 2);
    hasSeededRandom.takeTurn(horseCard, 1, 1);
    hasSeededRandom.takeTurn(monkeyCard, 2, 1);
    hasSeededRandom.takeTurn(goatCard, 2, 0);
    hasSeededRandom.takeTurn(rabbitCard, 2, 2);

    assertTrue(hasSeededRandom.isGameOver());
  }

  // test winner method
  // when the game has not started yet
  @Test (expected = IllegalStateException.class)
  public void shouldReturnIllegalStateWinnerGameNotStarted() {
    this.hasSeededRandom.winner();
  }

  // when the game has not ended yet
  @Test (expected = IllegalStateException.class)
  public void shouldReturnIllegalStateWinnerGameNotEnded() {
    this.hasSeededRandom.startGame(deck, true, gridWithNoHoles);
    this.hasSeededRandom.winner();
  }

  // when there is a tie
  @Test
  public void shouldReturnTieWinner() {
    hasSeededRandom.startGame(deck, true, gridWithNoHoles);
    hasSeededRandom.takeTurn(oxCard, 0, 1);
    hasSeededRandom.takeTurn(ratCard, 0, 0);
    hasSeededRandom.takeTurn(tigerCard, 1, 0);
    hasSeededRandom.takeTurn(dragonCard, 0, 2);
    hasSeededRandom.takeTurn(dogCard, 1, 2);
    hasSeededRandom.takeTurn(horseCard, 1, 1);
    hasSeededRandom.takeTurn(monkeyCard, 2, 1);
    hasSeededRandom.takeTurn(goatCard, 2, 0);
    hasSeededRandom.takeTurn(rabbitCard, 2, 2);

    assertEquals("Blue Player", hasSeededRandom.winner());
  }

  // finds winner when there is a winner
  @Test
  public void testWinner() {
    this.hasSeededRandom.startGame(this.deck, false, this.gridWithNoHoles);
    this.hasSeededRandom.takeTurn(this.roosterCard, 0, 1);
    this.hasSeededRandom.takeTurn(this.oxCard, 2, 2);
    this.hasSeededRandom.takeTurn(this.dragonCard, 1, 2);
    this.hasSeededRandom.takeTurn(this.horseCard, 0, 2);
    this.hasSeededRandom.takeTurn(this.ratCard, 1, 0);
    this.hasSeededRandom.takeTurn(this.rabbitCard, 2, 0);
    this.hasSeededRandom.takeTurn(this.tigerCard, 2, 1);
    this.hasSeededRandom.takeTurn(this.monkeyCard, 1, 1);
    this.hasSeededRandom.takeTurn(this.goatCard, 0, 0);

    assertEquals("Blue Player", this.hasSeededRandom.winner());
  }

  // test getGrid
  @Test
  public void testGetGrid() {
    hasSeededRandom.startGame(deck, true, gridWithNoHoles);
    assertEquals(gridWithNoHoles, hasSeededRandom.getGrid());
  }

  // test getCurrentPlayer
  @Test
  public void testGetCurrentPlayer() {
    hasSeededRandom.startGame(deck, true, gridWithNoHoles);
    assertEquals(redHumanPlayer.getColor(), hasSeededRandom.getCurrentPlayer().getColor());
  }

  // test hasStarted
  @Test
  public void testHasStarted() {
    assertFalse(hasSeededRandom.hasStarted());
    hasSeededRandom.startGame(deck, true, gridWithNoHoles);
    assertTrue(hasSeededRandom.hasStarted());
  }


  // testing the HumanPlayer class
  // exceptions to be thrown
  // when cards in hand is null
  @Test (expected = IllegalArgumentException.class)
  public void shouldThrowIllegalArgumentHandNull() {
    List<Card> cards = null;
    Player newPlayer = new HumanPlayer(cards, Color.RED);
  }

  // test the removeFromHand method
  // when the card does not exist in the hand
  @Test (expected = IllegalArgumentException.class)
  public void shouldThrowIllegalArgumentCardNotInHand() {
    this.redHumanPlayer.removeFromHand(this.monkeyCard);
  }

  // test the removeFromOwnership method
  // when the thing to remove is not in ownership
  @Test (expected = IllegalArgumentException.class)
  public void shouldThrowIllegalArgumentCardNotOwned() {
    this.redHumanPlayer.removeFromOwnership(this.ratCard);
  }

  // when there is something to remove
  @Test
  public void shouldRemoveFromOwnership() {
    this.redHumanPlayer.addToOwnership(this.ratCard);
    this.redHumanPlayer.addToOwnership(this.pigCard);
    assertEquals(2, this.redHumanPlayer.getNumberCardsOwned());

    this.redHumanPlayer.removeFromOwnership(this.ratCard);
    assertEquals(1, this.redHumanPlayer.getNumberCardsOwned());
  }

  // test the addToOwnerShip method
  // adding a card
  @Test
  public void shouldAddCardsToOwnershipProperly() {
    this.redHumanPlayer.addToOwnership(this.ratCard);
    assertEquals(1, this.redHumanPlayer.getNumberCardsOwned());
  }

  // test the getOwnedCardsOnBoard method
  // when there are no cards owned on the board
  @Test
  public void shouldReturnEmptyOwnedCards() {
    assertEquals(List.of(), this.blueHumanPlayer.getOwnedCardsOnGrid());
  }

  // when there are owned cards on the board
  @Test
  public void shouldReturnOwnedCards() {
    this.blueHumanPlayer.addToOwnership(this.ratCard);
    List<Card> expectedOwnedCards = List.of(this.ratCard);
    assertEquals(expectedOwnedCards, this.blueHumanPlayer.getOwnedCardsOnGrid());
  }

  // test the getCardsInHand method
  @Test
  public void shouldReturnCardsInHand() {
    this.noSeededRandom.startGame(deck, false, gridWithNoHoles);
    List<Card> expectedHand = List.of(this.ratCard, this.tigerCard, this.dragonCard,
            this.goatCard, this.roosterCard);
    assertEquals(expectedHand, this.noSeededRandom.getCurrentPlayer().getCardsInHand());
  }

  // test the getNumberOwnedCards method
  // when a card has been added to the grid
  @Test
  public void shouldReturnNumberOwnedCards() {
    this.noSeededRandom.startGame(deck, false, gridWithNoHoles);
    Player samplePlayer = this.noSeededRandom.getCurrentPlayer();
    samplePlayer.addToOwnership(this.pigCard);
    assertEquals(6, samplePlayer.getNumberCardsOwned());
  }

  // when no cards have been added to the grid
  @Test
  public void shouldReturnNumberOwnedCardsNoGridCards() {
    this.noSeededRandom.startGame(deck, false, gridWithNoHoles);
    Player samplePlayer = this.noSeededRandom.getCurrentPlayer();
    assertEquals(5, samplePlayer.getNumberCardsOwned());
  }

  // test the getColor method
  // when the color is blue
  @Test
  public void shouldReturnBlue() {
    assertEquals(this.blueHumanPlayer.getColor(), Color.BLUE);
  }

  // when the color is red
  @Test
  public void shouldReturnRed() {
    assertEquals(this.redHumanPlayer.getColor(), Color.RED);
  }

  // test the toString method
  // print out a player
  @Test
  public void shouldReturnStringOfPlayer() {
    assertEquals("Player: RED", this.redHumanPlayer.toString());
  }

  // testing the SimpleCard class
  // exceptions to be thrown from constructor
  // when the map of directions and values is null
  @Test (expected = IllegalArgumentException.class)
  public void shouldThrowIllegalArgumentMapNull() {
    Card simpleCard = new SimpleCard("simple", null);
  }

  // when the name is null
  @Test (expected = IllegalArgumentException.class)
  public void shouldThrowIllegalArgumentNameNull() {
    Card simpleCard = new SimpleCard(null, Value.ACE, Value.ONE, Value.TWO, Value.THREE);
  }

  // when not all directions are accounted
  @Test (expected = IllegalArgumentException.class)
  public void shouldThrowIllegalArgumentNotAllDirections() {
    Map<Direction, Value> values = new HashMap<>();
    values.put(Direction.NORTH, Value.FOUR);
    Card simpleCard = new SimpleCard("simple", values);
  }

  // test the compareTo method
  // when this card's value is greater than that card's value with the given direction
  @Test
  public void shouldReturnOne() {
    int compare = this.pigCard.compareTo(this.dogCard, Direction.NORTH);
    assertEquals(1, compare);
  }

  // when this card's value is equal to that card's value with the given direction
  @Test
  public void shouldReturnZero() {
    int compare = this.ratCard.compareTo(this.monkeyCard, Direction.NORTH);
    assertEquals(0, compare);
  }

  // when this card's value is less than that card's value with the given direction
  @Test
  public void shouldReturnNegativeOne() {
    int compare = this.ratCard.compareTo(this.monkeyCard, Direction.SOUTH);
    assertEquals(-1, compare);
  }

  // test the getValueFromDirection method
  // makes sure that illegalArgumentException is thrown when direction is null
  @Test (expected = IllegalArgumentException.class)
  public void shouldThrowIllegalDirectionNull() {
    this.oxCard.getValueFromDirection(null);
  }

  // gets the value from North direction
  @Test
  public void shouldGetDirectionNorth() {
    int value = this.ratCard.getValueFromDirection(Direction.NORTH);
    assertEquals(10, value);
  }

  // gets the value from South direction
  @Test
  public void shouldGetDirectionSouth() {
    int value = this.tigerCard.getValueFromDirection(Direction.SOUTH);
    assertEquals(1, value);
  }

  // gets the value from North direction
  @Test
  public void shouldGetDirectionEast() {
    int value = this.rabbitCard.getValueFromDirection(Direction.EAST);
    assertEquals(10, value);
  }

  // gets the value from North direction
  @Test
  public void shouldGetDirectionWest() {
    int value = this.dragonCard.getValueFromDirection(Direction.WEST);
    assertEquals(1, value);
  }

  // test the getName method
  // makes sure that changing the returned name doesn't mutate anything
  @Test
  public void shouldNotMutateGetName() {
    String name = this.ratCard.getName();
    name += "bleh";

    assertEquals("rat", this.ratCard.getName());
  }

  // gets the name
  @Test
  public void shouldReturnGetName() {
    String name = this.ratCard.getName();
    assertEquals("rat", this.ratCard.getName());
  }

  // test the copy method
  // makes sure that the returned card is the same as the original
  @Test
  public void shouldReturnCopyOfCard() {
    Card copiedCard = this.snakeCard.copy();
    boolean isEqual = this.snakeCard.equals(copiedCard);
    assertTrue(isEqual);
  }

  // makes sure that changing something in the copied card doesn't change anything in the original
  @Test
  public void shouldNotMutateOriginalCopyOfCard() {
    Card copiedCard = this.snakeCard.copy();
    assertEquals(this.snakeCard, copiedCard);

    Map<Direction, Value> newValues = new HashMap<>();
    newValues.put(Direction.NORTH, Value.TWO);
    newValues.put(Direction.SOUTH, Value.THREE);
    newValues.put(Direction.EAST, Value.FOUR);
    newValues.put(Direction.WEST, Value.FIVE);

    copiedCard = new SimpleCard(copiedCard.getName(), newValues);
    assertEquals(10, this.snakeCard.getValueFromDirection(Direction.NORTH));
    assertEquals(this.snakeCard, this.snakeCard);
  }

  // test the toString method
  // prints out a card
  @Test
  public void printOutCard() {
    String result = this.horseCard.toString();
    assertEquals("horse 2 8 2 3 ", result);
  }

  // print out another card
  @Test
  public void printOutCard2() {
    String result = this.roosterCard.toString();
    assertEquals("rooster 1 1 1 1 ", result);
  }

  // print out a card with an A
  @Test
  public void printOutCardWithA() {
    String result = this.goatCard.toString();
    assertEquals("goat A 6 4 7 ", result);
  }

  // test GameGrid
  // testing constructor enforcing no even number of card cells
  @Test(expected = IllegalArgumentException.class)
  public void testEvenCardCellCountThrowsException() {
    boolean[][] evenHoleLayout = {
            {false, true},
            {true, false}
    };
    new GameGrid(2, 2, evenHoleLayout);
  }

  // test placeCard
  @Test
  public void testPlaceCardOnEmptyCell() {
    gridWithNoHoles.placeCard(goatCard, 0, 0);
    assertEquals(goatCard, gridWithNoHoles.getCard(0, 0));
  }

  @Test(expected = IllegalStateException.class)
  public void testPlaceCardOnHole() {
    gridWithHoles.placeCard(goatCard, 0, 1);
  }

  @Test(expected = IllegalStateException.class)
  public void testPlaceCardOnOccupiedCell() {
    gridWithNoHoles.placeCard(ratCard, 1, 1);
    gridWithNoHoles.placeCard(tigerCard, 1, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPlaceCardOnOutOfBoundsCellOnGrid() {
    gridWithNoHoles.placeCard(ratCard, 7, 10);
  }

  @Test(expected = IllegalStateException.class)
  public void testPlaceCardOnHoleCell() {
    gridWithHoles.placeCard(ratCard, 0, 1);
  }

  // test validCell
  @Test
  public void testValidCellOnEmptyCell() {
    assertTrue(gridWithNoHoles.validCell(0, 0));
  }

  @Test
  public void testValidCellOnHole() {
    assertFalse(gridWithHoles.validCell(0, 1));
  }

  @Test
  public void testValidCellOnOccupiedCell() {
    gridWithNoHoles.placeCard(rabbitCard, 2, 2);
    assertFalse(gridWithNoHoles.validCell(2, 2));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPlaceCardOnOutOfBoundsCell() {
    gridWithNoHoles.placeCard(rabbitCard, 6, 3);
  }

  // test getAdjacentCardsWithDirections
  @Test
  public void testGetAdjacentCardsWithDirections() {
    gridWithNoHoles.placeCard(horseCard, 1, 1);
    gridWithNoHoles.placeCard(dragonCard, 1, 2);

    var adjacentCards = gridWithNoHoles.getAdjacentCardsWithDirections(1, 1);
    assertEquals(1, adjacentCards.size());
    assertTrue(adjacentCards.containsKey("dragon"));
    assertEquals(Direction.EAST, adjacentCards.get("dragon"));
  }

  // test getCardCellCount
  @Test
  public void testGetCardCellCountWithHoles() {
    assertEquals(5, gridWithHoles.getCardCellCount());
  }

  @Test
  public void testGetCardCellCountNoHoles() {
    assertEquals(9, gridWithNoHoles.getCardCellCount());
  }

  // test getCells
  @Test
  public void testGetCellsReturnsCorrectGrid() {
    Cell[][] cells = gridWithNoHoles.getCells();
    assertEquals(3, cells.length);
    assertEquals(3, cells[0].length);
  }

  // test getNumRows
  @Test
  public void testGetNumRows() {
    assertEquals(3, gridWithNoHoles.getNumRows());
  }

  // test getNumCols
  @Test
  public void testGetNumCols() {
    assertEquals(3, gridWithNoHoles.getNumCols());
  }

  // test getCard
  @Test
  public void testGetCard() {
    gridWithNoHoles.placeCard(pigCard, 2, 2);
    assertEquals(pigCard, gridWithNoHoles.getCard(2, 2));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetCardFromHoleOnGrid() {
    gridWithHoles.getCard(0, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetCardFromEmptyCellOnGrid() {
    gridWithNoHoles.getCard(0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetCardFromOutOfBoundsCellOnGrid() {
    gridWithNoHoles.getCard(11, 9);
  }

  // test getHoleLayout
  @Test
  public void testGetHoleLayout() {
    boolean[][] expectedLayout = {
            {false, true, false},
            {true, false, true},
            {false, true, false}
    };
    Assert.assertArrayEquals(expectedLayout, gridWithHoles.getHoleLayout());
  }

  // test GameCell file
  // test hasCard
  @Test
  public void testHasCardInitiallyFalse() {
    assertFalse(cellWithoutHole.hasCard());
  }

  // after placing a card
  @Test
  public void testHasCardAfterPlacingCard() {
    cellWithoutHole.placeCard(goatCard);
    assertTrue(cellWithoutHole.hasCard());
  }

  // test placeCard
  @Test
  public void testPlaceCardInEmptyCell() {
    cellWithoutHole.placeCard(goatCard);
    assertEquals(goatCard, cellWithoutHole.getCard());
  }

  // if the cell is occupied
  @Test(expected = IllegalStateException.class)
  public void testPlaceCardInOccupiedCell() {
    cellWithoutHole.placeCard(goatCard);
    cellWithoutHole.placeCard(goatCard);
  }

  // trying to place a card in a hole cell
  @Test(expected = IllegalStateException.class)
  public void testPlaceCardInHoleCell() {
    cellWithHole.placeCard(goatCard);
  }

  // test removeCard
  @Test
  public void testRemoveCard() {
    cellWithoutHole.placeCard(goatCard);
    cellWithoutHole.removeCard();
    assertFalse(cellWithoutHole.hasCard());
  }

  // removing card from an empty cell
  @Test(expected = IllegalStateException.class)
  public void testRemoveCardFromEmptyCell() {
    cellWithoutHole.removeCard();
  }

  @Test(expected = IllegalStateException.class)
  public void testRemoveCardFromHoleCell() {
    cellWithHole.removeCard();
  }

  // test isHole()
  @Test
  public void testIsHoleTrue() {
    assertTrue(cellWithHole.isHole());
  }

  @Test
  public void testIsHoleFalse() {
    assertFalse(cellWithoutHole.isHole());
  }

  // test getCard
  @Test
  public void testGetCardFromOccupiedCell() {
    cellWithoutHole.placeCard(goatCard);
    assertEquals(goatCard, cellWithoutHole.getCard());
  }

  @Test(expected = IllegalStateException.class)
  public void testGetCardFromEmptyCell() {
    cellWithoutHole.getCard();
  }

  @Test(expected = IllegalStateException.class)
  public void testGetCardFromHole() {
    cellWithHole.getCard();
  }


  // testing the CardFileParser class
  // test the createDeck method
  @Test
  public void createLessDeck() throws FileNotFoundException {
    String path = "configurationFiles/CardConfiguration/LessCards";
    CardFileParser parser = new CardFileParser(path);
    List<Card> result = parser.createDeck();

    Card sleepyCat =
            new SimpleCard("SleepyCat", Value.ONE, Value.TWO, Value.THREE, Value.FOUR);
    Card creepyCat =
            new SimpleCard("CreepyCat", Value.FIVE, Value.SIX, Value.SEVEN, Value.EIGHT);
    Card happyCat =
            new SimpleCard("HappyCat", Value.NINE, Value.ACE, Value.ONE, Value.TWO);
    Card angryCat =
            new SimpleCard("AngryCat", Value.THREE, Value.FOUR, Value.FIVE, Value.SIX);
    Card weepyCat =
            new SimpleCard("WeepyCat", Value.SEVEN, Value.EIGHT, Value.NINE, Value.ACE);
    Card crazyCat =
            new SimpleCard("CrazyCat", Value.SIX, Value.SIX, Value.SEVEN, Value.ONE);
    List<Card> expected = List.of(sleepyCat, creepyCat, happyCat, angryCat, weepyCat, crazyCat);

    assertEquals(expected, result);
  }


  // testing the GridFileParser class
  // testing createGridFromFile from Disconnected file
  @Test
  public void testDisconnectedFile() throws FileNotFoundException {
    File file = new File("configurationFiles/GridConfiguration/Disconnected");
    GridFileParser parser = new GridFileParser(file);
    GameGrid result = parser.createGridFromFile();

    int expectedNumRows = 3;
    int expectedNumCols = 4;
    boolean[][] expectedHoleLayout = {
            {true, false, false, true},
            {true, true, false, false},
            {true, false, true, true},
    };

    assertEquals(expectedNumRows, result.getNumRows());
    assertEquals(expectedNumCols, result.getNumCols());
    Assert.assertArrayEquals(expectedHoleLayout, result.getHoleLayout());
  }

  // testing createGridFromFile from HasHoles file
  @Test
  public void testHasHolesFile() throws FileNotFoundException {
    File file = new File("configurationFiles/GridConfiguration/HasHoles");
    GridFileParser parser = new GridFileParser(file);
    GameGrid result = parser.createGridFromFile();

    int expectedNumRows = 3;
    int expectedNumCols = 4;
    boolean[][] expectedHoleLayout = {
            {false, false, false, false},
            {true, false, false, false},
            {false, false, true, true}
    };

    assertEquals(expectedNumRows, result.getNumRows());
    assertEquals(expectedNumCols, result.getNumCols());
    Assert.assertArrayEquals(expectedHoleLayout, result.getHoleLayout());
  }

  // testing createGridFromFile from NoHoles file
  @Test
  public void testNoHolesFile() throws FileNotFoundException {
    File file = new File("configurationFiles/GridConfiguration/NoHoles");
    GridFileParser parser = new GridFileParser(file);
    GameGrid result = parser.createGridFromFile();

    int expectedNumRows = 3;
    int expectedNumCols = 3;
    boolean[][] expectedHoleLayout = {
            {false, false, false},
            {false, false, false},
            {false, false, false}
    };

    assertEquals(expectedNumRows, result.getNumRows());
    assertEquals(expectedNumCols, result.getNumCols());
    Assert.assertArrayEquals(expectedHoleLayout, result.getHoleLayout());
  }

  // TESTING NEW ADDED OBSERVATIONS FROM THE MODEL
  // test getRedPlayer method
  // throw exception when game not started
  @Test (expected = IllegalStateException.class)
  public void shouldReturnIllegalStateGetRedPlayer() {
    this.noSeededRandom.getRedPlayer();
  }

  // make sure nothing is mutated from original
  @Test
  public void shouldNotMutateGetRedPlayer() {
    this.hasSeededRandom.startGame(this.deck, false, this.gridWithNoHoles);
    int numCards = this.hasSeededRandom.getRedPlayer().size();
    assertEquals(5, numCards);
    List<Card> redPlayerCards = this.hasSeededRandom.getRedPlayer();
    redPlayerCards.add(this.roosterCard);
    assertEquals(5, this.hasSeededRandom.getRedPlayer().size());
  }

  // make sure correct cards
  @Test
  public void shouldContainCorrectCardsRedPlayer() {
    this.hasSeededRandom.startGame(this.deck, false, this.gridWithNoHoles);
    List<Card> expectedCards = List.of(this.ratCard, this.tigerCard, this.dragonCard,
            this.goatCard, this.roosterCard);
    assertEquals(expectedCards, this.hasSeededRandom.getRedPlayer());
  }

  // test getBluePlayer method
  @Test (expected = IllegalStateException.class)
  public void shouldReturnIllegalStateGetBluePlayer() {
    this.noSeededRandom.getBluePlayer();
  }

  // make sure nothing is mutated from original
  @Test
  public void shouldNotMutateGetBluePlayer() {
    this.hasSeededRandom.startGame(this.deck, false, this.gridWithNoHoles);
    int numCards = this.hasSeededRandom.getBluePlayer().size();
    assertEquals(5, numCards);
    List<Card> redPlayerCards = this.hasSeededRandom.getBluePlayer();
    redPlayerCards.add(this.roosterCard);
    assertEquals(5, this.hasSeededRandom.getBluePlayer().size());
  }

  // make sure correct cards
  @Test
  public void shouldContainCorrectCardsBluePlayer() {
    this.hasSeededRandom.startGame(this.deck, false, this.gridWithNoHoles);
    List<Card> expectedCards = List.of(this.oxCard, this.rabbitCard, this.horseCard,
            this.monkeyCard, this.dogCard);
    assertEquals(expectedCards, this.hasSeededRandom.getBluePlayer());
  }

  // test getOwnerAtCell method
  // when the game has not started yet
  @Test (expected = IllegalStateException.class)
  public void shouldReturnIllegalStateGetOwnerAtCell() {
    this.hasSeededRandom.getOwnerAtCell(0, 0);
  }

  // when the cell is a hole
  @Test (expected = IllegalArgumentException.class)
  public void shouldReturnIllegalArgumentCellIsHole() {
    List<Card> holesDeck = List.of(this.ratCard, this.rabbitCard, this.horseCard, this.monkeyCard,
            this.dogCard, this.snakeCard);
    this.hasSeededRandom.startGame(holesDeck, false, this.gridWithHoles);
    this.hasSeededRandom.getOwnerAtCell(0,1);
  }

  // when the cell is out of bounds
  @Test (expected = IllegalArgumentException.class)
  public void shouldReturnIllegalArgumentGetOwnerRowOutOfBounds() {
    this.hasSeededRandom.startGame(this.deck, false, this.gridWithNoHoles);
    this.hasSeededRandom.getOwnerAtCell(-1, 0);
  }

  // when there is no owner on the cell
  @Test (expected = IllegalArgumentException.class)
  public void shouldReturnIllegalArgumentGetOwnerColOutOfBounds() {
    this.hasSeededRandom.startGame(this.deck, false, this.gridWithNoHoles);
    this.hasSeededRandom.getOwnerAtCell(0, -1);
  }

  // when the red player owns the cell
  @Test
  public void shouldReturnRedPlayerGetOwnerAtCell() {
    this.hasSeededRandom.startGame(this.deck, false, this.gridWithNoHoles);
    this.hasSeededRandom.takeTurn(this.ratCard, 0, 0);
    String player = this.hasSeededRandom.getOwnerAtCell(0, 0).getColor().toString();
    assertEquals(player, "RED");
  }

  // when the blue player owns the cell
  @Test
  public void shouldReturnBluePlayerGetOwnerAtCell() {
    this.hasSeededRandom.startGame(this.deck, false, this.gridWithNoHoles);
    this.hasSeededRandom.takeTurn(this.ratCard, 0, 0);
    this.hasSeededRandom.takeTurn(this.monkeyCard, 0, 1);
    String player = this.hasSeededRandom.getOwnerAtCell(0, 1).getColor().toString();
    assertEquals(player, "BLUE");
  }

  // when there is no owner at the cell
  @Test (expected = IllegalArgumentException.class)
  public void shouldReturnIllegalArgumentGetOwnerNoOwnerPresent() {
    this.hasSeededRandom.startGame(this.deck, false, this.gridWithNoHoles);
    this.hasSeededRandom.getOwnerAtCell(0, 0);
  }

  // test isLegalToPlay method
  // when the game has not started yet
  @Test (expected = IllegalStateException.class)
  public void shouldReturnIllegalStateLegalPlay() {
    this.hasSeededRandom.isLegalToPlay(0, 0);
  }

  // when the row and col are out of bounds
  @Test
  public void shouldBeFalseLegalPlayRowOutOfBounds() {
    this.hasSeededRandom.startGame(this.deck, false, this.gridWithNoHoles);
    assertFalse(this.hasSeededRandom.isLegalToPlay(10, 0));
  }

  @Test
  public void shouldBeFalseLegalPlayColOutOfBounds() {
    this.hasSeededRandom.startGame(this.deck, false, this.gridWithNoHoles);
    assertFalse(this.hasSeededRandom.isLegalToPlay(0, 10));
  }

  // when it is a hole
  @Test
  public void shouldBeFalseLegalPlayCellIsHole() {
    List<Card> holesDeck = List.of(this.ratCard, this.rabbitCard, this.horseCard, this.monkeyCard,
            this.dogCard, this.snakeCard);
    this.hasSeededRandom.startGame(holesDeck, false, this.gridWithHoles);
    assertFalse(this.hasSeededRandom.isLegalToPlay(0, 1));
  }

  // when the row and col are in bounds
  @Test
  public void shouldBeTrueLegalPlay() {
    this.hasSeededRandom.startGame(this.deck, false, this.gridWithNoHoles);
    assertFalse(this.hasSeededRandom.isLegalToPlay(0, 10));
  }

  // test howManyWillFlip method
  @Test (expected = IllegalStateException.class)
  public void shouldReturnIllegalStateHowManyFlip() {
    this.hasSeededRandom.howManyWillFlip(this.ratCard,0, 0);
  }

  // when the row/col are out of bounds
  @Test (expected = IllegalArgumentException.class)
  public void shouldThrowIllegalArgumentExceptionRowOutOfBounds() {
    this.hasSeededRandom.startGame(this.deck, false, this.gridWithNoHoles);
    this.hasSeededRandom.howManyWillFlip(this.ratCard,-1, 0);
  }

  @Test (expected = IllegalArgumentException.class)
  public void shouldThrowIllegalArgumentExceptionColOutOfBounds() {
    this.hasSeededRandom.startGame(this.deck, false, this.gridWithNoHoles);
    this.hasSeededRandom.howManyWillFlip(this.ratCard,0, -1);
  }

  // when trying to find the flips on a hole
  @Test (expected = IllegalArgumentException.class)
  public void shouldThrowIllegalArgumentExceptionHowManyFlipOnHole() {
    List<Card> holesDeck = List.of(this.ratCard, this.rabbitCard, this.horseCard, this.monkeyCard,
            this.dogCard, this.snakeCard);
    this.hasSeededRandom.startGame(holesDeck, false, this.gridWithHoles);
    this.hasSeededRandom.howManyWillFlip(this.ratCard, 0, 1);
  }

  // when there is already a card there
  @Test (expected = IllegalArgumentException.class)
  public void shouldThrowIllegalArgumentExceptionAlreadyCard() {
    this.hasSeededRandom.startGame(this.deck, false, this.gridWithNoHoles);
    this.hasSeededRandom.takeTurn(this.ratCard, 0, 0);
    this.hasSeededRandom.howManyWillFlip(this.monkeyCard,0, 0);
  }

  // when nothing will flip & there are no other cards nearby
  @Test
  public void shouldReturnZeroFlipsNoCardNear() {
    this.hasSeededRandom.startGame(this.deck, false, this.gridWithNoHoles);
    int flips = this.hasSeededRandom.howManyWillFlip(this.ratCard, 0, 0);
    assertEquals(0, flips);
  }

  // when something will flip
  @Test
  public void shouldReturnOneFlips() {
    this.hasSeededRandom.startGame(this.deck, false, this.gridWithNoHoles);
    this.hasSeededRandom.takeTurn(this.ratCard, 0, 0);
    int flips = this.hasSeededRandom.howManyWillFlip(this.monkeyCard, 0, 1);
    assertEquals(1, flips);
  }

  // test currentScore method
  // when the game has not started (should throw and exception)
  @Test (expected = IllegalStateException.class)
  public void shouldReturnIllegalStateCurrentScore() {
    this.hasSeededRandom.currentScore(Color.BLUE);
  }

  @Test (expected = IllegalArgumentException.class)
  public void shouldReturnIllegalArgumentColorNull() {
    this.hasSeededRandom.startGame(this.deck, false, this.gridWithNoHoles);
    this.hasSeededRandom.currentScore(null);
  }

  // when the game has just started and score is just the hand
  @Test
  public void shouldReturnHandScore() {
    this.hasSeededRandom.startGame(this.deck, false, this.gridWithNoHoles);
    int score = this.hasSeededRandom.currentScore(Color.RED);
    assertEquals(5, score);
  }

  // when cards have been played, but not flipped
  @Test
  public void shouldReturnOriginalScore() {
    this.hasSeededRandom.startGame(this.deck, false, this.gridWithNoHoles);
    this.hasSeededRandom.takeTurn(this.ratCard, 0, 0);
    // red player's score
    assertEquals(5,
            this.hasSeededRandom.currentScore(Color.RED));
    this.hasSeededRandom.takeTurn(this.oxCard, 2, 2);
    // blue player's score
    assertEquals(5,
            this.hasSeededRandom.currentScore(Color.BLUE));
  }

  // when cards have been played, and flipped
  @Test
  public void shouldReturnFlippedScore() {
    this.hasSeededRandom.startGame(this.deck, false, this.gridWithNoHoles);
    this.hasSeededRandom.takeTurn(this.ratCard, 0, 0);
    // red player's score (should still be the same)
    assertEquals(5,
            this.hasSeededRandom.currentScore(Color.RED));
    assertEquals(5,
            this.hasSeededRandom.currentScore(Color.BLUE));
    this.hasSeededRandom.takeTurn(this.monkeyCard, 0,1);
    // blue player's new score now that card is flipped
    assertEquals(6,
            this.hasSeededRandom.currentScore(Color.BLUE));
    // red player's new score now that card is flipped
    assertEquals(4,
            this.hasSeededRandom.currentScore(Color.RED));
  }

  // when multiple cards have been flipped
  @Test
  public void shouldReturnMultipleFlippedScore() {
    this.hasSeededRandom.startGame(this.deck, false, this.gridWithNoHoles);
    this.hasSeededRandom.takeTurn(this.ratCard, 0, 0);
    // red player's score (should still be the same)
    assertEquals(5,
            this.hasSeededRandom.currentScore(Color.RED));
    assertEquals(5,
            this.hasSeededRandom.currentScore(Color.BLUE));
    this.hasSeededRandom.takeTurn(this.dogCard, 2,2);
    // blue player's new score now that card is flipped
    assertEquals(5,
            this.hasSeededRandom.currentScore(Color.BLUE));
    // red player's new score now that card is flipped
    assertEquals(5,
            this.hasSeededRandom.currentScore(Color.RED));
    this.hasSeededRandom.takeTurn(this.roosterCard, 2,0);
    this.hasSeededRandom.takeTurn(this.monkeyCard, 1,0);
    assertEquals(3, this.hasSeededRandom.currentScore(Color.RED));
    assertEquals(7, this.hasSeededRandom.currentScore(Color.BLUE));
  }

  // Testing AI player

  @Test
  public void testTakeTurn() {
    // Run the AI's turn
    testAIPlayer.addToHand(this.rabbitCard);
    testAIPlayer.addToHand(this.oxCard);
    // testAIPlayer.takeTurn();
    assertEquals(2, testAIPlayer.getCardsInHand().size());
    // assertEquals(testAIPlayer, this.hasSeededRandom.getOwnerAtCell(0, 0));
  }

  @Test
  public void testAddToHand() {
    testAIPlayer.addToHand(rabbitCard);
    assertEquals(1, testAIPlayer.getCardsInHand().size());
    assertTrue(testAIPlayer.getCardsInHand().contains(rabbitCard));
  }

  @Test
  public void testRemoveFromHand() {
    testAIPlayer.removeFromHand(rabbitCard);
    assertEquals(0, testAIPlayer.getCardsInHand().size());
    assertFalse(testAIPlayer.getCardsInHand().contains(rabbitCard));
  }

  @Test
  public void testAddToOwnership() {
    testAIPlayer.addToOwnership(oxCard);
    assertEquals(1, testAIPlayer.getOwnedCardsOnGrid().size());
    assertTrue(testAIPlayer.getOwnedCardsOnGrid().contains(oxCard));
  }

  @Test
  public void testRemoveFromOwnership() {
    testAIPlayer.addToOwnership(oxCard);
    testAIPlayer.removeFromOwnership(oxCard);

    assertEquals(0, testAIPlayer.getOwnedCardsOnGrid().size());
    assertFalse(testAIPlayer.getOwnedCardsOnGrid().contains(oxCard));
  }

  @Test
  public void testGetNumberCardsOwned() {
    testAIPlayer.addToOwnership(oxCard);
    assertEquals(1, testAIPlayer.getNumberCardsOwned());
  }
}