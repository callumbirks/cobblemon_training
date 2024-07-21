package uk.co.callumbirks.cobblemon_training

import net.minecraft.server.MinecraftServer

interface CobbleTrainingImplementation {
    val modAPI: ModAPI

    fun registerItems()

    fun environment(): Environment

    fun server(): MinecraftServer?
}

enum class ModAPI {
    FABRIC,
    FORGE
}

enum class Environment {
    CLIENT,
    SERVER
}