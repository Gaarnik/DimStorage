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
	public static void sendUpdateStorageToServer(TEDimChest tileEntity) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream(8);
		DataOutputStream out = new DataOutputStream(bos);

		try {
			out.writeInt(ID_STORAGE_UPDATE);

			out.writeInt(tileEntity.xCoord);
			out.writeInt(tileEntity.yCoord);
			out.writeInt(tileEntity.zCoord);

			out.writeUTF(tileEntity.getOwner());
			out.writeInt(tileEntity.getFreq());
			out.writeBoolean(tileEntity.isLocked());

			/*Packet250CustomPayload packet = new Packet250CustomPayload();
			packet.channel = CHANNEL_DIMCHEST;
			packet.data = bos.toByteArray();
			packet.length = bos.size();

			PacketDispatcher.sendPacketToServer(packet);*/

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/*private static void receiveUpdateStorageFromPlayer(DataInputStream in, Player player) throws Exception {
		int xCoord = in.readInt();
		int yCoord = in.readInt();
		int zCoord = in.readInt();

		String owner = in.readUTF();
		int freq = in.readInt();
		boolean locked = in.readBoolean();

		EntityPlayerMP playerMP = (EntityPlayerMP) player;
		TileEntity tileEntity = playerMP.worldObj.getBlockTileEntity(xCoord, yCoord, zCoord);

		if(tileEntity != null){
			if(tileEntity instanceof TEDimChest){
				TEDimChest tileEntityDimChest = (TEDimChest) tileEntity;

				tileEntityDimChest.setOwner(owner);
				tileEntityDimChest.setFreq(freq);
				tileEntityDimChest.setLocked(locked);

				tileEntityDimChest.reloadStorage();

				playerMP.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			}
		}
	}*/

	// *******************************************************************
	/*public static class DimStoragePacketHandler implements IPacketHandler {

		@Override
		public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
			if(packet.channel.equals(CHANNEL_DIMCHEST) == false)
				return;

			DataInputStream in = new DataInputStream(new ByteArrayInputStream(packet.data));

			try {
				int id = in.readInt();

				switch(id) {

				case ID_STORAGE_OPEN:
					receiveOpenStorageFromServer(in, player);
					break;

				case ID_STORAGE_UPDATE:
					receiveUpdateStorageFromPlayer(in, player);
					break;

				}
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
		}

	}*/

	// *******************************************************************

}
