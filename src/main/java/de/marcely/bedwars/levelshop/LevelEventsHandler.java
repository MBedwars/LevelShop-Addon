package de.marcely.bedwars.levelshop;

import de.marcely.bedwars.api.GameAPI;
import de.marcely.bedwars.api.arena.Arena;
import de.marcely.bedwars.api.arena.ArenaStatus;
import de.marcely.bedwars.levelshop.api.PlayerPickupOrbEvent;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;

@AllArgsConstructor
class LevelEventsHandler implements Listener {

  private final LevelShopPlugin plugin;

  @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
  public void onPlayerExpChangeEvent(PlayerExpChangeEvent event) {
    if (event.getAmount() <= 0)
      return;

    final Player player = event.getPlayer();
    final Arena arena = GameAPI.get().getArenaByPlayer(player);

    if (arena == null || (arena.getStatus() != ArenaStatus.RUNNING && arena.getStatus() != ArenaStatus.END_LOBBY) ||
        player.getGameMode() == GameMode.SPECTATOR) {
      return;
    }

    // ask api
    final PlayerPickupOrbEvent apiEvent = new PlayerPickupOrbEvent(player, arena, event.getAmount());

    Bukkit.getPluginManager().callEvent(apiEvent);

    final int level = apiEvent.getLevelAmount();

    if (level == 0) {
      event.setAmount(0);
      return;
    }

    // Hotfix: level and total exp desynced
    // Yes, they are internally two different variables that bukkit doesn't update properly
    if (player.getExp() == 0 && player.getLevel() == 0 && player.getTotalExperience() != 0)
      player.setTotalExperience(0);

    final int addExp = Util.getTotalExp(level+player.getLevel()) - player.getTotalExperience();

    event.setAmount(addExp);

    // Hotfix: Exp rounding errors (vanilla problem?)
    Bukkit.getScheduler().runTask(this.plugin, () -> {
      if (arena.getPlayerTeam(player) == null || player.getGameMode() == GameMode.SPECTATOR)
        return;

      if (player.getExpToLevel() == 0 || player.getExp() >= 0.99F) {
        player.setLevel(player.getLevel()+1);
        player.setExp(0);
      }
    });
  }
}
