package de.marcely.bedwars.levelshop;

import de.marcely.bedwars.api.game.spawner.DropType;
import org.bukkit.configuration.ConfigurationSection;

public class SpawnerConfig {

  public static int getItemLvlAmount(DropType type) {
    final ConfigurationSection config = type.getCustomHandlerConfig();

    if (config == null)
      return 0;

    return config.getInt("item-level-amount", 0);
  }
}
