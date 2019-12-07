package ru.ragnok123.menuAPI.inventory;

import cn.nukkit.Player;
import cn.nukkit.event.inventory.InventoryClickEvent;
import cn.nukkit.item.Item;

public abstract class ItemClick {
	
	private Player player = null;
	private ItemData item = null;
	private InventoryClickEvent event = null;
	
	public void init(Player player, ItemData item, InventoryClickEvent event) {
		this.player = player;
		this.item = item;
		this.event = event;
	}
	
	public abstract void onClick(Player player,Item item);
	
}
