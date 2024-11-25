package cs3500.threetrios.view;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import cs3500.threetrios.model.Color;
import cs3500.threetrios.model.ReadOnlyModel;

/**
 * The frame of the Three Trios game. This is the general frame and graphics will be placed on it
 * through the panel.
 */
public class ThreeTriosModelView extends JFrame implements ThreeTriosFrameView {
  private final ThreeTriosPanel panel; // represents the actual images to be placed on the frame
  private final ReadOnlyModel model; // represents the model that this view is printing
  private final Color color; // represents color of this frame
  private boolean gameOverMessageShown = false; // represents if the game over message is shown

  /**
   * Constructor for the ThreeTriosModelView. Takes in a model whose game state will be represented
   * through the graphics.
   * @param model represents the model whose game state is to be represented through the GUI
   * @param color represents the color of this frame
   */
  public ThreeTriosModelView(ReadOnlyModel model, Color color) {
    this.panel = new ThreeTriosPanel(model, color.toString());
    this.setSize(1000, 1000);
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.add(panel);
    this.model = model;
    this.color = color;
  }

  @Override
  public void refresh() {
    this.setTitle(color.toString() + " " + panel.getName());
    this.repaint();
  }

  @Override
  public void addClickListener(PlayerAction listener) {
    panel.addClickListener(listener);
  }

  @Override
  public void makeVisible() {
    this.setVisible(true);
    this.setTitle(color.toString() + " " + panel.getName());
  }

  @Override
  public void showMessage(String message) {
    if (this.model.isGameOver()) {
      JOptionPane.showMessageDialog(
              this, message, "Game Over!", JOptionPane.PLAIN_MESSAGE);
    }
    else {
      JOptionPane.showMessageDialog(
              this, message, "Alert", JOptionPane.ERROR_MESSAGE);
    }
  }

  @Override
  public boolean isGameOverMessageShown() {
    return gameOverMessageShown;
  }

  @Override
  public void setGameOverMessageShown(boolean shown) {
    this.gameOverMessageShown = shown;
  }
}
