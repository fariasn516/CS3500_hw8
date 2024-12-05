// NOTE: There was no way for this view class to be properly changed, as this utilizes some sort
// of concrete version of the model that we did not have access to, however this should not
// affect actual view/gameplay in any way as this is the text view version

package cs3500.threetrios.provider.view;


// import cs3500.threetrios.model.hw5.ThreeTriosModel;

/**
 * The class that can produce the text view of the game.
 */
public class TextView {
  // private final ThreeTriosModel model;

  /**
   * Constructor of the class taking a model.
   */
  // public TextView(ThreeTriosModel model) {
  // if (model == null) {
  // throw new IllegalArgumentException("Model cannot be null");
  // }
  // this.model = model;
  //}

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();

    //sb.append("Model.Player: ").append(model.displayPlayer()).append("\n");

    //sb.append(model.display());

    //sb.append("Hand: \n");
    //sb.append(model.displayPlayerHand());
    return sb.toString();
  }
}
