package com.example.fabric

import net.fabricmc.api.ModInitializer

class FabricBootstrap : ModInitializer {
    override fun onInitialize() {
        ExampleModFabric.initialize()
    }
}