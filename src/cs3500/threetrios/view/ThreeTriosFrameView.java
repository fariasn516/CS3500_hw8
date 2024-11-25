package cs3500.threetrios.view;

/**
 * Represents the frame of the gui graphics, this is where all the actual graphics of the game will
 * be put onto.
 */
public interface ThreeTriosFrameView {
  /**
   * Refresh the view to reflect any changes in the game state.
   */
  void refresh();

  /**
   * Helps with handling of clicking on GUI.
   * @param listener represents the listener to be added
   */
  void addClickListener(PlayerAction listener);

  /**
   * Make the view visible to start the game session.
   */
  void makeVisible();

  /**
   * Shows a dialogue message given the String message.
   * @param message represents the message to be printed out
   */
  void showMessage(String message);

  /**
   * Determines whether the game over message is shown.
   * @return boolean that represents whether the game over message should be/has been shown
   */
  boolean isGameOverMessageShown();

  /**
   * Sets the boolean value to given boolean.
   * @param shown boolean value to which GameOver message value will be changed to.
   */
  void setGameOverMessageShown(boolean shown);
}
