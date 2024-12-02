package cs3500.threetrios.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cs3500.threetrios.model.Card;
import cs3500.threetrios.model.Color;
import cs3500.threetrios.model.ReadOnlyModel;
import cs3500.threetrios.model.SimpleCard;
import cs3500.threetrios.model.Value;
import cs3500.threetrios.model.player.HumanPlayer;
import cs3500.threetrios.provider.hw5.CardColor;
import cs3500.threetrios.provider.hw5.Direction;
import cs3500.threetrios.provider.hw5.ICard;
import cs3500.threetrios.provider.hw5.IPlayer;

public class HumanPlayerAdapter extends HumanPlayer implements IPlayer {

  public HumanPlayerAdapter(ReadOnlyModel model, Color color) {
    super(model, color);
  }

  @Override
  public String getName() {
    return this.getColor().toString();
  }

  @Override
  public void setHand(List<ICard> cards) throws IllegalArgumentException {
    List<Card> cardList = new ArrayList<>();
    for (ICard card : cards) {
      this.addToHand(generateCardFromICard(card));
    }
  }

  @Override
  public List<ICard> getHand() {
    List<ICard> icardList = new ArrayList<>();
    for (Card card : this.getCardsInHand()) {
      Map<Direction, Integer> values = createICardMap(card);
      ICard currCard = new CardAdapter(card.getName(), this.getColor2(), values);
      icardList.add(currCard);
    }
    return icardList;
  }

  /**
   *
   * @param card
   * @return
   */
  private Map<Direction, Integer> createICardMap(Card card) {
    Map<Direction, Integer> values = new HashMap<>();
    values.put(Direction.NORTH, card.getValueFromDirection(cs3500.threetrios.model.Direction.NORTH));
    values.put(Direction.SOUTH, card.getValueFromDirection(cs3500.threetrios.model.Direction.SOUTH));
    values.put(Direction.EAST, card.getValueFromDirection(cs3500.threetrios.model.Direction.EAST));
    values.put(Direction.WEST, card.getValueFromDirection(cs3500.threetrios.model.Direction.WEST));
    return values;
  }

  @Override
  public ICard chooseCard(int cardIndex) throws IllegalStateException {
    Card card = this.getCardsInHand().get(cardIndex);
    Map<Direction, Integer> values = createICardMap(card);
    return new CardAdapter(card.getName(), this.getColor2(), values);
  }

  @Override
  public int[] choosePosition(List<int[]> availablePositions) throws IllegalArgumentException, IllegalStateException {
    if (availablePositions.isEmpty()) {
      throw new IllegalArgumentException("No available positions.");
    }
    return availablePositions.get(0);
  }

  @Override
  public void updateScore(int score) throws IllegalArgumentException {
    if (score < 0) {
      throw new IllegalArgumentException("Score cannot be negative.");
    }
    // implement
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

  /**
   *
   * @param card
   * @return
   */
  private Card generateCardFromICard(ICard card) {
    Map<cs3500.threetrios.model.Direction, Value> values = new HashMap<>();
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
  public void removeCard(int cardIndex) throws IllegalArgumentException {
    if (cardIndex < 0 || cardIndex >= this.getCardsInHand().size()) {
      throw new IllegalArgumentException("Invalid card index.");
    }
    this.getCardsInHand().remove(cardIndex);
  }
}
