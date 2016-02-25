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
    public static boolean negativeSwagg;

    public PacketSwaggAmountAnswer()
    {}

    public PacketSwaggAmountAnswer(int swaggAmount, int swaggLevel, boolean negativeSwagg)
    {
        this.swaggAmount = swaggAmount;
        this.swaggLevel = swaggLevel;
        this.negativeSwagg = negativeSwagg;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.swaggAmount = buf.readInt();
        this.swaggLevel = buf.readInt();
        this.negativeSwagg = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(this.swaggAmount);
        buf.writeInt(this.swaggLevel);
        buf.writeBoolean(negativeSwagg);
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

            props.setSwaggAmount(swaggAmount, false);
            props.setSwaggLevel(swaggLevel, false);
            props.negativeSwagg = negativeSwagg;

            return null;
        }
    }
}
