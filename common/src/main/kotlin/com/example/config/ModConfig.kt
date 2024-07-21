package com.example.config

import com.example.ExampleMod
import com.google.gson.GsonBuilder
import java.io.File
import java.io.FileReader
import java.io.PrintWriter

@Suppress("unused")
class ModConfig {
    data class Settings(val setting1: Int, val setting2: Int)

    val settings = Settings(0, 0)

    companion object {
        fun load(): ModConfig {
            val gson = GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create()

            var config = ModConfig()
            val configFile = File("config/${ExampleMod.MOD_ID}/config.json")
            configFile.parentFile.mkdirs()

            if (configFile.exists()) {
                try {
                    val fileReader = FileReader(configFile)
                    config = gson.fromJson(fileReader, ModConfig::class.java)
                    fileReader.close()
                } catch (e: Exception) {
                    ExampleMod.LOGGER.error("Error reading config file: ${e.message}")
                }
            }

            val pw = PrintWriter(configFile)
            gson.toJson(config, pw)
            pw.close()

            return config
        }
    }
}