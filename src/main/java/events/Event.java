package events;

public abstract class Event<T> {
  private T target;

  public Event(T target) {
    this.target = target;
  }

  public T getTarget() {
    return this.target;
  }
}
