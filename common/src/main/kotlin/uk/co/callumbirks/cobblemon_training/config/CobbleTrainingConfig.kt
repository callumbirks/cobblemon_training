package uk.co.callumbirks.cobblemon_training.config

import com.cobblemon.mod.common.util.cobblemonResource
import uk.co.callumbirks.cobblemon_training.CobbleTraining
import com.google.gson.GsonBuilder
import net.minecraft.loot.LootTables
import net.minecraft.util.Identifier
import java.io.File
import java.io.FileReader
import java.io.PrintWriter

@Suppress("unused")
class CobbleTrainingConfig {
    class BottlecapLootEntry(val table: String, val weight: Int) {
        constructor(table: Identifier, weight: Int) : this(table.toString(), weight)

        fun table(): Identifier {
            return Identifier.tryParse(table)
                ?: throw IllegalArgumentException("Invalid Identifier in loot config")
        }
    }

    class LootEntry(val table: String, val item: String, val weight: Int) {
        constructor(table: Identifier, item: Identifier, weight: Int) : this(table.toString(), item.toString(), weight)

        fun table(): Identifier {
            return Identifier.tryParse(table)
                ?: throw IllegalArgumentException("Invalid Identifier in loot config")
        }

        fun item(): Identifier {
            return Identifier.tryParse(item)
                ?: throw IllegalArgumentException("Invalid Identifier in loot config")
        }
    }

    data class LootConfig(
        val bottlecaps: Bottlecaps,
        val otherItems: ArrayList<LootEntry>,
    ) {
        data class Bottlecaps(
            val attack: ArrayList<BottlecapLootEntry>,
            val defence: ArrayList<BottlecapLootEntry>,
            val health: ArrayList<BottlecapLootEntry>,
            val spAttack: ArrayList<BottlecapLootEntry>,
            val spDefence: ArrayList<BottlecapLootEntry>,
            val speed: ArrayList<BottlecapLootEntry>
        )
    }

    val ivIncrease: Int = 1

    val loot: LootConfig = DEFAULT_LOOT

    companion object {
        val DEFAULT_LOOT = LootConfig(
            LootConfig.Bottlecaps(
                arrayListOf(BottlecapLootEntry(LootTables.SIMPLE_DUNGEON_CHEST, 12)),
                arrayListOf(BottlecapLootEntry(LootTables.SIMPLE_DUNGEON_CHEST, 12)),
                arrayListOf(BottlecapLootEntry(LootTables.SIMPLE_DUNGEON_CHEST, 12)),
                arrayListOf(BottlecapLootEntry(LootTables.SIMPLE_DUNGEON_CHEST, 12)),
                arrayListOf(BottlecapLootEntry(LootTables.SIMPLE_DUNGEON_CHEST, 12)),
                arrayListOf(BottlecapLootEntry(LootTables.SIMPLE_DUNGEON_CHEST, 12)),
            ),
            arrayListOf(
                LootEntry(LootTables.FISHING_TREASURE_GAMEPLAY, cobblemonResource("ability_capsule"), 5),
                LootEntry(LootTables.FISHING_TREASURE_GAMEPLAY, cobblemonResource("ability_patch"), 5),
            ),
        )

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