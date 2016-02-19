package fr.ftnt.mineswagg.common.packets;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.ftnt.mineswagg.common.MineSwagg;
import fr.ftnt.mineswagg.common.MineSwaggExtendedEntityPlayer;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;

public class PacketSwaggAmountAnswer implements IMessage
{
    public static int swaggAmount, swaggLevel;

    public PacketSwaggAmountAnswer()
    {}

    public PacketSwaggAmountAnswer(int swaggAmount, int swaggLevel)
    {
        this.swaggAmount = swaggAmount;
        this.swaggLevel = swaggLevel;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.swaggAmount = buf.readInt();
        this.swaggLevel = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(this.swaggAmount);
        buf.writeInt(this.swaggLevel);
    }

    public static class Handler implements IMessageHandler<PacketSwaggAmountAnswer, IMessage>
    {
        @SideOnly(Side.CLIENT)
        @Override
        public IMessage onMessage(PacketSwaggAmountAnswer message, MessageContext ctx)
        {
            Minecraft minecraft = Minecraft.getMinecraft();
            EntityClientPlayerMP player = minecraft.thePlayer;
            MineSwaggExtendedEntityPlayer props = MineSwaggExtendedEntityPlayer.get(player);

            MineSwagg.logger.debug("Answer Recieved: Swagg Amount: " + swaggAmount + " / Swagg Level: " + swaggLevel);
            props.setSwaggAmountNoSync(swaggAmount);
            props.setSwaggLevelNoSync(swaggLevel);

            return null;
        }
    }
}
