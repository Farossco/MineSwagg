package fr.ftnt.mineswagg.common.items;

import fr.ftnt.mineswagg.common.MineSwagg;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;

public class ItemSwaggiumShovel extends ItemSpade
{
    public ItemSwaggiumShovel()
    {
        super(MineSwagg.toolSwaggium);
        setUnlocalizedName("shovelSwaggium");
        setTextureName(MineSwagg.NAME + ":swaggium_shovel");
        setCreativeTab(MineSwagg.customTab);
    }

    public boolean getIsRepairable(ItemStack input, ItemStack repair)
    {
        if(repair.getItem() == MineSwagg.itemSwaggiumIngot)
        {
            return true;
        }
        return false;
    }
}