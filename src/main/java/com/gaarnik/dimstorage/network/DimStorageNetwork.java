package com.gaarnik.dimstorage.network;

import com.gaarnik.dimstorage.network.packet.PacketOpenDimChest;
import com.gaarnik.dimstorage.network.packet.PacketUpdateStorage;
import com.gaarnik.dimstorage.network.packet.PacketUpdateTE;
import com.gaarnik.dimstorage.storage.AbstractDimStorage;
import com.gaarnik.dimstorage.tilentity.TEDimChest;

public class DimStorageNetwork {
	// *******************************************************************
	private static final PacketManager packetManager = new PacketManager();
	
	// *******************************************************************
	public static void init() {
		packetManager.registerPacket(PacketOpenDimChest.class, 0);
		
		packetManager.registerPacket(PacketUpdateStorage.class, 1);
		packetManager.registerPacket(PacketUpdateTE.class, 2);
	}

	// *******************************************************************
	public static void sendUpdateStorageToServer(TEDimChest te) {
		packetManager.sendToServer(new PacketUpdateStorage(te));
	}
	
	public static void sendUpdateTEToServer(TEDimChest te) {
		packetManager.sendToServer(new PacketUpdateTE(te));
	}

	// *******************************************************************
	public static void sendUpdateStorageToClients(AbstractDimStorage storage) {
		packetManager.sendToAll(new PacketUpdateStorage(storage));
	}
	
	public static void sendOpenStorageToClients(TEDimChest te) {
		packetManager.sendToAll(new PacketOpenDimChest(te));
	}

}
