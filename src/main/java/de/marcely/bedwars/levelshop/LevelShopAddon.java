package de.marcely.bedwars.levelshop;

import de.marcely.bedwars.api.BedwarsAddon;

class LevelShopAddon extends BedwarsAddon {

  public LevelShopAddon(LevelShopPlugin plugin) {
    super(plugin);
  }

  @Override
  public String getName() {
    return "LevelShop";
  }
}
