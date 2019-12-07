package ru.ragnok123.menuAPI.inventory;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.inventory.InventoryClickEvent;
import cn.nukkit.event.inventory.InventoryCloseEvent;
import cn.nukkit.event.inventory.InventoryTransactionEvent;
import cn.nukkit.event.player.PlayerChatEvent;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.inventory.Inventory;
import cn.nukkit.inventory.transaction.InventoryTransaction;
import cn.nukkit.inventory.transaction.action.InventoryAction;
import cn.nukkit.inventory.transaction.action.SlotChangeAction;
import cn.nukkit.item.Item;
import lombok.NonNull;

import java.util.*;

public class InventoryMenuHandler implements Listener{
	
	public InventoryMenuHandler() {
	}
	
	public static HashMap<String, InventoryMenu> menus = new HashMap<String, InventoryMenu>();
	
	public static InventoryMenu getMenuById(@NonNull String id) {
		if(menus.containsKey(id)) {
			return menus.get(id);
		}
		return null;
	}
	
	public static InventoryMenu getMenuByPlayer(@NonNull UUID uuid) {
		for(InventoryMenu menu : menus.values()) {
			if(menu.getInventory(uuid) != null) {
				return menu;
			}
		}
		return null;
	}
	
	public static void registerNewMenu(String id, InventoryMenu menu) {
		if(!menus.containsKey(id)) {
			menus.put(id,menu);
		}
	}
	
	public static void unregisterMenu(String id) {
		if(menus.containsKey(id)) {
			menus.remove(id);
		}
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent event) {
		Player player = event.getPlayer();
		Inventory inv = event.getInventory();
		if(getMenuByPlayer(player.getUniqueId()) != null) {
			if(inv instanceof MenuInventory) {
				InventoryMenu menu = getMenuByPlayer(player.getUniqueId());
				int slot = event.getSlot();
				event.setCancelled();
				if(menu.getCurrentCategory().getItemClick(slot) != null) {
					menu.getCurrentCategory().getItemClick(slot).init(player, menu.getCurrentCategory().getItemData(slot), event);
					menu.getCurrentCategory().getItemClick(slot).onClick(player,event.getSourceItem());
				}
			}
		}
	}
	
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event) {
		Player player = event.getPlayer();
		Inventory inv = event.getInventory();
		if(getMenuByPlayer(player.getUniqueId()) != null) {
			InventoryMenu menu = getMenuByPlayer(player.getUniqueId());
			menu.destroy(player);
		}
	}
	
	@EventHandler
	public void onTransaction(InventoryTransactionEvent event) {
		InventoryTransaction trans = event.getTransaction();
		for (InventoryAction action : trans.getActions()) {
			if (action instanceof SlotChangeAction) {
				SlotChangeAction act = (SlotChangeAction) action;
				if (act.getInventory().getHolder() instanceof Player) {
					Player p = (Player) act.getInventory().getHolder();
					if(getMenuByPlayer(p.getUniqueId()) != null) {
						InventoryMenu menu = getMenuByPlayer(p.getUniqueId());
						if(menu.onlyRead()) {
							event.setCancelled();
						}
					}
				}
			}
		}
	}
	
}
