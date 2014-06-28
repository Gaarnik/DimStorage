package com.gaarnik.dimstorage.registry;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import com.gaarnik.dimstorage.DimStorage;
import com.gaarnik.dimstorage.item.ItemDimCore;

import cpw.mods.fml.common.registry.GameRegistry;

public class RegistryDimCore {
	// ****************************************************************

	// ****************************************************************
	private static boolean init;

	// ****************************************************************
	public static void init() {
		if(init)
			return;
        
		init = true;
		
		DimStorage.dimCore = new ItemDimCore().setUnlocalizedName("dimcore");
		GameRegistry.registerItem(DimStorage.dimCore, "item_dimcore");
        
        GameRegistry.addRecipe(new ItemStack(DimStorage.dimCore, 1), new Object[]{
            "iri",
            "rdr",
            "iri",
            'i', Items.iron_ingot,
            'r', Items.redstone,
            'd', Items.diamond});
	}

	// ****************************************************************

}
