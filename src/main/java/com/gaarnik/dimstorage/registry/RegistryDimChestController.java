package com.gaarnik.dimstorage.registry;

import net.minecraft.item.ItemStack;

import com.gaarnik.dimstorage.DimStorage;
import com.gaarnik.dimstorage.item.ItemDimChestController;

import cpw.mods.fml.common.registry.GameRegistry;

public class RegistryDimChestController {
	// ****************************************************************

	// ****************************************************************
	private static boolean init;

	// ****************************************************************
	public static void init() {
		if(init)
			return;
        
		init = true;
		
		DimStorage.dimChestController = new ItemDimChestController().setUnlocalizedName("dimchestcontroller");
		GameRegistry.registerItem(DimStorage.dimChestController, "item_dimchestcontroller");
        
        GameRegistry.addRecipe(new ItemStack(DimStorage.dimChestController, 1), new Object[]{
        	" w ",
            "wcw",
            " w ",
            'w', DimStorage.dimWall,
            'c', DimStorage.solidDimCore});
	}

}
