package vazkii.modmaker.mod;

import net.minecraft.src.EnumRarity;
import net.minecraft.src.ItemFood;
import net.minecraft.src.ItemStack;

public class ItemCustomFood extends ItemFood implements CustomizableItem {

	int colorOverlay = 0xFFFFFF;
	boolean shine = false;
	EnumRarity rarity = EnumRarity.common;

	public ItemCustomFood(int par1, int par2, float par3, boolean par4) {
		super(par1, par2, par3, par4);
	}

	@Override
	public int getColorFromDamage(int par1, int par2) {
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
}
