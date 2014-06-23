package com.gaarnik.dimstorage.network;

import com.gaarnik.dimstorage.DimStorage;
import com.gaarnik.dimstorage.network.message.MessageOpenStorage;
import com.gaarnik.dimstorage.network.message.MessageUpdateStorage;
import com.gaarnik.dimstorage.tilentity.TEDimChest;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public class DimStorageNetwork {
	// *******************************************************************
	public static final SimpleNetworkWrapper instance = NetworkRegistry.INSTANCE.newSimpleChannel(DimStorage.MODID);

	// *******************************************************************
	public static void init() {
		instance.registerMessage(MessageUpdateStorage.class, MessageUpdateStorage.class, 0, Side.SERVER);
		instance.registerMessage(MessageOpenStorage.class, MessageOpenStorage.class, 1, Side.CLIENT);
	}

	// *******************************************************************
	public static void sendUpdateStorageToServer(TEDimChest te) {
		instance.sendToServer(new MessageUpdateStorage(te));
	}

	public static void sendOpenStorageToPlayers(TEDimChest te, boolean open) {
		instance.sendToAll(new MessageOpenStorage(te, open));
	}

}
