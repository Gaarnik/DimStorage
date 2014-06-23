package com.gaarnik.dimstorage;

import com.gaarnik.dimstorage.container.ContainerDimChest;
import com.gaarnik.dimstorage.gui.GUIDimCHest;
import com.gaarnik.dimstorage.tilentity.TEDimChest;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class DimStorageGUIHandler implements IGuiHandler {
	// ****************************************************************
	public static final int GUI_DIMCHEST = 1;

	// ****************************************************************
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tileEntity = world.getTileEntity(x, y, z);

		switch(ID) {

		case GUI_DIMCHEST:
			return new GUIDimCHest(player.inventory, (TEDimChest) tileEntity);

		default:
			return null;

		}
	}

	// ****************************************************************
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tileEntity = world.getTileEntity(x, y, z);

		switch(ID) {

		case GUI_DIMCHEST:
			return new ContainerDimChest(player.inventory, (TEDimChest) tileEntity);
			
		default:
			return null;

		}
	}

}
