package cs3500.threetrios.adapter;

import java.util.ArrayList;
import java.util.List;

import cs3500.threetrios.model.Color;
import cs3500.threetrios.model.Model;
import cs3500.threetrios.model.ReadOnlyModel;
import cs3500.threetrios.model.player.HumanPlayer;
import cs3500.threetrios.model.player.Player;
import cs3500.threetrios.provider.hw5.CardColor;
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
    List<cs3500.threetrios.model.Card> cardList = new ArrayList<>();
    for (ICard card : cards) {
      this.addToHand(card);
    }
  }

  @Override
  public List<ICard> getHand() {
    List<ICard> icardList = new ArrayList<>();
    for (ICard card : this.getCardsInHand()) {
      icardList.add(card);
    }
    return icardList;
  }

  @Override
  public ICard chooseCard(int cardIndex) throws IllegalStateException {
    return this.getCardsInHand().get(cardIndex);
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
    this.getCardsInHand().add(card);
  }

  @Override
  public void removeCard(int cardIndex) throws IllegalArgumentException {
    if (cardIndex < 0 || cardIndex >= this.getCardsInHand().size()) {
      throw new IllegalArgumentException("Invalid card index.");
    }
    this.getCardsInHand().remove(cardIndex);
  }
}
