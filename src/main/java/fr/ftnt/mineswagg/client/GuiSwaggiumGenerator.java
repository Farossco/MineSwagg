package fr.ftnt.mineswagg.client;

import org.lwjgl.opengl.GL11;

import fr.ftnt.mineswagg.common.MineSwagg;
import fr.ftnt.mineswagg.common.MineSwaggExtendedEntityPlayer;
import fr.ftnt.mineswagg.common.containers.ContainerSwaggiumGenerator;
import fr.ftnt.mineswagg.common.packets.PacketSwaggGeneratorRequest;
import fr.ftnt.mineswagg.common.tileentities.TileEntitySwaggiumGenerator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class GuiSwaggiumGenerator extends GuiContainer
{
    private static final ResourceLocation texture = new ResourceLocation(MineSwagg.NAME + ":textures/gui/container/swaggGenerator.png");
    private TileEntitySwaggiumGenerator tile;
    ContainerSwaggiumGenerator container = (inventorySlots instanceof ContainerSwaggiumGenerator) ? (ContainerSwaggiumGenerator)inventorySlots : null;
    Minecraft minecraft = Minecraft.getMinecraft();
    EntityClientPlayerMP player = minecraft.thePlayer;
    MineSwaggExtendedEntityPlayer props = MineSwaggExtendedEntityPlayer.get(player);

    public GuiSwaggiumGenerator(TileEntitySwaggiumGenerator tile, InventoryPlayer inventory)
    {
        super(new ContainerSwaggiumGenerator(tile, inventory));
        this.tile = tile;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int i, int j)
    {
        if(inventorySlots instanceof ContainerSwaggiumGenerator)
        {
            String s = this.tile.hasCustomInventoryName() ? this.tile.getInventoryName() : I18n.format(this.tile.getInventoryName(), new Object[0]);
            String s2 = I18n.format("container.playerSwaggLevel", new Object[0]);
            this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
            this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 96 + 2, 4210752);
            this.fontRendererObj.drawString(I18n.format(s2 + ": " + props.getSwaggLevel(), new Object[0]), 8, this.ySize - 150 + 2, 4210752);
            this.fontRendererObj.drawString("-", 34 - 7, 39, 0xCCCCCC);
            this.fontRendererObj.drawString("-", 35 - 7, 39, 0xCCCCCC);
            this.fontRendererObj.drawString("+", 76 + 7, 39, 0xCCCCCC);
            String s3 = Integer.toString(container.getTile().getStockedSwagg());
            this.fontRendererObj.drawString(s3, 58 - this.fontRendererObj.getStringWidth(s3) / 2, 39, 4210752);
        }
    }

    public void initGui()
    {
        super.initGui();
        this.buttonList.add(new GuiButton(0, this.guiLeft + 27 - 7, this.guiTop + 33, 20, 20, ""));
        this.buttonList.add(new GuiButton(1, this.guiLeft + 68 + 7, this.guiTop + 33, 20, 20, ""));
    }

    @Override
    protected void actionPerformed(GuiButton button)
    {
        int amount;

        if(this.isShiftKeyDown() && this.isCtrlKeyDown())
        {
            amount = 500;
        }
        else if(this.isCtrlKeyDown())
        {
            amount = 50;
        }
        else if(this.isShiftKeyDown())
        {
            amount = 10;
        }
        else
        {
            amount = 1;
        }

        switch(button.id)
        {
            case 0:
                if(container.getTile().getStockedSwagg() - amount >= 0)
                {
                    props.addSwaggLevel(amount);
                    MineSwagg.network.sendToServer(new PacketSwaggGeneratorRequest(tile.xCoord, tile.yCoord, tile.zCoord, amount * -1));
                }
                break;
            case 1:
                if(props.consumeSwaggLevel(amount))
                {
                    MineSwagg.network.sendToServer(new PacketSwaggGeneratorRequest(tile.xCoord, tile.yCoord, tile.zCoord, amount));
                }
                return;
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
    {
        if(inventorySlots instanceof ContainerSwaggiumGenerator)
        {
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.mc.getTextureManager().bindTexture(new ResourceLocation(MineSwagg.NAME + ":textures/gui/container/swaggiumGenerator.png"));
            int k = (this.width - this.xSize) / 2;
            int l = (this.height - this.ySize) / 2;
            this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
            MineSwagg.network.sendToServer(new PacketSwaggGeneratorRequest(tile.xCoord, tile.yCoord, tile.zCoord, 0));
            World world = minecraft.theWorld;

            int i1 = 16 - container.getTile().getRemainingTimeScaled(16);
            int i2 = (int)world.getWorldTime() % 32;

            if(tile.getStackInSlot(0) == null)
                this.drawTexturedModalRect(k + 116, l + 35, 176 + (i2 / 16) * 16, (i2 % 16) * 16, 16, 16);

            if(container.getTile().isGenerating())
            {
                this.drawTexturedModalRect(k + 116, l + 35, 208 + (i2 / 16) * 16, (i2 % 16) * 16, i1, 16);
            }
        }
    }
}