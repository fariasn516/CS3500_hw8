package cs3500.threetrios;

import org.junit.Before;
import org.junit.Test;
import cs3500.threetrios.controller.Controller;
import cs3500.threetrios.controller.ThreeTriosSwingController;
import cs3500.threetrios.model.Color;
import cs3500.threetrios.view.ThreeTriosFrameView;
import cs3500.threetrios.view.ThreeTriosModelView;


/**
 * Tests for the controller.
 */
public class ThreeTriosControllerTest extends ThreeTriosModelTest {
  private Controller controller;

  @Before
  public void innit() {
    ThreeTriosFrameView view = new ThreeTriosModelView(this.hasSeededRandom, Color.RED);
    this.controller = new ThreeTriosSwingController(view);
  }

  // checking controller constructor will throw exception
  @Test (expected = IllegalArgumentException.class)
  public void shouldReturnIllegalArgumentControllerViewNull() {
    this.controller = new ThreeTriosSwingController(null);
  }

  // playGame method that doesn't take in Strings for files
  // when model is null
  @Test (expected = IllegalArgumentException.class)
  public void shouldReturnIllegalArgumentModelNullPlayGame() {
    this.controller.playGame(null, this.deck, this.gridWithNoHoles, false);
  }

  // when deck is null
  @Test (expected = IllegalArgumentException.class)
  public void shouldReturnIllegalArgumentDeckNullPlayGame() {
    this.controller.playGame(this.hasSeededRandom, null, this.gridWithNoHoles, false);
  }

  // when grid is null
  @Test (expected = IllegalArgumentException.class)
  public void shouldReturnIllegalArgumentGridNullPlayGame() {
    this.controller.playGame(this.hasSeededRandom, this.deck, null, false);
  }

  // when game has already started
  @Test (expected = IllegalStateException.class)
  public void shouldReturnIllegalStateModelAlreadyStartedPlayGame() {
    this.hasSeededRandom.startGame(this.deck, false, this.gridWithNoHoles);
    this.controller.playGame(this.hasSeededRandom, this.deck, this.gridWithNoHoles, false);
  }

  // playGame method that does take in StringFiles
  // when model is null
  @Test (expected = IllegalArgumentException.class)
  public void shouldReturnIllegalArgumentModelNullPlayGameString() {
    this.controller.playGame(null,
            "configurationFiles/CardConfiguration/MaxCards",
            "configurationFiles/GridConfiguration/NoHoles", false);
  }

  // when the file for deck cannot be found
  @Test (expected = IllegalArgumentException.class)
  public void shouldReturnIllegalArgumentCardPathNotFoundPlayGameString() {
    this.controller.playGame(this.hasSeededRandom,
            "configurationFiles/CardConfiguration/bleh",
            "configurationFiles/GridConfiguration/NoHoles", false);
  }

  // when the file for grid cannot be found
  @Test (expected = IllegalArgumentException.class)
  public void shouldReturnIllegalArgumentGridPathNotFoundPlayGameString() {
    this.controller.playGame(this.hasSeededRandom,
            "configurationFiles/CardConfiguration/MaxCards",
            "configurationFiles/GridConfiguration/bleh", false);
  }

  // when game has already started
  @Test (expected = IllegalStateException.class)
  public void shouldReturnIllegalStateGameStartedPlayGameString() {
    this.hasSeededRandom.startGame(this.deck, false, this.gridWithNoHoles);
    this.controller.playGame(this.hasSeededRandom,
            "configurationFiles/CardConfiguration/MaxCards",
            "configurationFiles/GridConfiguration/NoHoles", false);
  }
}
