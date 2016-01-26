package fr.ftnt.swaggmod.common;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntitySwaggiumChest extends TileEntity
{
    private int number;
    
    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        this.number=compound.getInteger("Number");
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setInteger("Number", this.number);
    }

    public void increase()
    {
        this.number ++;
    }

    public void decrease()
    {
        this.number --;
    }

    public int getNumber()
    {
        return number;
    }

}
