package ru.ragnok123.menuAPI.inventory.utils;

import java.io.IOException;
import java.nio.ByteOrder;
import java.util.HashMap;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.data.EntityMetadata;
import cn.nukkit.inventory.BaseInventory;
import cn.nukkit.inventory.InventoryHolder;
import cn.nukkit.inventory.InventoryType;
import cn.nukkit.item.Item;
import cn.nukkit.nbt.NBTIO;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.network.protocol.AddEntityPacket;
import cn.nukkit.network.protocol.ContainerClosePacket;
import cn.nukkit.network.protocol.RemoveEntityPacket;
import cn.nukkit.network.protocol.UpdateTradePacket;
import ru.ragnok123.menuAPI.trade.TradeMenu;

public class TradeInventory extends BaseInventory {

	public TradeMenu menu;
	
	public TradeInventory(InventoryHolder holder, TradeMenu menu) {
		super(holder, InventoryType.CHEST, new HashMap<>(), 3);
		this.menu = menu;
	}
	
	public void onOpen(Player player) {
		super.onOpen(player);
		long eid = menu.getUid();
		
		AddEntityPacket aep = new AddEntityPacket();
		aep.id = "minecraft:npc";
		aep.entityRuntimeId = eid;
		aep.entityUniqueId = eid;
		aep.x = (float) player.getX();
		aep.y = (float) (player.getY() - 2);
		aep.z = (float) player.getZ();
		aep.yaw = 0;
		aep.pitch = 0;
		aep.headYaw = 0;
		aep.metadata = new EntityMetadata()
				.putInt(Entity.DATA_TRADE_TIER, menu.getTraderTier())
				.putInt(Entity.DATA_TRADE_EXPERIENCE, menu.getExperience())
				.putInt(Entity.DATA_MAX_TRADE_TIER, menu.getMaxTradeTier())
				.putLong(Entity.DATA_TRADING_PLAYER_EID, player.getId())
				.putFloat(Entity.DATA_SCALE, 0.01F);
		player.dataPacket(aep);
		
		UpdateTradePacket pk = new UpdateTradePacket();
		pk.windowId = (byte) player.getWindowId(this);
		pk.windowType = (byte) 15;
		pk.size = 0;
		pk.displayName = menu.getTraderName();
		pk.newTradingUi = true;
		pk.usingEconomyTrade = true;
		pk.traderUniqueEntityId = eid;
		pk.tradeTier = menu.getTraderTier();
		pk.playerUniqueEntityId = player.getId();
		try {
			pk.offers = NBTIO.write(menu.getOffers(),ByteOrder.LITTLE_ENDIAN, true);
		} catch(IOException ex) {}
		player.dataPacket(pk);
	}
	
	public void onClose(Player player) {
		for(int i = 0; i <= 1; i++) {
			Item item = getItem(i);
			if(player.getInventory().canAddItem(item)) {
				player.getInventory().addItem(item);
			} else {
				player.dropItem(item);
			}
			this.clear(i);
		}
		
		ContainerClosePacket close = new ContainerClosePacket();
		close.windowId = player.getWindowId(this);
		close.wasServerInitiated = true;
		player.dataPacket(close);
		
		RemoveEntityPacket pk = new RemoveEntityPacket();
		pk.eid = menu.getUid();
		player.dataPacket(pk);
		
		super.onClose(player);
	}
	
	public String getName() {
		return "Trade Inventory";
	}
	
	public Player getHolder() {
		return (Player) this.holder;
	}
	
	public int getDefaultSize() {
		return 3;
	}

}
