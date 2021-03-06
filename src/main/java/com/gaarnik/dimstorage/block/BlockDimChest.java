package com.gaarnik.dimstorage.block;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.gaarnik.dimstorage.tilentity.TEDimChest;

public class BlockDimChest extends BlockDimStorage {
	// ****************************************************************

	// ****************************************************************

	// ****************************************************************
	public BlockDimChest() {
		super();
	}

	// ****************************************************************

	// ****************************************************************
	@Override
	public String getTopIcon() { return "dimchest_top"; }

	@Override
	public String getSidesIcon() { return "dimchest_sides"; }
	
	// ****************************************************************

	// ****************************************************************
	@Override
	public TileEntity createNewTileEntity(World world, int metadata) { return new TEDimChest(); }

}
