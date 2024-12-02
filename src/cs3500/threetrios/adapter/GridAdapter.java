package cs3500.threetrios.adapter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cs3500.threetrios.model.Card;
import cs3500.threetrios.model.Cell;
import cs3500.threetrios.model.GameGrid;
import cs3500.threetrios.provider.hw5.Direction;
import cs3500.threetrios.provider.hw5.ICard;
import cs3500.threetrios.provider.hw5.IGrid;

/**
 *
 */
public class GridAdapter extends GameGrid implements IGrid {
  int row;
  int col;

  public GridAdapter(int numRows, int numCols, boolean[][] holeLayout) {
    super(numRows, numCols, holeLayout);
  }

  public GridAdapter(int numRows, int numCols, Cell[][] grid) {
    super(numRows, numCols, grid);
  }

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
    Cell[][] cells = this.getCells();
    ICard[][] boardState = new ICard[cells.length][cells[0].length];
    for (int row = 0; row < cells.length; row++) {
      for (int col = 0; col < cells[row].length; col++) {
        if (cells[row][col].hasCard()) {
          boardState[row][col] = createICard(cells[row][col].getCard());
        }
      }
    }
    return boardState;
  }

  private ICard createICard(Card card) {
    Map<Direction, Integer> values = new HashMap<>();
    values.put(Direction.NORTH, card.getValueFromDirection(cs3500.threetrios.model.Direction.NORTH));
    values.put(Direction.SOUTH, card.getValueFromDirection(cs3500.threetrios.model.Direction.SOUTH));
    values.put(Direction.EAST, card.getValueFromDirection(cs3500.threetrios.model.Direction.EAST));
    values.put(Direction.WEST, card.getValueFromDirection(cs3500.threetrios.model.Direction.WEST));
    return new CardAdapter(card.getName(), null, values);
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
