package ru.ragnok123.menuAPI.inventory.utils;

import java.util.Map;

import cn.nukkit.Player;
import cn.nukkit.inventory.InventoryType;
import cn.nukkit.math.Vector3;
import ru.ragnok123.menuAPI.inventory.InventoryCategory;
import ru.ragnok123.menuAPI.inventory.InventoryMenu;
import ru.ragnok123.menuAPI.inventory.item.ItemData;

public class MenuDoubleInventory extends MenuInventory {

	public MenuDoubleInventory(Player owner, Vector3 holder, InventoryMenu menu) {
		super(owner, holder, menu, InventoryType.DOUBLE_CHEST);
		InventoryCategory category = menu.getMainCategory();
		for(Map.Entry<Integer,ItemData> entry : category.itemDataMap().entrySet()) {
			int position = entry.getKey();
			ItemData data = entry.getValue();
			setItem(position, data.build());
		}
	}

}
