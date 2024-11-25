package cs3500.threetrios.strategy;

import cs3500.threetrios.model.Model;

/**
 * Abstract class that implements game strategy and used so that code duplication is reduced.
 */
public abstract class AbstractStrat implements GameStrategy {
  @Override
  public abstract GameMove chooseMove(Model model);

  @Override
  public GameMove choosePassMove(Model model) {
    for (int row = 0; row < model.getGrid().getNumRows(); row++) {
      for (int col = 0; col < model.getGrid().getNumCols(); col++) {
        if (model.isLegalToPlay(row, col)) {
          // return the first available open spot with the first card in hand
          return new GameMove(model.getCurrentPlayer().getCardsInHand().get(0), row, col);
        }
      }
    }
    // if no moves are available, return null
    return null;
  }
}
