package events;

import org.junit.Assert;
import org.junit.Test;

class MyEvent extends Event {
  Boolean touch;
  Boolean touch2;

  MyEvent() {
    this.touch = false;
    this.touch2 = false;
  }

  void touch() {
    this.touch = true;
  }
}

class MyEvent2 extends MyEvent {
  @Override
  void touch() {
    this.touch2 = true;
  }
}

public class EventsTest {
  @Test
  public void events() {
    EventTarget target = new EventTarget() {
    };
    target.addEventListener(MyEvent.class, MyEvent::touch);
    MyEvent event = new MyEvent2();
    target.dispatchEvent(event);
    Assert.assertFalse(event.touch);
    Assert.assertTrue(event.touch2);
  }
}
