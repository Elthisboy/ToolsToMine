package com.elthisboy.toolstomine.item;

import net.minecraft.block.BlockState;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class SafeTntItem extends Item {

    public static final int RADIUS = 4;

    public SafeTntItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        if (world.isClient) return TypedActionResult.pass(stack);

        triggerSafeExplosion((ServerWorld) world, player, player.getBlockPos());
        if (!player.isCreative()) stack.decrement(1);
        return TypedActionResult.success(stack);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        PlayerEntity player = context.getPlayer();
        if (world.isClient || player == null) return ActionResult.PASS;

        triggerSafeExplosion((ServerWorld) world, player, context.getBlockPos());
        if (!player.isCreative()) context.getStack().decrement(1);
        return ActionResult.SUCCESS;
    }

    public static void triggerSafeExplosion(ServerWorld world, PlayerEntity player, BlockPos center) {
        List<BlockPos> toBreak = new ArrayList<>();

        for (int x = -RADIUS; x <= RADIUS; x++) {
            for (int y = -RADIUS; y <= RADIUS; y++) {
                for (int z = -RADIUS; z <= RADIUS; z++) {
                    if (x * x + y * y + z * z > RADIUS * RADIUS) continue;
                    BlockPos pos = center.add(x, y, z);
                    BlockState state = world.getBlockState(pos);
                    if (!state.isAir() && state.getHardness(world, pos) >= 0) {
                        toBreak.add(pos);
                    }
                }
            }
        }

        ItemStack tool = player.getMainHandStack();

        for (BlockPos pos : toBreak) {
            BlockState state = world.getBlockState(pos);
            if (state.isAir()) continue;

            // getDroppedStacks takes a Builder, not the built LootContextParameterSet
            LootContextParameterSet.Builder builder = new LootContextParameterSet.Builder(world)
                    .add(LootContextParameters.ORIGIN, Vec3d.ofCenter(pos))
                    .add(LootContextParameters.TOOL, tool)
                    .addOptional(LootContextParameters.THIS_ENTITY, player);

            List<ItemStack> drops = state.getDroppedStacks(builder);

            world.removeBlock(pos, false);

            for (ItemStack drop : drops) {
                ItemEntity ie = new ItemEntity(
                        world,
                        pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                        drop
                );
                world.spawnEntity(ie);
            }
        }

        // Sound — unwrap RegistryEntry with .value()
        world.playSound(
                (PlayerEntity) null,
                center.getX(), center.getY(), center.getZ(),
                SoundEvents.ENTITY_GENERIC_EXPLODE.value(),
                SoundCategory.BLOCKS,
                1.5f, 1.0f
        );

        world.spawnParticles(
                ParticleTypes.EXPLOSION_EMITTER,
                center.getX(), center.getY(), center.getZ(),
                1, 0, 0, 0, 0
        );
    }
}
