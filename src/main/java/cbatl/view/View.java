package cbatl.view;

import cbatl.model.Model;
import events.EventTarget;

public abstract class View extends EventTarget {
  protected final Model model;

  public View(Model model) {
    this.model = model;
  }
}
