package cs3500.threetrios.view;

import cs3500.threetrios.model.Card;

/**
 * Represents the possible actions a player can take.
 */
public interface PlayerAction {

  /**
   * Action to take once a card is selected.
   * @param card represents the selected card
   */
  void onCardSelected(Card card);

  /**
   * Places a card given the row and column.
   * @param row represents the row for the card to be placed on (indexing starts at 0)
   * @param col represents the column for the card to be placed on (indexing starts at 0)
   */
  void placeCard(int row, int col);
}
