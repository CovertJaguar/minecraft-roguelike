package greymerk.roguelike.treasure.loot.provider;

import java.util.Random;

import greymerk.roguelike.treasure.loot.Enchant;
import greymerk.roguelike.treasure.loot.Equipment;
import greymerk.roguelike.treasure.loot.Quality;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ItemWeapon extends ItemBase{
	
	public ItemWeapon(int weight, int level) {
		super(weight, level);
	}
	
	@Override
	public ItemStack getLootItem(Random rand, int level) {
		return getRandom(rand, level, true);
	}

	public static ItemStack getRandom(Random rand, int rank, boolean enchant){
		
		if(rand.nextInt(10) == 0){
			return ItemWeapon.getBow(rand, rank, enchant);
		} else {
			return ItemWeapon.getSword(rand, rank, enchant);
		}
	}
	
	public static ItemStack getBow(Random rand, int level, boolean enchant){
		
		if(rand.nextInt(20 + (level * 10)) == 0){
			return ItemSpecialty.getRandomItem(Equipment.BOW, rand, level);
		}
		
		ItemStack bow = new ItemStack(Items.BOW);
		
		if(enchant && rand.nextInt(6 - level) == 0){
			Enchant.enchantItem(rand, bow, Enchant.getLevel(rand, level));
		}
		
		return bow;
		
	}
	
	public static ItemStack getSword(Random rand, int level, boolean enchant){
		ItemStack sword;
		
		if(enchant && rand.nextInt(10 + (level * 10)) == 0){
			return ItemSpecialty.getRandomItem(Equipment.SWORD, rand, level);
		}
		
		sword = pickSword(rand, level);
		
		if(enchant && rand.nextInt(6 - level) == 0){
			Enchant.enchantItem(rand, sword, Enchant.getLevel(rand, level));
		}
		
		return sword;		
	}
	
	private static ItemStack pickSword(Random rand, int level){
		
		Quality quality = Quality.getWeaponQuality(rand, level);
		
		switch (quality) {
		case DIAMOND: return new ItemStack(Items.DIAMOND_SWORD);
		case GOLD: return new ItemStack(Items.GOLDEN_SWORD);
		case IRON: return new ItemStack(Items.IRON_SWORD);
			case BRONZE: return getIC2Item("bronze_sword","null");
		case STONE: return new ItemStack(Items.STONE_SWORD);
		default: return new ItemStack(Items.WOODEN_SWORD);
		}
	}




	
}
