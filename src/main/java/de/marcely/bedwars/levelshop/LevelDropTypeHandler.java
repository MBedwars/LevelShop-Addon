package de.marcely.bedwars.levelshop;

import de.marcely.bedwars.api.game.spawner.CustomDropTypeHandler;
import de.marcely.bedwars.api.game.spawner.Spawner;
import org.bukkit.Location;
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

    orb.setExperience(1); // 1 exp = 1 level
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
