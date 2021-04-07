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
import java.util.function.Consumer;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import ru.ragnok123.menuAPI.inventory.utils.TradeInventory;

public class TradeMenu {
	
	private ArrayList<TradeRecipe> recipes = new ArrayList<TradeRecipe>();
	private HashMap<UUID, Inventory> inventories = new HashMap<UUID, Inventory>();
	@Getter
	@Setter
	public int maxTradeTier = 4;
	@Getter
	@Setter
	public String traderName;
	@Getter
	@Setter
	public int traderTier = 4;
	@Getter
	@Setter
	public int experience = 30;
	@Getter
	@Setter
	public long uid;
	@Getter
	@Setter
	public Consumer<Player> closeHandler;
	
	public void show(@NonNull Player player) {
		uid = Entity.entityCount++;
		TradeInventory inv = new TradeInventory(player, this);
		player.getServer().getScheduler().scheduleDelayedTask(() -> {
			player.addWindow(inv);
			inventories.put(player.getUniqueId(), inv);
			TradeMenuHandler.pmenus.put(player.getUniqueId(), this);
		},10);
	}
	
	public void forceClose(@NonNull Player player) {
		player.getServer().getScheduler().scheduleDelayedTask(() -> {
			ContainerClosePacket close = new ContainerClosePacket();
			close.windowId = player.getWindowId(inventories.get(player.getUniqueId()));
			close.wasServerInitiated = true;
			player.dataPacket(close);
			
			RemoveEntityPacket pk = new RemoveEntityPacket();
			pk.eid = getUid();
			player.dataPacket(pk);
			
			inventories.remove(player.getUniqueId());
			TradeMenuHandler.pmenus.remove(player.getUniqueId());
		},1);
	}
	
	public TradeInventory getInventory(Player p) {
		return (TradeInventory) this.inventories.get(p.getUniqueId());
	}
	
	public void addRecipe(TradeRecipe recipe) {
		this.recipes.add(recipe);
	}
	
	
	public CompoundTag getOffers() {
		CompoundTag nbt = new CompoundTag("Offers");
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
	    tag.add(new CompoundTag().putInt("2", 70));
	    tag.add(new CompoundTag().putInt("3", 150));
	    tag.add(new CompoundTag().putInt("4", 250));
	    return tag;
	}
	
}