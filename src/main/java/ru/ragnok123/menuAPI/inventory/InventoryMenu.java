package ru.ragnok123.menuAPI.inventory;

import java.io.IOException;
import java.nio.ByteOrder;
import java.util.*;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.inventory.Inventory;
import cn.nukkit.level.GlobalBlockPalette;
import cn.nukkit.math.Vector3;
import cn.nukkit.nbt.NBTIO;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.network.protocol.BlockEntityDataPacket;
import cn.nukkit.network.protocol.ContainerClosePacket;
import cn.nukkit.network.protocol.UpdateBlockPacket;
import lombok.NonNull;
import ru.ragnok123.menuAPI.inventory.item.ItemData;
import ru.ragnok123.menuAPI.inventory.utils.MenuInventory;

public class InventoryMenu {

	private InventoryCategory category = null;
	private HashMap<String, InventoryCategory> categories = new HashMap<String, InventoryCategory>();
	private InventoryCategory currentCategory = null;
	private HashMap<UUID,Inventory> inventories = new HashMap<UUID,Inventory>();
	private String name = "Menu";
	private boolean read = true;
	
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
		MenuInventory inv = new MenuInventory(player,vec,this);
		player.addWindow(inv);
		inventories.put(player.getUniqueId(), inv);
		InventoryMenuHandler.pmenus.put(player.getUniqueId(),this);
		openMainCategory(player);
	}
	
	private Vector3 createInventory(Player player) {
		UpdateBlockPacket pk1 = new UpdateBlockPacket();
        pk1.x = (int) player.x;
        pk1.y = (int) player.y - 2;
        pk1.z = (int) player.z;
        pk1.blockRuntimeId = GlobalBlockPalette.getOrCreateRuntimeId(Block.CHEST,0);
        pk1.dataLayer = 0;
        pk1.flags = UpdateBlockPacket.FLAG_NONE;
        player.dataPacket(pk1);

        BlockEntityDataPacket pk2 = new BlockEntityDataPacket();
        pk2.x = (int) player.x;
        pk2.y = (int) player.y - 2;
        pk2.z = (int) player.z;
        CompoundTag nbt = new CompoundTag();
        nbt.putString("CustomName", getName());

        try {
            pk2.namedTag = NBTIO.write(nbt, ByteOrder.LITTLE_ENDIAN, true);
        } catch (IOException ex) {
        }
        player.dataPacket(pk2);
        return new Vector3(player.x, player.y - 2, player.z);
	}
	
	public void destroy(@NonNull Player player) {
		inventories.remove(player.getUniqueId());
		InventoryMenuHandler.pmenus.remove(player.getUniqueId());
		Vector3 vec = new Vector3(player.x, player.y -2, player.z);
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
		},20);
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
	
	public void openMainCategory(Player player) {
		InventoryCategory category = this.category;
		Inventory inventory = inventories.get(player.getUniqueId());
		inventory.clearAll();
		for(Map.Entry<Integer,ItemData> entry : category.itemDataMap().entrySet()) {
			int position = entry.getKey();
			ItemData data = entry.getValue();
			inventory.setItem(position, data.build());
		}
		this.currentCategory = category;
	}
	
	public void openCategory(@NonNull String id, Player player) {
		if(categories.containsKey(id)) {
			InventoryCategory category = categories.get(id);
			Inventory inventory = inventories.get(player.getUniqueId());
			inventory.clearAll();
			for(Map.Entry<Integer,ItemData> entry : category.itemDataMap().entrySet()) {
				int position = entry.getKey();
				ItemData data = entry.getValue();
				inventory.setItem(position, data.build());
			}
			this.currentCategory = category;
		}
	}
	
	public boolean onlyRead() {
		return this.read;
	}
	
	public void setOnlyRead(boolean value) {
		this.read = value;
	}
	
}
