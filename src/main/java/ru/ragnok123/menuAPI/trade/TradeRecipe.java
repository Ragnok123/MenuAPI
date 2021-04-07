package ru.ragnok123.menuAPI.trade;

import cn.nukkit.item.Item;
import cn.nukkit.nbt.NBTIO;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.utils.Utils;
import lombok.Getter;

public class TradeRecipe {
	
	@Getter
	private Item sellItem;
	@Getter
	private Item buyItem;
	@Getter
	private Item secondBuyItem;
	
	private int tier = 0;
	private int maxUses = 999;
	private int buyCountA = 0;
	private int buyCountB = 0;
	private int uses = 0;
	private int demand = 0;
	private byte rewardsExp = (byte) 0;
	private int traderExp = 0;
	private float priceMultiplierA = 0F;
	private float priceMultiplierB = 0F;
	
	/*
	 * NOTE: sellItem is that, what VILLAGER sells, not player
	 */
	public TradeRecipe(Item sellItem, Item buyItem) {
		this(sellItem, buyItem, Item.get(0));
	}
	
	public TradeRecipe(Item sellItem, Item buyItem, Item secondBuyItem) {
		this.sellItem = sellItem;
		this.buyItem = buyItem;
		this.secondBuyItem = secondBuyItem;
	}
	
	public TradeRecipe setTier(int tier) {
		this.tier = tier;
		return this;
	}
	
	public TradeRecipe setMaxUses(int maxUses) {
		this.maxUses = maxUses;
		return this;
	}
	
	public TradeRecipe setBuysA(int count) {
		this.buyCountA = count;
		return this;
	}
	
	public TradeRecipe setBuysB(int count) {
		this.buyCountB = count;
		return this;
	}
	
	public CompoundTag toNBT() {
		CompoundTag nbt = new CompoundTag();
		nbt.putCompound("buyA", NBTIO.putItemHelper(buyItem, -1));
		if(secondBuyItem != null) {
			nbt.putCompound("buyB", NBTIO.putItemHelper(secondBuyItem,-1));
		}
		nbt.putCompound("sell", NBTIO.putItemHelper(sellItem, -1));
		nbt.putInt("tier", tier);
		nbt.putInt("buyCountA", buyCountA);
		nbt.putInt("buyCountB", buyCountB);
		nbt.putInt("uses", uses);
		nbt.putInt("maxUses", maxUses);
		nbt.putByte("rewardExp", rewardsExp);
		nbt.putInt("demand", demand);
		nbt.putInt("traderExp", traderExp);
		nbt.putFloat("priceMultiplierA", priceMultiplierA);
		nbt.putFloat("priceMultiplierB", priceMultiplierB);
		return nbt;
	}
	
	private static CompoundTag putItemHelper(Item item, Integer slot, String tagName) {
        CompoundTag tag = new CompoundTag(tagName)
                .putShort("id", item.getId())
                .putByte("Count", item.getCount())
                .putShort("Damage", item.getDamage());
        if (slot != null) {
            tag.putByte("Slot", slot);
        }
        if (item.hasCompoundTag()) {
            tag.putCompound("tag", item.getNamedTag());
        }
        return tag;
    }
	
}