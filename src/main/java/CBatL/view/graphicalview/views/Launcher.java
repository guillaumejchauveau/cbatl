package CBatL.view.graphicalview.views;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.*;

public class Launcher extends JPanel
{
  private JFrame frame;
  private JButton play;

  public Launcher (JFrame frame)
  {
    this.frame = frame;
    this.frame.setSize(new Dimension(300, 500));

    this.setBackground(Color.CYAN);

    this.play = new JButton();
    this.setPreferredSize(new Dimension(60, 25));
    this.play.setText("Play");
    this.play.setLocation(100, 200);
    this.play.setForeground(Color.WHITE);
    this.play.setBorderPainted(false);

    this.add(this.play);
  }

  public final JButton getPlayButton ()
  {
    return  this.play;
  }
}
