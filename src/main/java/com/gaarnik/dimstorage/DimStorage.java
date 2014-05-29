package com.gaarnik.dimstorage;

import net.minecraftforge.common.Configuration;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(modid = DimStorage.MODID, version = DimStorage.VERSION, acceptedMinecraftVersions=DimStorage.MC_VERSION)
@NetworkMod(clientSideRequired = true, serverSideRequired = true)
public class DimStorage {
	// ****************************************************************
    public static final String MODID = "dimstorage";
    public static final String VERSION = "1.0";
    public static final String MC_VERSION = "1.6.4";

	// ****************************************************************
    @SidedProxy(clientSide="com.gaarnik.dimstorage.client.ClientProxy", serverSide="com.gaarnik.dimstorage.server.ServerProxy")
    public static CommonProxy proxy;
    
    public static Configuration config;
    
	// ****************************************************************
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
    	config = new Configuration(event.getSuggestedConfigurationFile());
    	config.load();
    	
    	proxy.preInit(event);
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event) {

    	proxy.init(event);
    }

	// ****************************************************************

	// ****************************************************************

}
