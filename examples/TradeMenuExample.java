import cn.nukkit.item.Item;
import ru.ragnok123.menuAPI.trade.TradeMenu;
import ru.ragnok123.menuAPI.trade.TradeRecipe;

public class TradeMenuExample {
	
	public void sendTradeMenu(Player player) {
		TradeMenu trade = new TradeMenu();
		trade.setTraderName("BLOCKS");
		trade.setTraderTier(4);
		trade.setMaxTradeTier(4);
		trade.addRecipe(new TradeRecipe(Item.get(Item.SANDSTONE,0,2), Item.get(Item.BRICK,0,1)));
		trade.addRecipe(new TradeRecipe(Item.get(Item.SANDSTONE,0,16), Item.get(Item.BRICK,0,8)));
		trade.addRecipe(new TradeRecipe(Item.get(Item.END_STONE,0,1), Item.get(Item.IRON_INGOT,0,7)).setTier(1));
		trade.show(player);
	}
	
}
