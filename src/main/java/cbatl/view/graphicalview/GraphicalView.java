package cbatl.view.graphicalview;

import cbatl.model.Model;
import cbatl.model.events.StateChangedEvent;
import cbatl.model.player.Player;
import cbatl.view.View;
import cbatl.view.events.ExitEvent;
import cbatl.view.events.MainMenuEvent;
import cbatl.view.graphicalview.panels.CreateGameMenuPanel;
import cbatl.view.graphicalview.panels.MainMenuPanel;
import cbatl.view.graphicalview.panels.Panel;
import cbatl.view.graphicalview.panels.PlayingGameMenuPanel;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class GraphicalView extends View {
  private Model model;
  private JFrame frame;

  private Panel mainMenuPanel;
  private Panel createGameMenuPanel;

  public GraphicalView() {
    this.frame = new JFrame("CBatL");
    this.frame.setMinimumSize(new Dimension(800, 400));
    this.frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

    JMenuBar menuBar = new JMenuBar();
    JMenu menu = new JMenu("CBatL");
    JMenuItem backToMainMenu = new JMenuItem("Menu principal");
    JMenuItem exit = new JMenuItem("Quitter");
    menu.add(backToMainMenu);
    menu.add(exit);
    menuBar.add(menu);
    this.frame.setJMenuBar(menuBar);

    GraphicalView that = this;
    this.frame.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        that.dispatchEvent(new ExitEvent());
      }
    });
    backToMainMenu.addActionListener(e -> {
      this.dispatchEvent(new MainMenuEvent());
    });
    exit.addActionListener(e -> {
      this.dispatchEvent(new ExitEvent());
    });
  }

  @Override
  public void attachModel(Model model) {
    this.model = model;
    this.mainMenuPanel = new MainMenuPanel(model);
    this.mainMenuPanel.addEventListener(this::dispatchEvent);
    this.createGameMenuPanel = new CreateGameMenuPanel(model);
    this.createGameMenuPanel.addEventListener(this::dispatchEvent);

    this.model.addEventListener(StateChangedEvent.class, event -> {
      this.updateState();
    });

    this.frame.setContentPane(this.mainMenuPanel);
    this.frame.setVisible(true);
  }

  private void updateState() {
    switch (this.model.getCurrentState()) {
      case MAIN_MENU:
        this.frame.setContentPane(this.mainMenuPanel);
        break;
      case CREATING_GAME:
        this.frame.setContentPane(this.createGameMenuPanel);
        List<Player> players = new ArrayList<>();
        players.add((Player) model.playerManager.getPlayers().toArray()[0]);
        break;
      case PLAYING_GAME:
        PlayingGameMenuPanel playingGameMenuPanel = new PlayingGameMenuPanel(model);
        playingGameMenuPanel.addEventListener(this::dispatchEvent);
        this.frame.setContentPane(playingGameMenuPanel);
        break;
      case GAME_OVER:
        Thread thread = new Thread(() -> {
          JOptionPane.showMessageDialog(this.frame,
            "Victoire de : " + this.model.getCurrentGame().getWinner().getName());
          if (this.model.getCurrentState() == Model.State.GAME_OVER) {
            this.dispatchEvent(new MainMenuEvent());
          }
        });
        thread.start();
        break;
    }
    this.frame.revalidate();
  }
}
