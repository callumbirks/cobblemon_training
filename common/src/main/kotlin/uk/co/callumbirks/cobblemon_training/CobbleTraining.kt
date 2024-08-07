package uk.co.callumbirks.cobblemon_training

import uk.co.callumbirks.cobblemon_training.config.CobbleTrainingConfig
import net.minecraft.util.Identifier
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object CobbleTraining {
    const val MOD_ID = "cobblemon_training"
    val LOGGER: Logger = LoggerFactory.getLogger(MOD_ID)
    val CONFIG = CobbleTrainingConfig.load()

    lateinit var implementation: CobbleTrainingImplementation

    fun preInitialize(implementation: CobbleTrainingImplementation) {
        CobbleTraining.implementation = implementation

        LOGGER.info("Initializing CobblemonTraining ...")

        implementation.registerItems()
    }

    fun initialize() {
        CobbleTrainingLoot.register()
        when (implementation.environment()) {
            Environment.CLIENT -> initializeClient()
            Environment.SERVER -> initializeServer()
        }
    }

    private fun initializeClient() {
        // Client-side specific initialization
    }

    private fun initializeServer() {
        // Server-side specific initialization
    }

    fun cobbleTrainingResource(name: String): Identifier {
        return Identifier(MOD_ID, name)
    }
}