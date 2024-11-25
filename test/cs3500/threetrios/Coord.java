package cs3500.threetrios;

import java.util.Objects;

/**
 * Represents the coordinates on a grid, going by row-column and starting with 0 indexing.
 */
public class Coord {
  private final int row; // represents the row
  private final int col; // represents the column

  /**
   * Constructor for the Coord. Takes in a row and column.
   * @param row represents the row, starting from 0
   * @param col represents the column, starting from 0
   */
  public Coord(int row, int col) {
    this.row = row;
    this.col = col;
  }

  /**
   * Returns the row.
   * @return returns the row index
   */
  public int getRow() {
    return row;
  }

  /**
   * Returns the column.
   * @return returns the col index.
   */
  public int getCol() {
    return col;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Coord coord = (Coord) o;
    return row == coord.row && col == coord.col;
  }

  @Override
  public int hashCode() {
    return Objects.hash(row, col);
  }
}
