package cs3500.threetrios.view;


import cs3500.threetrios.model.hw5.IPlayer;

/**
 * Interface representing the overall game view.
 */
public interface IGameView {


  /**
   * Updates the display to reflect the current game state.
   */
  void refresh();

  /**
   * Highlights the currently selected card or cell based on user interaction.
   *
   * @param x the x-coordinate of the selected cell (or -1 if no cell is selected).
   * @param y the y-coordinate of the selected cell (or -1 if no cell is selected).
   */
  void highlightSelection(int x, int y);

  /**
   * Registers a click handler for the overall view.
   *
   * @param handler the handler that will manage click events on the view.
   */
  void addClickHandler(ViewClickHandler handler);

  /**
   * Sets the current player label based on whose turn it is.
   *
   * @param playerName the name of the current player.
   */
  void setCurrentPlayer(String playerName);

  void showErrorMessage(String message);

  IPlayer getCurrentTurnPlayer();


  ViewFeature getViewFeature();

  void showEndMessage(String message);
}
