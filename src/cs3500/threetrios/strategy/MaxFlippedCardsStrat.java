package cs3500.threetrios.strategy;

import cs3500.threetrios.model.Card;
import cs3500.threetrios.model.Model;
import cs3500.threetrios.model.player.Player;

/**
 * A strategy that prioritizes flipping the maximum number of opponent cards.
 */
public class MaxFlippedCardsStrat extends AbstractStrat {
  @Override
  public GameMove chooseMove(Model model) {
    GameMove bestMove = choosePassMove(model);
    int maxFlips = 0;
    Player currentPlayer = model.getCurrentPlayer();

    // iterate through all moves
    for (int row = 0; row < model.getGrid().getNumRows(); row++) {
      for (int col = 0; col < model.getGrid().getNumCols(); col++) {
        // check first if move is legal
        if (model.isLegalToPlay(row, col)) {
          // find the best one for this position
          for (Card cardToPlay : currentPlayer.getCardsInHand()) {
            // how many opponent cards can be flipped for this move
            int flips = model.howManyWillFlip(cardToPlay, row, col);

            // update bestMove if we found a move that flips more cards
            if (flips > maxFlips) {
              maxFlips = flips;
              bestMove = new GameMove(cardToPlay, row, col);
            }
            else if (flips == maxFlips) {
              // if a tie, choose the move with the uppermost-leftmost coordinate
              if (row < bestMove.getRow()
                      || (row == bestMove.getRow() && col < bestMove.getCol())) {
                bestMove = new GameMove(cardToPlay, row, col);
              }
            }
          }
        }
      }
    }
    return bestMove;
  }
}