package cs3500.threetrios.adapter;

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
  /**
   * Constructor for the controller, takes in the model, player, and view.
   *
   * @param model  represents the model that the game is being played with
   * @param player represents the player this controller is acting for
   * @param view   represents the GUI view for this player
   */
  public ControllerAdapter(Model model, Player player, ThreeTriosFrameView view) {
    super(model, player, view);
  }

  @Override
  public void playGame(String cardPath, String boardPath, IModel model, IGrid grid) {
    super.startGame(boardPath, cardPath, false);
  }

  @Override
  public void update() {

  }

  @Override
  public void switchPlayer(IPlayer player) {

  }

  @Override
  public void selectCard(int handIndex) {
    super.onCardSelected(player.getCardsInHand().get(handIndex));
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
  }

  @Override
  public void handleCardClick(int cardIndex) {
    super.onCardSelected(player.getCardsInHand().get(cardIndex));  // Handle card click
  }
  @Override
  public void handleCellClick(int row, int col) {
    super.placeCard(row, col);
  }
}
