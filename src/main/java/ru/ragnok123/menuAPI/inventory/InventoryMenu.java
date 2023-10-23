package ru.ragnok123.menuAPI.inventory;

import java.io.IOException;
import java.nio.ByteOrder;
import java.util.*;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.blockentity.BlockEntity;
import cn.nukkit.inventory.Inventory;
import cn.nukkit.item.Item;
import cn.nukkit.level.GlobalBlockPalette;
import cn.nukkit.math.Vector3;
import cn.nukkit.nbt.NBTIO;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.network.protocol.BlockEntityDataPacket;
import cn.nukkit.network.protocol.ContainerClosePacket;
import cn.nukkit.network.protocol.UpdateBlockPacket;
import lombok.Getter;
import lombok.NonNull;
import ru.ragnok123.menuAPI.inventory.utils.MenuDoubleInventory;
import ru.ragnok123.menuAPI.inventory.utils.MenuInventory;

public class InventoryMenu {

	private InventoryCategory category = null;
	private HashMap<String, InventoryCategory> categories = new HashMap<String, InventoryCategory>();
	private InventoryCategory currentCategory = null;
	private HashMap<UUID,Inventory> inventories = new HashMap<UUID,Inventory>();
	private String name = "Menu";
	private boolean isDouble = false;
	
	public void setName(String name) { this.name = name; }
	public String getName() { return this.name; }
	
	public Inventory getInventory(UUID uuid) {
		if(inventories.containsKey(uuid)) {
			return inventories.get(uuid);
		}
		return null;
	}
	
	public void show(@NonNull Player player) {
		Vector3 vec = createInventory(player);
		MenuInventory inv = isDoubleChest() ? new MenuDoubleInventory(player, vec, this) : new MenuInventory(player,vec,this);
		player.getServer().getScheduler().scheduleDelayedTask(() -> {
			player.addWindow(inv);
			inventories.put(player.getUniqueId(), inv);
			InventoryMenuHandler.pmenus.put(player.getUniqueId(),this);
			openMainCategory(player);
		},10);
	}
	
	private Vector3 createInventory(Player player) {
		Vector3 pos = new Vector3((int)player.getX(), (int)player.getY() - 2, (int)player.getZ());
		spawnChest(player, pos);
		if(isDoubleChest()) {
			Vector3 pos2 = pos.clone().add(1,0,0);
			spawnChest(player, pos2);
			
			pairChests(player, pos, pos2);
			pairChests(player,pos2, pos);
		}

        return new Vector3(player.x, player.y - 2, player.z);
	}
	
	private void pairChests(Player player, Vector3 pos1, Vector3 pos2) {
		BlockEntityDataPacket pk = new BlockEntityDataPacket();
		pk.x = (int) pos1.x;
		pk.y = (int) pos1.y;
		pk.z = (int) pos1.z;
		
		CompoundTag nbt = new CompoundTag();
        nbt.putString("CustomName", getName());
        nbt.putInt("x", (int)pos1.x);
        nbt.putInt("y", (int)pos1.y);
        nbt.putInt("z", (int)pos1.z);
        nbt.putInt("pairx", (int)pos2.x);
        nbt.putInt("pairz", (int)pos2.z);
        nbt.putString("id", BlockEntity.CHEST);
        try {
            pk.namedTag = NBTIO.write(nbt, ByteOrder.LITTLE_ENDIAN, true);
        } catch (IOException ex) {
        }
        player.dataPacket(pk);
	}
	
