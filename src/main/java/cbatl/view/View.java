package cbatl.view;

import cbatl.model.Model;
import events.EventTarget;

/**
 * The base class for views.
 */
public abstract class View extends EventTarget {
  /**
   * Sets the model for the view.
   *
   * @param model The model used by the view
   */
  public abstract void attachModel(Model model);
}
