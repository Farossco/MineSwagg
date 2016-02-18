package fr.ftnt.mineswagg.common;

import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;

public class MineSwaggEventHandler
{
    private static MineSwaggExtendedEntity props;

    @SideOnly(Side.CLIENT)
    @SubscribeEvent(priority = EventPriority.NORMAL)
    public void onRender(RenderGameOverlayEvent event)
    {
        
        Minecraft minecraft = Minecraft.getMinecraft();
        EntityClientPlayerMP player = minecraft.thePlayer;
        FontRenderer fontRenderer = minecraft.fontRenderer;
        ScaledResolution resolution = new ScaledResolution(minecraft, minecraft.displayWidth, minecraft.displayHeight);
        GuiIngame gui = minecraft.ingameGUI;
        props = MineSwaggExtendedEntity.get(player);
        World world = minecraft.theWorld;

        int height = resolution.getScaledHeight();
        int width = resolution.getScaledWidth();
        int height2 = height - 500;
        int width2 = width / 2;
        int swaggAmount = props.getSwaggAmount();
        int swaggLevel = props.getSwaggLevel();
        int maxSwagg = props.getMaxSwagg();
        String swaggAmountString = "Swagg: " + swaggAmount;
        String swaggLevelString = "" + swaggLevel;

        if(swaggAmount > 0 || swaggLevel > 0)
        {
            if(!player.capabilities.isCreativeMode)
            {
                int y;
                if(player.getEquipmentInSlot(1) == null && player.getEquipmentInSlot(2) == null && player.getEquipmentInSlot(3) == null && player.getEquipmentInSlot(4) == null)
                {
                    y = 0;
                }
                else
                {
                    y = 10;

                }

                if(swaggLevel > 0)
                {

                    int l = 8453920;
                    String s = "" + swaggLevel;
                    int j = (width - fontRenderer.getStringWidth(s)) / 2;
                    int k = height - 54 - y;
                    fontRenderer.drawString(s, j + 1, k, 0);
                    fontRenderer.drawString(s, j - 1, k, 0);
                    fontRenderer.drawString(s, j, k + 1, 0);
                    fontRenderer.drawString(s, j, k - 1, 0);
                    fontRenderer.drawString(s, j, k, l);
                    fontRenderer.drawString("", 0, 0, 0xFFFFFF);
                }

                minecraft.renderEngine.bindTexture(new ResourceLocation("textures/gui/icons.png"));
                gui.drawTexturedModalRect(width2 - 91, height2 + 452 - y, 0, 74, maxSwagg, 5);

                minecraft.renderEngine.bindTexture(new ResourceLocation(MineSwagg.NAME + ":textures/gui/icons.png"));
                gui.drawTexturedModalRect(width2 - 91, height2 + 452 - y, 0, 0, swaggAmount % maxSwagg, 5);

                minecraft.renderEngine.bindTexture(new ResourceLocation("textures/gui/icons.png"));
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onPlayerJoinServer(EntityJoinWorldEvent event)
    {
        if(event.world.isRemote)
            if(event.entity instanceof EntityPlayer)
            {
                Minecraft minecraft = Minecraft.getMinecraft();
                EntityClientPlayerMP player = minecraft.thePlayer;
                props = MineSwaggExtendedEntity.get(player);
                props.syncFromServer();
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

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onEntityDeath(LivingDeathEvent event)
    {
        Entity source = event.source.getEntity();
        Entity target = event.entity;

        if(target.getEntityData().getBoolean("HasGivenSwagg") || !(target instanceof EntityLiving) || !(source instanceof EntityPlayer))
        {
            return;
        }

        EntityLiving living = (EntityLiving)target;
        EntityPlayer player = (EntityPlayer)source;
        int livingHealth = (int)living.getMaxHealth();
        props = MineSwaggExtendedEntity.get(player);

        props.addSwaggAmount(livingHealth);
        props.addSwaggLevel(target instanceof EntityDragon ? 7 : target instanceof EntityWither ? 3 : 0);
        target.getEntityData().setBoolean("HasGivenSwagg", true);

        Logger logger = MineSwagg.logger;
        logger.debug("addswagg: " + livingHealth);
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