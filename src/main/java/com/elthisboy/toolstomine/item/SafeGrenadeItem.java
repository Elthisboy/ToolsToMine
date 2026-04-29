package com.elthisboy.toolstomine.item;

import com.elthisboy.toolstomine.entity.SafeGrenadeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class SafeGrenadeItem extends Item {

    public SafeGrenadeItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);

        if (!world.isClient) {
            SafeGrenadeEntity grenade = new SafeGrenadeEntity(world, player);

            // Throw in the direction the player is looking, with a slight upward arc
            Vec3d look = player.getRotationVec(1.0f);
            grenade.setVelocity(look.x * 1.5, look.y * 1.5 + 0.1, look.z * 1.5);
            world.spawnEntity(grenade);

            world.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.ENTITY_SNOWBALL_THROW,
                    SoundCategory.NEUTRAL,
                    0.5f,
                    0.4f / (world.getRandom().nextFloat() * 0.4f + 0.8f));
        }

        if (!player.isCreative()) {
            stack.decrement(1);
        }

        return TypedActionResult.success(stack, world.isClient());
    }
}
