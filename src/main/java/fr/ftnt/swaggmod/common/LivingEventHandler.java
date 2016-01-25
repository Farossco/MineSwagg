package fr.ftnt.swaggmod.common;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class LivingEventHandler
{
    @SubscribeEvent
    public void onLivingHurt(LivingHurtEvent event)
    {
        ItemStack boots = event.entityLiving.getEquipmentInSlot(1);
        ItemStack leggings = event.entityLiving.getEquipmentInSlot(2);
        ItemStack chestplate = event.entityLiving.getEquipmentInSlot(3);
        ItemStack helmet = event.entityLiving.getEquipmentInSlot(4);

        if(boots != null && boots.getItem() == SwaggMod.itemSwaggiumBoots && leggings != null && leggings.getItem() == SwaggMod.itemSwaggiumLeggings && chestplate != null && chestplate.getItem() == SwaggMod.itemSwaggiumChestplate && helmet != null && helmet.getItem() == SwaggMod.itemSwaggiumHelmet)
        {
            if(event.source.getEntity() != null && event.source.getEntity() instanceof EntityCreeper)
            {
                event.setCanceled(true);

            }
        }
    }

    @SubscribeEvent
    public void onLivingFall(LivingFallEvent event)
    {
        ItemStack boots = event.entityLiving.getEquipmentInSlot(1);
        if(boots != null && boots.getItem() == SwaggMod.itemSwaggiumBoots)
        {
            boots.damageItem(MathHelper.floor_float(event.distance), event.entityLiving);
            //event.entityLiving.worldObj.newExplosion(event.entityLiving, event.entityLiving.posX, event.entityLiving.posY, event.entityLiving.posZ, 2, true, true);
            event.distance = 0F;
        }
    }

}
