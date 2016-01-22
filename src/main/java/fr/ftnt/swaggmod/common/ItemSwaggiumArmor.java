package fr.ftnt.swaggmod.common;

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

    public ItemSwaggiumArmor(ArmorMaterial material, int type)
    {
        super(material, 0, type);
    }

    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
    { // --- Must be modified ---
        if(slot == 2) // Leggings texture (layer_2)
        {
            return SwaggMod.MODID + ":/textures/models/armor/swaggium_layer_2.png";
        }
        return SwaggMod.MODID + ":/textures/models/armor/swaggium_layer_1.png"; // Other texture (Layer_1)
    }

    public void onArmorTick(World world, EntityPlayer player, ItemStack stack)
    {
        if(stack.getItem() == SwaggMod.itemHelmetSwaggium && (world.getBlockLightValue(MathHelper.floor_double(player.posX), MathHelper.floor_double(player.posY), MathHelper.floor_double(player.posZ)) < 7))
            player.addPotionEffect(new PotionEffect(Potion.nightVision.id, 240));
        player.addPotionEffect(new PotionEffect(Potion.resistance.id, 10, 2));
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
