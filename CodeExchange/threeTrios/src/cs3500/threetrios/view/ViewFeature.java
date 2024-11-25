package cs3500.threetrios.view;

import cs3500.threetrios.controller.IController;

/**
 * View Feature of Three Trios Game.
 */
public class ViewFeature implements IViewFeature {

  private final GameView view;
  private IController listener;

  public ViewFeature(GameView view) {
    this.view = view;
  }

  @Override
  public void selectHandCard(int handIndex) {
    if (listener != null) {
      System.out.println("ViewFeature: Hand card selected, index: " + handIndex);
      listener.selectCard(handIndex);
    } else {
      System.out.println("Listener might be null.");
    }
  }

  @Override
  public void selectGridCell(int row, int col) {
    if (listener != null) {
      System.out.println("ViewFeature: Cell selected at row col: " + row + col);
      listener.selectGridCell(row, col);
    } else {
      System.out.println("Listener might be null.");
    }
  }

  @Override
  public void setVisible(boolean visible) {
    view.setVisible(visible);
  }

  @Override
  public void addListener(IController listener) {
    this.listener = listener;
  }

  @Override
  public boolean isCurrentPlayer() {
    return listener != null && listener.getPlayer().equals(view.getCurrentTurnPlayer());
  }

  @Override
  public void showErrorMessage(String message) {
    view.showErrorMessage(message);
  }

}
