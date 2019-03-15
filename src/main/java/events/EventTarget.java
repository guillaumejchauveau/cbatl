package events;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public abstract class EventTarget {
  private Map<Class<? extends Event>, Collection<EventListener<? extends Event>>> eventListeners;

  /**
   *
   */
  public EventTarget() {
    this.eventListeners = new HashMap<>();
  }

  /**
   * @param eventType
   * @param eventListener
   * @param <T>
   */
  public <T extends Event> void addEventListener(Class<T> eventType,
                                                 EventListener<T> eventListener) {
    if (!this.eventListeners.containsKey(eventType)) {
      this.eventListeners.put(eventType, new ArrayList<>());
    }
    this.eventListeners.get(eventType).add(eventListener);
  }

  /**
   * @param eventType
   * @param eventListener
   * @param <T>
   */
  public <T extends Event> void removeEventListener(Class<T> eventType,
                                                    EventListener<T> eventListener) {
    if (this.eventListeners.containsKey(eventType)) {
      this.eventListeners.get(eventType).remove(eventListener);
    }
  }

  /**
   * @param eventType
   * @param <T>
   */
  public <T extends Event> void removeEventListener(Class<T> eventType) {
    this.eventListeners.remove(eventType);
  }

  /**
   * @param event
   * @param <T>
   */
  @SuppressWarnings("unchecked")
  protected <T extends Event> void dispatchEvent(T event) {
    for (Class<? extends Event> listenedEventType : this.eventListeners.keySet()) {
      if (listenedEventType.isInstance(event)) {
        for (EventListener<? extends Event> eventListener :
          this.eventListeners.get(listenedEventType)) {
          ((EventListener<T>) eventListener).handleEvent(event);
        }
      }
    }
  }
}
