package de.marcely.bedwars.levelshop.api;

import de.marcely.bedwars.api.game.spawner.CustomDropTypeHandler;
import de.marcely.bedwars.api.game.spawner.DropType;
import de.marcely.bedwars.levelshop.LevelShopPlugin;
import de.marcely.bedwars.levelshop.SpawnerConfig;
import de.marcely.bedwars.levelshop.Util;
import de.marcely.bedwars.tools.Validate;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * Base-API class for the LevelShop addon.
 */
public class LevelShopAPI {

  private static final LevelShopAPI INSTANCE =  new LevelShopAPI();

  private LevelShopAPI() { }

  /**
   * Calculates the total needed EXP needed to reach this level.
   *
   * @param level The level to be reached
   * @return The amount of total EXP needed
   */
  public int getTotalExp(int level) {
    return Util.getTotalExp(level);
  }

  /**
   * Plays orb and level sounds the same way vanilla does it.
   *
   * @param player The player who reached the level
   * @param loc The location where the orb was picked up from
   * @param newLevel The level that was reached
   */
  public void playLevelSound(Player player, Location loc, int newLevel) {
    Validate.notNull(player, "player");
    Validate.notNull(loc, "loc");

    Util.playLevelSound(player, loc, newLevel);
  }

  /**
   * Changes the player's level while playing vanilla's orb and level sounds.
   * <p>
   *   Use this over {@link Player#setLevel(int)} since Bukkit's API expects you to
   *   also set multiple attributes, which can cause desyncs (poor API). This method
   *   keeps care of that.
   * </p>
   * <p>
   *   Orb and level sounds are only played if the player's level actually increases.
   * </p>
   *
   * @param player The player who reached the level
   * @param loc The location where the orb was picked up from
   * @param newLevel The level that was reached
   */
  public void setEarnedLevel(Player player, Location loc, int newLevel) {
    Validate.notNull(player, "player");
    Validate.notNull(loc, "loc");

    Util.setEarnedLevel(player, loc, newLevel);
  }

  /**
   * Get the amount of orbs (in levels) a given drop type will drop.
   * <p>
   *   The value depends on the server's configuration.
   * </p>
   *
   * @param dropType The drop type to check
   * @return The amount of levels it will drop or 0 if it doesn't drop any
   */
  public int getDroppingOrbsLvlAmount(DropType dropType) {
    Validate.notNull(dropType, "dropType");

    if (dropType.getCustomHandler() != LevelShopPlugin.INSTANCE.getDropTypeHandler())
      return 0;

    return SpawnerConfig.getOrbLvlAmount(dropType);
  }

  /**
   * Get the amount of level a player will earn if it will pick up a dropped item.
   *
   * @param dropType The drop type to check that drops items
   * @return The amount of level the player will earn or 0 if he doesn't earn any
   */
  public int getPickupItemLvlAmount(DropType dropType) {
    Validate.notNull(dropType, "dropType");

    return Math.max(0, SpawnerConfig.getItemLvlAmount(dropType));
  }

  /**
   * Get the drop handler that is used for spawners to drop orbs.
   *
   * @return The global instance used to replace a spawner's dropping functionality
   * @see DropType#setCustomHandler(CustomDropTypeHandler)
   */
  public CustomDropTypeHandler getOrbsDropHandler() {
    return LevelShopPlugin.INSTANCE.getDropTypeHandler();
  }

  /**
   * Get the global instance of its API.
   *
   * @return The global instance
   */
  public static LevelShopAPI get() {
    if (LevelShopPlugin.INSTANCE == null)
      throw new IllegalStateException("Plugin \"MBedwars-LevelShop\" is not enabled");

    return INSTANCE;
  }
}
