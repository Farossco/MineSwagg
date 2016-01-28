package fr.ftnt.mineswagg.common.items;

import fr.ftnt.mineswagg.common.MineSwagg;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;

public class ItemSwaggiumHoe extends ItemHoe
{

    public ItemSwaggiumHoe()
    {
        super(MineSwagg.toolSwaggium);
        this.setUnlocalizedName("hoeSwaggium");
        this.setTextureName(MineSwagg.MODID + ":swaggium_hoe");
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