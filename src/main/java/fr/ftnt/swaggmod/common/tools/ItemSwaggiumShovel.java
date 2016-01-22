package fr.ftnt.swaggmod.common.tools;

import fr.ftnt.swaggmod.common.SwaggMod;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;

public class ItemSwaggiumShovel extends ItemSpade
{

    public ItemSwaggiumShovel(ToolMaterial material)
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