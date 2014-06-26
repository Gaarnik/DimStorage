package com.gaarnik.dimstorage.network.message;

import com.gaarnik.dimstorage.tilentity.TEDimChest;

import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MessageOpenStorage implements IMessage, IMessageHandler<MessageOpenStorage, IMessage> {
	// *******************************************************************

	// *******************************************************************
	private int x, y, z;

	private int openCount;

	// *******************************************************************
	public MessageOpenStorage(TEDimChest te) {
		this.x = te.xCoord;
		this.y = te.yCoord;
		this.z = te.zCoord;

		this.openCount = te.getOpenCount();
	}
	
	public MessageOpenStorage() {}

	// *******************************************************************
	@Override
	public IMessage onMessage(MessageOpenStorage message, MessageContext context) {
		TileEntity tileEntity = FMLClientHandler.instance().getClient().theWorld.getTileEntity(message.x, message.y, message.z);
		
		if(tileEntity == null || tileEntity instanceof TEDimChest == false)
			return null;
		
		TEDimChest tileEntityDimChest = (TEDimChest) tileEntity;

		tileEntityDimChest.setOpenCount(message.openCount);

		FMLClientHandler.instance().getClient().theWorld.markBlockForUpdate(message.x, message.y, message.z);
		
		return null;
	}

	// *******************************************************************
	@Override
	public void toBytes(ByteBuf out) {
		out.writeInt(this.x);
		out.writeInt(this.y);
		out.writeInt(this.z);

		out.writeInt(this.openCount);
	}
	
	@Override
	public void fromBytes(ByteBuf in) {
		this.x = in.readInt();
		this.y = in.readInt();
		this.z = in.readInt();

		this.openCount = in.readInt();
	}

	// *******************************************************************

	// *******************************************************************

}
