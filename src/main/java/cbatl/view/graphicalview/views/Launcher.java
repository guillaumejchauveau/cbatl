package cbatl.view.graphicalview.views;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Launcher extends JPanel
{
  private JFrame frame;
  private LayoutManager manager;
  private JButton play;
  private JPanel back;
  private JLabel titleField;
  private Image backImg;
  private int width;
  private int height;

  public Launcher (JFrame frame)
  {
    this.frame = frame;

    try
    {
      this.backImg = ImageIO.read(new File(this.getClass().getClassLoader().getResource("launcher_view.jpg").getFile()));
      this.width = ((BufferedImage) this.backImg).getWidth();
      this.height = ((BufferedImage) this.backImg).getHeight();
      this.frame.setSize(new Dimension(this.width, this.height));

    } catch (IOException | NullPointerException ioe) {
      System.out.println("Error on load");
    }

    this.titleField = new JLabel("BATTLESHIP");
    this.titleField.setAlignmentX(Component.CENTER_ALIGNMENT);
    this.titleField.setFont(new Font("Impact", Font.PLAIN, 60));
    this.titleField.setForeground(new Color(80,28,130));
    this.titleField.setOpaque(false);

    this.play = new JButton("Launch");
    
    this.play.setBackground(new Color(75, 175, 150));
    this.play.setForeground(new Color(255, 255, 200));
    this.play.setFocusPainted(false);
    this.play.setAlignmentX(Component.CENTER_ALIGNMENT);
    this.play.setPreferredSize(new Dimension(200, 35));

    JPanel buttonConatainer = new JPanel();
    buttonConatainer.setLayout(new FlowLayout());
    buttonConatainer.setOpaque(false);
    buttonConatainer.add(this.play);

    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    this.add(this.titleField);
    this.add(buttonConatainer, JFrame.BOTTOM_ALIGNMENT);
  }

  public void paintComponent (Graphics g)
  {
    super.paintComponent(g);
    if(this.backImg != null)
    {
      g.drawImage(this.backImg, 0, 0, this);
    }
  }
  public final JButton getPlayButton ()
  {
    return  this.play;
  }
}
