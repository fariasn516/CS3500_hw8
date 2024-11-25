package cs3500.threetrios;

import java.util.ArrayList;
import java.util.List;

import cs3500.threetrios.model.GameGrid;

/**
 * Class to help with testing, helps in making sure the necessary coordinates are inspected.
 */
public class TranscriptMockThreeTriosModel extends ConditionMockThreeTriosModel {
  // represents the list of inspected coordinates
  private final List<Coord> inspectedCoords = new ArrayList<>();

  /**
   * Constructor for TranscriptMockThreeTriosModel. Instantiates a new game grid.
   */
  public TranscriptMockThreeTriosModel() {
    super();

    boolean[][] holeLayout = {
            {false, false, false},
            {false, false, false},
            {false, false, false}
    };
    this.grid = new GameGrid(3, 3, holeLayout);
  }

  @Override
  public boolean isLegalToPlay(int row, int col) {
    inspectedCoords.add(new Coord(row, col));
    return super.isLegalToPlay(row, col);
  }

  /**
   * Returns a copy of the list of inspected coordinates.
   * @return a copy of the list of inspected coordinates.
   */
  public List<Coord> getInspectedCoords() {
    return new ArrayList<>(inspectedCoords);
  }

}