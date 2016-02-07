package fr.ftnt.mineswagg.client;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.ftnt.mineswagg.common.MineSwagg;
import fr.ftnt.mineswagg.common.MineSwaggExtendedEntity;
import fr.ftnt.mineswagg.common.packets.PacketSwaggAmountRequest;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

public class SwaggBarHandler
{
    public static int swaggAmount, swaggLevel;
    static EntityPlayer player;
    FontRenderer fontrenderer;

    @SideOnly(Side.CLIENT)
    @SubscribeEvent(priority = EventPriority.NORMAL)
    public void onRender(RenderGameOverlayEvent event)
    {
        Minecraft minecraft = Minecraft.getMinecraft();
        player = minecraft.thePlayer;
        fontrenderer = minecraft.fontRenderer;
        ScaledResolution resolution = new ScaledResolution(minecraft, minecraft.displayWidth, minecraft.displayHeight);
        GuiIngame gui = minecraft.ingameGUI;
        MineSwaggExtendedEntity props = MineSwaggExtendedEntity.get(player);
        World world = minecraft.theWorld;

        // -- Getting swagg amount and level from server --
        MineSwagg.network.sendToServer(new PacketSwaggAmountRequest());
        EntityClientPlayerMP player = minecraft.thePlayer;
        props.setSwaggAmount(this.swaggAmount);
        props.setSwaggLevel(this.swaggLevel);

        int height = resolution.getScaledHeight() - 500;
        int width = resolution.getScaledWidth() / 2;
        int swaggAmount = props.getSwaggAmount();
        int swaggLevel = props.getSwaggLevel();
        int maxSwagg = props.getMaxSwagg();
        String swaggAmountString = "Swagg: " + swaggAmount;
        String swaggLevelString = "Swagg level: " + swaggLevel;

        if(!(swaggAmount == 0 && swaggLevel == 0))
        {
            fontrenderer.drawString(swaggAmountString, 1, 1, 0xffffffff);
            fontrenderer.drawString(swaggLevelString, 1, 10, 0xffffffff);

            if(!this.player.capabilities.isCreativeMode)
            {
                minecraft.renderEngine.bindTexture(new ResourceLocation("textures/gui/icons.png"));
                gui.drawTexturedModalRect(width - 91, height + 452, 0, 74, maxSwagg, 5);

                minecraft.renderEngine.bindTexture(new ResourceLocation(MineSwagg.MODID + ":textures/gui/icons.png"));
                gui.drawTexturedModalRect(width - 91, height + 452, 0, 0, swaggAmount % maxSwagg, 5);
                //
                minecraft.renderEngine.bindTexture(new ResourceLocation("textures/gui/icons.png"));
            }
        }
    }
}
