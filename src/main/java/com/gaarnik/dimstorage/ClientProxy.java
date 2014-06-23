package com.gaarnik.dimstorage.client;

import net.minecraftforge.common.MinecraftForge;

import com.gaarnik.dimstorage.CommonProxy;
import com.gaarnik.dimstorage.DimStorage;
import com.gaarnik.dimstorage.client.renderer.TERendererDimChest;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.event.FMLInitializationEvent;

public class ClientProxy extends CommonProxy {
	
	@Override
	public void init(FMLInitializationEvent event) {
		super.init(event);
		
		MinecraftForge.EVENT_BUS.register(DimStorage.dimChest);
		
		ClientRegistry.bindTileEntitySpecialRenderer(com.gaarnik.dimstorage.common.TEDimChest.class, new TERendererDimChest());
	}

}
