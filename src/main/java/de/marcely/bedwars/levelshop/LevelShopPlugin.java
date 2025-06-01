package de.marcely.bedwars.levelshop;

import de.marcely.bedwars.api.GameAPI;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class LevelShopPlugin extends JavaPlugin {

  private static final short MBEDWARS_API_NUM = 201;
  private static final String MBEDWARS_API_NAME = "5.5.1";

  @Override
  public void onEnable() {
    if (!validateMBedwars())
      return;

    new LevelShopAddon(this).register();
    GameAPI.get().registerCustomSpawnerHandler(new LevelDropTypeHandler(this));

    Bukkit.getPluginManager().registerEvents(new LevelEventsHandler(this), this);
    Bukkit.getPluginManager().registerEvents(new ShopAbuseFix(), this);
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
