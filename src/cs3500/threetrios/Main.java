package cs3500.threetrios;

import cs3500.threetrios.controller.PlayerController;
import cs3500.threetrios.controller.ThreeTriosPlayerController;
import cs3500.threetrios.model.Color;
import cs3500.threetrios.model.Model;
import cs3500.threetrios.model.ThreeTriosModel;
import cs3500.threetrios.model.player.AIPlayer;
import cs3500.threetrios.model.player.HumanPlayer;
import cs3500.threetrios.model.player.Player;
import cs3500.threetrios.strategy.CornerCardStrat;
import cs3500.threetrios.strategy.MaxFlippedCardsStrat;
import cs3500.threetrios.view.ThreeTriosModelView;

/**
 * Main class where once run, the game ThreeTrios can be played.
 */
public class Main {

  /**
   * Method where the game can be run.
   * @param args represents a String of inputs from the console
   */
  public static void main(String[] args) {
    Model model = new ThreeTriosModel();
    ThreeTriosModelView viewPlayer1 = new ThreeTriosModelView(model, Color.RED);
    ThreeTriosModelView viewPlayer2 = new ThreeTriosModelView(model, Color.BLUE);

    if (args.length != 2) {
      throw new IllegalArgumentException("Please specify players.");
    }

    Player player1 = null;
    Player player2 = null;

    if (args[0].equalsIgnoreCase("human")) {
      player1 = new HumanPlayer(model, Color.RED);
    } else if (args[0].equalsIgnoreCase("strategy1")) {
      player1 = new AIPlayer(model, Color.RED, new MaxFlippedCardsStrat());
    } else if (args[0].equalsIgnoreCase("strategy2")) {
      player1 = new AIPlayer(model, Color.RED, new CornerCardStrat());
    } else {
      throw new IllegalArgumentException("Invalid player type for player 1. " +
              "Use 'human' or 'strategyX'.");
    }

    if (args[1].equalsIgnoreCase("human")) {
      player2 = new HumanPlayer(model, Color.BLUE);
    } else if (args[1].equalsIgnoreCase("strategy1")) {
      player2 = new AIPlayer(model, Color.BLUE, new MaxFlippedCardsStrat());
    } else if (args[1].equalsIgnoreCase("strategy2")) {
      player2 = new AIPlayer(model, Color.BLUE, new CornerCardStrat());
    } else {
      throw new IllegalArgumentException("Invalid player type for player 2. " +
              "Use 'human' or 'strategyX'.");
    }
    
    PlayerController controller1 = new ThreeTriosPlayerController(model, player1, viewPlayer1);
    controller1.startGame("configurationFiles/GridConfiguration/HasHoles",
            "configurationFiles/CardConfiguration/MaxCards", false);
    PlayerController controller2 = new ThreeTriosPlayerController(model, player2, viewPlayer2);
    controller2.startGame("configurationFiles/GridConfiguration/HasHoles",
            "configurationFiles/CardConfiguration/MaxCards", false);
  }
}
