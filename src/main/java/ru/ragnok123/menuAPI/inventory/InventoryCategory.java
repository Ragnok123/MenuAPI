package ru.ragnok123.menuAPI.inventory;

import java.util.HashMap;

import cn.nukkit.item.Item;
import lombok.NonNull;
import ru.ragnok123.menuAPI.inventory.item.ItemClick;
import ru.ragnok123.menuAPI.inventory.item.ItemData;

public class InventoryCategory {
	
	public InventoryMenu menu = null;
	
	private HashMap<Integer, ItemData> itemData = new HashMap<Integer,ItemData>();
	private HashMap<Integer, ItemClick> itemClick = new HashMap<Integer,ItemClick>();
	
	public HashMap<Integer,ItemData> itemDataMap(){
		return this.itemData;
	}
	
	public InventoryMenu getMenu() {
		return this.menu;
	}
	
	public void addElement(int position, @NonNull ItemData item) {
		addElement(position,item,null);
	}
	
	public void addElement(int position, @NonNull ItemData item, ItemClick click) {
		if(!itemData.containsKey(position)) {
			itemData.put(position, item);
			if(click != null) {
				itemClick.put(position, click);
			}
		}
	}
	
	public ItemData getItemData(int position) {
		if(this.itemData.containsKey(position)) {
			return this.itemData.get(position);
		}
		return null;
	}
	
	public ItemClick getItemClick(int position) {
		if(this.itemClick.containsKey(position)) {
			return this.itemClick.get(position);
		}
		return null;
	}
	
}
