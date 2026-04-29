package com.elthisboy.toolstomine.entity;

import com.elthisboy.toolstomine.item.ModItems;
import com.elthisboy.toolstomine.item.SafeTntItem;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SafeGrenadeEntity extends ThrownItemEntity {

    public SafeGrenadeEntity(EntityType<? extends ThrownItemEntity> type, World world) {
        super(type, world);
    }

    public SafeGrenadeEntity(World world, LivingEntity thrower) {
        super(ModEntities.SAFE_GRENADE, thrower, world);
    }

    @Override
    protected Item getDefaultItem() {
        // Use the safe_grenade item so FlyingItemEntityRenderer shows the correct texture
        return ModItems.SAFE_GRENADE;
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (this.getWorld().isClient()) return;

        BlockPos center = switch (hitResult.getType()) {
            case BLOCK  -> ((BlockHitResult) hitResult).getBlockPos();
            case ENTITY -> ((EntityHitResult) hitResult).getEntity().getBlockPos();
            default     -> this.getBlockPos();
        };

        PlayerEntity thrower = null;
        if (this.getOwner() instanceof PlayerEntity p) {
            thrower = p;
        } else {
            thrower = this.getWorld().getClosestPlayer(this, 64);
        }

        if (thrower != null) {
            SafeTntItem.triggerSafeExplosion((ServerWorld) this.getWorld(), thrower, center);
        }

        this.discard();
    }
}
