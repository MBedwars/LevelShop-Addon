package de.marcely.bedwars.levelshop;

import de.marcely.bedwars.tools.Helper;
import java.util.concurrent.ThreadLocalRandom;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class Util {

  private static final Sound PICKUP_SOUND = Helper.get().getSoundByName("ENTITY_EXPERIENCE_ORB_PICKUP");
  private static final Sound LEVELUP_SOUND = Helper.get().getSoundByName("ENTITY_PLAYER_LEVELUP");

  public static int getTotalExp(int level) {
    if (level >= 32)
      return (int) (4.5D*level*level - 162.5*level + 2220);
    else if (level >= 17)
      return (int) (2.5D*level*level - 40.5*level + 360);
    else
      return level*level + 6*level;
  }

  public static void playLevelSound(Player player, Location loc, int newLevel) {
    // volume and pitch copied from server code
    if (PICKUP_SOUND != null)
      loc.getWorld().playSound(loc, PICKUP_SOUND, .1F, ThreadLocalRandom.current().nextFloat() * .7f + .55f);

    if (newLevel%5 == 0 && LEVELUP_SOUND != null) {
      final float f = (newLevel > 30) ? 1.0F : (newLevel / 30.0F);

      player.playSound(loc, LEVELUP_SOUND, f * .75f, 1);
    }
  }

  public static void setEarnedLevel(Player player, Location loc, int newLevel) {
    final int prevLevel = player.getLevel();

    player.setLevel(newLevel);
    player.setExp(0);
    player.setTotalExperience(getTotalExp(newLevel)); // fix desyncs

    if (newLevel > prevLevel)
      playLevelSound(player, loc, newLevel);
  }
}
