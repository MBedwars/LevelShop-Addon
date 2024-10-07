package de.marcely.bedwars.levelshop;

import de.marcely.bedwars.api.GameAPI;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class LevelShopPlugin extends JavaPlugin {

  private static final byte MBEDWARS_API_NUM = 113;
  private static final String MBEDWARS_API_NAME = "5.4.14";

  @Override
  public void onEnable() {
    if (!validateMBedwars())
      return;

    GameAPI.get().registerCustomSpawnerHandler(new LevelDropTypeHandler(this));
    Bukkit.getPluginManager().registerEvents(new LevelEventsHandler(), this);
  }


  private boolean validateMBedwars() {
    try {
      final Class<?> apiClass = Class.forName("de.marcely.bedwars.api.BedwarsAPI");
      final int apiVersion = (int) apiClass.getMethod("getAPIVersion").invoke(null);

      if (apiVersion < MBEDWARS_API_NUM)
        throw new IllegalStateException();
    } catch (Exception e) {
      getLogger().warning("Sorry, your installed version of MBedwars is not supported. Please install at least v" + MBEDWARS_API_NAME);
      Bukkit.getPluginManager().disablePlugin(this);

      return false;
    }

    return true;
  }
}
