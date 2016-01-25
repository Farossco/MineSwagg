package fr.ftnt.swaggmod.common.items;

import fr.ftnt.swaggmod.common.SwaggMod;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;

public class ItemSwaggiumHoe extends ItemHoe
{

    public ItemSwaggiumHoe()
    {
        super(SwaggMod.toolSwaggium);
        this.setUnlocalizedName("hoeSwaggium");
        this.setTextureName(SwaggMod.MODID + ":swaggium_hoe");
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