	private void spawnChest(Player player, Vector3 pos) {
		UpdateBlockPacket pk1 = new UpdateBlockPacket();
        pk1.x = (int) pos.x;
        pk1.y = (int) pos.y;
        pk1.z = (int) pos.z;
        pk1.blockRuntimeId = GlobalBlockPalette.getOrCreateRuntimeId(Block.CHEST,0);
        pk1.dataLayer = 0;
        pk1.flags = UpdateBlockPacket.FLAG_NONE;
        player.dataPacket(pk1);

        BlockEntityDataPacket pk2 = new BlockEntityDataPacket();
        pk2.x = (int) pos.x;
        pk2.y = (int) pos.y;
        pk2.z = (int) pos.z;
        
        CompoundTag nbt = new CompoundTag();
        nbt.putString("CustomName", getName());
        nbt.putInt("x", (int)pos.x);
        nbt.putInt("y", (int)pos.y);
        nbt.putInt("z", (int)pos.z);
        nbt.putString("id", BlockEntity.CHEST);

        try {
            pk2.namedTag = NBTIO.write(nbt, ByteOrder.LITTLE_ENDIAN, true);
        } catch (IOException ex) {
        }
        player.dataPacket(pk2);
	}
	
	public void destroy(@NonNull Player player) {
		inventories.remove(player.getUniqueId());
		InventoryMenuHandler.pmenus.remove(player.getUniqueId());
		Vector3 vec = new Vector3(player.x, player.y -2, player.z);
		if(isDoubleChest()) {
			Vector3 vec1 = vec.add(1,0,0);
			player.level.sendBlocks(new Player[] {player}, new Vector3[] {vec, vec1});
			return;
		}
		player.level.sendBlocks(new Player[] {player}, new Vector3[] {vec});
	}
	
	public void forceDestroy(@NonNull Player player) {
		player.getServer().getScheduler().scheduleDelayedTask(new Runnable() {
			public void run() {
				//player.removeWindow(getInventory(player.getUniqueId()));
				ContainerClosePacket pk = new ContainerClosePacket();
				pk.windowId = player.getWindowId(inventories.get(player.getUniqueId()));
				pk.wasServerInitiated = true;
				destroy(player);
			}
		},1);
	}
	
	public void setDoubleChest() {
		this.isDouble = true;
	}
	
	public boolean isDoubleChest() {
		return this.isDouble;
	}
	
	public void setMainCategory(@NonNull InventoryCategory category) {
		category.menu = this;
		this.category = category;
	}
	
	public InventoryCategory getMainCategory() {
		return this.category;
	}
	
	public void addCategory(@NonNull String id, @NonNull InventoryCategory category) {
		if(categories.containsKey(id)) {
			return;
		}
		category.menu = this;
		categories.put(id, category);
	}
	
	public void removeCategory(@NonNull String id) {
		if(!categories.containsKey(id)) {
			return;
		}
		categories.remove(id);
	}
	
	public InventoryCategory getCategory(@NonNull String id) {
		if(categories.containsKey(id)) {
			return categories.get(id);
		}
		return null;
	}
	
	public InventoryCategory getCurrentCategory() {
		return this.currentCategory;
	}
	
	public void updateContents(Player p) {
		Inventory inventory = inventories.get(p.getUniqueId());
		inventory.clearAll();
		for(Map.Entry<Integer,Item> entry : category.itemDataMap().entrySet()) {
			int position = entry.getKey();
			Item data = entry.getValue();
			inventory.setItem(position, data);
		}
	}
	
	public void openMainCategory(Player player) {
		InventoryCategory category = this.category;
		Inventory inventory = inventories.get(player.getUniqueId());
		inventory.clearAll();
		for(Map.Entry<Integer,Item> entry : category.itemDataMap().entrySet()) {
			int position = entry.getKey();
			Item data = entry.getValue();
			inventory.setItem(position, data);
		}
		this.currentCategory = category;
	}
	
	public void openCategory(@NonNull String id, Player player) {
		if(categories.containsKey(id)) {
			InventoryCategory category = categories.get(id);
			Inventory inventory = inventories.get(player.getUniqueId());
			inventory.clearAll();
			for(Map.Entry<Integer,Item> entry : category.itemDataMap().entrySet()) {
				int position = entry.getKey();
				Item data = entry.getValue();
				inventory.setItem(position, data);
			}
			this.currentCategory = category;
		}
	}
}
