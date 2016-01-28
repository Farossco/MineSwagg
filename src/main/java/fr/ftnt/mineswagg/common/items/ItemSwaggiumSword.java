package fr.ftnt.mineswagg.common.items;

import fr.ftnt.mineswagg.common.MineSwagg;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

public class ItemSwaggiumSword extends ItemSword
{

    public ItemSwaggiumSword()
    {
        super(MineSwagg.toolSwaggium);
        this.setUnlocalizedName("swordSwaggium");
        this.setTextureName(MineSwagg.MODID + ":swaggium_sword");
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