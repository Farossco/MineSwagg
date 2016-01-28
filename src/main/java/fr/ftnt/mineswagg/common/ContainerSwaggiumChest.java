package fr.ftnt.mineswagg.common;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerSwaggiumChest extends Container
{
    private final TileEntitySwaggiumChest tileSwaggiumChest;

    public ContainerSwaggiumChest(TileEntitySwaggiumChest tile, InventoryPlayer playerInventory)
    {
        this.tileSwaggiumChest = tile;
        tile.openInventory();
        int rows = this.tileSwaggiumChest.getSizeInventory() / 9;
        int i = (this.tileSwaggiumChest.getSizeInventory() / 9 - 4) * 18;
        int j;
        int k;
        int l = -19;
        int m = 4;

        // -- Chest Inventory --
        for(j = 0; j < rows; ++j)
        {
            for(k = 0; k < 9; ++k)
            {
                this.addSlotToContainer(new Slot(tile, k + j * 9, 8 + k * 18 + m, j * 18 + l + 27));
            }
        }

        // -- Player Inventory --
        for(j = 0; j < 3; ++j)
        {
            for(k = 0; k < 9; ++k)
            {
                this.addSlotToContainer(new Slot(playerInventory, k + j * 9 + 9, 8 + k * 18 + m, 103 + j * 18 + i + l));
            }
        }
        for(j = 0; j < 9; ++j)
        {
            this.addSlotToContainer(new Slot(playerInventory, j, 8 + j * 18 + m, 161 + i + l));
        }
    }

    public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(slotIndex);

        if(slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if(slotIndex < this.tileSwaggiumChest.getSizeInventory())
            {
                if(!this.mergeItemStack(itemstack1, this.tileSwaggiumChest.getSizeInventory(), this.inventorySlots.size(), true))
                {
                    return null;
                }
            }
            else if(!this.mergeItemStack(itemstack1, 0, this.tileSwaggiumChest.getSizeInventory(), false))
            {
                return null;
            }

            if(itemstack1.stackSize == 0)
            {
                slot.putStack((ItemStack)null);
            }
            else
            {
                slot.onSlotChanged();
            }
        }

        return itemstack;
    }

    @Override
    public boolean canInteractWith(EntityPlayer player)
    {
        return this.tileSwaggiumChest.isUseableByPlayer(player);
    }

    public void onContainerClosed(EntityPlayer player)
    {
        super.onContainerClosed(player);
        this.tileSwaggiumChest.closeInventory();
    }

}
