package fr.ftnt.swaggmod.common.items;

import fr.ftnt.swaggmod.common.SwaggMod;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;

public class ItemSwaggiumPickaxe extends ItemPickaxe
{

    public ItemSwaggiumPickaxe()
    {
        super(SwaggMod.toolSwaggium);
        this.setUnlocalizedName("pickaxeSwaggium");
        this.setTextureName(SwaggMod.MODID + ":swaggium_pickaxe");
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