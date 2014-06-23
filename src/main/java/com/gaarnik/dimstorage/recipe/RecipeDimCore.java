package com.gaarnik.dimstorage.common;

import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

import com.gaarnik.dimstorage.DimStorage;

import cpw.mods.fml.common.registry.GameRegistry;

public class RecipeDimCore implements IRecipe {
	// ****************************************************************

	// ****************************************************************
	private static boolean init;

	// ****************************************************************
	public static void init() {
		if(init)return;
        init = true;
        
        RecipeDimChest instance = new RecipeDimChest();
        GameRegistry.addRecipe(instance);
        
        GameRegistry.addRecipe(new ItemStack(DimStorage.dimCore, 1), new Object[]{
            "iri",
            "rer",
            "iri",
            'i', Items.iron_ingot,
            'r', Items.redstone,
            'e', Items.ender_pearl});
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
