package fr.ftnt.mineswagg.common.items;

import fr.ftnt.mineswagg.common.MineSwagg;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

public class ItemSwaggiumSword extends ItemSword
{

    public ItemSwaggiumSword()
    {
        super(MineSwagg.toolSwaggium);
        this.setUnlocalizedName("swordSwaggium");
        this.setTextureName(MineSwagg.MODID + ":swaggium_sword");
    }

    public boolean getIsRepairable(ItemStack input, ItemStack repair)
    {
        if(repair.getItem() == MineSwagg.itemSwaggiumIngot)
        {
            return true;
        }
        return false;
    }

    /*
     * public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
     * {
     * if(!world.isRemote)
     * {
     * if(!stack.hasTagCompound())
     * {
     * stack.setTagCompound(new NBTTagCompound());
     * }
     * byte mode = stack.getTagCompound().getByte("mode");
     * mode++;
     * if(mode >= 3)
     * mode = 0;
     * stack.getTagCompound().setByte("mode", mode);
     * player.addChatMessage(new ChatComponentTranslation(mode == 0 ? "sword.mode.message.0" : mode == 1 ? "sword.mode.message.1" : "sword.mode.message.2"));
     * }
     * return stack;
     * }
     * public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean p_77624_4_)
     * {
     * if(!stack.hasTagCompound())
     * {
     * stack.setTagCompound(new NBTTagCompound());
     * }
     * list.add(stack.getTagCompound().getByte("mode") == 0 ? "sword.mode.message.0" : stack.getTagCompound().getByte("mode") == 1 ? "sword.mode.message.1" : "sword.mode.message.2");
     * }
     * public boolean onEntitySwing(EntityLivingBase player, ItemStack stack)
     * {
     * if(!stack.hasTagCompound())
     * {
     * stack.setTagCompound(new NBTTagCompound());
     * }
     * IEntitySelector filter;
     * if(stack.getTagCompound().getByte("mode") == 0)
     * {
     * filter = new IEntitySelector()
     * {
     * @Override
     * public boolean isEntityApplicable(Entity entity)
     * {
     * if(entity instanceof EntityPlayer)
     * {
     * return true;
     * }
     * return false;
     * }
     * };
     * }
     * else if(stack.getTagCompound().getByte("mode") == 1)
     * {
     * filter = new IEntitySelector()
     * {
     * @Override
     * public boolean isEntityApplicable(Entity entity)
     * {
     * if(entity instanceof EntityMob)
     * {
     * return true;
     * }
     * return false;
     * }
     * };
     * }
     * else
     * {
     * filter = new IEntitySelector()
     * {
     * @Override
     * public boolean isEntityApplicable(Entity entity)
     * {
     * if(entity instanceof EntityAnimal)
     * {
     * return true;
     * }
     * return false;
     * }
     * };
     * }
     * List entityTargetList = player.worldObj.selectEntitiesWithinAABB(EntityLivingBase.class, player.boundingBox.expand(640.0D, 640.0D, 64.0D), filter);for(
     * Object entity:entityTargetList)
     * {
     * EntityLivingBase living = (EntityLivingBase)entity;
     * living.setCurrentItemOrArmor(0, new ItemStack(SwaggMod.ItemSwaggiumSword));
     * living.setCurrentItemOrArmor(1, new ItemStack(SwaggMod.itemSwaggiumBoots));
     * living.setCurrentItemOrArmor(2, new ItemStack(SwaggMod.itemSwaggiumLeggings));
     * living.setCurrentItemOrArmor(3, new ItemStack(SwaggMod.itemSwaggiumChestplate));
     * living.setCurrentItemOrArmor(4, new ItemStack(SwaggMod.itemSwaggiumHelmet));
     * }
     * return false;
     * }
     */
}