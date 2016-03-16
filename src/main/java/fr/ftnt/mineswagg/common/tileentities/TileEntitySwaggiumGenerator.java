package fr.ftnt.mineswagg.common.tileentities;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.ftnt.mineswagg.common.MineSwagg;
import fr.ftnt.mineswagg.common.blocks.BlockSwaggiumGenerator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.Constants;

public class TileEntitySwaggiumGenerator extends TileEntity implements IInventory
{
    private ItemStack outputStack;
    private int stockedSwagg, remainingTime;
    private String customName;

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        NBTTagList nbttaglist = compound.getTagList("Item", Constants.NBT.TAG_COMPOUND);
        NBTTagCompound compound1 = nbttaglist.getCompoundTagAt(0);
        byte b0 = compound1.getByte("Slot");

        this.outputStack = ItemStack.loadItemStackFromNBT(compound1);
        this.stockedSwagg = compound.getShort("stockedSwagg");
        System.out.println(compound.getShort("stockedSwagg"));
        this.remainingTime = compound.getShort("remainingTime");

        if(compound.hasKey("CustomName", Constants.NBT.TAG_COMPOUND))
        {
            this.customName = compound.getString("CustomName");
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setShort("stockedSwagg", (short)this.stockedSwagg);
        compound.setShort("remainingTime", (short)this.remainingTime);
        NBTTagList nbttaglist = new NBTTagList();

        if(this.outputStack != null)
        {
            NBTTagCompound nbttagcompound1 = new NBTTagCompound();
            nbttagcompound1.setByte("Slot", (byte)0);
            this.outputStack.writeToNBT(nbttagcompound1);
            nbttaglist.appendTag(nbttagcompound1);
        }

        compound.setTag("Item", nbttaglist);
        if(this.hasCustomInventoryName())
        {
            compound.setString("CustomName", this.customName);
        }
    }

    @SideOnly(Side.CLIENT)
    public int getRemainingTimeScaled(int size)
    {
        // System.out.println("Remaining: " + remainingTime * size / 182);
        return this.remainingTime * size / 182;
    }

    @Override
    public int getSizeInventory()
    {
        return 1;
    }

    @Override
    public ItemStack getStackInSlot(int slotIndex)
    {
        return this.outputStack;
    }

    @Override
    public ItemStack decrStackSize(int slotIndex, int amount)
    {
        if(this.outputStack != null)
        {
            ItemStack itemstack;

            if(this.outputStack.stackSize <= amount)
            {
                itemstack = this.outputStack;
                this.outputStack = null;
                return itemstack;
            }
            else
            {
                itemstack = this.outputStack.splitStack(amount);

                if(this.outputStack.stackSize == 0)
                {
                    this.outputStack = null;
                }

                return itemstack;
            }
        }
        else
        {
            return null;
        }
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slotIndex_)
    {
        if(this.outputStack != null)
        {
            ItemStack itemstack = this.outputStack;
            this.outputStack = null;
            return itemstack;
        }
        else
        {
            return null;
        }
    }

    @Override
    public void setInventorySlotContents(int slotIndex, ItemStack stack)
    {
        this.outputStack = stack;

        if(stack != null && stack.stackSize > this.getInventoryStackLimit())
        {
            stack.stackSize = this.getInventoryStackLimit();
        }
    }

    @Override
    public String getInventoryName()
    {
        return this.hasCustomInventoryName() ? this.customName : "container.swaggiumGenerator";
    }

    @Override
    public boolean hasCustomInventoryName()
    {
        return(this.customName != null && !this.customName.isEmpty());
    }

    public void setCustomName(String customName)
    {
        this.customName = customName;
    }

    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player)
    {
        return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : player.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
    }

    @Override
    public void openInventory()
    {}

    @Override
    public void closeInventory()
    {}

    @Override
    public boolean isItemValidForSlot(int slotIndex, ItemStack stack)
    {
        return false;
    }

    public void updateEntity()
    {
        boolean full = false;
        boolean flag = false;

        if(this.outputStack != null && this.outputStack.stackSize >= 64)
        {
            full = true;
        }

        if(!full)
        {
            if(!this.worldObj.isRemote)
            {
                if(this.stockedSwagg > 0 && remainingTime == 0)
                {
                    this.remainingTime = 182;
                    this.stockedSwagg--;
                    BlockSwaggiumGenerator.updateSwaggGeneratorBlockState(isGenerating(), this.worldObj, this.xCoord, this.yCoord, this.zCoord);
                }
                else if(remainingTime == 1)
                {
                    if(this.outputStack == null)
                    {
                        this.outputStack = new ItemStack(MineSwagg.itemSwaggiumIngot, 1);
                    }
                    else
                    {
                        this.outputStack = new ItemStack(MineSwagg.itemSwaggiumIngot, this.outputStack.stackSize + 1);
                    }
                    this.remainingTime = 0;
                    if(stockedSwagg == 0)
                    {
                        flag = true;
                    }
                }
                else if(this.remainingTime > 0)
                {
                    this.remainingTime--;
                }
                if(remainingTime == 0 && flag)
                {
                    BlockSwaggiumGenerator.updateSwaggGeneratorBlockState(isGenerating(), this.worldObj, this.xCoord, this.yCoord, this.zCoord);
                    flag = false;
                }

            }
        }
    }

    public void addStockedSwagg(int amount)
    {
        this.stockedSwagg += amount;
    }

    public int getStockedSwagg()
    {
        return stockedSwagg;
    }

    public void setStockedSwagg(int stockedSwagg)
    {
        this.stockedSwagg = stockedSwagg;
    }

    public int getRemainingTime()
    {
        return remainingTime;
    }

    public void setRemainingTime(int remainingTime)
    {
        this.remainingTime = remainingTime;
    }

    public boolean isGenerating()
    {
        return remainingTime > 0;
    }
}
