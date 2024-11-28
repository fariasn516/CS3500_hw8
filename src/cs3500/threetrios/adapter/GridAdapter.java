package cs3500.threetrios.adapter;

import java.util.List;

import cs3500.threetrios.provider.hw5.ICard;
import cs3500.threetrios.provider.hw5.IGrid;

/**
 *
 */
public class GridAdapter implements IGrid {
  int row;
  int col;

  @Override
  public void initialize(int[][] configuration) throws IllegalArgumentException {

  }

  @Override
  public boolean placeCard(ICard card, int x, int y) throws IllegalArgumentException, IllegalStateException {
    return false;
  }

  @Override
  public boolean isValidPosition(int x, int y) {
    return false;
  }

  @Override
  public ICard[] getAdjacentCards(int x, int y) throws IllegalArgumentException {
    return new ICard[0];
  }

  @Override
  public List<int[]> getAvailablePositions() {
    return List.of();
  }

  @Override
  public ICard getCardPosition(int x, int y) {
    return null;
  }

  @Override
  public int[] getDimensions() {
    return new int[0];
  }

  @Override
  public String display() {
    return "";
  }

  @Override
  public int[][] getBoard() {
    return new int[0][];
  }

  @Override
  public ICard[][] getBoardState() {
    return new ICard[0][];
  }

  @Override
  public int getCol() {
    return this.col;
  }

  @Override
  public int getRow() {
    return this.row;
  }

  @Override
  public int getFlippedCardsCount() {
    return 0;
  }
}
