package fr.ftnt.mineswagg.common;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.ftnt.mineswagg.common.packets.PacketSwaggAmountRequest;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;

public class MineSwaggEventHandler
{
    @SideOnly(Side.CLIENT)
    private static int swaggAmount, swaggLevel, packetSleepTime = 0;
    @SideOnly(Side.CLIENT)
    private static Minecraft minecraft;
    @SideOnly(Side.CLIENT)
    private static EntityClientPlayerMP player;
    @SideOnly(Side.CLIENT)
    private static FontRenderer fontRenderer;
    @SideOnly(Side.CLIENT)
    private static ScaledResolution resolution;
    @SideOnly(Side.CLIENT)
    private static GuiIngame gui;
    @SideOnly(Side.CLIENT)
    private static MineSwaggExtendedEntity props;
    @SideOnly(Side.CLIENT)
    private static World world;

    @SideOnly(Side.CLIENT)
    @SubscribeEvent(priority = EventPriority.NORMAL)
    public void onRender(RenderGameOverlayEvent event)
    {
        minecraft = Minecraft.getMinecraft();
        player = minecraft.thePlayer;
        fontRenderer = minecraft.fontRenderer;
        resolution = new ScaledResolution(minecraft, minecraft.displayWidth, minecraft.displayHeight);
        gui = minecraft.ingameGUI;
        props = MineSwaggExtendedEntity.get(player);
        world = minecraft.theWorld;

        packetSleepTime += 1;
        if(packetSleepTime >= 1)
        { // -- Getting swagg amount and level from server --
            MineSwagg.network.sendToServer(new PacketSwaggAmountRequest());
            props.setSwaggAmount(this.swaggAmount);
            props.setSwaggLevel(this.swaggLevel);
            packetSleepTime = 0;
        }

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
            if(!this.player.capabilities.isCreativeMode)
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
    public static void setSwaggAmount(int swaggAmountArg)
    {
        swaggAmount = swaggAmountArg;
    }

    @SideOnly(Side.CLIENT)
    public static void setSwaggLevel(int swaggLevelArg)
    {
        swaggLevel = swaggLevelArg;
    }

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
    public void onEntityAttacking(AttackEntityEvent event)
    {
        EntityPlayer player = event.entityPlayer;

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