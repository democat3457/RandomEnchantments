package com.tfar.randomenchants.ench.enchantment;

import com.tfar.randomenchants.Config;
import com.tfar.randomenchants.RandomEnchants;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.AbstractCookingRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;

import static com.tfar.randomenchants.Config.Restriction.*;

@Mod.EventBusSubscriber(modid = RandomEnchants.MODID)
public class EnchantmentAutoSmelt extends Enchantment {
  public EnchantmentAutoSmelt() {

    super(Rarity.VERY_RARE, EnchantmentType.DIGGER, new EquipmentSlotType[]{
            EquipmentSlotType.MAINHAND
    });
    this.setRegistryName("autosmelt");
  }

  @Override
  public int getMinEnchantability(int level) {
    return 15;
  }

  @Override
  public int getMaxLevel() {
    return 1;
  }

  @Override
  public boolean canApply(ItemStack stack) {
    return Config.ServerConfig.disarm.get() != DISABLED && super.canApply(stack);
  }

  @Override
  public boolean canApplyAtEnchantingTable(ItemStack stack) {
    return Config.ServerConfig.disarm.get() != DISABLED && super.canApplyAtEnchantingTable(stack);
  }

  @Override
  public boolean isAllowedOnBooks() {
    return Config.ServerConfig.disarm.get() == NORMAL;
  }

  @Override
  public boolean isTreasureEnchantment() {
    return Config.ServerConfig.disarm.get() == ANVIL;
  }

  public static ItemStack getResult(ItemStack stackInSlot, World world) {
    AbstractCookingRecipe irecipe = getRecipe(stackInSlot,world);
    return irecipe != null ? irecipe.getRecipeOutput() : ItemStack.EMPTY;
  }

  @Nullable
  public static AbstractCookingRecipe getRecipe(ItemStack input, World world) {
    IInventory inventory = new Inventory(1);
    inventory.setInventorySlotContents(0,input);
    return world.getRecipeManager().getRecipe(IRecipeType.SMELTING,inventory, world).orElse(null);
    }
}

