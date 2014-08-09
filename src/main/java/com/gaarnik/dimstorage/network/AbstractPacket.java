package com.gaarnik.dimstorage.network;

import net.minecraft.entity.player.EntityPlayer;
import io.netty.buffer.ByteBuf;

public abstract class AbstractPacket {

	public abstract void handleClientSide(EntityPlayer player);

	public abstract void handleServerSide(EntityPlayer player);

	public abstract void writeData(ByteBuf out);

	public abstract void readData(ByteBuf in);

}
