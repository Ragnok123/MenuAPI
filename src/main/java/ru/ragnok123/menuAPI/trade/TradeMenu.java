package ru.ragnok123.menuAPI.trade;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.data.EntityMetadata;
import cn.nukkit.inventory.Inventory;
import cn.nukkit.nbt.NBTIO;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.ListTag;
import cn.nukkit.network.protocol.AddEntityPacket;
import cn.nukkit.network.protocol.ContainerClosePacket;
import cn.nukkit.network.protocol.RemoveEntityPacket;
import cn.nukkit.network.protocol.UpdateTradePacket;

import java.io.IOException;
import java.nio.ByteOrder;
import java.util.*;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import ru.ragnok123.menuAPI.inventory.utils.TradeInventory;

public class TradeMenu {
	
	private ArrayList<TradeRecipe> recipes = new ArrayList<TradeRecipe>();
	private HashMap<UUID, Inventory> inventories = new HashMap<UUID, Inventory>();
	@Getter
	@Setter
	public int maxTradeTier;
	@Getter
	@Setter
	public String traderName;
	@Getter
	@Setter
	public int traderTier;
	@Getter
	@Setter
	public int experience;
	@Getter
	@Setter
	public long uid;
	
	public void show(@NonNull Player player) {
		uid = Entity.entityCount++;
		TradeInventory inv = new TradeInventory(player, this);
		player.addWindow(inv);
		inventories.put(player.getUniqueId(), inv);
		TradeMenuHandler.pmenus.put(player.getUniqueId(), this);
	}
	
	public void forceClose(@NonNull Player player) {
		ContainerClosePacket close = new ContainerClosePacket();
		close.windowId = player.getWindowId(inventories.get(player.getUniqueId()));
		close.wasServerInitiated = true;
		player.dataPacket(close);
		
		RemoveEntityPacket pk = new RemoveEntityPacket();
		pk.eid = getUid();
		player.dataPacket(pk);
		
		inventories.remove(player.getUniqueId());
		TradeMenuHandler.pmenus.remove(player.getUniqueId());
	}
	
	public TradeInventory getInventory(Player p) {
		return (TradeInventory) this.inventories.get(p.getUniqueId());
	}
	
	
	public CompoundTag getOffers() {
		CompoundTag nbt = new CompoundTag();
		nbt.putList(recipesToNbt());
		nbt.putList(getTierExpRequirements());
		return nbt;
	}
	
	private ListTag<CompoundTag> recipesToNbt() {
		ListTag<CompoundTag> tag = new ListTag<CompoundTag>("Recipes");
		for(TradeRecipe recipe : recipes) {
			tag.add(recipe.toNBT());
		}
		return tag;
	}
	
	private ListTag<CompoundTag> getTierExpRequirements() {
		ListTag<CompoundTag> tag = new ListTag<CompoundTag>("TierExpRequirements");
		tag.add(new CompoundTag().putInt("0", 0));
		tag.add(new CompoundTag().putInt("1", 10));
		tag.add(new CompoundTag().putInt("2", 60));
		tag.add(new CompoundTag().putInt("3", 160));
		tag.add(new CompoundTag().putInt("4", 310));
		return tag;
	}
	
}