package CBatL.terminalview;

import java.util.*;


public class TerminalView{

  public TerminalView(int x)
  {
    StringBuilder s1 = new StringBuilder("   ");
    for(int c  = 0; c < x ; c++)
    {
      s1.append((char)(c + 65));
      if(c < x-1)
      {
        s1.append("  ");
      }
    }
    s1.append(System.lineSeparator());
    System.out.println(s1.length());
    /*StringBuilder s1 = new StringBuilder(
    "   A  B  C  D  E  F  G  H  I  J" + System.lineSparator() +
    "1                             " + System.lineSparator() +
    "2                             " + System.lineSparator() +
    "3                             " + System.lineSparator() +
    "4                             " + System.lineSparator() +
    "5                             " + System.lineSparator() +
    "6                             " + System.lineSparator() +
    "7                             " + System.lineSparator() +
    "8                             " + System.lineSparator() +
    "9                             " + System.lineSparator() +
    "10                             " + System.lineSparator() +

*/
  }

  public static void main(String[] arg)
  {
    TerminalView t1 = new TerminalView(2); // -> 8 de length;
  }
}
