package cbatl.view.graphicalview.panels;

import cbatl.model.Model;
import cbatl.view.events.CreateGameMenuEvent;
import javax.swing.JButton;

public class MainMenuPanel extends Panel {
  private Model model;

  public MainMenuPanel(Model model) {
    this.model = model;

    JButton createGame = new JButton("Commencer une partie");
    createGame.addActionListener(e -> {
      this.dispatchEvent(new CreateGameMenuEvent());
    });
    this.add(createGame);
  }
}
