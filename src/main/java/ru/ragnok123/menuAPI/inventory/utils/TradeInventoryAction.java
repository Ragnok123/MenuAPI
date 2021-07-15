package ru.ragnok123.menuAPI.inventory.utils;

import cn.nukkit.Player;
import cn.nukkit.inventory.Inventory;
import cn.nukkit.inventory.transaction.action.InventoryAction;
import cn.nukkit.inventory.transaction.action.SlotChangeAction;
import cn.nukkit.network.protocol.types.NetworkInventoryAction;
import ru.ragnok123.menuAPI.trade.TradeMenuHandler;

public class TradeInventoryAction extends NetworkInventoryAction{
	
	public boolean fake;
	
	public TradeInventoryAction(boolean fake) {
		this.fake = fake;
	}
	
	@Override
	public InventoryAction createInventoryAction(Player p) {
		if(oldItem.equalsExact(newItem)) {
			return null;
		}
		if(!this.fake && this.sourceType == SOURCE_CONTAINER) {
			Inventory window = null;
			int slot = this.inventorySlot;
			if(this.windowId == 124 && (slot == 4 || slot == 5)) {
				window = TradeMenuHandler.getMenuByPlayer(p.getUniqueId()).getInventory(p);
				slot -= 4;
			}
			if(this.windowId == 124 && slot == 51) {
				window = TradeMenuHandler.getMenuByPlayer(p.getUniqueId()).getInventory(p);
				slot -= 49;
			}
			if(window != null) {
				return new SlotChangeAction(window, slot, this.oldItem, this.newItem);
			}
		} else if(this.fake && this.sourceType == SOURCE_TODO) {
			if(this.windowId == -30 || this.windowId == -31) {
				return new TradeAction(this.oldItem, this.newItem);
			}
		}
		return null;
	}
	
}