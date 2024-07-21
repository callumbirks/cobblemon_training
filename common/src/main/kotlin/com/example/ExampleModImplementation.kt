package com.example

import net.minecraft.server.MinecraftServer

interface ExampleModImplementation {
    val modAPI: ModAPI

    fun registerItems()

    fun registerBlocks()

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