# MenuAPI
 Basic nukkit API for creating menu
 
## Supported menus
 [x] Inventory menu
 [ ] Form menu
 
##Inventory API
 To create new inventory menu, you should do this:
 ```InventoryMenu menu = new InventoryMenu();```
 Every menu has a category. It is made to avoid bilion times changing menu content;
 To create new category, you can simply do this:
 ```InventoryCategory category = new InventoryCategory();```
 You can add items to your category:
 
 ```java
	category.addElement(int position, ItemData data);
 ```
 *Position* - It is slot. You're writing it cause it's required
 *ItemData* - Object, created for managing items. In future it planned to use nbt things and more
 
 You can also manage item clicking:
 
 ```java
 	category.addElement(int position, ItemData data, new ItemClick(){
 		@Override
 		public void onClick(Player player, Item item){
 			player.sendMessage("Item clicked");
 		}
 	});
 ```
 After you've added all items, dont forget add reference to menu:
 ```category.setMenu(menu);```
 
 You can set category as main category (when menu opens):
 ```menu.setMainCategory(category);```
 Or just add as another category:
 ```menu.addCategory(String id, category);```
 
 Making custom text is also easy:
 ```menu.setName("§l§eMenu");```
 
 If you want only read menu without transactions, just make:
 ```menu.setOnlyRead(true);```
 
 To send menu, just use 
 ```menu.show(player);```
 Or force close with
 ```menu.forceDestroy(player);```
 
 And finally, register new menu:
 ```InventoryMenuHandler.registerNewMenu(String id, menu);```