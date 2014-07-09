package com.gaarnik.dimstorage.util;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;

public class FluidUtils {
	
	public static FluidStack copy(FluidStack liquid, int quantity) {
        liquid = liquid.copy();
        liquid.amount = quantity;
        
        return liquid;
    }
	
	public static FluidStack read(NBTTagCompound tag) {
        FluidStack stack = FluidStack.loadFluidStackFromNBT(tag);
        return stack != null ? stack : new FluidStack(0, 0);
    }
    
    public static NBTTagCompound write(FluidStack fluid, NBTTagCompound tag) {
        return fluid == null || fluid.getFluid() == null ? new NBTTagCompound() : fluid.writeToNBT(new NBTTagCompound());
    }

}
