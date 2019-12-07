package ru.ragnok123.menuAPI;

import cn.nukkit.plugin.PluginBase;
import ru.ragnok123.menuAPI.inventory.InventoryMenuHandler;

public class MenuMain extends PluginBase {
	
	public void onEnable() {
		InventoryMenuHandler handler = new InventoryMenuHandler();
		getServer().getPluginManager().registerEvents(handler,this);
	}
	
}
