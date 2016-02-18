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

public class MineSwaggExtendedEntity implements IExtendedEntityProperties
{
    public final static String EXT_PROP_NAME = "MineSwaggExtendedPlayer";
    private final EntityPlayer player;
    private int swaggAmount, swaggLevel;
    private final int maxSwagg = 182;

    public MineSwaggExtendedEntity(EntityPlayer player)
    {
        this.player = player;
    }

    public static final void register(EntityPlayer player)
    {
        player.registerExtendedProperties(MineSwaggExtendedEntity.EXT_PROP_NAME, new MineSwaggExtendedEntity(player));
    }

    public static final MineSwaggExtendedEntity get(EntityPlayer player)
    {
        return (MineSwaggExtendedEntity)player.getExtendedProperties(EXT_PROP_NAME);
    }

    @Override
    public void saveNBTData(NBTTagCompound compound)
    {
        NBTTagCompound properties = new NBTTagCompound();
        properties.setInteger("swaggAmount", this.swaggAmount);
        properties.setInteger("swaggLevel", this.swaggLevel);
        compound.setTag(EXT_PROP_NAME, properties);
    }

    @Override
    public void loadNBTData(NBTTagCompound compound)
    {
        NBTTagCompound properties = (NBTTagCompound)compound.getTag(EXT_PROP_NAME);
        this.swaggAmount = properties.getInteger("swaggAmount");
        this.swaggLevel = properties.getInteger("swaggLevel");
    }

    @Override
    public void init(Entity entity, World world)
    {
        MinecraftForge.EVENT_BUS.register(new MineSwaggEventHandler());
    }

    public boolean consumeSwaggAmount(int amount)
    {
        boolean sufficient = amount <= this.swaggAmount + swaggLevel * maxSwagg;

        if(sufficient)
        {
            this.swaggAmount -= amount;
            testLevelUp();
        }
        syncWithServer();
        return sufficient;
    }

    public boolean consumeSwaggAmountNoSync(int amount)
    {
        boolean sufficient = amount <= this.swaggAmount + swaggLevel * maxSwagg;

        if(sufficient)
        {
            this.swaggAmount -= amount;
            testLevelUp();
        }
        return sufficient;
    }

    public boolean consumeSwaggLevel(int amount)
    {
        return consumeSwaggAmount(maxSwagg * amount);
    }

    public boolean consumeSwaggLevelNoSync(int amount)
    {
        return consumeSwaggAmountNoSync(maxSwagg * amount);
    }

    public void addSwaggAmount(int amount)
    {
        this.swaggAmount += amount;
        testLevelUp();
        syncWithServer();
    }

    public void addSwaggAmountNoSync(int amount)
    {
        this.swaggAmount += amount;
        testLevelUpNoSync();
    }

    public void addSwaggLevel(int amount)
    {
        this.swaggLevel += amount;
        if(amount > 0)
            playSound();
        syncWithServer();
    }

    public void addSwaggLevelNoSync(int amount)
    {
        this.swaggLevel += amount;
        if(amount > 0)
            playSound();
    }

    private void testLevelUp()
    {
        int amount = 0;
        while(swaggAmount >= maxSwagg)
        {
            swaggAmount -= maxSwagg;
            amount++;
        }
        addSwaggLevel(amount);

        while(swaggAmount < 0)
        {
            swaggAmount += maxSwagg;
            swaggLevel--;
        }
    }
    
    private void testLevelUpNoSync()
    {
        int amount = 0;
        while(swaggAmount >= maxSwagg)
        {
            swaggAmount -= maxSwagg;
            amount++;
        }
        addSwaggLevelNoSync(amount);

        while(swaggAmount < 0)
        {
            swaggAmount += maxSwagg;
            swaggLevel--;
        }
    }

    private void playSound()
    {
        World world = player.worldObj;
        world.playSoundAtEntity(this.player, "random.levelup", 1, 2);
    }

    public int getSwaggAmount()
    {
        return this.swaggAmount;
    }

    public int getSwaggLevel()
    {
        return this.swaggLevel;
    }

    public int getMaxSwagg()
    {
        return maxSwagg;
    }

    public boolean setSwaggAmount(int swaggAmount)
    {
        if(swaggAmount >= 0)
        {
            this.swaggAmount = swaggAmount;
            testLevelUp();
            syncWithServer();
            return true;
        }
        testLevelUp();
        syncWithServer();
        return false;
    }

    public boolean setSwaggAmountNoSync(int swaggAmount)
    {
        if(swaggAmount >= 0)
        {
            this.swaggAmount = swaggAmount;
            testLevelUpNoSync();
            return true;
        }
        testLevelUpNoSync();
        return false;
    }

    public boolean setSwaggLevel(int swaggLevel)
    {
        if(swaggLevel >= 0)
        {
            this.swaggLevel = swaggLevel;
            syncWithServer();
            return true;
        }
        return false;
    }

    public boolean setSwaggLevelNoSync(int swaggLevel)
    {
        if(swaggLevel >= 0)
        {
            this.swaggLevel = swaggLevel;
            return true;
        }
        return false;
    }

    public void syncWithServer()
    {
        if(player.worldObj.isRemote)
        {
            MineSwagg.network.sendToServer(new PacketSwaggAmountRequest(swaggAmount, swaggLevel));
            MineSwagg.logger.debug("Sync to server");
        }
        else
        {
            MineSwagg.network.sendTo(new PacketSwaggAmountAnswer(swaggAmount, swaggLevel), (EntityPlayerMP)player);
            MineSwagg.logger.debug("Sync to client");
        }
    }

    public void syncFromServer()
    {
        MineSwagg.network.sendToServer(new PacketSwaggAmountRequest());
        MineSwagg.logger.debug("Sync from server");
    }
}