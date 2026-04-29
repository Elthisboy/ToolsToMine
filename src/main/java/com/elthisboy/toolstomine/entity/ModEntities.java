package com.elthisboy.toolstomine.entity;

import com.elthisboy.toolstomine.ToolsToMine;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {

    public static final EntityType<SafeGrenadeEntity> SAFE_GRENADE = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(ToolsToMine.MOD_ID, "safe_grenade"),
            FabricEntityTypeBuilder.<SafeGrenadeEntity>create(SpawnGroup.MISC, SafeGrenadeEntity::new)
                    .dimensions(EntityDimensions.fixed(0.25f, 0.25f))
                    .trackRangeBlocks(4)
                    .trackedUpdateRate(10)
                    .build()
    );

    public static void registerAll() {
        ToolsToMine.LOGGER.info("Registering ModEntities for " + ToolsToMine.MOD_ID);
    }
}
