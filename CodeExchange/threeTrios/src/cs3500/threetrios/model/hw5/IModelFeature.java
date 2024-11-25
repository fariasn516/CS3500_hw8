package cs3500.threetrios.model.hw5;

import cs3500.threetrios.controller.IController;

/**
 * Interface of model feature of Three Trios Game.
 */
public interface IModelFeature {
  /**
   * Switch player turn.
   */
  void switchPlayerTurn();

  /**
   * Add listener to controller.
   */
  void addListener(IController listener);

  /**
   * set this class as the listener.
   */
  void setThisAsListener();

  /**
   * notify the listener.
   */
  void notifyListeners();

  /**
   * notify the game is ended.
   */
  void notifyGameEnd(String message);
}
