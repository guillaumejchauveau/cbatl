package cbatl.view.graphicalview.views;


import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class PlayerConfigView extends JPanel
{
  private JFrame frame;
  private JPanel playerPane;
  private JLabel playerText;
  private JButton valid;
  private ButtonGroup choice;
  private JRadioButton small;
  private JRadioButton medium;
  private JRadioButton large;

  public PlayerConfigView (JFrame frame)
  {
    this.frame = frame;

    this.playerPane = new JPanel();
    this.playerPane.setLayout(new FlowLayout());

    this.playerText = new JLabel("Player One");
    this.playerText.setPreferredSize(new Dimension(100, 30));

    this.valid = new JButton("Enter");

    this.small = new JRadioButton("small : 8x8");
    this.medium = new JRadioButton("medium : 12x12");
    this.large = new JRadioButton("large : 16x16");

    this.choice = new ButtonGroup();
    this.choice.add(this.small);
    this.choice.add(this.medium);
    this.choice.add(this.large);

    this.setLayout(new FlowLayout());
    this.add(this.playerText);
    this.add(this.small);
    this.add(this.medium);
    this.add(this.large);
    this.add(this.valid);
  }
}
