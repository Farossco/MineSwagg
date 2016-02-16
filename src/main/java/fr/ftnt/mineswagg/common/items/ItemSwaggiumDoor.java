package fr.ftnt.mineswagg.common.items;

import fr.ftnt.mineswagg.common.MineSwagg;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemDoor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ItemSwaggiumDoor extends ItemDoor
{
    private Material doorMaterial;
    private static final String __OBFID = "CL_00000020";

    public ItemSwaggiumDoor()
    {
        super(Material.iron);
        setUnlocalizedName("doorSwaggium");
        setTextureName(MineSwagg.NAME + ":door_swaggium");
        setCreativeTab(MineSwagg.customTab);
    }

    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float posX, float posY, float posZ)
    {
        if(side != 1)
            return false;

        ++y;
        Block block;
        block = MineSwagg.BlockSwaggiumDoor;

        if(player.canPlayerEdit(x, y, z, side, stack) && player.canPlayerEdit(x, y + 1, z, side, stack))
        {
            if(!block.canPlaceBlockAt(world, x, y, z))
                return false;

            int i1 = MathHelper.floor_double((double)((player.rotationYaw + 180.0F) * 4.0F / 360.0F) - 0.5D) & 3;
            placeDoorBlock(world, x, y, z, i1, block);
            --stack.stackSize;
            return true;
        }

        return false;
    }
}