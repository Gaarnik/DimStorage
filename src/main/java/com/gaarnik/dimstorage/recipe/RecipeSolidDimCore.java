package com.gaarnik.dimstorage.recipe;

import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

import com.gaarnik.dimstorage.DimStorage;

import cpw.mods.fml.common.registry.GameRegistry;

public class RecipeSolidDimCore implements IRecipe {
	// ****************************************************************

	// ****************************************************************
	private static boolean init;

	// ****************************************************************
	public static void init() {
		if(init)
			return;
        
		init = true;
        
        RecipeDimChest instance = new RecipeDimChest();
        GameRegistry.addRecipe(instance);
        
        GameRegistry.addRecipe(new ItemStack(DimStorage.solidDimCore, 1), new Object[]{
            "iii",
            "idi",
            "iii",
            'i', Items.iron_ingot,
            'd', DimStorage.dimCore});
	}

	// ****************************************************************
	@Override
	public boolean matches(InventoryCrafting inventorycrafting, World world) { return false; }
	
	@Override
	public ItemStack getCraftingResult(InventoryCrafting inventorycrafting) { return null; }

	@Override
	public ItemStack getRecipeOutput() { return null; }

	@Override
	public int getRecipeSize() { return 6; }

}
