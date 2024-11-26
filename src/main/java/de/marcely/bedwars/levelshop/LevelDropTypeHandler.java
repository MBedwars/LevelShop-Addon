package de.marcely.bedwars.levelshop;

import de.marcely.bedwars.api.game.spawner.CustomDropTypeHandler;
import de.marcely.bedwars.api.game.spawner.Spawner;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;

class LevelDropTypeHandler extends CustomDropTypeHandler {

  LevelDropTypeHandler(LevelShopPlugin plugin) {
    super("lvl-shop", plugin);
  }

  @Override
  public void handleDrop(Spawner spawner, Location dropLocation) {
    final int amount = getDropAmount(spawner);
    ExperienceOrb orb = getNearbyOrb(dropLocation);

    if (orb == null) {
      orb = (ExperienceOrb) dropLocation.getWorld().spawnEntity(dropLocation, EntityType.EXPERIENCE_ORB);
      orb.setExperience(amount);

    } else
      orb.setExperience(orb.getExperience() + amount);
  }

  // First try to merge them together. Otherwise, they get merged but their amount doesn't get added together
  private static ExperienceOrb getNearbyOrb(Location location) {
    return location.getWorld().getNearbyEntities(location, 2, 2, 2).stream()
      .filter(entity -> entity.getType() == EntityType.EXPERIENCE_ORB)
      .map(entity -> (ExperienceOrb) entity)
      .findFirst()
      .orElse(null);
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
