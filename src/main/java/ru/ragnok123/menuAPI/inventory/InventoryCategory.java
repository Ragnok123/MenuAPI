package ru.ragnok123.menuAPI.inventory;

import java.util.HashMap;
import java.util.function.BiConsumer;

import cn.nukkit.Player;
import cn.nukkit.item.Item;
import lombok.Getter;
import lombok.NonNull;
import ru.ragnok123.menuAPI.inventory.item.ItemClick;
import ru.ragnok123.menuAPI.inventory.item.handler.ReceiveHandler;

public class InventoryCategory {
	
	public InventoryMenu menu = null;
	
	/*							Y(row)
	 *   	x x x x x x x x x 	0
	 *   	x x x x x x x x x	1
	 *   	x x x x x x x x x	2
	 *   	x x x x x x x x x	3
	 *   	x x x x x x x x x	4
	 *   	x x x x x x x x x	5
	 *   
	 *   X: 0 1 2 3 4 5 6 7 8 
	 */
	
	public String[] inventoryGui;
	
	private boolean read = true;
	
	@Getter
	private boolean moveToPlayer = false;
	@Getter
	private boolean moveToChest = false;
	
	private ReceiveHandler handler;
	
	private HashMap<Integer, Item> itemData = new HashMap<Integer,Item>();
	private HashMap<Integer, ItemClick> itemClick = new HashMap<Integer,ItemClick>();
	
	public HashMap<Integer,Item> itemDataMap(){
		return this.itemData;
	}
	
	public InventoryMenu getMenu() {
		return this.menu;
	}
	
	public void allowToChest(boolean value) {
		this.moveToChest = value;
	}
	
	public void allowToPlayer(boolean value) {
		this.moveToPlayer = value;
	}
	
	public boolean onlyRead() {
		return this.read;
	}
	
	public void setOnlyRead(boolean value) {
		this.read = value;
	}
	
	public void setStringElements(String[] elements, HashMap<Character, Item> itemDatas) {
		setStringElements(elements, itemDatas, null);
	}
	
	private int convertLineToSlot(int line, int rowSlot) {
		return (9 * line) + rowSlot;
	}
	
	public void setStringElements(String[] elements, HashMap<Character, Item> itemDatas, HashMap<Character, ItemClick> itemClicks) {
		if(elements.length == 3 || elements.length == 6) {
			this.inventoryGui = elements;
			for(int line = 0; line < elements.length; line++) {
				String row = elements[line];
				for(int rowSize = 0; rowSize < row.length(); rowSize++) {
					char c = row.charAt(rowSize);
					if(c != ' ' && !itemDatas.containsKey(c)) {
						throw new RuntimeException("Element with symbol " + c + " not registered");
					}
					int slot = convertLineToSlot(line, rowSize);
					if(c == ' ') {
						continue;
					}
					if(itemClicks != null) {
						if(itemClicks.containsKey(c)) {
							addElement(slot, itemDatas.get(c), itemClicks.get(c));
						} else {
							addElement(slot, itemDatas.get(c));
						}
					} else {
						addElement(slot, itemDatas.get(c));
					}
				}
			}
		}
	}
	
	public void addElement(int position, @NonNull Item item) {
		addElement(position,item,null);
	}
	
	public void addElement(int position, @NonNull Item item, ItemClick click) {
		if(!itemData.containsKey(position)) {
			itemData.put(position, item);
			if(click != null) {
				itemClick.put(position, click);
			}
		}
	}
	
	public void replaceElement(int position, @NonNull Item item) {
		replaceElement(position, item, null);
	}
	
	public void replaceElement(int position, @NonNull Item item, ItemClick click) {
		itemData.put(position, item);
		if(click != null) {
			itemClick.put(position, click);
		} else {
			if(itemClick.containsKey(position)) {
				itemClick.remove(position);
			}
		}
	}
	
	public Item getItemData(int position) {
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
	
	public void addHandler(ReceiveHandler handler) {
		this.handler = handler;
	}
	
	public ReceiveHandler getHandler() {
		return this.handler;
	}
	
}
