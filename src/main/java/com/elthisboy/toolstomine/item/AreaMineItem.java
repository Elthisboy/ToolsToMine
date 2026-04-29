package com.elthisboy.toolstomine.item;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class AreaMineItem extends Item {

    private final Predicate<BlockState> blockFilter;
    private final int width;
    private final int height;

    public AreaMineItem(Settings settings, Predicate<BlockState> blockFilter, int width, int height) {
        super(settings);
        this.blockFilter = blockFilter;
        this.width = width;
        this.height = height;
    }

    /** Called when the player right-clicks a block face. */
    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        PlayerEntity player = context.getPlayer();
        BlockPos center = context.getBlockPos();
        Direction face = context.getSide();
        ItemStack stack = context.getStack();

        if (world.isClient || player == null) return ActionResult.PASS;
        if (!blockFilter.test(world.getBlockState(center))) return ActionResult.PASS;

        int halfW = width / 2;
        int halfH = height / 2;
        Direction.Axis axis = face.getAxis();

        List<BlockPos> targets = new ArrayList<>();
        for (int a = -halfW; a <= halfW; a++) {
            for (int b = -halfH; b <= halfH; b++) {
                BlockPos pos = switch (axis) {
                    case Y -> center.add(a, 0, b);
                    case X -> center.add(0, b, a);
                    case Z -> center.add(a, b, 0);
                };
                targets.add(pos);
            }
        }

        for (BlockPos pos : targets) {
            BlockState state = world.getBlockState(pos);
            if (state.isAir()) continue;
            if (!blockFilter.test(state)) continue;
            if (state.getHardness(world, pos) < 0) continue; // bedrock etc.

            state.getBlock().onBreak(world, pos, state, player);
            boolean removed = world.removeBlock(pos, false);
            if (removed) {
                state.getBlock().afterBreak(world, player, pos, state, null, stack);
            }

            if (!player.isCreative()) {
                stack.damage(1, player, PlayerEntity.getSlotForHand(player.getActiveHand()));
                if (stack.isEmpty()) return ActionResult.SUCCESS;
            }
        }

        return ActionResult.SUCCESS;
    }
}
