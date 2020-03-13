package ru.ragnok123.menuAPI;

import cn.nukkit.plugin.PluginBase;
import ru.ragnok123.menuAPI.form.FormMenuHandler;
import ru.ragnok123.menuAPI.inventory.InventoryMenuHandler;

public class MenuMain extends PluginBase {
	
	public void onEnable() {
		InventoryMenuHandler handler = new InventoryMenuHandler();
		FormMenuHandler handler2 = new FormMenuHandler();
		getServer().getPluginManager().registerEvents(handler,this);
		getServer().getPluginManager().registerEvents(handler2,this);
	}
	
}
