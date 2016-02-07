package fr.ftnt.mineswagg.common.containers;

import fr.ftnt.mineswagg.common.tileentities.TileEntitySwaggGenerator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerSwaggGenerator extends Container
{
    private final TileEntitySwaggGenerator tile;

    public ContainerSwaggGenerator(TileEntitySwaggGenerator tile, InventoryPlayer inventory)
    {
        this.tile = tile;
        this.addSlotToContainer(new Slot(tile, 2, 116, 35)
        {
            @Override
            public boolean isItemValid(ItemStack stack)
            {
                return false;
            }
        });

        int i;
        for(i = 0; i < 3; ++i)
        {
            for(int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for(i = 0; i < 9; ++i)
        {
            this.addSlotToContainer(new Slot(inventory, i, 8 + i * 18, 142));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player)
    {
        return this.tile.isUseableByPlayer(player);
    }

    public void onContainerClosed(EntityPlayer player)
    {
        super.onContainerClosed(player);
        this.tile.closeInventory();
    }

    public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex)
    {
        return null;
    }
}