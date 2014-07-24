package com.gaarnik.dimstorage.network.message;

import com.gaarnik.dimstorage.tilentity.TEDimChest;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MessageUpdateTE implements IMessage, IMessageHandler<MessageUpdateTE, IMessage> {
	// *******************************************************************

	// *******************************************************************
	private int x, y, z;
	
	private String owner;
	private int freq;
	
	private int openCount;

	// *******************************************************************
	public MessageUpdateTE(TEDimChest te) {
		this.x = te.xCoord;
		this.y = te.yCoord;
		this.z = te.zCoord;
		
		this.owner = te.getOwner();
		this.freq = te.getFreq();
		
		this.openCount = te.getOpenCount();
	}
	
	public MessageUpdateTE() {}

	// *******************************************************************
	@Override
	public IMessage onMessage(MessageUpdateTE message, MessageContext context) {
		EntityPlayerMP playerMP = context.getServerHandler().playerEntity;
		TileEntity tileEntity = playerMP.worldObj.getTileEntity(message.x, message.y, message.z);
		
		if(tileEntity == null || tileEntity instanceof TEDimChest == false)
			return null;
		
		TEDimChest tileEntityDimChest = (TEDimChest) tileEntity;
		tileEntityDimChest.setOwner(message.owner);
		tileEntityDimChest.setFreq(message.freq);
		tileEntityDimChest.setOpenCount(message.openCount);

		tileEntityDimChest.reloadStorage();

		playerMP.worldObj.markBlockForUpdate(message.x, message.y, message.z);
		
		return null;
	}

	// *******************************************************************
	@Override
	public void toBytes(ByteBuf out) {
		out.writeInt(this.x);
		out.writeInt(this.y);
		out.writeInt(this.z);
		
		out.writeInt(this.owner.length());
		out.writeBytes(this.owner.getBytes());
		
		out.writeInt(this.freq);

		out.writeInt(this.openCount);
	}
	
	@Override
	public void fromBytes(ByteBuf in) {
		this.x = in.readInt();
		this.y = in.readInt();
		this.z = in.readInt();
		
		byte[] buffer = new byte[in.readInt()];
		in.readBytes(buffer);
		this.owner = new String(buffer);
		
		this.freq = in.readInt();

		this.openCount = in.readInt();
	}

	// *******************************************************************

	// *******************************************************************

}
