package cs3500.threetrios.model.player;

import java.util.ArrayList;
import java.util.List;

import cs3500.threetrios.model.Card;
import cs3500.threetrios.model.Color;
import cs3500.threetrios.model.Model;
import cs3500.threetrios.strategy.GameMove;
import cs3500.threetrios.strategy.GameStrategy;
import cs3500.threetrios.view.PlayerAction;

/**
 * This class represents an AI player for the game ThreeTrios, things are played based on
 * a given strategy.
 */
public class AIPlayer implements Player {
  private final List<Card> cardsInHand; // represents the cards in the hand
  private final List<Card> ownedCardsOnGrid; // represents the cards that are owned and on the grid
  private final Color color; // represents the color of this player
  private final Model model; // represents the passed-in model for the AI player
  private final GameStrategy strat; // represents the strategy(ies) to be used
  private PlayerAction features; // represents the observer that notifies the turn

  /**
   * Constructor for the AI player, takes in a model to be played with, a color representing the
   * AI's color, and a GameStrategy that determines this AI player's behavior when placing cards.
   * @param model represents the model to be played with
   * @param color represents the color this player has
   * @param strat represents the strategy this AI player is going to be using
   */
  public AIPlayer(Model model, Color color, GameStrategy strat) {
    this.model = model;
    this.color = color;
    this.strat = strat;
    this.cardsInHand = new ArrayList<>();
    this.ownedCardsOnGrid = new ArrayList<>();
  }

  @Override
  public void takeTurn() {
    GameMove move = this.strat.chooseMove(this.model);
    try {
      this.features.onCardSelected(move.getCard());
      this.features.placeCard(move.getRow(), move.getCol());
    }
    catch (IllegalArgumentException | IllegalStateException e) {
      throw new IllegalStateException("Something went wrong with the AI");
    }
  }


  @Override
  public void removeFromHand(Card card) {
    this.cardsInHand.remove(card);
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
  public List<Card> getCardsInHand() {
    return new ArrayList<>(this.cardsInHand);
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
  public List<Card> getOwnedCardsOnGrid() {
    return new ArrayList<>(this.ownedCardsOnGrid);
  }

  @Override
  public void addToHand(Card card) {
    this.cardsInHand.add(card);
  }

  @Override
  public void addListener(PlayerAction listener) {
    this.features = listener;
  }
}
