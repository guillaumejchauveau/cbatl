package events;

/**
 * @param <T>
 */
@FunctionalInterface
public interface EventListener<T extends Event> {
  void handleEvent(T event);
}
