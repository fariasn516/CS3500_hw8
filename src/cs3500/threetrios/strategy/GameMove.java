package cs3500.threetrios.strategy;

import java.util.Objects;

import cs3500.threetrios.model.Card;

/**
 * Represents a move in the Three Trios game.
 */
public class GameMove {
  private final Card card;
  private final int row;
  private final int col;

  /**
   * Constructs a Move with a card and position.
   *
   * @param card the card being played
   * @param row  the row position
   * @param col  the column position
   */
  public GameMove(Card card, int row, int col) {
    this.card = card;
    this.row = row;
    this.col = col;
  }

  /**
   * Returns the card associated with this cell.
   *
   * @return The `Card` object placed in this cell, or null if no card is placed.
   */
  public Card getCard() {
    return card;
  }

  /**
   * Returns the row index of this cell in the grid.
   *
   * @return The row index of the cell.
   */
  public int getRow() {
    return row;
  }

  /**
   * Returns the column index of this cell in the grid.
   *
   * @return The column index of the cell.
   */
  public int getCol() {
    return col;
  }

  /**
   * Overrides equals to compare GameMove objects by card, row, and col.
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    GameMove gameMove = (GameMove) obj;
    return row == gameMove.row
            && col == gameMove.col
            && Objects.equals(card, gameMove.card);
  }

  /**
   * Overrides hashCode to produce a hash based on card, row, and col.
   */
  @Override
  public int hashCode() {
    return Objects.hash(card, row, col);
  }

}
