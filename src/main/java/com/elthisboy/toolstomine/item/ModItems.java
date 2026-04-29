package com.elthisboy.toolstomine.item;

import com.elthisboy.toolstomine.ToolsToMine;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.Identifier;

public class ModItems {

    public static final Item DIRT_DRILL = register("dirt_drill", new AreaMineItem(
            new Item.Settings().maxDamage(400),
            state -> state.isOf(Blocks.DIRT)         || state.isOf(Blocks.GRASS_BLOCK)  ||
                     state.isOf(Blocks.GRAVEL)        || state.isOf(Blocks.SAND)         ||
                     state.isOf(Blocks.ROOTED_DIRT)   || state.isOf(Blocks.COARSE_DIRT)  ||
                     state.isOf(Blocks.PODZOL)         || state.isOf(Blocks.MYCELIUM)    ||
                     state.isOf(Blocks.FARMLAND)       || state.isOf(Blocks.RED_SAND),
            3, 3
    ));

    public static final Item CHAINSAW = register("chainsaw", new AreaMineItem(
            new Item.Settings().maxDamage(400),
            state -> state.isIn(BlockTags.LOGS)          ||
                     state.isIn(BlockTags.LEAVES)         ||
                     state.isIn(BlockTags.WOODEN_SLABS)   ||
                     state.isIn(BlockTags.PLANKS),
            3, 3
    ));

    public static final Item STONE_DRILL = register("stone_drill", new AreaMineItem(
            new Item.Settings().maxDamage(400),
            state -> state.isOf(Blocks.STONE)            || state.isOf(Blocks.COBBLESTONE)        ||
                     state.isOf(Blocks.GRANITE)           || state.isOf(Blocks.DIORITE)            ||
                     state.isOf(Blocks.ANDESITE)          || state.isOf(Blocks.DEEPSLATE)          ||
                     state.isOf(Blocks.COBBLED_DEEPSLATE) || state.isOf(Blocks.SANDSTONE)          ||
                     state.isOf(Blocks.NETHERRACK)        || state.isOf(Blocks.BLACKSTONE)         ||
                     state.isOf(Blocks.TUFF)              || state.isOf(Blocks.CALCITE)            ||
                     state.isIn(BlockTags.BASE_STONE_OVERWORLD) || state.isIn(BlockTags.BASE_STONE_NETHER),
            3, 3
    ));

    public static final Item SAFE_TNT = register("safe_tnt",
            new SafeTntItem(new Item.Settings().maxCount(16)));

    public static final Item SAFE_GRENADE = register("safe_grenade",
            new SafeGrenadeItem(new Item.Settings().maxCount(16)));

    private static Item register(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(ToolsToMine.MOD_ID, name), item);
    }

    public static void registerAll() {
        // Drills & chainsaw → Tools tab
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> {
            entries.add(DIRT_DRILL);
            entries.add(CHAINSAW);
            entries.add(STONE_DRILL);
        });
        // Explosives → Combat tab
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> {
            entries.add(SAFE_TNT);
            entries.add(SAFE_GRENADE);
        });
        ToolsToMine.LOGGER.info("Registering ModItems for " + ToolsToMine.MOD_ID);
    }
}
