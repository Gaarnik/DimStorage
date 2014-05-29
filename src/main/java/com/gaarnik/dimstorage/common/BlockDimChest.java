package com.gaarnik.dimstorage.common;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.gaarnik.dimstorage.DimStorage;
import com.gaarnik.dimstorage.DimStorageGUIHandler;

public class BlockDimChest extends BlockContainer {
	// ****************************************************************

	// ****************************************************************

	// ****************************************************************
	public BlockDimChest(int id) {
		super(id, Material.rock);
		
		this.setHardness(20F);
		this.setResistance(100F);
		this.setStepSound(soundStoneFootstep);
		this.setCreativeTab(CreativeTabs.tabDecorations);
	}

	// ****************************************************************
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int idk, float what, float these, float are) {
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);

		if (tileEntity == null || player.isSneaking())
			return false;

		player.openGui(DimStorage.instance, DimStorageGUIHandler.GUI_DIMCHEST, world, x, y, z);

		return true;
	}

	// ****************************************************************

	// ****************************************************************

	// ****************************************************************
	@Override
	public TileEntity createNewTileEntity(World world) { return new TEDimChest(); }

}
