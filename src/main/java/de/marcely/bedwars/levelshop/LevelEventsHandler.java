package de.marcely.bedwars.levelshop;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;

public class LevelEventsHandler implements Listener {

  @EventHandler
  public void onPlayerExpChangeEvent(PlayerExpChangeEvent event) {
    if (event.getAmount() == 0)
      return;

    final Player player = event.getPlayer();
    final int addExp = Util.getTotalExp(event.getAmount()+player.getLevel()) - player.getTotalExperience();

    event.setAmount(addExp);
  }
}
