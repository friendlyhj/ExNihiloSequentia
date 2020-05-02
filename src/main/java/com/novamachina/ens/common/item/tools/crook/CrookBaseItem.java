package com.novamachina.ens.common.item.tools.crook;

import com.google.common.collect.Sets;
import com.novamachina.ens.common.setup.ModInitialization;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CrookBaseItem extends ToolItem {

    private static final Set<Block> effectiveBlocksOn = Sets
        .newHashSet(BlockTags.LEAVES.getAllElements());

    public CrookBaseItem(IItemTier tier, int maxDamage) {
        super(0.5F, 0.5F, tier, effectiveBlocksOn,
            new Item.Properties().defaultMaxDamage(maxDamage).group(ModInitialization.ITEM_GROUP));
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, BlockState state, BlockPos pos,
        LivingEntity entityLiving) {
        super.onBlockDestroyed(stack, worldIn, state, pos, entityLiving);
        if (state.getBlock() instanceof LeavesBlock) {
            List<ItemStack> itemDrops = new ArrayList<>();
            for (int i = 0; i < CrookDrops.numberOfTimesToTestVanillaDrops + 1; i++) {
                List<ItemStack> items = Block
                    .getDrops(state, worldIn.getServer().getWorld(worldIn.getDimension().getType()),
                        pos, null);
                itemDrops.addAll(items);
            }

            itemDrops.addAll(CrookDrops.getDrops());

            for (ItemStack item : itemDrops) {
                worldIn.addEntity(
                    new ItemEntity(worldIn, pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F,
                        item));
            }
        }
        return false;
    }
}
