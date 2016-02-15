package fr.ftnt.mineswagg.common.items;

import fr.ftnt.mineswagg.common.MineSwagg;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;

public class ItemSwaggiumHoe extends ItemHoe
{
    public ItemSwaggiumHoe()
    {
        super(MineSwagg.toolSwaggium);
        setUnlocalizedName("hoeSwaggium");
        setTextureName(MineSwagg.NAME + ":swaggium_hoe");
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