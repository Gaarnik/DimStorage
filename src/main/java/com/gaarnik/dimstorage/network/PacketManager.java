package com.gaarnik.dimstorage.network;

import java.util.EnumMap;

import net.minecraft.entity.player.EntityPlayerMP;

import com.gaarnik.dimstorage.DimStorage;

import cpw.mods.fml.common.network.FMLEmbeddedChannel;
import cpw.mods.fml.common.network.FMLOutboundHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;

public class PacketManager {
	// *******************************************************************

	// *******************************************************************
	private EnumMap<Side, FMLEmbeddedChannel> channels;
	private ChannelHandler channelHandler;
	private PacketHandler packetHandler;

	// *******************************************************************
	public PacketManager() {
		this.channelHandler = new ChannelHandler();
		this.packetHandler = new PacketHandler();
		this.channels = NetworkRegistry.INSTANCE.newChannel(DimStorage.MODID, channelHandler, packetHandler);
	}

	// *******************************************************************
	public void registerPacket(Class<? extends AbstractPacket> packet, int discriminator) {
		this.channelHandler.addDiscriminator(discriminator, packet);
	}
	
	public void sendToAll(AbstractPacket packet) {
        this.channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.ALL);
        this.channels.get(Side.SERVER).writeAndFlush(packet);
    }

    public void sendTo(AbstractPacket packet, EntityPlayerMP player) {
        this.channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.PLAYER);
        this.channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(player);
        this.channels.get(Side.SERVER).writeAndFlush(packet);
    }

    public void sendToAllAround(AbstractPacket packet, NetworkRegistry.TargetPoint point) {
        this.channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.ALLAROUNDPOINT);
        this.channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(point);
        this.channels.get(Side.SERVER).writeAndFlush(packet);
    }

    public void sendToDimension(AbstractPacket packet, int dimensionId) {
        this.channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.DIMENSION);
        this.channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(dimensionId);
        this.channels.get(Side.SERVER).writeAndFlush(packet);
    }

    public void sendToServer(AbstractPacket packet) {
        this.channels.get(Side.CLIENT).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.TOSERVER);
        this.channels.get(Side.CLIENT).writeAndFlush(packet);
    }

	// *******************************************************************

	// *******************************************************************

	// *******************************************************************

}
