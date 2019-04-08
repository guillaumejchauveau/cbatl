package cbatl.view.graphicalview.panels;

import events.Event;
import events.EventListener;
import java.util.ArrayList;
import java.util.Collection;
import javax.swing.JPanel;

public abstract class Panel extends JPanel {
  private Collection<EventListener<Event>> eventListeners;

  public Panel() {
    this.eventListeners = new ArrayList<>();
  }

  public void addEventListener(EventListener<Event> eventListener) {
    this.eventListeners.add(eventListener);
  }

  protected void dispatchEvent(Event event) {
    for (EventListener<Event> eventListener : this.eventListeners) {
      eventListener.handleEvent(event);
    }
  }
}
