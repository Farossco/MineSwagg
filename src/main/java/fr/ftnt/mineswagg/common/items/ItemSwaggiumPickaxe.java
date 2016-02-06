package fr.ftnt.mineswagg.common.items;

import fr.ftnt.mineswagg.common.MineSwagg;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemSwaggiumPickaxe extends ItemPickaxe
{

    public ItemSwaggiumPickaxe()
    {
        super(MineSwagg.toolSwaggium);
        this.setUnlocalizedName("pickaxeSwaggium");
        this.setTextureName(MineSwagg.MODID + ":swaggium_pickaxe");
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
        if((double)block.getBlockHardness(world, x, y, z) != 0.0D)
        {
            stack.damageItem(1, entity);
        }

        return true;
    }

}