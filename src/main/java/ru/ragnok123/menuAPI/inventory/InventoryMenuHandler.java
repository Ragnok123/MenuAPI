package ru.ragnok123.menuAPI.inventory;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.inventory.InventoryClickEvent;
import cn.nukkit.event.inventory.InventoryCloseEvent;
import cn.nukkit.event.inventory.InventoryTransactionEvent;
import cn.nukkit.event.player.PlayerChatEvent;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.inventory.Inventory;
import cn.nukkit.inventory.PlayerInventory;
import cn.nukkit.inventory.transaction.InventoryTransaction;
import cn.nukkit.inventory.transaction.action.InventoryAction;
import cn.nukkit.inventory.transaction.action.SlotChangeAction;
import cn.nukkit.item.Item;
import lombok.NonNull;
import ru.ragnok123.menuAPI.inventory.utils.MenuInventory;

import java.util.*;

public class InventoryMenuHandler implements Listener{
	
	public static HashMap<String, InventoryMenu> menus = new HashMap<String, InventoryMenu>();
	public static HashMap<UUID, InventoryMenu> pmenus = new HashMap<UUID, InventoryMenu>();
	
	public static InventoryMenu getMenuById(@NonNull String id) {
		if(menus.containsKey(id)) {
			return menus.get(id);
		}
		return null;
	}
	
	public static InventoryMenu getMenuByPlayer(@NonNull UUID uuid) {
		if(pmenus.containsKey(uuid)) {
			return pmenus.get(uuid);
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
				if(menu.getCurrentCategory().getItemClick(slot) != null) {
					event.setCancelled();
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
				if(act.getInventory() instanceof MenuInventory) {
					Server.getInstance().getLogger().info("It was chest");
					MenuInventory inv = (MenuInventory) act.getInventory();
					if(inv.getOwner() instanceof Player) {
						Player p = inv.getOwner();
						if(getMenuByPlayer(p.getUniqueId()) != null) {
							InventoryMenu menu = getMenuByPlayer(p.getUniqueId());
							if(menu.getCurrentCategory().onlyRead()) {
								event.setCancelled();
								return;
							}
							if(menu.getCurrentCategory().getItemClick(act.getSlot()) != null) {
								event.setCancelled();
								menu.getCurrentCategory().getItemClick(act.getSlot()).init(p, menu.getCurrentCategory().getItemData(act.getSlot()));
								menu.getCurrentCategory().getItemClick(act.getSlot()).onClick(p,act.getSourceItem());
								return;
							}
							Item it = act.getTargetItem();
							menu.getCurrentCategory().getHandler().receive(p, act.getSlot(), it);
							menu.getCurrentCategory().addElement(act.getSlot(),it);
							menu.updateContents(p);
						}
					}
				}
			}
		}
	}
	
}
