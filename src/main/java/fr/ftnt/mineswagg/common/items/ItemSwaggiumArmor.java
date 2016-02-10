package fr.ftnt.mineswagg.common.items;

import fr.ftnt.mineswagg.common.MineSwagg;
import fr.ftnt.mineswagg.common.MineSwaggExtendedEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemSwaggiumArmor extends ItemArmor
{
    private int swaggIncreaser = 0;

    public ItemSwaggiumArmor(int type)
    {
        super(MineSwagg.armorSwaggium, 0, type);
        switch(type)
        {
            case 0:
                this.setUnlocalizedName("helmetSwaggium");
                this.setTextureName(MineSwagg.MODID + ":swaggium_helmet");
                break;
            case 1:
                this.setUnlocalizedName("chestplateSwaggium");
                this.setTextureName(MineSwagg.MODID + ":swaggium_chestplate");
                break;
            case 2:
                this.setUnlocalizedName("leggingsSwaggium");
                this.setTextureName(MineSwagg.MODID + ":swaggium_leggings");
                break;
            case 3:
                this.setUnlocalizedName("bootsSwaggium");
                this.setTextureName(MineSwagg.MODID + ":swaggium_boots");
                break;
            default:
                this.setUnlocalizedName("unknownToolSwaggium");
                break;
        }
    }

    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
    {
        if(slot == 2)
        {
            return MineSwagg.MODID + ":/textures/models/armor/swaggium_layer_2.png";
        }
        return MineSwagg.MODID + ":/textures/models/armor/swaggium_layer_1.png";
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack stack)
    {
        this.swaggIncreaser += 1;
        MineSwaggExtendedEntity props = MineSwaggExtendedEntity.get(player);
        if(this.swaggIncreaser >= 1000000 / ((stack.isItemEqual(new ItemStack(MineSwagg.itemSwaggiumHelmet))) ? 2000 : (stack.isItemEqual(new ItemStack(MineSwagg.itemSwaggiumChestplate))) ? 6000 : (stack.isItemEqual(new ItemStack(MineSwagg.itemSwaggiumLeggings))) ? 3000 : (stack.isItemEqual(new ItemStack(MineSwagg.itemSwaggiumBoots))) ? 1000 : 1) && !world.isRemote)
        {
            props.addSwaggAmount(1);
            this.swaggIncreaser = 0;
        }
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