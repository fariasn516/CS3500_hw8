package cs3500.threetrios.controller;

import java.io.IOException;
import java.util.List;

import cs3500.threetrios.model.Card;
import cs3500.threetrios.model.CardFileParser;
import cs3500.threetrios.model.Color;
import cs3500.threetrios.model.Grid;
import cs3500.threetrios.model.GridFileParser;
import cs3500.threetrios.model.Model;
import cs3500.threetrios.model.player.AIPlayer;
import cs3500.threetrios.model.player.Player;
import cs3500.threetrios.view.ThreeTriosFrameView;
import cs3500.threetrios.view.ThreeTriosModelView;

/**
 * Controller that takes in the user's clicks from the GUI and feeds the action to the model.
 */
public class ThreeTriosPlayerController implements PlayerController {
  Model model; // represents the model where all the rules of ThreeTrios is being run
  Player player; // represents the player that is playing the game
  ThreeTriosFrameView view; // represents the view that shows the game state as a GUI
  Card selectedCard; // represents the card that is currently selected
  boolean yourTurn; // represents whether it is this player's turn

  /**
   * Constructor for the controller, takes in the model, player, and view.
   * @param model represents the model that the game is being played with
   * @param player represents the player this controller is acting for
   * @param view represents the GUI view for this player
   */
  public ThreeTriosPlayerController(Model model, Player player, ThreeTriosModelView view) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null.");
    }
    if (player == null) {
      throw new IllegalArgumentException("Player cannot be null.");
    }
    if (view == null) {
      throw new IllegalArgumentException("View cannot be null.");
    }
    this.model = model;
    this.player = player;
    this.view = view;

    this.model.addListener(this);
    this.view.addClickListener(this);
    this.player.addListener(this);

    this.yourTurn = this.player.getColor().equals(Color.RED);
  }

  @Override
  public void placeCard(int row, int col) {
    checkTurn();
    if (yourTurn) {
      if (this.selectedCard != null) {
        try {
          this.model.takeTurn(this.selectedCard, row, col);
          this.selectedCard = null;
        }
        catch (IllegalArgumentException | IllegalStateException e) {
          this.view.showMessage(e.getMessage());
          this.selectedCard = null;
        }
      }
      else {
        this.view.showMessage("Please choose a card first!");
      }
      notifyStatus();
    }
    else {
      view.showMessage("Not your turn!");
    }
    this.view.refresh();
  }

  private void checkTurn() {
    this.yourTurn = this.player.getColor().equals(this.model.getCurrentPlayer().getColor());
  }

  @Override
  public void onCardSelected(Card card) {
    checkTurn();
    if (yourTurn) {
      if (this.selectedCard != card) {
        this.selectedCard = card;
      }
      else {
        this.selectedCard = null;
      }
    }
    else {
      view.showMessage("Not your turn!");
    }
    view.refresh();
  }

  @Override
  public void notifyStatus() {
    this.view.refresh();
    yourTurn = model.getCurrentPlayer().getColor().equals(player.getColor());

    if (this.model.isGameOver()) {
      if (this.model.winner().equals("Tie")) {
        if (!this.view.isGameOverMessageShown()) {
          this.view.showMessage("This game is a tie!");
          this.view.setGameOverMessageShown(true);
        }
        return;
      }
      else {
        String winnerMessage = this.model.winner() + " has won!\n" + "Score: " +
                this.model.currentScore(this.model.winner().equals("Red Player") ?
                        Color.RED : Color.BLUE);
        if (!this.view.isGameOverMessageShown()) {
          this.view.showMessage(winnerMessage);
          this.view.setGameOverMessageShown(true);
        }
        return;
      }
    }

    if (this.yourTurn && this.player instanceof AIPlayer) {
      this.player.takeTurn();
    }

  }

  @Override
  public void startGame(String gridPath, String deckPath, boolean shuffle) {
    Grid grid;
    List<Card> deck;
    if (!model.hasStarted()) {
      try {
        grid = new GridFileParser(gridPath).createGridFromFile();
        deck = new CardFileParser(deckPath).createDeck();
      }
      catch (IOException e) {
        throw new IllegalArgumentException(e.getMessage());
      }
      try {
        model.startGame(deck, shuffle, grid);
        if (this.player instanceof AIPlayer) {
          this.player.takeTurn();
        }
      }
      catch (IllegalArgumentException | IllegalStateException e) {
        throw new IllegalArgumentException(e.getMessage());
      }
    }
    if (this.player instanceof AIPlayer
            && this.player.getColor().equals(this.model.getCurrentPlayer().getColor())) {
      this.player.takeTurn();
    }
    view.refresh();
    view.makeVisible();
  }
}