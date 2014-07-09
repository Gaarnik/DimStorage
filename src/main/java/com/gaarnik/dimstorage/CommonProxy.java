package com.gaarnik.dimstorage;

import net.minecraftforge.common.MinecraftForge;

import com.gaarnik.dimstorage.registry.RegistryDimChest;
import com.gaarnik.dimstorage.registry.RegistryDimChestController;
import com.gaarnik.dimstorage.registry.RegistryDimCore;
import com.gaarnik.dimstorage.registry.RegistryDimTank;
import com.gaarnik.dimstorage.registry.RegistryDimWall;
import com.gaarnik.dimstorage.registry.RegistrySolidDimCore;
import com.gaarnik.dimstorage.storage.DimStorageManager.DimStorageSaveHandler;
import com.gaarnik.dimstorage.tilentity.TEDimChest;
import com.gaarnik.dimstorage.tilentity.TEDimTank;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

public class CommonProxy {

	public void preInit(FMLPreInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(new DimStorageSaveHandler());
	}

	public void init(FMLInitializationEvent event) {
		// Items
		RegistryDimCore.init();
		RegistrySolidDimCore.init();
		RegistryDimWall.init();
		RegistryDimChestController.init();
		
		// Blocks
		RegistryDimChest.init();
		RegistryDimTank.init();
		
		// Entities
		GameRegistry.registerTileEntity(TEDimChest.class, "TEDimChest");
		GameRegistry.registerTileEntity(TEDimTank.class, "TEDimTank");

		// GUI Handler
		NetworkRegistry.INSTANCE.registerGuiHandler(DimStorage.instance, new DimStorageGUIHandler());
	}

}
