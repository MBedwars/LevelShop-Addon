package de.marcely.bedwars.levelshop;

import de.marcely.bedwars.api.game.spawner.CustomDropTypeHandler;
import de.marcely.bedwars.api.game.spawner.Spawner;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;

public class LevelDropTypeHandler extends CustomDropTypeHandler {

  LevelDropTypeHandler(LevelShopPlugin plugin) {
    super("lvl-shop", plugin);
  }

  @Override
  public void handleDrop(Spawner spawner, Location dropLocation) {
    final ExperienceOrb orb = (ExperienceOrb) dropLocation.getWorld().spawnEntity(dropLocation, EntityType.EXPERIENCE_ORB);

    orb.setExperience(getDropAmount(spawner)); // 1 exp = 1 level
  }

  private static int getDropAmount(Spawner spawner) {
    final ConfigurationSection config = spawner.getDropType().getCustomHandlerConfig();

    if (config == null)
      return 1;

    return Math.max(1, config.getInt("level-amount", 1));
  }

  @Override
  public int getHoldingAmount(Player player) {
    return player.getLevel();
  }

  @Override
  public void take(Player player, int amount) {
    player.setLevel(Math.max(0, player.getLevel() - amount));
  }

  @Override
  public void give(Player player, int amount) {
    player.setLevel(player.getLevel() + amount);
  }
}
