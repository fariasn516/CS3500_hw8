package cs3500.threetrios.model;

/**
 * Represents the cards that is going to be played with in the game.
 */
public interface Card {

  /**
   * Compares the values of the two cards given a Direction.
   * (Note: if the direction given is NORTH, then it will
   * check for the value of SOUTH for the card other)
   * @param card represents the card that this card's value is being compared to
   * @param direction represents the value that you want from this card
   * @return 0, if the values are equal, -1, if this value is smaller than the other value,
   *         1 if this value is larger than the other value
   */
  int compareTo(Card card, Direction direction);

  // Below are OBSERVATIONS
  /**
   * Returns the Value at the given Direction.
   * @param direction represents the direction that you want the value from
   * @return the Value at the given Direction in integer form
   */
  int getValueFromDirection(Direction direction);

  /**
   * Returns the Value at the given Direction.
   * @param direction represents the direction that you want the value from
   * @return the Value in String form from the given Direction
   */
  String getValueGivenDirection(Direction direction);

  /**
   * Finds and returns the name of this card.
   * @return a String that represents the name of this card
   */
  String getName();

  /**
   * Creates and returns a copy of this card.
   * @return a new Card object that is a copy of this card.
   */
  Card copy();
}
