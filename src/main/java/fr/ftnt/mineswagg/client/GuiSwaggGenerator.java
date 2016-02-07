package fr.ftnt.mineswagg.client;

import org.lwjgl.opengl.GL11;

import fr.ftnt.mineswagg.common.MineSwagg;
import fr.ftnt.mineswagg.common.containers.ContainerSwaggGenerator;
import fr.ftnt.mineswagg.common.tileentities.TileEntitySwaggGenerator;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiSwaggGenerator extends GuiContainer
{
    private static final ResourceLocation texture = new ResourceLocation(MineSwagg.MODID + ":textures/gui/container/swaggGenerator.png");
    private TileEntitySwaggGenerator tile;

    public GuiSwaggGenerator(TileEntitySwaggGenerator tile, InventoryPlayer inventory)
    {
        super(new ContainerSwaggGenerator(tile, inventory));
        this.tile = tile;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_)
    {
        String s = this.tile.hasCustomInventoryName() ? this.tile.getInventoryName() : I18n.format(this.tile.getInventoryName(), new Object[0]);
        this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
        this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 96 + 2, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(new ResourceLocation(MineSwagg.MODID + ":textures/gui/container/swaggiumGenerator.png"));
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);

        int i1 = this.tile.getPassedTimeScaled(16);
        this.drawTexturedModalRect(k + 116, l + 36, 176, 0, i1, 16);
    }
}