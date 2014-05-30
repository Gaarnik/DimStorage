package com.gaarnik.dimstorage;

import net.minecraftforge.common.MinecraftForge;

import com.gaarnik.dimstorage.common.BlockDimChest;
import com.gaarnik.dimstorage.common.RecipeDimChest;
import com.gaarnik.dimstorage.common.TEDimChest;
import com.gaarnik.dimstorage.storage.DimStorageManager.DimStorageSaveHandler;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

public abstract class CommonProxy {

	public void preInit(FMLPreInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(new RecipeDimChest());
		MinecraftForge.EVENT_BUS.register(new DimStorageSaveHandler());
	}
	
	public void init(FMLInitializationEvent event) {
		DimStorage.dimChest = new BlockDimChest(DimStorage.config.get("block", "DimChest", 1547).getInt());
		DimStorage.dimChest.setUnlocalizedName("dimchest");
        GameRegistry.registerBlock(DimStorage.dimChest, "block_dimchest");
        MinecraftForge.EVENT_BUS.register(DimStorage.dimChest);
        
        GameRegistry.registerTileEntity(TEDimChest.class, "TEDimChest");

        NetworkRegistry.instance().registerGuiHandler(DimStorage.instance, new DimStorageGUIHandler());
        
		RecipeDimChest.init();
	}
	
}
