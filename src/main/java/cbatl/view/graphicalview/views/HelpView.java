package cbatl.view.graphicalview.views;

import javax.swing.*;

public class HelpView extends JPanel
{
  private JMenuBar menubar;
  private JMenuItem back;
  private JLabel text;

  public HelpView (JFrame frame)
  {
    this.text = new JLabel();
    this.menubar = new JMenuBar();
    this.back = new JMenuItem("Back");

    this.initText();

    this.menubar.add(this.back);

    this.add(this.text);
    this.add(this.menubar);

    frame.setJMenuBar(this.menubar);
  }

  public final JMenuBar getJMenuBar ()
  {
    return this.menubar;
  }
  public final JMenuItem getBackButton ()
  {
    return this.back;
  }
  private final void initText ()
  {
    String str = "This is a tutorial to game.";
    this.text.setText(str);
  }
}
