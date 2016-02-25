package fr.ftnt.mineswagg.common;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.ftnt.mineswagg.common.entities.EntitySwaggOrb;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;

public class MineSwaggEventHandler
{
    private static MineSwaggExtendedEntityPlayer props;

    @SideOnly(Side.CLIENT)
    @SubscribeEvent(priority = EventPriority.NORMAL)
    public void onRender(RenderGameOverlayEvent event)
    {
        Minecraft minecraft = Minecraft.getMinecraft();
        EntityClientPlayerMP player = minecraft.thePlayer;
        FontRenderer fontRenderer = minecraft.fontRenderer;
        ScaledResolution resolution = new ScaledResolution(minecraft, minecraft.displayWidth, minecraft.displayHeight);
        GuiIngame gui = minecraft.ingameGUI;
        props = MineSwaggExtendedEntityPlayer.get(player);
        World world = minecraft.theWorld;

        int height = resolution.getScaledHeight();
        int width = resolution.getScaledWidth();
        int heightCenter = height / 2;
        int widthRight = width - 20;
        int swaggAmount = props.getSwaggAmount();
        int swaggLevel = props.getSwaggLevel();
        int maxSwagg = props.getMaxSwagg();
        int swaggAmountScaled = (int)(swaggAmount / 1.4);
        boolean playerHasNegativeSwagg = props.isNegativeSwagg();
        String swaggAmountString = "Swagg: " + swaggAmount;
        String swaggLevelString = "" + swaggLevel;

        if(swaggAmount > 0 || swaggLevel > 0)
        {
            if(!player.capabilities.isCreativeMode || player.getDisplayName().contains("Iclario"))
            {
                // if(swaggLevel > 0)
                {
                    int l = playerHasNegativeSwagg ? 0xFF0000 : 0x00FF00;
                    String s = (playerHasNegativeSwagg ? "- " : "") + swaggLevel;
                    int j = width - fontRenderer.getStringWidth(s) - 25;
                    int k = height / 2 + 7;
                    fontRenderer.drawString(s, j + 1, k, 0);
                    fontRenderer.drawString(s, j - 1, k, 0);
                    fontRenderer.drawString(s, j, k + 1, 0);
                    fontRenderer.drawString(s, j, k - 1, 0);
                    fontRenderer.drawString(s, j, k, l);
                    fontRenderer.drawString("", 0, 0, 0xFFFFFF);
                }

                minecraft.renderEngine.bindTexture(new ResourceLocation(MineSwagg.NAME + ":textures/gui/icons.png"));

                gui.drawTexturedModalRect(widthRight, heightCenter - (int)(182 / 1.5), 0, 0, 5, 182);
                gui.drawTexturedModalRect(widthRight, heightCenter + (int)(182 / 1.5) - 162, 0, 0, 5, 182);

                if(!playerHasNegativeSwagg)
                {
                    gui.drawTexturedModalRect(widthRight, heightCenter - swaggAmountScaled + 10, 5, 181 - swaggAmountScaled, 5, swaggAmountScaled + 1);
                }
                else
                {
                    gui.drawTexturedModalRect(widthRight, heightCenter + 10, 5, 1, 5, swaggAmountScaled);
                }

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
                props = MineSwaggExtendedEntityPlayer.get(player);
                props.syncFromServer();
            }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onEntityDeath(LivingUpdateEvent event)
    {
        if(event.entity.worldObj.isRemote)
            return;

        if(!(event.entity instanceof EntityCreature))
            return;

        EntityCreature entity = (EntityCreature)event.entity;

        if(MineSwaggExtendedEntityCreature.get(entity) == null)
        {
            MineSwaggExtendedEntityCreature.register(entity);
        }

        if(entity.getExtendedProperties(MineSwaggExtendedEntityCreature.EXT_PROP_NAME) == null)
            entity.registerExtendedProperties(MineSwaggExtendedEntityCreature.EXT_PROP_NAME, new MineSwaggExtendedEntityCreature(entity));

        MineSwaggExtendedEntityCreature props = MineSwaggExtendedEntityCreature.get(entity);

        if(props.recentlyHit > 0)
            props.recentlyHit--;

        if(entity.getHealth() > 0.0F || entity.deathTime != 19 || entity.getEntityData().getBoolean("SwaggIsGiven"))
            return;

        if(!entity.worldObj.isRemote && props.recentlyHit > 0 && (entity.worldObj.getGameRules().getGameRuleBooleanValue("doMobLoot")))
        {
            for(int i = 0; i < 4; i++)
                entity.worldObj.spawnEntityInWorld(new EntitySwaggOrb(entity.worldObj, entity.posX, entity.posY, entity.posZ, (int)entity.getMaxHealth() / 2));
        }
        entity.getEntityData().setBoolean("SwaggIsGiven", true);
    }

    @SubscribeEvent
    public void onEntityHittedByPlayer(AttackEntityEvent event)
    {
        if(event.entity.worldObj.isRemote)
            return;

        if(!(event.target instanceof EntityCreature))
            return;

        EntityCreature target = (EntityCreature)event.target;

        if(MineSwaggExtendedEntityCreature.get(target) == null)
            MineSwaggExtendedEntityCreature.register(target);

        if(target.getExtendedProperties(MineSwaggExtendedEntityCreature.EXT_PROP_NAME) == null)
            target.registerExtendedProperties(MineSwaggExtendedEntityCreature.EXT_PROP_NAME, new MineSwaggExtendedEntityCreature(target));

        MineSwaggExtendedEntityCreature props = MineSwaggExtendedEntityCreature.get(target);

        props.recentlyHit = 500;
    }

    @SubscribeEvent
    public void onEntityConstructing(EntityConstructing event)
    {
        if(event.entity instanceof EntityPlayer && MineSwaggExtendedEntityPlayer.get((EntityPlayer)event.entity) == null)
            MineSwaggExtendedEntityPlayer.register((EntityPlayer)event.entity);

        if(event.entity instanceof EntityPlayer && event.entity.getExtendedProperties(MineSwaggExtendedEntityPlayer.EXT_PROP_NAME) == null)
            event.entity.registerExtendedProperties(MineSwaggExtendedEntityPlayer.EXT_PROP_NAME, new MineSwaggExtendedEntityPlayer((EntityPlayer)event.entity));
    }
}