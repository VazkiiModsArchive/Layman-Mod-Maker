package vazkii.modmaker.mod;

import net.minecraft.src.EnumRarity;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

public class ItemCustom extends Item implements CustomizableItem {

	int colorOverlay = 0xFFFFFF;
	boolean shine = false;
	EnumRarity rarity = EnumRarity.common;

	public ItemCustom(int par1) {
		super(par1);
	}

	@Override
	public int func_82790_a(ItemStack par1, int par2) {
		return colorOverlay;
	}

	@Override
	public boolean hasEffect(ItemStack par1ItemStack) {
		return shine;
	}

	@Override
	public EnumRarity getRarity(ItemStack par1ItemStack) {
		return rarity;
	}

	@Override
	public void setColorOverlay(int i) {
		colorOverlay = i;
	}

	@Override
	public void setShine(boolean b) {
		shine = b;
	}

	@Override
	public void setRarity(int i) {
		EnumRarity rarity = EnumRarity.common;
		switch (i) {
			case 1: {
				rarity = EnumRarity.uncommon;
				break;
			}
			case 2: {
				rarity = EnumRarity.rare;
				break;
			}
			case 3: {
				rarity = EnumRarity.epic;
				break;
			}
		}
		this.rarity = rarity;
	}

	@Override
	public void setPotionEffect0(String effect) {
		if (!effect.equals("none")) setPotionEffect(effect);
	}

}
