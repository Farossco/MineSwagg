package fr.ftnt.swaggmod.common;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class itemSwaggiumIngot extends Item
{
    public itemSwaggiumIngot()
    {
        super();
        this.setUnlocalizedName("ingotSwaggium");
        this.setCreativeTab(CreativeTabs.tabMaterials);
        this.setTextureName(SwaggMod.MODID + ":swaggium_ingot");
    }
}
