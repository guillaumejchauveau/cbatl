package events;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public abstract class EventTarget {
  private Map<Class<? extends Event>, Collection<EventListener<? extends Event>>> eventListeners;

  public EventTarget() {
    this.eventListeners = new HashMap<>();
  }

  public <T extends Event> void addEventListener(Class<T> eventType,
                                                 EventListener<T> eventListener) {
    if (!this.eventListeners.containsKey(eventType)) {
      this.eventListeners.put(eventType, new ArrayList<>());
    }
    this.eventListeners.get(eventType).add(eventListener);
  }

  public <T extends Event> void removeEventListener(Class<T> eventType,
                                                    EventListener<T> eventListener) {
    if (this.eventListeners.containsKey(eventType)) {
      this.eventListeners.get(eventType).remove(eventListener);
    }
  }

  public <T extends Event> void removeEventListener(Class<T> eventType) {
    this.eventListeners.remove(eventType);
  }

  @SuppressWarnings("unchecked")
  public <T extends Event> void dispatchEvent(T event) {
    Class<? extends Event> eventType = event.getClass();
    if (this.eventListeners.containsKey(eventType)) {
      for (EventListener<? extends Event> eventListener : this.eventListeners.get(eventType)) {
        ((EventListener<T>) eventListener).handleEvent(event);
      }
    }
  }
}
