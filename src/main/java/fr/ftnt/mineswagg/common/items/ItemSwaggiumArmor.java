package fr.ftnt.mineswagg.common.items;

import fr.ftnt.mineswagg.common.MineSwagg;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ItemSwaggiumArmor extends ItemArmor
{
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

    public void onArmorTick(World world, EntityPlayer player, ItemStack stack)
    {
        if(stack.getItem() == MineSwagg.itemSwaggiumHelmet && (world.getBlockLightValue(MathHelper.floor_double(player.posX), MathHelper.floor_double(player.posY), MathHelper.floor_double(player.posZ)) < 7))
            player.addPotionEffect(new PotionEffect(Potion.nightVision.id, 240));
        player.addPotionEffect(new PotionEffect(Potion.resistance.id, 10, 2));
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