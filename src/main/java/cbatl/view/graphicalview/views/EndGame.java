package cbatl.view.graphicalview.views;

import javax.swing.*;
import java.awt.*;

public class EndGame extends JPanel
{
  private JFrame frame;
  private Boolean win;
  private JLabel label;
  private JButton replayButton;
  private JButton quitButton;

  public EndGame (JFrame frame, Boolean win)
  {
    this.frame = frame;
    this.frame.setSize(new Dimension(600, 350));

    this.win = win;

    this.label = new JLabel();
    this.label.setFont(new Font("Serif", Font.BOLD, 80));
    this.initLabel();

    this.replayButton = new JButton();
    this.replayButton.setText("Replay");
    this.replayButton.setFont(new Font("Arial", Font.ITALIC, 25));
    this.replayButton.setPreferredSize(new Dimension(150, 35));
    this.replayButton.setOpaque(true);
    this.replayButton.setBackground(new Color(50, 200, 250));

    this.quitButton = new JButton();
    this.quitButton.setText("Quit");
    this.quitButton.setFont(new Font("Arial", Font.ITALIC, 25));
    this.quitButton.setPreferredSize(new Dimension(150, 35));
    this.quitButton.setOpaque(true);
    this.quitButton.setBackground(new Color(35, 200, 250));

    JPanel buttonPane = new JPanel();
    buttonPane.setLayout(new FlowLayout());
    buttonPane.add(this.replayButton);
    buttonPane.add(this.quitButton);

    this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
    this.add(this.label);
    this.add(buttonPane);
  }

    public void initLabel ()
    {
      if(this.win)
      {
        this.label.setText("You won !!");
      } else {
        this.label.setText("You lose !!");
      }
    }
}
