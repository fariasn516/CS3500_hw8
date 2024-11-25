package cs3500.threetrios.controller;

import cs3500.threetrios.model.ModelStatus;
import cs3500.threetrios.view.PlayerAction;

/**
 * Represents the interface for the controller to be used for players.
 */
public interface PlayerController extends PlayerAction, ModelStatus {
  /**
   * Starts the game given the file path for a grid, file path for a deck, and a boolean for
   * whether the deck is to be shuffled.
   * @param gridPath represents the file path for a grid
   * @param deckPath represents the file path for a deck
   * @param shuffle represents whether the deck of cards is to be shuffled
   * @throws IllegalArgumentException when either filepath for a grid or deck is not found
   */
  void startGame(String gridPath, String deckPath, boolean shuffle);
}
