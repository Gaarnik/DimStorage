package com.gaarnik.dimstorage;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import com.gaarnik.dimstorage.common.TEDimChest;

public class DimStorageNetwork {
	// *******************************************************************
	public static final String CHANNEL_DIMCHEST = "ds_dimchest";

	public static final int ID_STORAGE_OPEN 	= 1;
	public static final int ID_STORAGE_UPDATE 	= 2;

	// *******************************************************************
	public static void sendOpenStorageToPlayers(TEDimChest tileEntity, boolean open) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream(8);
		DataOutputStream out = new DataOutputStream(bos);

		try {
			out.writeInt(ID_STORAGE_OPEN);

			out.writeInt(tileEntity.xCoord);
			out.writeInt(tileEntity.yCoord);
			out.writeInt(tileEntity.zCoord);
			
			out.writeBoolean(open);

			/*Packet250CustomPayload packet = new Packet250CustomPayload();
			packet.channel = CHANNEL_DIMCHEST;
			packet.data = bos.toByteArray();
			packet.length = bos.size();

			PacketDispatcher.sendPacketToAllPlayers(packet);*/

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/*private static void receiveOpenStorageFromServer(DataInputStream in, Player player) throws Exception {
		int xCoord = in.readInt();
		int yCoord = in.readInt();
		int zCoord = in.readInt();
		
		boolean open = in.readBoolean();
		
		EntityClientPlayerMP playerMP = (EntityClientPlayerMP) player;
		TileEntity tileEntity = playerMP.worldObj.getBlockTileEntity(xCoord, yCoord, zCoord);

		if(tileEntity != null){
			if(tileEntity instanceof TEDimChest){
				TEDimChest tileEntityDimChest = (TEDimChest) tileEntity;

				tileEntityDimChest.setOpening(open);

				playerMP.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			}
		}
	}*/

	// *******************************************************************

}
