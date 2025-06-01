package de.marcely.bedwars.levelshop;

import de.marcely.bedwars.api.GameAPI;
import de.marcely.bedwars.api.arena.Arena;
import de.marcely.bedwars.api.event.player.PlayerOpenShopEvent;
import de.marcely.bedwars.api.event.player.PlayerUseSpecialItemEvent;
import de.marcely.bedwars.api.game.specialitem.SpecialItemType;
import de.marcely.bedwars.api.game.specialitem.SpecialItemUseSession;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.Nullable;

// As the teleporter special item uses the exp bar as its countdown,
// players may abuse it to buy items from the shop
class ShopAbuseFix implements Listener {

  @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
  public void onPlayerOpenShop(PlayerOpenShopEvent event) {
    if (event.getArena() == null)
      return;

    final SpecialItemUseSession session = getTeleportSession(event.getPlayer(), event.getArena());

    if (session == null)
      return;

    session.stop(); // stop the teleportation immediately
  }

  @Nullable
  private SpecialItemUseSession getTeleportSession(Player player, Arena arena) {
    // fast check if it's even worth the effort
    if (GameAPI.get().getLastTeleporterSpecialItemUse(player) == null)
      return null;

    // deep look up
    for (SpecialItemUseSession session : arena.getSpecialItemSessions()) {
      final PlayerUseSpecialItemEvent event = session.getEvent();

      if (event.getPlayer() != player || event.getSpecialItem().getType() != SpecialItemType.TELEPORTER)
        continue;

      return session;
    }

    return null;
  }
}
