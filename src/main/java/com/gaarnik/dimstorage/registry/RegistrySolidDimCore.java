package com.gaarnik.dimstorage.registry;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import com.gaarnik.dimstorage.DimStorage;
import com.gaarnik.dimstorage.item.ItemSolidDimCore;

import cpw.mods.fml.common.registry.GameRegistry;

public class RegistrySolidDimCore {
	// ****************************************************************

	// ****************************************************************
	private static boolean init;

	// ****************************************************************
	public static void init() {
		if(init)
			return;
        
		init = true;
		
		DimStorage.solidDimCore = new ItemSolidDimCore().setUnlocalizedName("soliddimcore");
		GameRegistry.registerItem(DimStorage.solidDimCore, "item_soliddimcore");
        
        GameRegistry.addRecipe(new ItemStack(DimStorage.solidDimCore, 1), new Object[]{
            "iii",
            "idi",
            "iii",
            'i', Items.iron_ingot,
            'd', DimStorage.dimCore});
	}

	// ****************************************************************

}
