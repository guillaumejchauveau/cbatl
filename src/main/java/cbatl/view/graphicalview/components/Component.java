package cbatl.view.graphicalview.components;

import events.Event;
import events.EventListener;
import java.util.ArrayList;
import java.util.Collection;
import javax.swing.JComponent;

public abstract class Component extends JComponent {
  private Collection<EventListener<Event>> eventListeners;

  public Component() {
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
