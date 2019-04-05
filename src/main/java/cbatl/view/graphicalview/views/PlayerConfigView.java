package cbatl.view.graphicalview.views;


import cbatl.view.graphicalview.controllers.SelectionFieldController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayerConfigView extends JPanel
{
  private JFrame frame;
  private JPanel playerPane;
  private JPanel choicePane;
  private JLabel playerText;
  private JComboBox<String> selectionField;
  private JButton valid;
  private ButtonGroup choice;
  private JRadioButton small;
  private JRadioButton medium;
  private JRadioButton large;
  private JButton selectPlayer;
  private JButton createpPlayer;
  private String[] listePlayer;

  public PlayerConfigView (JFrame frame)
  {
    this.frame = frame;

    this.playerPane = new JPanel();
    this.playerPane.setLayout(new FlowLayout());

    this.choicePane = new JPanel();
    this.choicePane.setLayout(new FlowLayout());

    this.playerText = new JLabel("Player One");
    this.playerText.setPreferredSize(new Dimension(100, 30));

    this.listePlayer = new String[]{};

    this.selectPlayer = new JButton();
    this.selectPlayer.setText("Select Player");
    this.selectPlayer.setPreferredSize(new Dimension(110, 30));
    this.selectPlayer.addActionListener(new SelectionFieldController(this));

    this.createpPlayer = new JButton();
    this.createpPlayer.setText("Create Player");
    this.createpPlayer.setPreferredSize(new Dimension(110, 30));

    this.selectionField = new JComboBox<>(this.listePlayer);
    this.selectionField.setVisible(false);

    this.valid = new JButton("Enter");

    this.small = new JRadioButton("small : 8x8");
    this.medium = new JRadioButton("medium : 12x12");
    this.large = new JRadioButton("large : 16x16");

    this.choice = new ButtonGroup();
    this.choice.add(this.small);
    this.choice.add(this.medium);
    this.choice.add(this.large);

    this.playerPane.add(this.playerText);
    this.playerPane.add(this.selectionField);
    this.playerPane.add(this.selectPlayer);
    this.playerPane.add(this.createpPlayer);

    this.choicePane.add(this.small);
    this.choicePane.add(this.medium);
    this.choicePane.add(this.large);
    this.choicePane.add(this.valid);

    this.setLayout(new FlowLayout());
    this.add(this.playerPane);
    this.add(this.choicePane);
  }

  public void setPlayerText (String text)
  {
    this.playerText.setText(text);
  }

  public void setSelectionVisible ()
  {
    if(this.selectionField.isVisible())
    {
      this.selectionField.setVisible(false);
    } else {
      this.selectionField.setVisible(true);
    }
  }

  public void setPlayerList(String[] list)
  {
    this.listePlayer = list;
  }
}
