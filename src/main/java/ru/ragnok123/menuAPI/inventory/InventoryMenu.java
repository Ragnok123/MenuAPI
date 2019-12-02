package ru.ragnok123.menuAPI.inventory;

import java.util.*;

import cn.nukkit.Player;

public class InventoryMenu {

	private InventoryCategory category = null;
	private HashMap<String, InventoryCategory> categories = new HashMap<String, InventoryCategory>();
	
	/**
	 * InventoryMenu menu = new InventoryMenu();
	 * 
	 */
	
	public InventoryMenu() {
		
	}
	
	public void setSize(int size) {
		
	}
	
	public void build() {
		
	}
	
	public void show(Player player) {
		
	}
	
	public void destroy(Player player) {
		
	}
	
	public void setMainCategory(InventoryCategory category) {
		this.category = category;
	}
	
	public InventoryCategory getMainCategory() {
		return this.category;
	}
	
	public void addCategory(String id, InventoryCategory category) {
		if(categories.containsKey(id)) {
			//message;
			return;
		}
		categories.put(id, category);
	}
	
	public void removeCategory(String id) {
		if(!categories.containsKey(id)) {
			//message;
			return;
		}
		categories.remove(id);
	}
	
	public InventoryCategory getCategory(String id) {
		if(categories.containsKey(id)) {
			return categories.get(id);
		}
		return null;
	}
	

}
