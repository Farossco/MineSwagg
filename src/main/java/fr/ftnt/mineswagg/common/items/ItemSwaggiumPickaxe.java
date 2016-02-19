package fr.ftnt.mineswagg.common.items;

import fr.ftnt.mineswagg.common.MineSwagg;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;

public class ItemSwaggiumPickaxe extends ItemPickaxe
{
    public ItemSwaggiumPickaxe()
    {
        super(MineSwagg.toolSwaggium);
        setUnlocalizedName("pickaxeSwaggium");
        setTextureName(MineSwagg.NAME + ":swaggium_pickaxe");
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