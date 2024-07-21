package com.example

import com.example.config.ModConfig
import net.minecraft.util.Identifier
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object ExampleMod {
    const val MOD_ID = "example_mod"
    val LOGGER: Logger = LoggerFactory.getLogger(MOD_ID)
    val CONFIG = ModConfig.load()

    lateinit var implementation: ExampleModImplementation

    fun preInitialize(implementation: ExampleModImplementation) {
        ExampleMod.implementation = implementation

        LOGGER.info("Initializing ExampleMod ...")

        implementation.registerBlocks()
        implementation.registerItems()
    }

    fun initialize() {
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

    fun exampleModResource(name: String): Identifier {
        return Identifier(MOD_ID, name)
    }
}