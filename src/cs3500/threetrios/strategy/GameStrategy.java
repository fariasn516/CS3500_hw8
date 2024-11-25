package cs3500.threetrios.strategy;

import cs3500.threetrios.model.Model;

/**
 * Represents a strategy for making moves in the Three Trios game.
 */
public interface GameStrategy {
  /**
   * Chooses the best move for the current player based on the current game state.
   *
   * @param model the current game model, which contains the state of the game,
   *              including the grid and players.
   * @return a GameMove representing the best move for the player, or null
   *         if no valid moves are possible.
   */
  GameMove chooseMove(Model model);

  /**
   * If no valid move is found during the turn, the strategy will select the uppermost-leftmost
   * open position on the grid using card at index 0 in the current player's hand.
   *
   * @param model The current game model, which contains information about the grid, players,
   *              and the game state.
   * @return A `GameMove` representing the best pass move for the current player.
   *         Returns a pass (uppermost-leftmost open position) when no valid moves are found.
   */
  GameMove choosePassMove(Model model);
}