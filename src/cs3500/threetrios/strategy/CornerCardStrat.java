package cs3500.threetrios.strategy;

import java.util.ArrayList;
import java.util.List;

import cs3500.threetrios.model.Card;
import cs3500.threetrios.model.Direction;
import cs3500.threetrios.model.Model;
import cs3500.threetrios.model.player.Player;

/**
 * A strategy that targets corner positions for card placement.
 */
public class CornerCardStrat extends AbstractStrat {
  @Override
  public GameMove chooseMove(Model model) {
    GameMove bestMove = choosePassMove(model);
    int bestSum = 0;
    Player currentPlayer = model.getCurrentPlayer();

    // corner positions
    List<int[]> corners = List.of(
            new int[]{0, 0},
            new int[]{0, model.getGrid().getNumCols() - 1},
            new int[]{model.getGrid().getNumRows() - 1, 0},
            new int[]{model.getGrid().getNumRows() - 1, model.getGrid().getNumCols() - 1}
    );

    // iterate through each corner
    for (int[] corner : corners) {
      int row = corner[0];
      int col = corner[1];

      // first check if move is legal
      if (model.isLegalToPlay(row, col)) {
        // find the best card for this corner
        for (Card cardToPlay : currentPlayer.getCardsInHand()) {
          // get the exposed directions for this card if placed in the corner
          List<Direction> exposedDirections = getExposedDirections(model, row, col);
          int sum = 0;

          // get sum of the highest two exposed values
          for (Direction direction : exposedDirections) {
            sum += cardToPlay.getValueFromDirection(direction);
          }

          // if this card has a higher sum, update the best move
          if (sum > bestSum) {
            bestSum = sum;
            bestMove = new GameMove(cardToPlay, row, col);
          }
          // if there's a tie in sum, choose the card that comes first in the hand
          else if (sum == bestSum) {
            int bestMoveIndex = currentPlayer.getCardsInHand().indexOf(bestMove.getCard());
            int currentCardIndex = currentPlayer.getCardsInHand().indexOf(cardToPlay);
            // this will never be true therefore bestMove will stay as previously set
            if (currentCardIndex < bestMoveIndex) {
              bestMove = new GameMove(cardToPlay, row, col);
            }
          }
        }
      }
    }

    return bestMove;
  }

  // helper method to get exposed directions for the corner placement
  private List<Direction> getExposedDirections(Model model, int row, int col) {
    List<Direction> exposedDirections = new ArrayList<>();

    // depending on the corner, output which directions will be shown
    if (row == 0 && col == 0) {
      exposedDirections.add(Direction.EAST);
      exposedDirections.add(Direction.SOUTH);
    } else if (row == 0 && col == model.getGrid().getNumCols() - 1) {
      exposedDirections.add(Direction.WEST);
      exposedDirections.add(Direction.SOUTH);
    } else if (row == model.getGrid().getNumRows() - 1 && col == 0) {
      exposedDirections.add(Direction.EAST);
      exposedDirections.add(Direction.NORTH);
    } else if (row == model.getGrid().getNumRows() - 1 && col == model.getGrid().getNumCols() - 1) {
      exposedDirections.add(Direction.WEST);
      exposedDirections.add(Direction.NORTH);
    }
    return exposedDirections;
  }
}