package com.gaarnik.dimstorage.network;

import com.gaarnik.dimstorage.DimStorage;
import com.gaarnik.dimstorage.common.TEDimChest;
import com.gaarnik.dimstorage.network.message.MessageUpdateStorage;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public class PacketHandler {
	// *******************************************************************
	public static final SimpleNetworkWrapper instance = NetworkRegistry.INSTANCE.newSimpleChannel(DimStorage.MODID);

	// *******************************************************************
	public static void init() {
		instance.registerMessage(MessageUpdateStorage.class, MessageUpdateStorage.class, 0, Side.SERVER);
	}

	// *******************************************************************
	public static void sendUpdateStorageToServer(TEDimChest te) {
		instance.sendToServer(new MessageUpdateStorage(te));
	}

}
