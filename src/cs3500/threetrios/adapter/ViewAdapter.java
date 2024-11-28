package cs3500.threetrios.adapter;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;


import cs3500.threetrios.provider.hw5.ReadOnlyTriosModel;
import cs3500.threetrios.provider.view.GameView;
import cs3500.threetrios.provider.view.IViewFeature;
import cs3500.threetrios.provider.view.ViewFeature;
import cs3500.threetrios.view.PlayerAction;
import cs3500.threetrios.view.ThreeTriosFrameView;

/**
 *
 */
public class ViewAdapter extends JFrame implements ThreeTriosFrameView {
  private final GameView frame;
  private final IViewFeature feature;
  private final ReadOnlyTriosModel model;
  private final List<PlayerAction> playerActions;
  private boolean gameOverMessageShown = false; // represents if the game over message is shown

  /**
   *
   * @param model
   * @param playerPerspective
   */
  public ViewAdapter(ReadOnlyTriosModel model, String playerPerspective) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    if (playerPerspective == null) {
      throw new IllegalArgumentException("Player perspective cannot be null");
    }
    this.frame = new GameView(model, playerPerspective);
    this.feature = new ViewFeature(frame);
    this.model = model;
    this.playerActions = new ArrayList<>();
  }

  @Override
  public void refresh() {
    this.frame.refresh();
  }

  @Override
  public void addClickListener(PlayerAction listener) {
    this.playerActions.add(listener);
  }

  @Override
  public void makeVisible() {
    feature.setVisible(true);
  }

  @Override
  public void showMessage(String message) {
    if (this.model.isGameOver()) {
      this.frame.showEndMessage(message);
    }
    this.frame.showErrorMessage(message);
  }

  @Override
  public boolean isGameOverMessageShown() {
    return this.gameOverMessageShown;
  }

  @Override
  public void setGameOverMessageShown(boolean shown) {
    this.gameOverMessageShown = shown;
  }
}
