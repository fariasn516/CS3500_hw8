package cs3500.threetrios.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cs3500.threetrios.model.Card;
import cs3500.threetrios.model.Color;
import cs3500.threetrios.model.Direction;
import cs3500.threetrios.model.Model;
import cs3500.threetrios.model.SimpleCard;
import cs3500.threetrios.model.Value;
import cs3500.threetrios.model.player.AIPlayer;
import cs3500.threetrios.provider.hw5.CardColor;
import cs3500.threetrios.provider.hw5.ICard;
import cs3500.threetrios.provider.hw5.IPlayer;
import cs3500.threetrios.strategy.GameStrategy;

public class AIPlayerAdapter extends AIPlayer implements IPlayer {
  /**
   * Constructor for the AI player, takes in a model to be played with, a color representing the
   * AI's color, and a GameStrategy that determines this AI player's behavior when placing cards.
   *
   * @param model represents the model to be played with
   * @param color represents the color this player has
   * @param strat represents the strategy this AI player is going to be using
   */
  public AIPlayerAdapter(Model model, Color color, GameStrategy strat) {
    super(model, color, strat);
  }

  @Override
  public String getName() {
    return this.getColor().toString();
  }

  @Override
  public void setHand(List<ICard> cards) throws IllegalArgumentException {
    for (ICard card : cards) {
        this.addToHand(generateCardFromICard(card));
    }
  }
  private Card generateCardFromICard(ICard card) {
    Map<Direction, Value> values = new HashMap<>();
    for (Value val : Value.values()) {
      if (card.getNorthValue() == val.getValue()) {
        values.put(cs3500.threetrios.model.Direction.NORTH, val);
      }
      if (card.getSouthValue() == val.getValue()) {
        values.put(cs3500.threetrios.model.Direction.SOUTH, val);
      }
      if (card.getEastValue() == val.getValue()) {
        values.put(cs3500.threetrios.model.Direction.EAST, val);
      }
      if (card.getWestValue() == val.getValue()) {
        values.put(cs3500.threetrios.model.Direction.WEST, val);
      }
    }
    return new SimpleCard("", values);
  }

  @Override
  public List<ICard> getHand() {
    List<ICard> icardList = new ArrayList<>();
    for (Card card : this.getCardsInHand()) {
      Map<cs3500.threetrios.provider.hw5.Direction, Integer> values = createICardMap(card);
      ICard currCard = new CardAdapter(card.getName(), this.getColor2(), values);
      icardList.add(currCard);
    }
    return icardList;
  }

  private Map<cs3500.threetrios.provider.hw5.Direction, Integer> createICardMap(Card card) {
    Map<cs3500.threetrios.provider.hw5.Direction, Integer> values = new HashMap<>();
    values.put(cs3500.threetrios.provider.hw5.Direction.NORTH, card.getValueFromDirection(cs3500.threetrios.model.Direction.NORTH));
    values.put(cs3500.threetrios.provider.hw5.Direction.SOUTH, card.getValueFromDirection(cs3500.threetrios.model.Direction.SOUTH));
    values.put(cs3500.threetrios.provider.hw5.Direction.EAST, card.getValueFromDirection(cs3500.threetrios.model.Direction.EAST));
    values.put(cs3500.threetrios.provider.hw5.Direction.WEST, card.getValueFromDirection(cs3500.threetrios.model.Direction.WEST));
    return values;
  }

  @Override
  public ICard chooseCard(int cardIndex) throws IllegalStateException {
    Card card = this.getCardsInHand().get(cardIndex);
    Map<cs3500.threetrios.provider.hw5.Direction, Integer> values = createICardMap(card);
    return new CardAdapter(card.getName(), this.getColor2(), values);
  }

  @Override
  public int[] choosePosition(List<int[]> availablePositions) throws IllegalArgumentException, IllegalStateException {
    return new int[0];
  }

  @Override
  public void updateScore(int score) throws IllegalArgumentException {

  }

  @Override
  public int getScore() {
    return this.getNumberCardsOwned();
  }

  @Override
  public CardColor getColor2() {
    return this.getColor() == Color.RED ? CardColor.RED : CardColor.BLUE;
  }

  @Override
  public void addCard(ICard card) {
    this.getCardsInHand().add(generateCardFromICard(card));
  }

  @Override
  public void removeCard(int cardIndex) throws IllegalArgumentException {
    if (cardIndex < 0 || cardIndex >= this.getCardsInHand().size()) {
      throw new IllegalArgumentException("Invalid card index.");
    }
    this.getCardsInHand().remove(cardIndex);
  }
}
