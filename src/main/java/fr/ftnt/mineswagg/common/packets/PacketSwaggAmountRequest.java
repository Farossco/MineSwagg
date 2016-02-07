package fr.ftnt.mineswagg.common.packets;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import fr.ftnt.mineswagg.common.MineSwaggExtendedEntity;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;

public class PacketSwaggAmountRequest implements IMessage
{
    public PacketSwaggAmountRequest()
    {}

    @Override
    public void fromBytes(ByteBuf buf)
    {

    }

    @Override
    public void toBytes(ByteBuf buf)
    {

    }

    public static class Handler implements IMessageHandler<PacketSwaggAmountRequest, IMessage>
    {
        @Override
        public IMessage onMessage(PacketSwaggAmountRequest message, MessageContext ctx)
        {

            EntityPlayerMP player = ctx.getServerHandler().playerEntity;
            MineSwaggExtendedEntity props = MineSwaggExtendedEntity.get(player);
            int swaggAmount = props.getSwaggAmount();
            int swaggLevel = props.getSwaggLevel();

            return new PacketSwaggAmountAnswer(swaggAmount, swaggLevel);
        }
    }
}
