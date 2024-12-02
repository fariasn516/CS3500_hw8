package cs3500.threetrios.adapter;

import java.util.List;

import cs3500.threetrios.model.Color;
import cs3500.threetrios.model.Model;
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
  public CardColor getColor2() {
    return null;
  }

  @Override
  public void addCard(ICard card) {

  }

  @Override
  public void removeCard(int cardIndex) throws IllegalArgumentException {

  }
}
