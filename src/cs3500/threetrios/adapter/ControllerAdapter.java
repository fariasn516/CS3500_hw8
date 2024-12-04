package cs3500.threetrios.adapter;

import java.awt.event.ComponentAdapter;

import javax.swing.text.View;

import cs3500.threetrios.controller.ThreeTriosPlayerController;
import cs3500.threetrios.model.Model;
import cs3500.threetrios.model.player.Player;
import cs3500.threetrios.provider.controller.IController;
import cs3500.threetrios.provider.hw5.IGrid;
import cs3500.threetrios.provider.hw5.IModel;
import cs3500.threetrios.provider.hw5.IModelFeature;
import cs3500.threetrios.provider.hw5.IPlayer;
import cs3500.threetrios.provider.view.CardClickHandler;
import cs3500.threetrios.provider.view.CellClickHandler;
import cs3500.threetrios.provider.view.CellClickHandlerImpl;
import cs3500.threetrios.provider.view.ViewClickHandler;
import cs3500.threetrios.view.ThreeTriosFrameView;

public class ControllerAdapter extends ThreeTriosPlayerController implements IController, ViewClickHandler, CardClickHandler, CellClickHandler {
  ViewAdapter viewAdapter;
  boolean listenerAdded = false;

  /**
   * Constructor for the controller, takes in the model, player, and view.
   *
   * @param model  represents the model that the game is being played with
   * @param player represents the player this controller is acting for
   * @param view   represents the GUI view for this player
   */
  public ControllerAdapter(Model model, Player player, ViewAdapter view) {
    super(model, player, view);
    this.viewAdapter = view;
  }

  @Override
  public void playGame(String cardPath, String boardPath, IModel model, IGrid grid) {
    super.startGame(boardPath, cardPath, false);
    viewAdapter.addProviderListener(this, this);
  }

  @Override
  public void update() {
    System.err.println("update");
    if (!listenerAdded) {
      viewAdapter.addProviderListener(this, this);
      listenerAdded = true;
    }
  }

  @Override
  public void switchPlayer(IPlayer player) {

  }

  @Override
  public void selectCard(int handIndex) {
    super.onCardSelected(model.getCurrentPlayer().getCardsInHand().get(handIndex));
  }

  @Override
  public void selectGridCell(int row, int col) {
    super.placeCard(row, col);
  }

  @Override
  public boolean isCurrentPlayer() {
    return false;
  }

  @Override
  public IPlayer getPlayer() {
    return null;
  }

  @Override
  public IModelFeature getModelFeature() {
    return null;
  }

  @Override
  public void displayEndMessage(String message) {

  }

  @Override
  public void handleViewClick(int x, int y, String componentId) {
    System.err.println("handleViewClick");
  }

  @Override
  public void handleCardClick(int cardIndex) {
    super.onCardSelected(player.getCardsInHand().get(cardIndex));
  }

  @Override
  public void handleCellClick(int row, int col) {
    super.placeCard(row, col);
  }
}
