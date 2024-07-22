package uk.co.callumbirks.cobblemon_training

import dev.architectury.event.events.common.LootEvent
import net.minecraft.item.Item
import net.minecraft.item.Items
import net.minecraft.loot.LootManager
import net.minecraft.loot.LootPool
import net.minecraft.loot.entry.ItemEntry
import net.minecraft.registry.Registries
import net.minecraft.util.Identifier
import uk.co.callumbirks.cobblemon_training.CobbleTrainingItems.BOTTLECAP_ATTACK
import uk.co.callumbirks.cobblemon_training.CobbleTrainingItems.BOTTLECAP_DEFENCE
import uk.co.callumbirks.cobblemon_training.CobbleTrainingItems.BOTTLECAP_HEALTH
import uk.co.callumbirks.cobblemon_training.CobbleTrainingItems.BOTTLECAP_SPATTACK
import uk.co.callumbirks.cobblemon_training.CobbleTrainingItems.BOTTLECAP_SPDEFENCE
import uk.co.callumbirks.cobblemon_training.CobbleTrainingItems.BOTTLECAP_SPEED
import java.rmi.NoSuchObjectException
import kotlin.jvm.optionals.getOrNull

object CobbleTrainingLoot : LootEvent.ModifyLootTable {
    private var loot: HashMap<Identifier, ArrayList<LootEntry>> = hashMapOf()

    data class LootEntry(val item: Item, val weight: Int)

    private fun registerLoot(tableIdentifier: Identifier, item: Item, weight: Int) {
        val list = loot.getOrPut(tableIdentifier) { arrayListOf() }
        list.add(LootEntry(item, weight))
    }

    fun register() {
        val lootConfig = CobbleTraining.CONFIG.loot
        for (entry in lootConfig.bottlecaps.attack) {
            registerLoot(entry.table(), BOTTLECAP_ATTACK, entry.weight)
        }
        for (entry in lootConfig.bottlecaps.defence) {
            registerLoot(entry.table(), BOTTLECAP_DEFENCE, entry.weight)
        }
        for (entry in lootConfig.bottlecaps.health) {
            registerLoot(entry.table(), BOTTLECAP_HEALTH, entry.weight)
        }
        for (entry in lootConfig.bottlecaps.spAttack) {
            registerLoot(entry.table(), BOTTLECAP_SPATTACK, entry.weight)
        }
        for (entry in lootConfig.bottlecaps.spDefence) {
            registerLoot(entry.table(), BOTTLECAP_SPDEFENCE, entry.weight)
        }
        for (entry in lootConfig.bottlecaps.speed) {
            registerLoot(entry.table(), BOTTLECAP_SPEED, entry.weight)
        }
        for (entry in lootConfig.otherItems) {
            val identifier = entry.item()
            val item = Registries.ITEM.getOrEmpty(identifier).getOrNull()
            if (item == null) {
                CobbleTraining.LOGGER.warn("No such item '{}', skipping!", identifier)
                continue
            }
            registerLoot(entry.table(), item, entry.weight)
        }

        for (lootEntry in loot) {
            val totalWeight = lootEntry.value.stream().map { it.weight }.reduce(0) { total, weight -> total + weight }
            if (totalWeight < 100) {
                registerLoot(lootEntry.key, Items.AIR, 100 - totalWeight)
            }
        }

        LootEvent.MODIFY_LOOT_TABLE.register(this)
    }

    override fun modifyLootTable(
        lootDataManager: LootManager?,
        id: Identifier,
        context: LootEvent.LootTableModificationContext,
        builtin: Boolean
    ) {
        loot[id]?.let { entries ->
            CobbleTraining.LOGGER.info("Adding {} entries to loot table '{}'", entries.size, id)
            var builder = LootPool.builder()
            for (entry in entries) {
                builder = builder.with(ItemEntry.builder(entry.item).weight(entry.weight))
            }
            context.addPool(builder)
        }
    }
}