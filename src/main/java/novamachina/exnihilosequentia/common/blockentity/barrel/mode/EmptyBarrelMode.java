package novamachina.exnihilosequentia.common.blockentity.barrel.mode;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import javax.annotation.Nonnull;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.IItemHandler;
import novamachina.exnihilosequentia.common.blockentity.barrel.AbstractBarrelEntity;
import novamachina.exnihilosequentia.common.registries.ExNihiloRegistries;
import novamachina.exnihilosequentia.common.utility.ExNihiloConstants;

public class EmptyBarrelMode extends AbstractBarrelMode {

  public EmptyBarrelMode(@Nonnull final String name) {
    super(name);
  }

  @Override
  public void tick(@Nonnull final AbstractBarrelEntity barrelTile) {
    // NOOP
  }

  @Override
  @Nonnull
  public InteractionResult onBlockActivated(@Nonnull final AbstractBarrelEntity barrelTile,
      @Nonnull final Player player, @Nonnull final InteractionHand handIn,
      @Nonnull final IFluidHandler fluidHandler,
      @Nonnull final IItemHandler itemHandler) {
    if (!player.getItemInHand(handIn).isEmpty()) {

      @Nonnull final ItemStack stack = player.getItemInHand(handIn);
      @Nonnull final List<Supplier<AbstractBarrelMode>> modes = BarrelModeRegistry.getModes(
          BarrelModeRegistry.TriggerType.ITEM);
      for (Supplier<AbstractBarrelMode> mode : modes) {
        if (mode.get().isTriggerItem(stack)) {
          barrelTile.setMode(mode.get());
          barrelTile.getMode()
              .onBlockActivated(barrelTile, player, handIn, fluidHandler, itemHandler);
          return InteractionResult.SUCCESS;
        }
      }
    }
    return InteractionResult.SUCCESS;
  }

  @Override
  public boolean canFillWithFluid(@Nonnull final AbstractBarrelEntity barrel) {
    return true;
  }

  @Override
  public boolean isEmptyMode() {
    return true;
  }

  @Override
  protected boolean isTriggerItem(@Nonnull final ItemStack stack) {
    return false;
  }

  @Override
  public void read(@Nonnull final CompoundTag nbt) {
    // NOOP
  }

  @Override
  @Nonnull
  public CompoundTag write() {
    return new CompoundTag();
  }

  @Override
  protected void spawnParticle(@Nonnull final AbstractBarrelEntity barrelTile) {
    // NOOP
  }

  @Override
  @Nonnull
  public List<Component> getWailaInfo(@Nonnull final AbstractBarrelEntity barrelTile) {
    return new ArrayList<>();
  }

  @Override
  @Nonnull
  public ItemStack handleInsert(@Nonnull final AbstractBarrelEntity barrelTile,
      @Nonnull final ItemStack stack,
      final boolean simulate) {
    if (ExNihiloRegistries.COMPOST_REGISTRY.containsSolid(stack.getItem())) {
      barrelTile.setMode(ExNihiloConstants.BarrelModes.COMPOST);
      @Nonnull final ItemStack returnStack = barrelTile.getMode()
          .handleInsert(barrelTile, stack, simulate);
      if (simulate) {
        barrelTile.setMode(ExNihiloConstants.BarrelModes.EMPTY);
      }
      return returnStack;
    }
    return stack;
  }
}
