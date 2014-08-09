package com.gaarnik.dimstorage.network.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

import com.gaarnik.dimstorage.network.AbstractPacket;
import com.gaarnik.dimstorage.network.DimStorageNetwork;
import com.gaarnik.dimstorage.storage.AbstractDimStorage;
import com.gaarnik.dimstorage.storage.DimStorageManager;
import com.gaarnik.dimstorage.tilentity.TEDimChest;

public class PacketUpdateStorage extends AbstractPacket {
	// *******************************************************************

	// *******************************************************************
	private String owner;
	private int freq;
	private String type;
	
	private boolean locked;

	// *******************************************************************
	public PacketUpdateStorage(String owner, int freq, String type, boolean locked) {
		this.owner = owner;
		this.freq = freq;
		this.type = type;
		
		this.locked = locked;
	}
	
	public PacketUpdateStorage(TEDimChest te) {
		this(te.getOwner(), te.getFreq(), te.getStorageType(), te.isLocked());
	}
	
	public PacketUpdateStorage(AbstractDimStorage storage) {
		this(storage.getOwner(), storage.getFreq(), storage.getType(), storage.isLocked());
	}
	
	public PacketUpdateStorage() {}

	// *******************************************************************
	@Override
	public void handleClientSide(EntityPlayer player) {
		DimStorageManager manager = DimStorageManager.instance(true);
		
		AbstractDimStorage storage = manager.getStorage(this.owner, this.freq, this.type);
		storage.setLocked(this.locked);
		storage.setDirty();
	}

	@Override
	public void handleServerSide(EntityPlayer player) {
		DimStorageManager manager = DimStorageManager.instance(false);
		
		AbstractDimStorage storage = manager.getStorage(this.owner, this.freq, this.type);
		storage.setLocked(this.locked);
		storage.setDirty();
		
		DimStorageNetwork.sendUpdateStorageToClients(storage);
	}

	// *******************************************************************
	@Override
	public void writeData(ByteBuf out) {
		out.writeInt(this.owner.length());
		out.writeBytes(this.owner.getBytes());
		
		out.writeInt(this.freq);
		
		out.writeInt(this.type.length());
		out.writeBytes(this.type.getBytes());
		
		out.writeBoolean(this.locked);
	}

	@Override
	public void readData(ByteBuf in) {
		byte[] buffer = new byte[in.readInt()];
		in.readBytes(buffer);
		this.owner = new String(buffer);
		
		this.freq = in.readInt();
		
		buffer = new byte[in.readInt()];
		in.readBytes(buffer);
		this.type = new String(buffer);
		
		this.locked = in.readBoolean();
	}

}
