package com.gaarnik.dimstorage.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import cpw.mods.fml.common.network.FMLIndexedMessageToMessageCodec;

public class ChannelHandler extends FMLIndexedMessageToMessageCodec<AbstractPacket> {

	@Override
	public void encodeInto(ChannelHandlerContext ctx, AbstractPacket packet, ByteBuf target) throws Exception {
		packet.writeData(target);
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf source, AbstractPacket packet) {
		packet.readData(source);
	}

}
