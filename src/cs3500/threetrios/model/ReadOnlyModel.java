package cs3500.threetrios.model;

import java.util.List;

import cs3500.threetrios.model.player.Player;

/**
 * The read-only interface for our game model, contains all the observation methods.
 */
public interface ReadOnlyModel {

  /**
   * Returns the current state of the grid.
   * @return the current grid state
   */
  Grid getGrid();

  /**
   * Returns the current player.
   * @return the current player
   */
  Player getCurrentPlayer();

  /**
   * Checks if the game has been started and returns as such.
   * @return if the game has started
   */
  boolean hasStarted();

  /**
   * Returns a specific card's owner's color.
   * @return owner's color
   */
  Color getCardOwnerColor(Card card);

  /**
   * Checks whether the game is over by finding if all card cells are filled.
   * @return true if the game is over
   * @throws IllegalStateException if the game has not started.
   */
  boolean isGameOver();

  /**
   * Returns the winner of the game based on the number of cards each player owns on the grid.
   * @return a string representing the winner: "Blue Player" or "Red Player".
   * @throws IllegalStateException if the game has not started or is not yet over.
   */
  String winner();

  /**
   * Returns the list of cards in the blue player's hand.
   * @return the list of cards in the blue player's hand
   * @throws IllegalStateException if the game has not started
   */
  List<Card> getBluePlayer();

  /**
   * Returns the list of cards in the red player's hand.
   * @return the list of cards in the red player's hand
   * @throws IllegalStateException if the game has not started
   */
  List<Card> getRedPlayer();

  /**
   * Returns the player who owns the card at the specified grid cell.
   *
   * @param row the row of the grid cell
   * @param col the column of the grid cell
   * @return the player who owns the card in the specified cell
   * @throws IllegalArgumentException if the specified cell is empty (hole or no card placed)
   * @throws IllegalStateException if the game has not started yet
   */
  Player getOwnerAtCell(int row, int col);

  /**
   * Determines if the current player can legally play a card at the specified cell.
   *
   * @param row the row of the grid cell to check
   * @param col the column of the grid cell to check
   * @return true if the current player can legally play at the specified cell, false otherwise
   * @throws IllegalArgumentException if the specified coordinates are out of bounds
   * @throws IllegalStateException if the game has not started yet
   */
  boolean isLegalToPlay(int row, int col);

  /**
   * Calculates how many cards a player can flip by playing the specified card at the given cell.
   *
   * @param card the card to be played
   * @param row the row of the grid cell where the card is being played
   * @param col the column of the grid cell where the card is being played
   * @return the number of cards that will be flipped if the card is played at the specified cell
   * @throws IllegalArgumentException if the specified cell is out of bounds
   * @throws IllegalArgumentException if the specified cell is a hole (i.e., cannot play here)
   * @throws IllegalArgumentException if the card is null
   * @throws IllegalArgumentException if the there is already a card on the specified row and col
   * @throws IllegalStateException if the game has not started yet
   */
  int howManyWillFlip(Card card, int row, int col);

  /**
   * Calculates the current score for the specified player.
   * The score is defined as the total number of cards the player owns in their hand plus
   * the number of cards they own on the grid.
   *
   * @param color the color of the player whose score is to be calculated
   * @return the total score of the specified player
   * @throws IllegalStateException if the game has not started yet
   * @throws IllegalArgumentException if color is null
   */
  int currentScore(Color color);
}