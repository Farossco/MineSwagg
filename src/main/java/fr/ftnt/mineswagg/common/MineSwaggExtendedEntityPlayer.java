package fr.ftnt.mineswagg.common;

import fr.ftnt.mineswagg.common.packets.PacketSwaggAmountAnswer;
import fr.ftnt.mineswagg.common.packets.PacketSwaggAmountRequest;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.common.MinecraftForge;
import scala.tools.nsc.util.StackTracing;

public class MineSwaggExtendedEntityPlayer implements IExtendedEntityProperties
{
    public final static String EXT_PROP_NAME = "MineSwaggExtendedPlayer";
    private final EntityPlayer player;
    private int swaggAmount, swaggLevel;
    public boolean negativeSwagg;
    private boolean addingSwaggLevel;
    private boolean soundPlayed;

    private static final int maxSwagg = 182;

    public MineSwaggExtendedEntityPlayer(EntityPlayer player)
    {
        this.player = player;
    }

    public static final void register(EntityPlayer player)
    {
        player.registerExtendedProperties(MineSwaggExtendedEntityPlayer.EXT_PROP_NAME, new MineSwaggExtendedEntityPlayer(player));
    }

    public static final MineSwaggExtendedEntityPlayer get(EntityPlayer player)
    {
        return (MineSwaggExtendedEntityPlayer)player.getExtendedProperties(EXT_PROP_NAME);
    }

    @Override
    public void saveNBTData(NBTTagCompound compound)
    {
        NBTTagCompound properties = new NBTTagCompound();
        properties.setInteger("swaggAmount", this.swaggAmount);
        properties.setInteger("swaggLevel", this.swaggLevel);
        properties.setBoolean("negativeSwagg", this.negativeSwagg);
        compound.setTag(EXT_PROP_NAME, properties);
    }

    @Override
    public void loadNBTData(NBTTagCompound compound)
    {
        NBTTagCompound properties = (NBTTagCompound)compound.getTag(EXT_PROP_NAME);
        this.swaggAmount = properties.getInteger("swaggAmount");
        this.swaggLevel = properties.getInteger("swaggLevel");
        this.negativeSwagg = properties.getBoolean("negativeSwagg");
    }

    @Override
    public void init(Entity entity, World world)
    {
        MinecraftForge.EVENT_BUS.register(new MineSwaggEventHandler());
    }

    public boolean consumeSwaggAmount(int amount, boolean sync)
    {
        MineSwagg.logger.error("This have to be changed !");
        return consumeSwaggAmount(amount, sync, false);
    }

    public boolean consumeSwaggAmount(int amount, boolean sync, boolean canGoNegative)
    {
        boolean sufficient = amount <= this.swaggAmount + swaggLevel * maxSwagg && isPositiveSwagg();

        if(sufficient || canGoNegative)
        {
            soundPlayed = false;
            for(int i = 0; i < amount; i++)
            {
                if(isNegativeSwagg())
                {
                    this.swaggAmount++;
                }
                else if(this.swaggAmount == 0 && this.swaggLevel == 0)
                {
                    this.swaggAmount++;
                    this.setNegativeSwagg();
                }
                else
                {
                    this.swaggAmount--;
                }
            }
        }

        testLevelUp();

        if(sync)
            syncWithServer();

        return canGoNegative ? !sufficient : sufficient;
    }

    public boolean consumeSwaggLevel(int amount, boolean sync)
    {
        return consumeSwaggAmount(amount * maxSwagg, sync);
    }

    public boolean consumeSwaggLevel(int amount, boolean sync, boolean canGoNegative)
    {
        return consumeSwaggAmount(amount * maxSwagg, sync, canGoNegative);
    }

    /**
     * Add the specified amount of swagg to the player.
     * @param amount The amount of swagg to give to the player.
     * @param sync Specify if the informations have to be synced to client or server.
     */
    @Deprecated
    public boolean addSwaggAmount(int amount, boolean sync)
    {
        MineSwagg.logger.error("This have to be changed !");
        return addSwaggAmount(amount, sync, true);
    }

