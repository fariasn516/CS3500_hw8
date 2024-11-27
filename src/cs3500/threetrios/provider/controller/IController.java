package cs3500.threetrios.provider.controller;

import cs3500.threetrios.provider.hw5.IGrid;
import cs3500.threetrios.provider.hw5.IModel;
import cs3500.threetrios.provider.hw5.IModelFeature;
import cs3500.threetrios.provider.hw5.IPlayer;

/**
 * The IController interface defines the operations required for controlling
 * the game flow and interacting with the model and view components.
 */
public interface IController {

  /**
   * Starts the game using the provided card and board paths, model, and grid.
   *
   * @param cardPath Path to the file containing card data
   * @param boardPath Path to the file containing the board configuration
   * @param model The game model to be used for the game
   * @param grid The grid representation of the game board
   */
  void playGame(String cardPath, String boardPath, IModel model, IGrid grid);

  /**
   * Updates the controller in response to changes in the model state.
   * This method should be called whenever the model's state changes.
   */
  void update();

  /**
   * Handles player switching when it's the next player's turn.
   *
   * @param player The player to switch to
   */
  void switchPlayer(IPlayer player);

  /**
   * Handles card selection from the current player's hand.
   *
   * @param handIndex The index of the card selected from the player's hand
   */
  void selectCard(int handIndex);

  /**
   * Handles the selection of a specific cell on the game grid.
   *
   * @param row The row index of the selected cell
   * @param col The column index of the selected cell
   */
  void selectGridCell(int row, int col);

  /**
   * Checks if the current player is allowed to perform an action.
   *
   * @return {@code true} if it's the current player's turn, {@code false} otherwise
   */
  boolean isCurrentPlayer();

  /**
   * Retrieves the player whose turn it currently is.
   *
   * @return The current player
   */
  IPlayer getPlayer();

  /**
   * Retrieves the feature-rich model for accessing model-related operations.
   *
   * @return The interface for interacting with the model
   */
  IModelFeature getModelFeature();

  /**
   * Displays a message to indicate the end of the game.
   *
   * @param message The message to be displayed
   */
  void displayEndMessage(String message);
}
