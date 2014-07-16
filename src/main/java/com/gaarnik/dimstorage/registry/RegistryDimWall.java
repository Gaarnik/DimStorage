package com.gaarnik.dimstorage.registry;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import com.gaarnik.dimstorage.DimStorage;
import com.gaarnik.dimstorage.item.ItemDimWall;

import cpw.mods.fml.common.registry.GameRegistry;

public class RegistryDimWall {
	// ****************************************************************

	// ****************************************************************
	private static boolean init;

	// ****************************************************************
	public static void init() {
		if(init)
			return;
        
		init = true;
        
		DimStorage.dimWall = new ItemDimWall().setUnlocalizedName("dimwall");
		GameRegistry.registerItem(DimStorage.dimWall, "item_dimwall");
		
        GameRegistry.addRecipe(new ItemStack(DimStorage.dimWall, 2), new Object[]{
            "iri",
            "rer",
            "iri",
            'i', Items.iron_ingot,
            'r', Items.redstone,
            'e', Items.ender_pearl});
	}

	// ****************************************************************

}
