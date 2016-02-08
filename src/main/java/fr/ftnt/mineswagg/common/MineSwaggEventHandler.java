package fr.ftnt.mineswagg.common;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class MineSwaggEventHandler
{
    @SubscribeEvent
    public void onLivingHurt(LivingHurtEvent event)
    {
        ItemStack boots = event.entityLiving.getEquipmentInSlot(1);
        ItemStack leggings = event.entityLiving.getEquipmentInSlot(2);
        ItemStack chestplate = event.entityLiving.getEquipmentInSlot(3);
        ItemStack helmet = event.entityLiving.getEquipmentInSlot(4);

        if(boots != null && boots.getItem() == MineSwagg.itemSwaggiumBoots && leggings != null && leggings.getItem() == MineSwagg.itemSwaggiumLeggings && chestplate != null && chestplate.getItem() == MineSwagg.itemSwaggiumChestplate && helmet != null && helmet.getItem() == MineSwagg.itemSwaggiumHelmet)
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
        if(boots != null && boots.getItem() == MineSwagg.itemSwaggiumBoots)
        {
            boots.damageItem(MathHelper.floor_float(event.distance), event.entityLiving);
            // event.entityLiving.worldObj.newExplosion(event.entityLiving, event.entityLiving.posX, event.entityLiving.posY, event.entityLiving.posZ, 2, true, true);
            event.distance = 0F;
        }
    }

    @SubscribeEvent
    public void onEntityConstructing(EntityConstructing event)
    {
        if(event.entity instanceof EntityPlayer && MineSwaggExtendedEntity.get((EntityPlayer)event.entity) == null)
            MineSwaggExtendedEntity.register((EntityPlayer)event.entity);

        if(event.entity instanceof EntityPlayer && event.entity.getExtendedProperties(MineSwaggExtendedEntity.EXT_PROP_NAME) == null)
            event.entity.registerExtendedProperties(MineSwaggExtendedEntity.EXT_PROP_NAME, new MineSwaggExtendedEntity((EntityPlayer)event.entity));
    }
}