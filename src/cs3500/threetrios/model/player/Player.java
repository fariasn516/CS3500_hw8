package cs3500.threetrios.model.player;

import java.util.List;

import cs3500.threetrios.model.Card;
import cs3500.threetrios.model.Color;
import cs3500.threetrios.view.PlayerAction;

/**
 * Represents the player for the game Three Trios.
 */
public interface Player {
  /**
   * Takes the turn.
   */
  void takeTurn();

  /**
   * Removes the specified card from the player's hand.
   *
   * @param card the card to remove from the player's hand.
   * @throws IllegalArgumentException if the card is not in the player's hand.
   */
  void removeFromHand(Card card);

  /**
   * Removes the given card from the ownership of the player.
   */
  void removeFromOwnership(Card card);

  /**
   * Adds the given card to ownership.
   * @param cards represents the card that is going to be added to ownership
   */
  void addToOwnership(Card cards);

  /**
   * Adds the given card to this player's hand.
   * @param card represents the card to be added to this player's hand.
   */
  void addToHand(Card card);

  /**
   * Makes the listener of this Player equal to the given listener.
   */
  void addListener(PlayerAction listener);

  // Below are OBSERVATIONS
  /**
   * Returns the cards that are in the player's hand. NON-MUTABLE!!!
   * @return a new list of cards that represent the player's hand
   */
  List<Card> getCardsInHand();

  /**
   * Returns the number of cards that is owned by the player.
   * @return the number of cards owned by the player
   */
  int getNumberCardsOwned();

  /**
   * Returns the player's color.
   * @return the color of the player.
   */
  Color getColor();

  /**
   * Returns a non-mutable list of cards owned by the player on grid.
   * @return a list of cards representing the player's ownership on grid.
   */
  List<Card> getOwnedCardsOnGrid();
}
