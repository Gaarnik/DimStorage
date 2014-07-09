package com.gaarnik.dimstorage.block;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.gaarnik.dimstorage.tilentity.TEDimTank;

public class BlockDimTank extends BlockDimStorage {
	// ****************************************************************

	// ****************************************************************

	// ****************************************************************
	public BlockDimTank() {
		super();
	}

	// ****************************************************************

	// ****************************************************************
	@Override
	public String getTopIcon() { return "dimtank_top"; }

	@Override
	public String getSidesIcon() { return "dimtank_sides"; }
	
	// ****************************************************************

	// ****************************************************************
	@Override
	public TileEntity createNewTileEntity(World world, int metadata) { return new TEDimTank(); }

}
