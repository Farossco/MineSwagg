package fr.ftnt.mineswagg.common.items;

import fr.ftnt.mineswagg.common.MineSwagg;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class itemSwaggiumIngot extends Item
{
    public itemSwaggiumIngot()
    {
        super();
        setUnlocalizedName("ingotSwaggium");
        setCreativeTab(MineSwagg.customTab);
        setTextureName(MineSwagg.NAME + ":swaggium_ingot");
    }

    @Override
    public boolean isBeaconPayment(ItemStack stack)
    {
        return true;
    }
}