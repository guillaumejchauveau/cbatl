package events;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * An object that can dispatch events.
 */
public abstract class EventTarget {
  private Map<Class<? extends Event>, Collection<EventListener<? extends Event>>> eventListeners;

  /**
   * Initializes the target with an empty set of listeners.
   */
  public EventTarget() {
    this.eventListeners = new HashMap<>();
  }

  /**
   * Adds an event listener.
   *
   * @param eventType     The type of the listened event
   * @param eventListener The event listener
   * @param <T>           The type of the listened event
   */
  public <T extends Event> void addEventListener(Class<T> eventType,
                                                 EventListener<T> eventListener) {
    if (!this.eventListeners.containsKey(eventType)) {
      this.eventListeners.put(eventType, new ArrayList<>());
    }
    this.eventListeners.get(eventType).add(eventListener);
  }

  /**
   * Removes an event listener.
   *
   * @param eventType     The type of the listened event
   * @param eventListener The event listener
   * @param <T>           The type of the listened event
   */
  public <T extends Event> void removeEventListener(Class<T> eventType,
                                                    EventListener<T> eventListener) {
    if (this.eventListeners.containsKey(eventType)) {
      this.eventListeners.get(eventType).remove(eventListener);
    }
  }

  /**
   * Removes all event listeners for a given event type.
   *
   * @param eventType The type of the listened event
   * @param <T>       The type of the listened event
   */
  public <T extends Event> void removeEventListener(Class<T> eventType) {
    this.eventListeners.remove(eventType);
  }

  /**
   * Dispatches an event to the corresponding listeners.
   *
   * @param event The event to dispatch
   * @param <T>   The type of the dispatched event
   */
  @SuppressWarnings("unchecked")
  protected <T extends Event> void dispatchEvent(T event) {
    for (Class<? extends Event> listenedEventType : this.eventListeners.keySet()) {
      if (listenedEventType.isInstance(event)) {
        for (EventListener eventListener : this.eventListeners.get(listenedEventType)) {
          eventListener.handleEvent(event);
        }
      }
    }
  }
}
