package com.gaarnik.dimstorage.storage.tank;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

import com.gaarnik.dimstorage.storage.AbstractDimStorage;
import com.gaarnik.dimstorage.storage.DimStorageManager;
import com.gaarnik.dimstorage.util.Tank;

public class DimTankStorage extends AbstractDimStorage implements IFluidHandler {
	// ****************************************************************
	public static final String TYPE = "DimTank";

	// ****************************************************************
	private DimTank tank;

	// ****************************************************************
	public DimTankStorage(DimStorageManager manager, String owner, int freq) {
		super(manager, owner, freq);
		
		this.tank = new DimTank(16 * FluidContainerRegistry.BUCKET_VOLUME);
	}

	// ****************************************************************
	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		return this.tank.fill(resource, doFill);
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		return tank.drain(resource, doDrain);
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		return tank.drain(maxDrain, doDrain);
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return true;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return true;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		return new FluidTankInfo[]{ this.tank.getInfo() };
	}
	
	// ****************************************************************
	@Override
	public void loadFromTag(NBTTagCompound tag) {
		this.tank.fromTag(tag.getCompoundTag("tank"));
	}

	@Override
	public NBTTagCompound saveToTag() {
		NBTTagCompound compound = new NBTTagCompound();
        compound.setTag("tank", this.tank.toTag());

        return compound;
	}

	// ****************************************************************
	private class DimTank extends Tank {

		public DimTank(int capacity) {
			super(capacity);
		}
		
		@Override
		public void onLiquidChanged() {
			setDirty();
		}
		
	}

	// ****************************************************************
	@Override
	public String getType() { return TYPE; }
	
	public FluidStack getFluid() {
        return this.tank.getFluid();
    }

}
