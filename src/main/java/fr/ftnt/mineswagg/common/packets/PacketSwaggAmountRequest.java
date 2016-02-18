package fr.ftnt.mineswagg.common.packets;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import fr.ftnt.mineswagg.common.MineSwagg;
import fr.ftnt.mineswagg.common.MineSwaggExtendedEntity;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;

public class PacketSwaggAmountRequest implements IMessage
{
    public static int swaggAmount, swaggLevel;
    public static boolean updateSwaggAmount = false, updateSwaggLevel = false, add = false;

    public PacketSwaggAmountRequest()
    {
        this.updateSwaggAmount = false;
        this.updateSwaggLevel = false;
        this.add = false;
    }

    /*
     * choice: true -> swaggAmount / false -> swaggLevel
     * add: false -> set value / true -> add amount to value
     */
    public PacketSwaggAmountRequest(boolean choice, int swaggAmountLevel, boolean add)
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
        this.add = add;

    }

    public PacketSwaggAmountRequest(int swaggAmount, int swaggLevel)
    {
        this.swaggAmount = swaggAmount;
        this.swaggLevel = swaggLevel;
        this.updateSwaggAmount = this.updateSwaggLevel = true;
        this.add = false;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.swaggAmount = buf.readInt();
        this.swaggLevel = buf.readInt();
        this.updateSwaggAmount = buf.readBoolean();
        this.updateSwaggLevel = buf.readBoolean();
        this.add = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(this.swaggAmount);
        buf.writeInt(this.swaggLevel);
        buf.writeBoolean(this.updateSwaggAmount);
        buf.writeBoolean(this.updateSwaggLevel);
        buf.writeBoolean(this.add);
    }

    public static class Handler implements IMessageHandler<PacketSwaggAmountRequest, IMessage>
    {
        @Override
        public IMessage onMessage(PacketSwaggAmountRequest message, MessageContext ctx)
        {
            MineSwagg.logger.debug("Request Recieved");
            EntityPlayerMP player = ctx.getServerHandler().playerEntity;
            MineSwaggExtendedEntity props = MineSwaggExtendedEntity.get(player);
            int swaggAmount = props.getSwaggAmount();
            int swaggLevel = props.getSwaggLevel();
            if(updateSwaggAmount)
            {
                if(add)
                {
                    props.addSwaggAmountNoSync(PacketSwaggAmountRequest.swaggAmount);
                }
                else
                {
                    props.setSwaggAmountNoSync(PacketSwaggAmountRequest.swaggAmount);
                }
            }
            if(updateSwaggLevel)
            {
                if(add)
                {
                    props.addSwaggLevelNoSync(PacketSwaggAmountRequest.swaggLevel);
                }
                else
                {
                    props.setSwaggLevelNoSync(PacketSwaggAmountRequest.swaggLevel);
                }
            }
            if(!updateSwaggLevel && !updateSwaggAmount)
            {
                MineSwagg.logger.debug("Get Answer from server: swagg Amount: " + swaggAmount + " / Swagg Level: " + swaggLevel);
                return new PacketSwaggAmountAnswer(swaggAmount, swaggLevel);
            }
                
            return null;
        }
    }
}
