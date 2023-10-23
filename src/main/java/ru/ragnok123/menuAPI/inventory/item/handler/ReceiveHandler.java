package ru.ragnok123.menuAPI.inventory.item.handler;

import cn.nukkit.Player;
import cn.nukkit.item.Item;

public abstract class ReceiveHandler {
	
	public abstract void receive(Player p, int slot, Item item);
	public abstract void send(Player p, int targetSlot, Item item);
	
}