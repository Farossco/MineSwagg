package fr.ftnt.mineswagg.client;

import org.lwjgl.opengl.GL11;

import fr.ftnt.mineswagg.common.ContainerSwaggiumChest;
import fr.ftnt.mineswagg.common.MineSwagg;
import fr.ftnt.mineswagg.common.TileEntitySwaggiumChest;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GUISwaggiumChest extends GuiContainer
{
    private static final ResourceLocation texture = new ResourceLocation(MineSwagg.MODID + ":textures/gui/container/swaggiumcontainer.png");
    private TileEntitySwaggiumChest tileSwaggiumChest;
    private IInventory playerInventory;
    private int inventoryRows;

    public GUISwaggiumChest(TileEntitySwaggiumChest tile, InventoryPlayer inventory)
    {
        super(new ContainerSwaggiumChest(tile, inventory));
        this.tileSwaggiumChest = tile;
        this.playerInventory = inventory;
        this.allowUserInput = false;
        this.ySize = 256;
        this.xSize = 184;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int x, int y)
    {

    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(this.texture);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, 184, this.ySize);
    }

}
