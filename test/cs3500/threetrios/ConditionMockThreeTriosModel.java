package cs3500.threetrios;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import cs3500.threetrios.model.Card;
import cs3500.threetrios.model.Color;
import cs3500.threetrios.model.GameGrid;
import cs3500.threetrios.model.Grid;
import cs3500.threetrios.model.ModelStatus;
import cs3500.threetrios.model.player.HumanPlayer;
import cs3500.threetrios.model.Model;
import cs3500.threetrios.model.player.Player;
import cs3500.threetrios.model.ReadOnlyModel;

/**
 * A mock model to test the necessary components for our strategies.
 */
public class ConditionMockThreeTriosModel implements Model, ReadOnlyModel {
  // represents the legal moves on the grid
  private final Map<Coord, Boolean> legalMoves = new HashMap<>();
  // represents how many flips at each place on the grid
  private final Map<Coord, Integer> flipCounts = new HashMap<>();
  // represents the grid
  protected Grid grid;
  // represents the current player
  private final Player currentPlayer;

  /**
   * Constructor for the mock model.
   */
  public ConditionMockThreeTriosModel() {
    boolean[][] holeLayout = {
            {false, true},
            {false, false}
    };

    this.grid = new GameGrid(2, 2, holeLayout);
    this.currentPlayer = new HumanPlayer(new ArrayList<>(), Color.RED);
  }

  /**
   * Helps to put whether a move is legal at the given coordinates.
   * @param coord represents the coordinates
   * @param isLegal represents whether the move is legal
   */
  public void setLegalMove(Coord coord, boolean isLegal) {
    legalMoves.put(coord, isLegal);
  }

  /**
   * Helps to count the number of flips at a coordinate.
   * @param coord represents the coordinates.
   * @param count represents the number of flips.
   */
  public void setFlipCount(Coord coord, int count) {
    flipCounts.put(coord, count);
  }

  /**
   * Adds a card to the hand of a player.
   * @param card represents the card to be added to the hand of the player
   */
  public void addCardToPlayer(Card card) {
    this.currentPlayer.addToHand(card);
  }

  @Override
  public boolean isLegalToPlay(int row, int col) {
    return legalMoves.get(new Coord(row, col));
  }

  @Override
  public int howManyWillFlip(Card card, int row, int col) {
    return flipCounts.get(new Coord(row, col));
  }

  @Override
  public <C extends Card> void startGame(List<C> cards, boolean shuffle, Grid grid) {
    // not meant to do anything, just stuff from interface(s)
  }

  @Override
  public void placingPhase(Card card, int row, int col) {
    // not meant to do anything, just stuff from interface(s)
  }

  @Override
  public void battlingPhase(int row, int col) {
    // not meant to do anything, just stuff from interface(s)
  }

  @Override
  public void takeTurn(Card card, int row, int col) {
    // not meant to do anything, just stuff from interface(s)
  }

  @Override
  public void addListener(ModelStatus listener) {
    // not meant to do anything
  }

  @Override
  public boolean isGameOver() {
    return false;
  }

  @Override
  public String winner() {
    return "None";
  }

  @Override
  public Grid getGrid() {
    return this.grid;
  }

  @Override
  public Player getCurrentPlayer() {
    return this.currentPlayer;
  }

  @Override
  public boolean hasStarted() {
    return true;
  }

  @Override
  public Color getCardOwnerColor(Card card) {
    return null;
  }

  @Override
  public Player getOwnerAtCell(int row, int col) {
    return null;
  }

  @Override
  public int currentScore(Color color) {
    return 0;
  }

  @Override
  public List<Card> getBluePlayer() {
    return null;
  }

  @Override
  public List<Card> getRedPlayer() {
    return null;
  }
}
