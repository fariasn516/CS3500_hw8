package cs3500.threetrios.adapter;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import cs3500.threetrios.model.Card;
import cs3500.threetrios.model.CardFileParser;
import cs3500.threetrios.model.Cell;
import cs3500.threetrios.model.Color;
import cs3500.threetrios.model.Grid;
import cs3500.threetrios.model.GridFileParser;
import cs3500.threetrios.model.ThreeTriosModel;
import cs3500.threetrios.provider.controller.IController;
import cs3500.threetrios.provider.hw5.CardColor;
import cs3500.threetrios.provider.hw5.Direction;
import cs3500.threetrios.provider.hw5.ICard;
import cs3500.threetrios.provider.hw5.IGrid;
import cs3500.threetrios.provider.hw5.IModel;
import cs3500.threetrios.provider.hw5.IModelFeature;
import cs3500.threetrios.provider.hw5.IPlayer;

/**
 * Adapter class that allows a ThreeTriosModel to be conformed to the IModel interface.
 */
public class ModelAdapter extends ThreeTriosModel implements IModelFeature, IModel {
  private List<IController> listeners;

  /**
   * Default constructor for the ModelAdapter class.
   * Calls the superclass constructor to initialize the model.
   */
  public ModelAdapter() {
    super();
  }


  /**
   * Constructor for the ModelAdapter class with a specified Random object.
   * Initializes the model with the provided Random instance for randomization.
   *
   * @param rand the Random object used to introduce randomness into the model's behavior
   */
  public ModelAdapter(Random rand) {
    super(rand);
  }

  @Override
  public void switchPlayerTurn() {
    // not needed
  }

  @Override
  public void addListener(IController listener) {
    listeners.add(listener);
  }

  @Override
  public void setThisAsListener() {
    this.addListener(this);
  }

  @Override
  public void notifyListeners() {
    super.notifyStatus();
    for (IController listener : listeners) {
      listener.update();
    }
  }

  @Override
  public void notifyGameEnd(String message) {
    // not needed
  }

  @Override
  public void initializeGame(IGrid grid, IPlayer[] players, String cardFile, String gridFile) {
    Grid theGrid;
    List<Card> deck;
    try {
      GridFileParser parseGrid = new GridFileParser(gridFile);
      CardFileParser parseDeck = new CardFileParser(cardFile);
      theGrid = parseGrid.createGridFromFile();
      deck = parseDeck.createDeck();
      super.startGame(deck, false, theGrid);
    }
    catch (FileNotFoundException e) {
      throw new IllegalArgumentException(e.getMessage());
    }
  }

  @Override
  public boolean placeCard(int cardIdx, int x, int y) {
    try {
      super.takeTurn(this.getCurrentPlayer().getCardsInHand().get(cardIdx), x, y);
      return true;
    }
    catch (IllegalStateException | IllegalArgumentException e) {
      return false;
    }
  }

  @Override
  public void endGame() throws IllegalStateException {
    // not needed
  }

  @Override
  public void switchTurn() {
    switchPlayerTurn();
  }

  @Override
  public int getFlippedCardsCount() {
    return 0;
  }

  @Override
  public int getHandSize() {
    return this.getCurrentPlayer().getCardsInHand().size();
  }

  @Override
  public IPlayer[] getPlayers() {
    return new IPlayer[0];
  }

  @Override
  public IGrid getGrid2() {
    return null;
  }

  @Override
  public IModelFeature getModelFeature() {
    return null;
  }

  @Override
  public IPlayer getCurrentTurnPlayer() {
    return new HumanPlayerAdapter(this, this.getCurrentPlayer().getColor());
  }

  @Override
  public List<ICard> getHand(IPlayer player) {
    return List.of();
  }

  @Override
  public int[] getScores() {
    return new int[]{this.currentScore(Color.RED), this.currentScore(Color.BLUE)};
  }

  @Override
  public int[][] getBoard() {
    boolean[][] holeLayout = this.getGrid().getHoleLayout();
    int [][] board = new int[holeLayout.length][holeLayout[0].length];
    for (int row = 0; row < holeLayout.length; row++) {
      for (int col = 0; col < holeLayout[0].length; col++) {
        if (holeLayout[row][col]) {
          board[row][col] = -1;
        }
        else {
          board[row][col] = -0;
        }
      }
    }
    return board;
  }

  @Override
  public ICard[][] getBoardState() {
    Cell[][] cells = this.getGrid().getCells();
    ICard[][] boardState = new ICard[cells.length][cells[0].length];
    for (int row = 0; row < cells.length; row++) {
      for (int col = 0; col < cells[row].length; col++) {
        if (cells[row][col].hasCard()) {
          boardState[row][col] = createICard(cells[row][col].getCard());
        }
      }
    }
    return boardState;
  }

  /**
   * Converts a Card object into an ICard by mapping its direction values to corresponding values,
   * and assigning a color based on the card owner.
   *
   * @param card the Card object to convert
   * @return a new ICard instance with the corresponding direction-value mappings and color
   */
  private ICard createICard(Card card) {
    Map<Direction, Integer> values = new HashMap<>();
    values.put(Direction.NORTH,
            card.getValueFromDirection(cs3500.threetrios.model.Direction.NORTH));
    values.put(Direction.SOUTH,
            card.getValueFromDirection(cs3500.threetrios.model.Direction.SOUTH));
    values.put(Direction.EAST,
            card.getValueFromDirection(cs3500.threetrios.model.Direction.EAST));
    values.put(Direction.WEST,
            card.getValueFromDirection(cs3500.threetrios.model.Direction.WEST));

    CardColor color = CardColor.BLUE;

    Color color1 = getCardOwnerColor(card);
    if (color1 == null || color1 == Color.BLUE) {
      color = CardColor.BLUE;
    }
    if (color1 == Color.RED) {
      color = CardColor.RED;
    }

    return new CardAdapter(card.getName(), color, values);
  }

  @Override
  public IPlayer getWinner() {
    this.winner();
    return null;
  }

  @Override
  public String displayPlayer() {
    if (!hasStarted()) {
      return "RED";
    }
    return this.getCurrentPlayer().getColor().toString();
  }

  @Override
  public int getCol() {
    return this.getGrid().getNumCols();
  }

  @Override
  public int getRow() {
    return this.getGrid().getNumRows();
  }

  @Override
  public List<ICard> getRedHand() {
    List<Card> redHand = this.getRedPlayer();
    return getICards(redHand, CardColor.RED);
  }

  @Override
  public List<ICard> getBlueHand() {
    List<Card> blueHand = this.getBluePlayer();
    return getICards(blueHand, CardColor.BLUE);
  }

  /**
   * Converts the Cards to ICards.
   * @param hand represents the hand of cards to be converted
   * @return the hand of ICards that belong to the player
   */
  private List<ICard> getICards(List<Card> hand, CardColor color) {
    List<ICard> finalHand = new ArrayList<>();
    for (Card card : hand) {
      finalHand.add(createICard(card));
    }
    return finalHand;
  }
}
