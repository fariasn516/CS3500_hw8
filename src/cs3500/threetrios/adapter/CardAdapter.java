package cs3500.threetrios.adapter;

import java.util.Map;

import cs3500.threetrios.provider.hw5.CardColor;
import cs3500.threetrios.provider.hw5.Direction;
import cs3500.threetrios.provider.hw5.ICard;

/**
 *
 */
public class CardAdapter implements ICard {
  private CardColor color;
  private final String name;
  private final Map<Direction, Integer> values;

  /**
   * @param name
   * @param color
   * @param values
   */
  public CardAdapter(String name, CardColor color, Map<Direction, Integer> values) {
    if (name == null) {
      throw new IllegalArgumentException("Card name cannot be null");
    }
    if (color == null) {
      throw new IllegalArgumentException("Color cannot be null");
    }
    if (values == null) {
      throw new IllegalArgumentException("Values cannot be null");
    }
    if (!validValues(values)) {
      throw new IllegalArgumentException("Not all directions are accounted for");
    }
    this.name = name;
    this.color = color;
    this.values = values;
  }

  /**
   * Checks if the map has every possible direction.
   * @param values represents the map of directions and values
   * @return true if each direction is accounted for and false if not
   */
  private boolean validValues(Map<Direction, Integer> values) {
    for (Direction direction : Direction.values()) {
      if (!values.containsKey(direction)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public boolean battle(ICard opponentCard, Direction direction) {
    switch (direction) {
      case NORTH:
        if (values.get(Direction.NORTH) >= opponentCard.getSouthValue()) {
          return true;
        }
        break;
      case SOUTH:
        if (values.get(Direction.SOUTH) >= opponentCard.getNorthValue()) {
          return true;
        }
        break;
      case EAST:
        if (values.get(Direction.EAST) >= opponentCard.getWestValue()) {
          return true;
        }
        break;
      case WEST:
        if (values.get(Direction.WEST) >= opponentCard.getEastValue()) {
          return true;
        }
        break;
      default:
        throw new IllegalArgumentException("Invalid direction: " + direction);
    }
    return false;
  }

  @Override
  public void flip() throws IllegalStateException {
    if (this.color.equals(CardColor.RED)) {
      this.color = CardColor.BLUE;
    }
    else {
      this.color = CardColor.RED;
    }
  }

  @Override
  public CardColor getColor() {
    return this.color;
  }

  @Override
  public void setColor(CardColor color) {
    this.color = color;
  }

  @Override
  public int getSouthValue() {
    return this.values.get(Direction.SOUTH);
  }

  @Override
  public int getNorthValue() {
    return this.values.get(Direction.NORTH);
  }

  @Override
  public int getWestValue() {
    return this.values.get(Direction.WEST);
  }

  @Override
  public int getEastValue() {
    return this.values.get(Direction.EAST);
  }

  @Override
  public ICard clone() {
    return new CardAdapter(name, this.color, this.values);
  }
}
