import cn.nukkit.item.Item;
import ru.ragnok123.menuAPI.inventory.InventoryMenu;

public class ChestMenuExample {
	
	private static String[] sword_category = new String[] {
			"xxxxxxxxx",
			"x       x",
			"x w i d x",
			"x  s g  x",
			"x       x",
			"xxxxxxxxx"
	}
	
	public void sendChestMenu(Player player) {
		
		InventoryMenu menu = new InventoryMenu();
		menu.setName("TEST MENU");
		menu.setDoubleChest();
		menu.setMainCategory(new InventoryCategory() {{
			addElement(0, new ItemData(Item.SANDSTONE));
			addElement(1, new ItemData(Item.COBBLESTONE), new ItemClick() {
				public void onClick(Player player, Item item) {
					player.sendMessage("Clicked item " + item.getName());
				}
			})
			addElement(0, new ItemData(Item.IRON_SWORD, 0, 1, "Swords"), new ItemClick() {
				public void onClick(Player player, Item item) {
					menu.openCategory("swords", player);
				}
			})
		}});
		menu.addCategory("swords", new InventoryCategory() {{
			HashMap<Character, ItemData> datas = new HashMap<Character, ItemData>();
			datas.put('x', new ItemData(Item.GLASS_PANEL));
			datas.put('w', new ItemData(Item.WOODEN_SWORD));
			datas.put('s', new ItemData(Item.STONE_SWORD));
			datas.put('i', new ItemData(Item.IRON_SWORD));
			datas.put('g', new ItemData(Item.GOLD_SWORD));
			datas.put('d', new ItemData(Item.DIAMOND_SWORD));
			
			setStringElements(sword_category, datas);
		}});
		menu.setOnlyRead(true);
		menu.show(player);
		
	}
	
}