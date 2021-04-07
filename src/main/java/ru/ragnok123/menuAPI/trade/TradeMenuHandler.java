package ru.ragnok123.menuAPI.trade;

import java.util.HashMap;
import java.util.UUID;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.inventory.InventoryClickEvent;
import cn.nukkit.event.inventory.InventoryCloseEvent;
import cn.nukkit.event.inventory.InventoryTransactionEvent;
import cn.nukkit.event.player.PlayerChatEvent;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.event.server.DataPacketReceiveEvent;
import cn.nukkit.inventory.Inventory;
import cn.nukkit.inventory.transaction.InventoryTransaction;
import cn.nukkit.inventory.transaction.action.InventoryAction;
import cn.nukkit.inventory.transaction.action.SlotChangeAction;
import cn.nukkit.item.Item;
import cn.nukkit.network.protocol.ContainerClosePacket;
import cn.nukkit.network.protocol.InventoryTransactionPacket;
import cn.nukkit.network.protocol.types.ContainerIds;
import cn.nukkit.network.protocol.types.NetworkInventoryAction;
import lombok.NonNull;
import ru.ragnok123.menuAPI.inventory.utils.TradeInventoryAction;

import java.util.*;

public class TradeMenuHandler implements Listener {
	
	public static HashMap<String, TradeMenu> menus = new HashMap<String, TradeMenu>();
	public static HashMap<UUID, TradeMenu> pmenus = new HashMap<UUID, TradeMenu>();
	
	public static TradeMenu getMenuById(@NonNull String id) {
		if(menus.containsKey(id)) {
			return menus.get(id);
		}
		return null;
	}
	
	public static TradeMenu getMenuByPlayer(@NonNull UUID uuid) {
		if(pmenus.containsKey(uuid)) {
			return pmenus.get(uuid);
		}
		return null;
	}
	
	public static void registerNewMenu(String id, TradeMenu menu) {
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
	public void quit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		if(getMenuByPlayer(p.getUniqueId()) != null) {
			getMenuByPlayer(p.getUniqueId()).forceClose(p);
		}
	}
	
	@EventHandler
	public void handleTransaction(DataPacketReceiveEvent e) {
		Player p = e.getPlayer();
		if(e.getPacket() instanceof InventoryTransactionPacket) {
			InventoryTransactionPacket pk = (InventoryTransactionPacket)e.getPacket();

			List<NetworkInventoryAction> actions = new ArrayList<NetworkInventoryAction>();
			boolean edited = false;
			
			for(NetworkInventoryAction action : pk.actions) {
				NetworkInventoryAction newAction = action;
				if(action.windowId == ContainerIds.UI && (action.inventorySlot == 4 || action.inventorySlot == 5 || action.inventorySlot == 50)) {
					if(action.inventorySlot == 50) {
						if(getMenuByPlayer(p.getUniqueId()) != null) {
							action.inventorySlot = 51;
						} else {
							actions.add(newAction);
							continue;
						}
					}
					newAction = new TradeInventoryAction(false);
					newAction.inventorySlot = action.inventorySlot;
					newAction.windowId = action.windowId;
					newAction.oldItem = action.oldItem;
					newAction.newItem = action.newItem;
					newAction.sourceType = action.sourceType;
					newAction.unknown = action.unknown;
					edited = true;
				}
				if(action.sourceType == NetworkInventoryAction.SOURCE_TODO && (action.windowId == -30 || action.windowId == -31)) {
					newAction = new TradeInventoryAction(true);
					newAction.inventorySlot = action.inventorySlot;
					newAction.windowId = action.windowId;
					newAction.oldItem = action.oldItem;
					newAction.newItem = action.newItem;
					newAction.sourceType = action.sourceType;
					newAction.unknown = action.unknown;
					edited = true;
				}
				actions.add(newAction);
			}
			if(edited) {
				NetworkInventoryAction[] networkActions = new NetworkInventoryAction[actions.size()];
				for(int i = 0; i < actions.size(); i++) {
					networkActions[i] = actions.get(i);
				}
				pk.actions = networkActions;
			}
		} else if(e.getPacket() instanceof ContainerClosePacket) {
			ContainerClosePacket pk = (ContainerClosePacket)e.getPacket();
			if(getMenuByPlayer(p.getUniqueId()) != null && pk.windowId == p.getWindowId(getMenuByPlayer(p.getUniqueId()).getInventory(p))) {
				getMenuByPlayer(p.getUniqueId()).getCloseHandler().accept(p);
			}
		}
	}
	
}