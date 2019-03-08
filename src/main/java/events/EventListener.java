package events;

public abstract class EventListener<T extends Event> {
  public abstract void handleEvent(T event);
}
