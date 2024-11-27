package cs3500.threetrios.provider.view;

import cs3500.threetrios.provider.controller.IController;

/**
 * Interface of View Features.
 */
public interface IViewFeature {
  void selectHandCard(int handIndex); // Method to select a card in hand

  void selectGridCell(int row, int col); // Method to select a grid cell to place a card

  void setVisible(boolean visible); // Make the view visible or invisible

  void addListener(IController listener); // Register a controller as a listener for view events

  boolean isCurrentPlayer();

  void showErrorMessage(String message);
}
