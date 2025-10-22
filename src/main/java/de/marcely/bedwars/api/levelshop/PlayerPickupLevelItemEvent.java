package de.marcely.bedwars.api.levelshop;

import de.marcely.bedwars.api.arena.Arena;
import de.marcely.bedwars.api.event.arena.ArenaEvent;
import de.marcely.bedwars.api.game.spawner.DropType;
import de.marcely.bedwars.tools.Validate;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

/**
 * Gets called when a player picks up a spawner item that earns the player level.
 * <p>
 *   Cancelling this event results in the player not picking up the item and not
 *   earning levels at all.
 * </p>
 */
public class PlayerPickupLevelItemEvent extends PlayerEvent implements ArenaEvent, Cancellable {

  private static final HandlerList HANDLERS = new HandlerList();

  @Getter
  private final Arena arena;
  private final DropType dropType;
  private final Item item;

  private int perItemLevelAmount;
  @Getter @Setter
  private boolean cancelled = false;

  public PlayerPickupLevelItemEvent(Player player, Arena arena, DropType dropType, Item item, int perItemLevelAmount) {
    super(player);

    this.arena = arena;
    this.dropType = dropType;
    this.item = item;
    this.perItemLevelAmount = perItemLevelAmount;
  }

  public PlayerPickupLevelItemEvent(PlayerPickupLevelItemEvent event, Player player) { // Used by GenSplitter addon
    this(player, event.arena, event.dropType, event.item, event.perItemLevelAmount);
  }

  /**
   * Get the spawner/drop type the player is about to pick up.
   *
   * @return The drop type of the item
   */
  public DropType getDropType() {
    return this.dropType;
  }

  /**
   * Get the item that the player is attempting to pick up.
   *
   * @return The item the player attempts to pick up
   */
  public Item getItem() {
    return this.item;
  }

  /**
   * Get the amount of levels the player is about to earn per item.
   * <p>
   *   Total amount of levels he is going to earn is equal to this number multiplied
   *   with {@link org.bukkit.inventory.ItemStack#getAmount()} of {@link #getItem()}.
   * </p>
   *
   * @return The amount of levels he picked up for each item
   */
  public int getPerItemLevelAmount() {
    return this.perItemLevelAmount;
  }

  /**
   * Set the amount of levels the player is about to earn per item.
   * <p>
   *   Total amount of levels he is going to earn is equal to this number multiplied
   *   with {@link org.bukkit.inventory.ItemStack#getAmount()} of {@link #getItem()}.
   * </p>
   *
   * @param levelAmount The amount of levels he picked up for each item
   * @throws IllegalArgumentException levelAmount must be 0 or greater
   */
  public void setPerItemLevelAmount(int levelAmount) {
    Validate.isTrue(levelAmount >= 0, "Level amount must be greater or equal to 0");

    this.perItemLevelAmount = levelAmount;
  }

  @Override
  public HandlerList getHandlers() {
    return HANDLERS;
  }

  public static HandlerList getHandlerList() {
    return HANDLERS;
  }
}
