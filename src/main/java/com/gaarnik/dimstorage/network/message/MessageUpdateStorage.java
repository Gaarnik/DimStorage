package com.gaarnik.dimstorage.network.message;

import io.netty.buffer.ByteBuf;

import com.gaarnik.dimstorage.storage.AbstractDimStorage;
import com.gaarnik.dimstorage.storage.DimStorageManager;
import com.gaarnik.dimstorage.tilentity.TEDimChest;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MessageUpdateStorage implements IMessage, IMessageHandler<MessageUpdateStorage, IMessage> {
	// *******************************************************************

	// *******************************************************************
	private String owner;
	private int freq;
	private String type;
	
	private boolean locked;

	// *******************************************************************
	public MessageUpdateStorage(TEDimChest te) {
		this.owner = te.getOwner();
		this.freq = te.getFreq();
		this.type = te.getStorageType();
		
		this.locked = te.isLocked();
	}
	
	public MessageUpdateStorage() {}

	// *******************************************************************
	@Override
	public IMessage onMessage(MessageUpdateStorage message, MessageContext ctx) {
		DimStorageManager manager = DimStorageManager.instance(false);
		
		AbstractDimStorage storage = manager.getStorage(message.owner, message.freq, message.type);
		storage.setLocked(message.locked);
		storage.setDirty();
		
		return null;
	}

	// *******************************************************************
	@Override
	public void toBytes(ByteBuf out) {
		out.writeInt(this.owner.length());
		out.writeBytes(this.owner.getBytes());
		
		out.writeInt(this.freq);
		
		out.writeInt(this.type.length());
		out.writeBytes(this.type.getBytes());
		
		out.writeBoolean(this.locked);
	}
	
	@Override
	public void fromBytes(ByteBuf in) {
		byte[] buffer = new byte[in.readInt()];
		in.readBytes(buffer);
		this.owner = new String(buffer);
		
		this.freq = in.readInt();
		
		buffer = new byte[in.readInt()];
		in.readBytes(buffer);
		this.type = new String(buffer);
		
		this.locked = in.readBoolean();
	}

	// *******************************************************************

	// *******************************************************************

}
