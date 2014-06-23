package com.gaarnik.dimstorage;

import net.minecraftforge.common.MinecraftForge;

import com.gaarnik.dimstorage.renderer.TERendererDimChest;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.event.FMLInitializationEvent;

public class ClientProxy extends CommonProxy {
	
	@Override
	public void init(FMLInitializationEvent event) {
		super.init(event);
		
		MinecraftForge.EVENT_BUS.register(DimStorage.dimChest);
		
		ClientRegistry.bindTileEntitySpecialRenderer(com.gaarnik.dimstorage.tilentity.TEDimChest.class, new TERendererDimChest());
	}

}
