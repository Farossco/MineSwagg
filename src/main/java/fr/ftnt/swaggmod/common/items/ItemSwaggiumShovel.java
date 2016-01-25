package fr.ftnt.swaggmod.common.items;

import fr.ftnt.swaggmod.common.SwaggMod;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;

public class ItemSwaggiumShovel extends ItemSpade
{

    public ItemSwaggiumShovel()
    {
        super(SwaggMod.toolSwaggium);
        this.setUnlocalizedName("shovelSwaggium");
        this.setTextureName(SwaggMod.MODID + ":swaggium_shovel");
    }
    
    public boolean getIsRepairable(ItemStack input, ItemStack repair)
    {
        if(repair.getItem() == SwaggMod.itemSwaggiumIngot)
        {
            return true;
        }
        return false;
    }

}