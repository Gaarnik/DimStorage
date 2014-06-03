package com.gaarnik.dimstorage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;

import com.gaarnik.dimstorage.common.TEDimChest;

import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

public class DimStorageNetwork {
	// *******************************************************************
	public static final String CHANNEL_DIMCHEST = "ds_dimchest";

	public static final int ID_STORAGE_UPDATE = 1;

	// *******************************************************************
	public static void sendUpdateStorage(TEDimChest tileEntity) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream(8);
		DataOutputStream out = new DataOutputStream(bos);

		try {
			out.writeInt(ID_STORAGE_UPDATE);

			out.writeInt(tileEntity.xCoord);
			out.writeInt(tileEntity.yCoord);
			out.writeInt(tileEntity.zCoord);

			out.writeUTF(tileEntity.getOwner());
			out.writeInt(tileEntity.getFreq());

			Packet250CustomPayload packet = new Packet250CustomPayload();
			packet.channel = CHANNEL_DIMCHEST;
			packet.data = bos.toByteArray();
			packet.length = bos.size();

			PacketDispatcher.sendPacketToServer(packet);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private static void receiveUpdateStorage(INetworkManager manager, Packet250CustomPayload packet, Player player) {
		DataInputStream in = new DataInputStream(new ByteArrayInputStream(packet.data));

		try {
			// skip packet id (not used for now)
			in.readInt();

			int xCoord = in.readInt();
			int yCoord = in.readInt();
			int zCoord = in.readInt();

			String owner = in.readUTF();
			int freq = in.readInt();

			EntityPlayerMP playerMP = (EntityPlayerMP) player;
			TileEntity tileEntity = playerMP.worldObj.getBlockTileEntity(xCoord, yCoord, zCoord);

			if(tileEntity != null){
				if(tileEntity instanceof TEDimChest){
					TEDimChest tileEntityDimChest = (TEDimChest) tileEntity;
					tileEntityDimChest.setOwner(owner);
					tileEntityDimChest.setFreq(freq);
					tileEntityDimChest.reloadStorage();
					
					playerMP.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}

	// *******************************************************************
	public static class DimStoragePacketHandler implements IPacketHandler {

		@Override
		public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
			if(packet.channel.equals(CHANNEL_DIMCHEST))
				receiveUpdateStorage(manager, packet, player);
		}

	}

	// *******************************************************************

}
