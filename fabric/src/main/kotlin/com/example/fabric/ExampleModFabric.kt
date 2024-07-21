package com.example.fabric

import com.example.*
import net.fabricmc.api.EnvType
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.client.MinecraftClient
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.server.MinecraftServer

object ExampleModFabric : ExampleModImplementation {
    fun initialize() {
        ExampleMod.preInitialize(this)
        ExampleMod.initialize()
    }

    private var server: MinecraftServer? = null

    override val modAPI = ModAPI.FABRIC

    override fun environment(): Environment {
        return when (FabricLoader.getInstance().environmentType) {
            EnvType.CLIENT -> Environment.CLIENT
            EnvType.SERVER -> Environment.SERVER
            else -> throw IllegalStateException("Fabric implementation cannot resolve environment yet")
        }
    }

    override fun registerItems() {
        ExampleModItems.register { identifier, item -> Registry.register(ExampleModItems.registry, identifier, item) }
        ExampleModItemGroups.register { provider ->
            Registry.register(
                Registries.ITEM_GROUP, provider.key, FabricItemGroup.builder()
                    .displayName(provider.displayName)
                    .icon(provider.displayIconProvider)
                    .entries(provider.entryCollector)
                    .build()
            )
        }
    }

    override fun registerBlocks() {
        ExampleModBlocks.register { identifier, block ->
            Registry.register(ExampleModBlocks.registry, identifier, block)
        }
    }

    override fun server(): MinecraftServer? =
        if (environment() == Environment.CLIENT) MinecraftClient.getInstance().server else server
}