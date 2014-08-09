package com.gaarnik.dimstorage.network.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;

import com.gaarnik.dimstorage.network.AbstractPacket;
import com.gaarnik.dimstorage.tilentity.TEDimChest;

public class PacketUpdateTE extends AbstractPacket {
	// *******************************************************************

	// *******************************************************************
	private int x, y, z;
	
	private String owner;
	private int freq;
	
	private int openCount;

	// *******************************************************************
	public PacketUpdateTE(TEDimChest te) {
		this.x = te.xCoord;
		this.y = te.yCoord;
		this.z = te.zCoord;
		
		this.owner = te.getOwner();
		this.freq = te.getFreq();
		
		this.openCount = te.getOpenCount();
	}
	
	public PacketUpdateTE() {}

	// *******************************************************************
	@Override
	public void handleClientSide(EntityPlayer player) {}

	@Override
	public void handleServerSide(EntityPlayer player) {
		EntityPlayerMP playerMP = (EntityPlayerMP) player;
		TileEntity tileEntity = playerMP.worldObj.getTileEntity(this.x, this.y, this.z);
		
		if(tileEntity == null || tileEntity instanceof TEDimChest == false)
			return;
		
		TEDimChest tileEntityDimChest = (TEDimChest) tileEntity;
		tileEntityDimChest.setOwner(this.owner);
		tileEntityDimChest.setFreq(this.freq);
		tileEntityDimChest.setOpenCount(this.openCount);

		tileEntityDimChest.reloadStorage();

		playerMP.worldObj.markBlockForUpdate(this.x, this.y, this.z);
	}

	// *******************************************************************
	@Override
	public void writeData(ByteBuf out) {
		out.writeInt(this.x);
		out.writeInt(this.y);
		out.writeInt(this.z);
		
		out.writeInt(this.owner.length());
		out.writeBytes(this.owner.getBytes());
		
		out.writeInt(this.freq);

		out.writeInt(this.openCount);
	}

	@Override
	public void readData(ByteBuf in) {
		this.x = in.readInt();
		this.y = in.readInt();
		this.z = in.readInt();
		
		byte[] buffer = new byte[in.readInt()];
		in.readBytes(buffer);
		this.owner = new String(buffer);
		
		this.freq = in.readInt();

		this.openCount = in.readInt();
	}

}
