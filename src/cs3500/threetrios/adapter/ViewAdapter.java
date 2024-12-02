package cs3500.threetrios.adapter;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;


import cs3500.threetrios.provider.controller.IController;
import cs3500.threetrios.provider.hw5.ReadOnlyTriosModel;
import cs3500.threetrios.provider.view.GameView;
import cs3500.threetrios.provider.view.ViewClickHandler;
import cs3500.threetrios.view.PlayerAction;
import cs3500.threetrios.view.ThreeTriosFrameView;

/**
 *
 */
public class ViewAdapter extends JFrame implements ThreeTriosFrameView {
  private GameView frame;
  private final ReadOnlyTriosModel model;
  private final String playerPerspective;
  private final List<PlayerAction> playerActions;
  private boolean gameOverMessageShown = false; // represents if the game over message is shown
  private boolean justStarted = true;

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
    this.model = model;
    this.playerPerspective = playerPerspective;
    this.playerActions = new ArrayList<>();
  }

  @Override
  public void refresh() {
    if (justStarted && model.getCurrentTurnPlayer()!=null) {
      this.frame = new GameView(this.model, this.playerPerspective);
      justStarted = false;
    }
    this.frame.refresh();
  }

  @Override
  public void addClickListener(PlayerAction listener) {
    /////////NEED A WAY TO ADD LISTENER TO THE PROVIDED VIEW//////
    System.err.println(listener instanceof IController);
    if (listener instanceof IController && !justStarted) {
      this.frame.addClickHandler((ViewClickHandler) listener);
      System.err.println("listener added!");
    }
    this.playerActions.add(listener);
  }

  @Override
  public void makeVisible() {
    this.frame.setVisible(true);
  }

  @Override
  public void showMessage(String message) {
    if (this.model.isGameOver() && !this.gameOverMessageShown) {
      setGameOverMessageShown(true);
      this.frame.showEndMessage(message);
    }
    else {
      this.frame.showErrorMessage(message);
    }
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
