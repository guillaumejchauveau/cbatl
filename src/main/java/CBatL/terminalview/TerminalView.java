package cbatl.terminalview;

import java.util.*;


public class TerminalView{

  public StringBuilder s1;
  public StringBuilder s2;
  public int length;

  public TerminalView(int x)
  {
    this.length = x;
    StringBuilder s1 = new StringBuilder("   ");
    for(int c  = 0; c < x ; c++)
    {
      s1.append((char)(c + 65));
      if(c < x-1)
      {
        s1.append(" ");
      }
    }
    s1.append(System.lineSeparator());
    for(int i = 1; i <= x; i++)
    {
      s1.append(i + " ");
      for(int y = 0; y < x; y++)
      {
        s1.append("  ");
      }
      s1.append(System.lineSeparator());
    }

    StringBuilder s2 = new StringBuilder(s1.toString());
    this.s1 = s1;
    this.s2 = s2;
  }

  public void insert(int x, int y, int table)
  {
    int index = 2*x*(this.length+2)+ 2*y + 1;
    if(table == 0)
    {
      if(s1.charAt(index) == 'O' ||s1.charAt(index) == 'X')
      {
        System.err.println("Mauvaise case");
      }
      if(s1.charAt(index) == ' ')
      {
        s1.replace(index, index+1, "O");
      }
      else
      {
        s1.replace(index, index+1, "X");
      }
    }

    if(table == 1)
    {
      if(s2.charAt(index) == 'O' ||s2.charAt(index) == 'X')
      {
        System.err.println("Mauvaise case");
      }
      if(s2.charAt(index) == ' ')
      {
        s2.replace(index, index+1, "O");
      }
      else
      {
        s2.replace(index, index+1, "X");
      }
    }
  }

  public void insert(int x, char c, int table)
  {
    int y = Character.getNumericValue(c) - 9;
    this.insert(x, y, table);
  }

  @Override
  public String toString()
  {
    String legende = new String(
      "legende :  O plouf" + System.lineSeparator()+
      "           X boom " + System.lineSeparator()+
      "           / boat " + System.lineSeparator()
    );
    return this.s1.toString() + System.lineSeparator() + this.s2.toString() +System.lineSeparator() + legende;
  }

  public static void main(String[] arg)
  {
    TerminalView t1 = new TerminalView(7); // -> 7 de length;
    t1.insert(2, 'G', 0);
    System.out.println(t1);
  }
}
