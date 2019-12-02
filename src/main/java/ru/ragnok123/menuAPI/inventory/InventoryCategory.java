package ru.ragnok123.menuAPI.inventory;

public class InventoryCategory {
	
	private InventoryMenu menu = null;
	
	public InventoryCategory() {
		
	}
	
	public void setMenu(InventoryMenu menu) {
		this.menu = menu;
	}
	
	public InventoryMenu getMenu() {
		return this.menu;
	}

}
