package cs3500.threetrios.adapter;

import java.util.List;

import cs3500.threetrios.provider.hw5.CardColor;
import cs3500.threetrios.provider.hw5.ICard;
import cs3500.threetrios.provider.hw5.IPlayer;

public class HumanPlayerAdapter implements IPlayer {
  @Override
  public String getName() {
    return "";
  }

  @Override
  public void setHand(List<ICard> cards) throws IllegalArgumentException {

  }

  @Override
  public List<ICard> getHand() {
    return List.of();
  }

  @Override
  public ICard chooseCard(int cardIndex) throws IllegalStateException {
    return null;
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
    return 0;
  }

  @Override
  public CardColor getColor() {
    return null;
  }

  @Override
  public void addCard(ICard card) {

  }

  @Override
  public void removeCard(int cardIndex) throws IllegalArgumentException {

  }
}
