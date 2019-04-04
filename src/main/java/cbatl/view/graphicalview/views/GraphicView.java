package cbatl.view.graphicalview.views;

import cbatl.model.Model;
import cbatl.model.events.StateChangedEvent;
import cbatl.view.View;

import javax.swing.*;

public class GraphicView extends View
{
  private JFrame frame;
  private JMenuBar menu;
  private Model model;
  private Launcher launcher;
  private PlayerConfigView config;
  private BShipPanel game;
  private EndGame end;

  public GraphicView ()
  {
    this.frame = new JFrame();
    this.frame.setTitle("BATTLESHIP");
    this.frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

    this.menu = new MenuBar();

    this.launcher = new Launcher(frame);
    this.config = new PlayerConfigView(frame);
    this.game = new BShipPanel(frame);
    this.end = new EndGame(frame, true);

    this.frame.setJMenuBar(this.menu);
    this.frame.setContentPane(this.launcher);
    this.frame.setLocationRelativeTo(null);
    this.frame.setVisible(true);
  }

  @Override
  public void attachModel(Model model) {
    this.model = model;

    this.model.addEventListener(StateChangedEvent.class, event -> {
      if (model.getCurrentState() == Model.State.MAIN_MENU) {
        this.frame.setContentPane(this.launcher);
      }
    });
  }
}
