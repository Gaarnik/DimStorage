package com.gaarnik.dimstorage.network.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

import com.gaarnik.dimstorage.network.AbstractPacket;
import com.gaarnik.dimstorage.tilentity.TEDimChest;

import cpw.mods.fml.client.FMLClientHandler;

public class PacketOpenDimChest extends AbstractPacket {
	// *******************************************************************

	// *******************************************************************
	private int x, y, z;

	private int openCount;

	// *******************************************************************
	public PacketOpenDimChest(TEDimChest te) {
		this.x = te.xCoord;
		this.y = te.yCoord;
		this.z = te.zCoord;

		this.openCount = te.getOpenCount();
	}
	
	public PacketOpenDimChest() {}

	// *******************************************************************
	@Override
	public void handleClientSide(EntityPlayer player) {
		TileEntity tileEntity = FMLClientHandler.instance().getClient().theWorld.getTileEntity(this.x, this.y, this.z);
		
		if(tileEntity == null || tileEntity instanceof TEDimChest == false)
			return;
		
		TEDimChest tileEntityDimChest = (TEDimChest) tileEntity;

		tileEntityDimChest.setOpenCount(this.openCount);

		FMLClientHandler.instance().getClient().theWorld.markBlockForUpdate(this.x, this.y, this.z);
	}

	@Override
	public void handleServerSide(EntityPlayer player) {}

	// *******************************************************************
	@Override
	public void writeData(ByteBuf out) {
		out.writeInt(this.x);
		out.writeInt(this.y);
		out.writeInt(this.z);

		out.writeInt(this.openCount);
	}

	@Override
	public void readData(ByteBuf in) {
		this.x = in.readInt();
		this.y = in.readInt();
		this.z = in.readInt();

		this.openCount = in.readInt();
	}

}
