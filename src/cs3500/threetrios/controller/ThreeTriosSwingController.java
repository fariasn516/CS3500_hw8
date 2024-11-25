package cs3500.threetrios.controller;

import java.io.FileNotFoundException;
import java.util.List;

import cs3500.threetrios.model.Card;
import cs3500.threetrios.model.CardFileParser;
import cs3500.threetrios.model.Grid;
import cs3500.threetrios.model.GridFileParser;
import cs3500.threetrios.model.Model;
import cs3500.threetrios.view.ThreeTriosFrameView;

/**
 * Controller class that takes in the user's inputs, feeding to the model and
 * allowing the user to play the game.
 */
public class ThreeTriosSwingController implements Controller {
  private final ThreeTriosFrameView view; // view that shows everything graphically

  /**
   * Constructor for the ThreeTriosSwingController. Takes in a view that represents gameplay.
   * @param view represents the view that will be printing out things graphically
   */
  public ThreeTriosSwingController(ThreeTriosFrameView view) {
    if (view == null) {
      throw new IllegalArgumentException("View cannot be null");
    }
    this.view = view;
  }

  @Override
  public void playGame(Model model, String cardFilePath, String gridFilePath, boolean shuffle) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null.");
    }

    CardFileParser deckParse = new CardFileParser(cardFilePath);
    GridFileParser gridParse = new GridFileParser(gridFilePath);
    List<Card> deck;
    Grid grid;

    try {
      deck = deckParse.createDeck();
      grid = gridParse.createGridFromFile();
      model.startGame(deck, shuffle, grid);
    }
    catch (FileNotFoundException e) {
      throw new IllegalArgumentException("One or more of your files cannot be found.");
    }
    catch (IllegalArgumentException e) {
      throw new IllegalArgumentException(e.getMessage());
    }
    catch (IllegalStateException e) {
      throw new IllegalStateException(e.getMessage());
    }

    //view.addClickListener(this);
    view.makeVisible();
  }

  @Override
  public void playGame(Model model, List<Card> deck, Grid grid, boolean shuffle) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null.");
    }
    if (deck == null) {
      throw new IllegalArgumentException("Deck cannot be null.");
    }
    if (grid == null) {
      throw new IllegalArgumentException("Grid cannot be null.");
    }
    try {
      model.startGame(deck, shuffle, grid);
    }
    catch (IllegalArgumentException e) {
      throw new IllegalArgumentException(e.getMessage());
    }
    catch (IllegalStateException e) {
      throw new IllegalStateException(e.getMessage());
    }
    //view.addClickListener(this);
    view.makeVisible();
  }

  @Override
  public void handleCellClick(int row, int column) {
    System.err.println("grid");
    System.err.println(row + ", " + column);
    // has not been actually implemented yet, and as such does not have any tests for it
    view.refresh();
  }

  @Override
  public void handleHandClick(int index, boolean red) {
    // has not been actually implemented yet, and as such does not have any tests for it
    view.refresh();
    if (red) {
      System.err.println("red player: " + index);
    }
    else {
      System.err.println("blue player: " + index);
    }
  }
}
