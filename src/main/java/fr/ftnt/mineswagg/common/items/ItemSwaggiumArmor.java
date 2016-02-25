package fr.ftnt.mineswagg.common.items;

import fr.ftnt.mineswagg.common.MineSwagg;
import fr.ftnt.mineswagg.common.MineSwaggExtendedEntityPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemSwaggiumArmor extends ItemArmor
{
    private int swaggIncreaser = 0;
    private int type;

    public ItemSwaggiumArmor(int type)
    {
        super(MineSwagg.armorSwaggium, 0, type);
        this.type = type;
        switch(type)
        {
            case 0:
                setUnlocalizedName("helmetSwaggium");
                setTextureName(MineSwagg.NAME + ":swaggium_helmet");
                break;
            case 1:
                setUnlocalizedName("chestplateSwaggium");
                setTextureName(MineSwagg.NAME + ":swaggium_chestplate");
                break;
            case 2:
                setUnlocalizedName("leggingsSwaggium");
                setTextureName(MineSwagg.NAME + ":swaggium_leggings");
                break;
            case 3:
                setUnlocalizedName("bootsSwaggium");
                setTextureName(MineSwagg.NAME + ":swaggium_boots");
                break;
            default:
                setUnlocalizedName("unknownToolSwaggium");
                break;
        }
        this.setCreativeTab(MineSwagg.customTab);
    }

    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
    {
        if(slot == 2)
        {
            return MineSwagg.NAME + ":/textures/models/armor/swaggium_layer_2.png";
        }
        return MineSwagg.NAME + ":/textures/models/armor/swaggium_layer_1.png";
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack stack)
    {
        this.swaggIncreaser += 1;
        MineSwaggExtendedEntityPlayer props = MineSwaggExtendedEntityPlayer.get(player);
        if(this.swaggIncreaser >= 1000000 / (type == 0 ? 4000 : type == 1 ? 12000 : type == 2 ? 6000 : type == 3 ? 2000 : 1) && !world.isRemote)
        {
            props.addSwaggAmount(1, true);
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