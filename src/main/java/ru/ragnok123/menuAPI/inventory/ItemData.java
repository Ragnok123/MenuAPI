package ru.ragnok123.menuAPI.inventory;

import cn.nukkit.item.Item;

public class ItemData {
	
	public String customName = "Item";
	public int count = 0;
	public int id = 0;
	public int damage = 0;
	public String[] lores = {};
	
	public ItemData(int id) {
		this(id,0);
	}
	public ItemData(int id, int damage) {
		this(id,damage,0);
	}
	public ItemData(int id,int damage,int count) {
		this(id,damage,count,"Name");
	}
	public ItemData(int id,int damage, int count, String customName) {
		this(id,damage,count,customName, new String[] {});
	}
	public ItemData(int id, int damage, int count, String customName, String[] lores) {
		this.id = id; 
		this.damage = damage;
		this.count = count;
		this.customName = customName;
		this.lores = lores;
	}
	
	public Item build() {
		Item item = Item.get(id,damage,count);
		if(!customName.equals("Name")) {
			item.setCustomName(customName);
		}
		if(lores.length > 0) {
			item.setLore(lores);
		}
		return item;
	}
	

}
