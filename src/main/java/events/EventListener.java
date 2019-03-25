package events;

/**
 * Used for event callbacks.
 *
 * @param <T> The type of the listened event
 */
@FunctionalInterface
public interface EventListener<T extends Event> {
  /**
   * Handles a dispatched event.
   *
   * @param event The dispatched event
   */
  void handleEvent(T event);
}
