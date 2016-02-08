package fr.ftnt.mineswagg.common.packets;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.ftnt.mineswagg.common.containers.ContainerSwaggiumGenerator;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.inventory.Container;

public class PacketSwaggGeneratorAnswer implements IMessage
{
    public static int remainingTime, stockedSwagg;

    public PacketSwaggGeneratorAnswer()
    {

    }

    public PacketSwaggGeneratorAnswer(int remainingTime, int stockedSwagg)
    {
        this.remainingTime = remainingTime;
        this.stockedSwagg = stockedSwagg;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.remainingTime = buf.readInt();
        this.stockedSwagg = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(this.remainingTime);
        buf.writeInt(this.stockedSwagg);
    }

    public static class Handler implements IMessageHandler<PacketSwaggGeneratorAnswer, IMessage>
    {
        @Override
        @SideOnly(Side.CLIENT)
        public IMessage onMessage(PacketSwaggGeneratorAnswer message, MessageContext ctx)
        {
            Minecraft minecraft = Minecraft.getMinecraft();
            EntityClientPlayerMP player = minecraft.thePlayer;
            Container container = player.openContainer;
            if(container instanceof ContainerSwaggiumGenerator)
            {
                ((ContainerSwaggiumGenerator)container).getTile().setStockedSwagg(PacketSwaggGeneratorAnswer.stockedSwagg);
                ((ContainerSwaggiumGenerator)container).getTile().setRemainingTime(PacketSwaggGeneratorAnswer.remainingTime);
            }
            return null;
        }
    }
}
