package de.marcely.bedwars.levelshop.api;

import de.marcely.bedwars.api.arena.Arena;
import de.marcely.bedwars.api.event.arena.ArenaEvent;
import de.marcely.bedwars.tools.Validate;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

/**
 * Gets called when a player picks up an orb and thereby earns level.
 * <p>
 *   It is not possible to cancel or obtain the orb entity, as the bukkit API
 *   doesn't provide an easy solution for that either.
 * </p>
 */
public class PlayerPickupOrbEvent extends PlayerEvent implements ArenaEvent {

  private static final HandlerList HANDLERS = new HandlerList();

  @Getter
  private final Arena arena;

  private int levelAmount;

  public PlayerPickupOrbEvent(Player player, Arena arena, int levelAmount) {
    super(player);

    this.arena = arena;
    this.levelAmount = levelAmount;
  }

  public PlayerPickupOrbEvent(PlayerPickupOrbEvent event, Player player) { // Used by GenSplitter addon
    this(player, event.arena, event.levelAmount);
  }

  /**
   * Get the amount of levels the player is about to earn.
   *
   * @return The amount of levels he picked up
   */
  public int getLevelAmount() {
    return this.levelAmount;
  }

  /**
   * Set the amount of levels the player is about to earn.
   *
   * @param levelAmount The amount of levels he picked up
   * @throws IllegalArgumentException levelAmount must be 0 or greater
   */
  public void setLevelAmount(int levelAmount) {
    Validate.isTrue(levelAmount >= 0, "Level amount must be greater or equal to 0");

    this.levelAmount = levelAmount;
  }

  @Override
  public HandlerList getHandlers() {
    return HANDLERS;
  }

  public static HandlerList getHandlerList() {
    return HANDLERS;
  }
}
