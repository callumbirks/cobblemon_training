package uk.co.callumbirks.fabric

import net.fabricmc.api.ModInitializer

class FabricBootstrap : ModInitializer {
    override fun onInitialize() {
        CobbleTrainingFabric.initialize()
    }
}