    /**
     * Add the specified amount of swagg to the player.
     * @param amount The amount of swagg to give to the player.
     * @param sync Specify if the informations have to be synced to client or server.
     * @param canGoPositive Specify if the swagg can switch from negative to positive.
     * 
     */
    public boolean addSwaggAmount(int amount, boolean sync, boolean canGoPositive)
    {
        boolean sufficient = amount <= this.swaggAmount + swaggLevel * maxSwagg && isNegativeSwagg();

        soundPlayed = false;
        if(sufficient || canGoPositive)
        {
            for(int i = 0; i < amount; i++)
            {
                if(isPositiveSwagg())
                {
                    this.swaggAmount++;
                }
                else if(this.swaggAmount == 0 && this.swaggLevel == 0)
                {
                    this.swaggAmount++;
                    this.setPositiveSwagg();
                }
                else
                {
                    this.swaggAmount--;
                }
            }
        }
        testLevelUp();

        if(sync)
            syncWithServer();

        return canGoPositive ? !sufficient : sufficient;
    }

    public boolean addSwaggLevel(int amount, boolean sync)
    {
        return addSwaggAmount(amount * maxSwagg, sync);
    }

    public boolean addSwaggLevel(int amount, boolean sync, boolean canGoPositive)
    {
        return addSwaggAmount(amount * maxSwagg, sync, canGoPositive);
    }

    private void testLevelUp()
    {
        while(swaggAmount >= maxSwagg)
        {
            swaggAmount -= maxSwagg;
            this.swaggLevel++;

            playSound();
            soundPlayed = true;
        }

        while(swaggAmount < 0)
        {
            swaggAmount += maxSwagg;
            swaggLevel--;
        }

        if(swaggLevel < 0)
        {
            swaggLevel = -swaggLevel;
            negativeSwagg = !negativeSwagg;
        }
    }

    private void playSound()
    {
        if(!(player.worldObj == null) && !soundPlayed)
            player.worldObj.playSoundAtEntity(this.player, "random.levelup", 1, 2);
    }

    public int getSwaggAmount()
    {
        return this.swaggAmount;
    }

    public int getSwaggLevel()
    {
        return this.swaggLevel;
    }

    public static int getMaxSwagg()
    {
        return maxSwagg;
    }

    public boolean setSwaggAmount(int swaggAmount, boolean sync)
    {
        if(swaggAmount >= 0)
        {
            this.swaggAmount = swaggAmount;
            testLevelUp();
            if(sync)
                syncWithServer();
            return true;
        }
        testLevelUp();
        if(sync)
            syncWithServer();
        return false;
    }

    public boolean setSwaggLevel(int swaggLevel, boolean sync)
    {
        if(swaggLevel >= 0)
        {
            this.swaggLevel = swaggLevel;
            if(sync)
                syncWithServer();
            return true;
        }
        return false;
    }

    public void syncWithServer()
    {
        if(player.worldObj.isRemote)
        {
            MineSwagg.network.sendToServer(new PacketSwaggAmountRequest(swaggAmount, swaggLevel, negativeSwagg));
        }
        else
        {
            MineSwagg.network.sendTo(new PacketSwaggAmountAnswer(swaggAmount, swaggLevel, negativeSwagg), (EntityPlayerMP)player);
        }
    }

    public boolean isNegativeSwagg()
    {
        return negativeSwagg;
    }

    public void setNegativeSwagg()
    {
        this.negativeSwagg = true;
    }

    public boolean isPositiveSwagg()
    {
        return !negativeSwagg;
    }

    public void setPositiveSwagg()
    {
        this.negativeSwagg = false;
    }

    public void syncFromServer()
    {
        MineSwagg.network.sendToServer(new PacketSwaggAmountRequest());
    }
}