package cs3500.threetrios;

import java.util.ArrayList;
import java.util.List;

import cs3500.threetrios.model.Card;
import cs3500.threetrios.model.Color;
import cs3500.threetrios.model.Grid;
import cs3500.threetrios.model.Model;
import cs3500.threetrios.model.ModelStatus;
import cs3500.threetrios.model.ReadOnlyModel;
import cs3500.threetrios.model.player.Player;

/**
 * Mock model representation of three trios model.
 */
public class ThreeTriosMockModel implements Model, ReadOnlyModel, ModelStatus {
  // track method calls
  private boolean startGameCalled = false;
  private boolean placingPhaseCalled = false;
  private boolean battlingPhaseCalled = false;
  private boolean takeTurnCalled = false;
  private Card lastCard;
  private int lastRow;
  private int lastCol;

  @Override
  public <C extends Card> void startGame(List<C> cards, boolean shuffle, Grid grid) {
    startGameCalled = true;
  }

  @Override
  public void placingPhase(Card card, int row, int col) {
    placingPhaseCalled = true;
    lastCard = card;
    lastRow = row;
    lastCol = col;
  }

  @Override
  public void battlingPhase(int row, int col) {
    battlingPhaseCalled = true;
    lastRow = row;
    lastCol = col;
  }

  @Override
  public void takeTurn(Card card, int row, int col) {
    takeTurnCalled = true;
    lastCard = card;
    lastRow = row;
    lastCol = col;
  }

  @Override
  public void addListener(ModelStatus listener) {
    // left empty
  }

  @Override
  public void notifyStatus() {
    // left empty

  }

  @Override
  public Grid getGrid() {
    return null;
  }

  @Override
  public Player getCurrentPlayer() {
    return null;
  }

  @Override
  public boolean hasStarted() {
    return startGameCalled;
  }

  @Override
  public Color getCardOwnerColor(Card card) {
    return Color.RED;
  }

  @Override
  public boolean isGameOver() {
    return false;
  }

  @Override
  public String winner() {
    return "Red";
  }

  @Override
  public List<Card> getBluePlayer() {
    return new ArrayList<>();
  }

  @Override
  public List<Card> getRedPlayer() {
    return new ArrayList<>();
  }

  @Override
  public Player getOwnerAtCell(int row, int col) {
    return null;
  }

  @Override
  public boolean isLegalToPlay(int row, int col) {
    return !(row == 1 & col == 1);
  }

  @Override
  public int howManyWillFlip(Card card, int row, int col) {
    return 3;
  }

  @Override
  public int currentScore(Color color) {
    return 10;
  }

  public boolean isStartGameCalled() {
    return startGameCalled;
  }

  public boolean isPlacingPhaseCalled() {
    return placingPhaseCalled;
  }

  public boolean isBattlingPhaseCalled() {
    return battlingPhaseCalled;
  }

  public boolean isTakeTurnCalled() {
    return takeTurnCalled;
  }

  public Card getLastCard() {
    return lastCard;
  }

  public int getLastRow() {
    return lastRow;
  }

  public int getLastCol() {
    return lastCol;
  }
}