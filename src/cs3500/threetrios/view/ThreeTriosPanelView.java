package cs3500.threetrios.view;

/**
 * Interface for the panels; the panels are how the state of the model are to be represented in
 * GUI form.
 */
public interface ThreeTriosPanelView {

  /**
   * Listener that handles the clicking which then feeds back to controller so that it can
   * call the correct model rule handling.
   */
  void addClickListener(PlayerAction listener);

  /**
   * Returns the name at the top of the GUI graphics, indicating which player's turn it is.
   */
  String getName();
}
