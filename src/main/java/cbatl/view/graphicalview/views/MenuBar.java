package cbatl.view.graphicalview.views;

import javax.swing.*;

public class MenuBar extends JMenuBar
{
  private JMenu fileMenu;
  private JMenu helpMenu;
  private JMenuItem replay;
  private JMenuItem quit;
  private JMenuItem help;

  public MenuBar ()
  {
    this.fileMenu = new JMenu("File");
    this.helpMenu = new JMenu("?");

    this.replay = new JMenuItem("Restart");
    this.quit = new JMenuItem("Quit");
    this.help = new JMenuItem("Help");

    this.fileMenu.add(this.replay);
    this.fileMenu.add(this.quit);
    this.helpMenu.add(this.help);

    this.add(this.fileMenu);
    this.add(this.helpMenu);
  }

  /**
   * Return the replay button in the menubar
   * @return
   */
  public JMenuItem getReplay()
  {
    return this.replay;
  }

  /**
   * Return the quit button in the menubar
   * @return
   */
  public JMenuItem getQuit()
  {
    return this.quit;
  }

  /**
   * Return the help button in the menubar
   * @return
   */
  public JMenuItem getHelp()
  {
    return this.help;
  }

}
