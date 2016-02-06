package fr.ftnt.mineswagg.common;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;

public class PacketSwaggAmount implements IMessage
{
    public PacketSwaggAmount()
    {}

    @Override
    public void fromBytes(ByteBuf buf)
    {

    }

    @Override
    public void toBytes(ByteBuf buf)
    {

    }

    public static class Handler implements IMessageHandler<PacketSwaggAmount, IMessage>
    {
        @Override
        public IMessage onMessage(PacketSwaggAmount message, MessageContext ctx)
        {

            EntityPlayerMP player = ctx.getServerHandler().playerEntity;
            ExtendedEntity props = ExtendedEntity.get(player);
            int swaggAmount = props.getSwaggAmount();
            int swaggLevel = props.getSwaggLevel();

            return new PacketSwaggAmountAnswer(swaggAmount, swaggLevel);
        }
    }
}
