package cbatl;

import cbatl.view.graphicalview.views.GraphicalView;

/**
 * Coucou
 */
public class App {
  public static void main(String[] args)
  {
    System.out.println(new App().getGreeting());
    new GraphicalView();
  }

  public String getGreeting() {
    return "Hello world.";
  }
}
