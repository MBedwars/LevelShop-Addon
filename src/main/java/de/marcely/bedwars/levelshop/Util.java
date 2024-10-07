package de.marcely.bedwars.levelshop;

public class Util {

  public static int getTotalExp(int level) {
    if (level >= 32)
      return (int) (4.5D*level*level - 162.5*level + 2220);
    else if (level >= 17)
      return (int) (2.5D*level*level - 40.5*level + 360);
    else
      return level*level + 6*level;
  }
}
