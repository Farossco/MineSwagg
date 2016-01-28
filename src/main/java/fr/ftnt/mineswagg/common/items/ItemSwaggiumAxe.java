package fr.ftnt.mineswagg.common.items;

import fr.ftnt.mineswagg.common.MineSwagg;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemSwaggiumAxe extends ItemAxe
{

    public ItemSwaggiumAxe()
    {
        super(MineSwagg.toolSwaggium);
        this.setUnlocalizedName("axeSwaggium");
        this.setTextureName(MineSwagg.MODID + ":swaggium_axe");
    }

    public boolean getIsRepairable(ItemStack input, ItemStack repair)
    {
        if(repair.getItem() == MineSwagg.itemSwaggiumIngot)
        {
            return true;
        }
        return false;
    }

    public boolean onBlockDestroyed(ItemStack stack, World world, Block block, int x, int y, int z, EntityLivingBase entity)
    {
        int y2 = y;
        while(world.getBlock(x, y2, z).isWood(world, x, y2, z) || world.getBlock(x, y2, z).isLeaves(world, x, y2, z))
        {
            world.getBlock(x, y2, z).dropBlockAsItem(world, x, y2, z, block.getDamageValue(world, x, y2, z), 0);
            world.setBlockToAir(x, y2, z);
            y2++;
        }

        return super.onBlockDestroyed(stack, world, block, x, y, z, entity);
    }

}