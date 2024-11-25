package cs3500.threetrios.model.player;

import java.util.ArrayList;
import java.util.List;

import cs3500.threetrios.model.Card;
import cs3500.threetrios.model.Color;
import cs3500.threetrios.model.Model;
import cs3500.threetrios.view.PlayerAction;

/**
 * Represents a human player, aka a player of the game Three Trios.
 */
public class HumanPlayer implements Player {
  private final List<Card> cardsInHand; // represents the cards in the hand
  private final List<Card> ownedCardsOnGrid; // represents the cards that are owned and on the grid
  private final Color color; // represents this player's color

  /**
   * Constructor for the HumanPlayer class.
   * @param cardsInHand represents the list of playable cards that are in the player's hand
   * @param color represents the color of this player
   */
  public HumanPlayer(List<Card> cardsInHand, Color color) {
    if (cardsInHand == null) {
      throw new IllegalArgumentException("Cards cannot be null");
    }
    if (color == null) {
      throw new IllegalArgumentException("Color cannot be null");
    }
    this.color = color;
    this.cardsInHand = cardsInHand;
    this.ownedCardsOnGrid = new ArrayList<>();
  }

  /**
   * Constructor that takes in a model to be played with and color representing this player.
   * @param model represents the model to be played with
   * @param color represents the color of this player
   */
  public HumanPlayer(Model model, Color color) {
    this.color = color;
    this.cardsInHand = new ArrayList<>();
    this.ownedCardsOnGrid = new ArrayList<>();
  }

  @Override
  public void removeFromHand(Card card) {
    if (!cardsInHand.contains(card)) {
      throw new IllegalArgumentException("The card is not in the player's hand.");
    }
    cardsInHand.remove(card);
  }


  @Override
  public void removeFromOwnership(Card card) {
    if (!ownedCardsOnGrid.contains(card)) {
      throw new IllegalArgumentException("The card is not in the player's grid yet.");
    }
    this.ownedCardsOnGrid.remove(card);
  }

  @Override
  public void addToOwnership(Card cards) {
    this.ownedCardsOnGrid.add(cards);
  }

  @Override
  public List<Card> getOwnedCardsOnGrid() {
    return new ArrayList<>(this.ownedCardsOnGrid);
  }

  @Override
  public List<Card> getCardsInHand() {
    return new ArrayList<>(this.cardsInHand);
  }

  @Override
  public void addToHand(Card card) {
    this.cardsInHand.add(card);
  }

  @Override
  public int getNumberCardsOwned() {
    return this.ownedCardsOnGrid.size() + this.cardsInHand.size();
  }

  @Override
  public Color getColor() {
    return this.color;
  }

  @Override
  public String toString() {
    return "Player: " + color;
  }

  @Override
  public void addListener(PlayerAction listener) {
    return;
  }

  @Override
  public void takeTurn() {
    return;
  }
}
