package fr.ftnt.mineswagg.common;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class MineSwaggCreativeTab extends CreativeTabs
{
    public MineSwaggCreativeTab(String label)
    {
        super(label);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Item getTabIconItem()
    {
        ItemStack stack = new ItemStack(MineSwagg.itemSwaggiumIngot);
        return stack.getItem();
    }

}
