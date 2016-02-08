package fr.ftnt.mineswagg.client;

import org.lwjgl.opengl.GL11;

import fr.ftnt.mineswagg.common.MineSwagg;
import fr.ftnt.mineswagg.common.MineSwaggExtendedEntity;
import fr.ftnt.mineswagg.common.containers.ContainerSwaggiumGenerator;
import fr.ftnt.mineswagg.common.packets.PacketSwaggAmountRequest;
import fr.ftnt.mineswagg.common.packets.PacketSwaggGeneratorRequest;
import fr.ftnt.mineswagg.common.tileentities.TileEntitySwaggiumGenerator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiSwaggiumGenerator extends GuiContainer
{
    private static final ResourceLocation texture = new ResourceLocation(MineSwagg.MODID + ":textures/gui/container/swaggGenerator.png");
    private TileEntitySwaggiumGenerator tile;
    ContainerSwaggiumGenerator container = (inventorySlots instanceof ContainerSwaggiumGenerator) ? (ContainerSwaggiumGenerator)inventorySlots : null;
    Minecraft minecraft = Minecraft.getMinecraft();
    EntityClientPlayerMP player = minecraft.thePlayer;
    MineSwaggExtendedEntity props = MineSwaggExtendedEntity.get(player);

    public GuiSwaggiumGenerator(TileEntitySwaggiumGenerator tile, InventoryPlayer inventory)
    {
        super(new ContainerSwaggiumGenerator(tile, inventory));
        this.tile = tile;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_)
    {
        if(inventorySlots instanceof ContainerSwaggiumGenerator)
        {
            String s = this.tile.hasCustomInventoryName() ? this.tile.getInventoryName() : I18n.format(this.tile.getInventoryName(), new Object[0]);
            String s2 = I18n.format("container.playerSwaggLevel", new Object[0]);
            this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
            this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 96 + 2, 4210752);
            this.fontRendererObj.drawString(I18n.format(s2 + ": " + props.getSwaggLevel(), new Object[0]), 8, this.ySize - 150 + 2, 4210752);
            this.fontRendererObj.drawString("-", 34, 39, 0xCCCCCC);
            this.fontRendererObj.drawString("-", 35, 39, 0xCCCCCC);
            this.fontRendererObj.drawString("+", 76, 39, 0xCCCCCC);
            String s3 = Integer.toString(container.getTile().getStockedSwagg());
            this.fontRendererObj.drawString(s3, 58 - this.fontRendererObj.getStringWidth(s3) / 2, 39, 4210752);
        }
    }

    public void initGui()
    {
        super.initGui();
        this.buttonList.add(new GuiButton(0, this.guiLeft + 27, this.guiTop + 33, 20, 20, ""));
        this.buttonList.add(new GuiButton(1, this.guiLeft + 68, this.guiTop + 33, 20, 20, ""));
    }

    @Override
    protected void actionPerformed(GuiButton button)
    {
        switch(button.id)
        {
            case 0:
                if(container.getTile().getStockedSwagg() > 0)
                {
                    props.addSwaggLevel(1);
                    MineSwagg.network.sendToServer(new PacketSwaggAmountRequest(false, props.getSwaggLevel()));
                    MineSwagg.network.sendToServer(new PacketSwaggGeneratorRequest(tile.xCoord, tile.yCoord, tile.zCoord, 1));
                }
                break;
            case 1:
                if(props.consumeSwaggLevel(1))
                {
                    MineSwagg.network.sendToServer(new PacketSwaggAmountRequest(false, props.getSwaggLevel()));
                    MineSwagg.network.sendToServer(new PacketSwaggGeneratorRequest(tile.xCoord, tile.yCoord, tile.zCoord, 2));
                }
                return;
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_)
    {
        if(inventorySlots instanceof ContainerSwaggiumGenerator)
        {
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.mc.getTextureManager().bindTexture(new ResourceLocation(MineSwagg.MODID + ":textures/gui/container/swaggiumGenerator.png"));
            int k = (this.width - this.xSize) / 2;
            int l = (this.height - this.ySize) / 2;
            this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
            MineSwagg.network.sendToServer(new PacketSwaggGeneratorRequest(tile.xCoord, tile.yCoord, tile.zCoord, 0));

            int i1 = 17 - container.getTile().getRemainingTimeScaled(16);
            int i2 = 17 - container.getTile().getRemainingTimeScaled(64);

            if(tile.getStackInSlot(0) == null)
                this.drawTexturedModalRect(k + 116, l + 35, 176, i2 * 16, 16, 16);

            if(container.getTile().isGenerating())
            {
                this.drawTexturedModalRect(k + 116, l + 35, 176 + 17, 16 + i2 * 16, i1, 16);
            }
        }
    }
}