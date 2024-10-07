package de.marcely.bedwars.levelshop;

import de.marcely.bedwars.api.GameAPI;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class LevelShopPlugin extends JavaPlugin {

  @Override
  public void onEnable() {
    GameAPI.get().registerCustomSpawnerHandler(new LevelDropTypeHandler(this));
    Bukkit.getPluginManager().registerEvents(new LevelEventsHandler(), this);
  }
}
