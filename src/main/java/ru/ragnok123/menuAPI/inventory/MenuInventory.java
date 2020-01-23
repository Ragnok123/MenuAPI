package ru.ragnok123.menuAPI.inventory;

import cn.nukkit.inventory.ContainerInventory;
import cn.nukkit.inventory.Inventory;
import cn.nukkit.inventory.InventoryHolder;
import cn.nukkit.inventory.InventoryType;
import cn.nukkit.level.BlockPosition;
import cn.nukkit.math.Vector3f;

import java.util.Map;

public final class MenuInventory extends ContainerInventory {
	public MenuInventory(BlockPosition holder, InventoryMenu menu) {
		super(new Holder(holder.x, holder.y, holder.z), InventoryType.CHEST);
		InventoryCategory category = menu.getMainCategory();
		for(Map.Entry<Integer,ItemData> entry : category.itemDataMap().entrySet()) {
			int position = entry.getKey();
			ItemData data = entry.getValue();
			setItem(position, data.build());
		}
	}

	static final class Holder extends Vector3f implements InventoryHolder {

		private Holder(double x, double y, double z) {
			super(x, y, z);
		}

		@Override
		public Inventory getInventory() {
			return null;
		}
	}
}