package cs3500.threetrios;

import javax.naming.ldap.Control;

import cs3500.threetrios.adapter.AIPlayerAdapter;
import cs3500.threetrios.adapter.ControllerAdapter;
import cs3500.threetrios.adapter.GridAdapter;
import cs3500.threetrios.adapter.HumanPlayerAdapter;
import cs3500.threetrios.adapter.ModelAdapter;
import cs3500.threetrios.adapter.ViewAdapter;
import cs3500.threetrios.controller.PlayerController;
import cs3500.threetrios.controller.ThreeTriosPlayerController;
import cs3500.threetrios.model.Color;
import cs3500.threetrios.model.Model;
import cs3500.threetrios.model.ReadOnlyModel;
import cs3500.threetrios.model.ThreeTriosModel;
import cs3500.threetrios.model.player.AIPlayer;
import cs3500.threetrios.model.player.HumanPlayer;
import cs3500.threetrios.model.player.Player;
import cs3500.threetrios.provider.hw5.IGrid;
import cs3500.threetrios.provider.hw5.ReadOnlyTriosModel;
import cs3500.threetrios.strategy.CornerCardStrat;
import cs3500.threetrios.strategy.MaxFlippedCardsStrat;
import cs3500.threetrios.view.ThreeTriosFrameView;
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

    ModelAdapter model = new ModelAdapter();
    ThreeTriosFrameView viewPlayer1 = new ThreeTriosModelView(model, Color.RED);
    ThreeTriosFrameView viewPlayer2 = new ViewAdapter(model, "BLUE");

    IGrid grid = new GridAdapter();

    if (args.length != 2) {
      throw new IllegalArgumentException("Please specify players.");
    }

    Player player1 = null;
    Player player2 = null;

    if (args[0].equalsIgnoreCase("human")) {
      player1 = new HumanPlayerAdapter(model, Color.RED);
    }
    else if (args[0].equalsIgnoreCase("strategy1")) {
      player1 = new AIPlayerAdapter(model, Color.RED, new MaxFlippedCardsStrat());
    }
    else if (args[0].equalsIgnoreCase("strategy2")) {
      player1 = new AIPlayerAdapter(model, Color.RED, new CornerCardStrat());
    }
    else {
      throw new IllegalArgumentException("Invalid player type for player 1. " +
              "Use 'human' or 'strategyX'.");
    }

    if (args[1].equalsIgnoreCase("human")) {
      player2 = new HumanPlayerAdapter(model, Color.BLUE);
    }
    else if (args[1].equalsIgnoreCase("strategy1")) {
      player2 = new AIPlayerAdapter(model, Color.BLUE, new MaxFlippedCardsStrat());
    }
    else if (args[1].equalsIgnoreCase("strategy2")) {
      player2 = new AIPlayerAdapter(model, Color.BLUE, new CornerCardStrat());
    }
    else {
      throw new IllegalArgumentException("Invalid player type for player 2. " +
              "Use 'human' or 'strategyX'.");
    }

    PlayerController controller1 = new ThreeTriosPlayerController(model, player1, viewPlayer1);
    ControllerAdapter controller2 = new ControllerAdapter(model, player2, (ViewAdapter)viewPlayer2);

    controller1.startGame("configurationFiles/GridConfiguration/HasHoles",
            "configurationFiles/CardConfiguration/MaxCards", false);
    controller2.playGame("configurationFiles/CardConfiguration/MaxCards",
            "configurationFiles/GridConfiguration/HasHoles", model, grid);
  }
}
