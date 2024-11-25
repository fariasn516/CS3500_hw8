package cs3500.threetrios.view;


import cs3500.threetrios.model.hw5.ICard;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.FontMetrics;

import java.util.List;

/**
 * Implementation of the player's hand panel.
 */
public class HandsPanel extends JPanel implements IHandsPanel {

  private List<ICard> cards;
  private final Color panelColor;
  private int highlightedCardIndex = -1;
  private final ViewFeature viewFeature;

  /**
   * Constructor of the player's hand panel.
   */
  public HandsPanel(Color panelColor, ViewFeature viewFeature) {
    this.panelColor = panelColor;
    this.viewFeature = viewFeature;
    this.setPreferredSize(new Dimension(150, 400));
    this.setBackground(panelColor);
  }

  @Override
  public void displayHand(List<ICard> cards) {
    this.cards = cards;
    highlightedCardIndex = -1;
    repaint();
  }

  @Override
  public void highlightCard(int cardIndex) {

    // Only highlight if the clicked card is different
    if (highlightedCardIndex != cardIndex) {
      highlightedCardIndex = cardIndex;
      System.out.println("Selected card at index: " + cardIndex);
      repaint();
    }

  }

  @Override
  public void dehighlightCard(int cardIndex) {
    if (highlightedCardIndex == cardIndex) {
      System.out.println("De select card at index: " + cardIndex);
      highlightedCardIndex = -1;
      repaint();
    }
  }

  @Override
  public void addCardClickHandler(CardClickHandler handler) {

    this.addMouseListener(new java.awt.event.MouseAdapter() {
      @Override
      public void mouseClicked(java.awt.event.MouseEvent e) {


        int cardHeight = getHeight() / cards.size();
        int cardIndex = e.getY() / cardHeight;

        // Dehighlight if clicked card is already highlighted
        if (highlightedCardIndex == cardIndex) {
          dehighlightCard(cardIndex);
        } else {
          highlightCard(cardIndex);
        }
        handler.handleCardClick(cardIndex);
        viewFeature.selectHandCard(cardIndex);
      }
    });

  }


  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    if (cards == null || cards.isEmpty()) {
      return;  // Nothing to draw, so we return early to avoid the exception
    }

    int cardHeight = getHeight() / cards.size();
    int cardWidth = getWidth();

    for (int i = 0; i < cards.size(); i++) {
      ICard card = cards.get(i);
      int cardTopY = i * cardHeight;

      drawCardBackground(g, i, cardWidth, cardHeight);
      drawCardBorder(g, i, cardWidth, cardHeight);
      drawCardValues(g, card, cardWidth, cardHeight, cardTopY);
    }

  }


  private void drawCardBackground(Graphics g, int cardIndex, int cardWidth, int cardHeight) {
    g.setColor(panelColor.darker());
    g.fillRect(0, cardIndex * cardHeight, cardWidth, cardHeight);
  }


  private void drawCardBorder(Graphics g, int cardIndex, int cardWidth, int cardHeight) {
    if (cardIndex == highlightedCardIndex) {
      g.setColor(Color.BLACK);
      ((Graphics2D) g).setStroke(new BasicStroke(5));
    } else {
      g.setColor(Color.BLACK);
      ((Graphics2D) g).setStroke(new BasicStroke(1));
    }
    g.drawRect(0, cardIndex * cardHeight, cardWidth, cardHeight);
  }


  private void drawCardValues(Graphics g, ICard card, int cardWidth, int cardHeight, int cardTopY) {
    g.setColor(Color.BLACK);
    FontMetrics fm = g.getFontMetrics();
    int textMargin = 10;

    // Top value (North)
    String topValue = String.valueOf(card.getNorthValue());
    int topValueX = (cardWidth - fm.stringWidth(topValue)) / 2;
    int topValueY = cardTopY + textMargin + fm.getAscent();
    g.drawString(topValue, topValueX, topValueY);

    // Bottom value (South)
    String bottomValue = String.valueOf(card.getSouthValue());
    int bottomValueX = (cardWidth - fm.stringWidth(bottomValue)) / 2;
    int bottomValueY = cardTopY + cardHeight - textMargin;
    g.drawString(bottomValue, bottomValueX, bottomValueY);

    // Left value (West)
    String leftValue = String.valueOf(card.getWestValue());
    int leftValueX = textMargin;
    int leftValueY = cardTopY + (cardHeight + fm.getAscent()) / 2;
    g.drawString(leftValue, leftValueX, leftValueY);

    // Right value (East)
    String rightValue = String.valueOf(card.getEastValue());
    int rightValueX = cardWidth - fm.stringWidth(rightValue) - textMargin;
    int rightValueY = cardTopY + (cardHeight + fm.getAscent()) / 2;
    g.drawString(rightValue, rightValueX, rightValueY);
  }


  /**
   * Clear all the hand card.
   */
  public void clearHand() {
    this.removeAll();
    revalidate();
    repaint();
  }


}
