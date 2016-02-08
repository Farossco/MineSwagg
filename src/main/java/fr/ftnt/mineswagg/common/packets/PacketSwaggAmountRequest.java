package fr.ftnt.mineswagg.common.packets;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import fr.ftnt.mineswagg.common.MineSwaggExtendedEntity;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;

public class PacketSwaggAmountRequest implements IMessage
{
    public static int swaggAmount, swaggLevel;
    public static boolean updateSwaggAmount = false, updateSwaggLevel = false;

    public PacketSwaggAmountRequest()
    {
        updateSwaggAmount = false;
        updateSwaggLevel = false;
    }

    // true -> swaggAmount / false -> swaggLevel
    public PacketSwaggAmountRequest(boolean choice, int swaggAmountLevel)
    {
        if(choice)
        {
            this.swaggAmount = swaggAmountLevel;
        }
        else
        {
            this.swaggLevel = swaggAmountLevel;
        }
        this.updateSwaggAmount = choice;
        this.updateSwaggLevel = !choice;
    }

    public PacketSwaggAmountRequest(int swaggAmount, int swaggLevel)
    {
        this.swaggAmount = swaggAmount;
        this.swaggLevel = swaggLevel;
        this.updateSwaggAmount = this.updateSwaggLevel = true;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.swaggAmount = buf.readInt();
        this.swaggLevel = buf.readInt();
        this.updateSwaggAmount = buf.readBoolean();
        this.updateSwaggLevel = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(this.swaggAmount);
        buf.writeInt(this.swaggLevel);
        buf.writeBoolean(this.updateSwaggAmount);
        buf.writeBoolean(this.updateSwaggLevel);
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
            if(updateSwaggAmount)
            {
                props.setSwaggAmount(PacketSwaggAmountRequest.swaggAmount);

            }
            if(updateSwaggLevel)
            {
                props.setSwaggLevel(PacketSwaggAmountRequest.swaggLevel);
            }

            return new PacketSwaggAmountAnswer(swaggAmount, swaggLevel);
        }
    }
}
