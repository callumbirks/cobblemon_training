package uk.co.callumbirks.cobblemon_training.fabric

import net.fabricmc.api.ModInitializer

class FabricBootstrap : ModInitializer {
    override fun onInitialize() {
        CobbleTrainingFabric.initialize()
    }
}