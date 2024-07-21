package uk.co.callumbirks.config

import uk.co.callumbirks.CobbleTraining
import com.google.gson.GsonBuilder
import java.io.File
import java.io.FileReader
import java.io.PrintWriter

@Suppress("unused")
class CobbleTrainingConfig {
    val ivIncrease: Int = 1

    companion object {
        fun load(): CobbleTrainingConfig {
            val gson = GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create()

            var config = CobbleTrainingConfig()
            val configFile = File("config/${CobbleTraining.MOD_ID}/config.json")
            configFile.parentFile.mkdirs()

            if (configFile.exists()) {
                try {
                    val fileReader = FileReader(configFile)
                    config = gson.fromJson(fileReader, CobbleTrainingConfig::class.java)
                    fileReader.close()
                } catch (e: Exception) {
                    CobbleTraining.LOGGER.error("Error reading config file: ${e.message}")
                }
            }

            val pw = PrintWriter(configFile)
            gson.toJson(config, pw)
            pw.close()

            return config
        }
    }
}