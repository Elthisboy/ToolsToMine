package com.elthisboy.toolstomine;

import com.elthisboy.toolstomine.entity.ModEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;

public class ToolsToMineClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // Register a renderer for the safe grenade so the client doesn't crash
        // FlyingItemEntityRenderer renders it as its default item (snowball appearance)
        EntityRendererRegistry.register(ModEntities.SAFE_GRENADE, FlyingItemEntityRenderer::new);
    }
}
