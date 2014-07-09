package com.gaarnik.dimstorage.util;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidTank;

public class Tank implements IFluidTank {
	// ****************************************************************

	// ****************************************************************
	private FluidStack fluid;
	private boolean changeType;
	private int capacity;

	// ****************************************************************
	public Tank(FluidStack type, int capacity) {
		if(type == null) {
            this.fluid = new FluidStack(0, 0);
            this.changeType = true;
        }
        else
            this.fluid = FluidUtils.copy(type, 0);
		
        this.capacity = capacity;
	}
	
	public Tank(int capacity) {
        this(null, capacity);
    }

	// ****************************************************************
	@Override
	public int fill(FluidStack resource, boolean doFill) {
		if(resource == null || resource.fluidID <= 0)
            return 0;

        if(!this.canAccept(resource))
            return 0;
        
        int tofill = Math.min(this.getCapacity() - fluid.amount, resource.amount);
        
        if(doFill && tofill > 0) {
            if(!fluid.isFluidEqual(resource))
                fluid = FluidUtils.copy(resource, fluid.amount+tofill);
            else
                fluid.amount+=tofill;
            
            this.onLiquidChanged();
        }
        
        return tofill;
	}

	@Override
	public FluidStack drain(int maxDrain, boolean doDrain) {
		if(this.fluid.amount == 0 || maxDrain <= 0)
            return null;
        
        int todrain = Math.min(maxDrain, this.fluid.amount);
        
        if(doDrain && todrain > 0) {
            this.fluid.amount-=todrain;
            this.onLiquidChanged();
        }
        
        return FluidUtils.copy(fluid, todrain);
	}
	
	public FluidStack drain(FluidStack resource, boolean doDrain) {
        if (resource == null || !resource.isFluidEqual(fluid))
            return null;
        
        return drain(resource.amount, doDrain);
    }

	// ****************************************************************
	public void fromTag(NBTTagCompound tag) {
        this.fluid = FluidUtils.read(tag);
    }
    
    public NBTTagCompound toTag() {
        return FluidUtils.write(this.fluid, new NBTTagCompound());
    }

	// ****************************************************************
	public void onLiquidChanged() {}

	// ****************************************************************
	@Override
	public FluidStack getFluid() { return this.fluid.copy(); }

	public boolean canAccept(FluidStack type) {
        return type == null || type.fluidID <= 0 || (this.fluid.amount == 0 && this.changeType) || this.fluid.isFluidEqual(type);
    }

	@Override
	public int getFluidAmount() {
		return this.fluid.amount;
	}
	
	@Override
	public int getCapacity() { return this.capacity; }

	@Override
	public FluidTankInfo getInfo() {
		return new FluidTankInfo(this);
	}

}
