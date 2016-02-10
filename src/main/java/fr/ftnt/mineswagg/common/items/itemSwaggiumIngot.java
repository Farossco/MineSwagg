package fr.ftnt.mineswagg.common.items;

import fr.ftnt.mineswagg.common.MineSwagg;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class itemSwaggiumIngot extends Item
{
    public itemSwaggiumIngot()
    {
        super();
        this.setUnlocalizedName("ingotSwaggium");
        this.setCreativeTab(CreativeTabs.tabMaterials);
        this.setTextureName(MineSwagg.MODID + ":swaggium_ingot");
    }
    
    @Override
    public boolean isBeaconPayment(ItemStack stack)
    {
        return true;
    }
}
