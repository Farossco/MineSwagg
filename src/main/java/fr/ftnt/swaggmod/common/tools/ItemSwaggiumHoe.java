package fr.ftnt.swaggmod.common.tools;

import fr.ftnt.swaggmod.common.SwaggMod;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;

public class ItemSwaggiumHoe extends ItemHoe
{

    public ItemSwaggiumHoe(ToolMaterial material)
    {
        super(material);
    }

    public boolean getIsRepairable(ItemStack input, ItemStack repair)
    {
        if(repair.getItem() == SwaggMod.itemIngotSwaggium)
        {
            return true;
        }
        return false;
    }
}