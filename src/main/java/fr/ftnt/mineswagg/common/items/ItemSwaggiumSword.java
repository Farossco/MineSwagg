package fr.ftnt.mineswagg.common.items;

import fr.ftnt.mineswagg.common.MineSwagg;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

public class ItemSwaggiumSword extends ItemSword
{
    public ItemSwaggiumSword()
    {
        super(MineSwagg.toolSwaggium);
        setUnlocalizedName("swordSwaggium");
        setTextureName(MineSwagg.NAME + ":swaggium_sword");
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