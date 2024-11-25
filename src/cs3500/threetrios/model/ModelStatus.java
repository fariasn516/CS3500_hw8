package cs3500.threetrios.model;

/**
 * Model status interface, helps with model and view communications.
 */
public interface ModelStatus {
  /**
   * Notifies the listeners of their turn.
   */
  void notifyStatus();
}
