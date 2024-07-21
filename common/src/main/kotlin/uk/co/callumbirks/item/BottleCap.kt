package uk.co.callumbirks.item

import com.cobblemon.mod.common.api.interaction.PokemonEntityInteraction
import com.cobblemon.mod.common.api.pokemon.stats.Stat
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity
import com.cobblemon.mod.common.pokemon.IVs
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.text.Text
import uk.co.callumbirks.CobbleTraining
import kotlin.math.min

class BottleCap(private val ivStat: Stat) : Item(Settings()), PokemonEntityInteraction {
    override val accepted: Set<PokemonEntityInteraction.Ownership> = setOf(PokemonEntityInteraction.Ownership.OWNER)

    override fun processInteraction(player: ServerPlayerEntity, entity: PokemonEntity, stack: ItemStack): Boolean {
        val existingIV = entity.pokemon.ivs[ivStat]

        if (existingIV == null) {
            CobbleTraining.LOGGER.error("Unknown IV stat {}", ivStat)
            return false
        }

        if (existingIV >= IVs.MAX_VALUE) {
            player.sendMessage(Text.translatable("message.${CobbleTraining.MOD_ID}.max_${ivStat.showdownId}"))
            return false
        }

        val newIV = min(IVs.MAX_VALUE, existingIV + CobbleTraining.CONFIG.ivIncrease)

        entity.pokemon.setIV(ivStat, newIV)
        stack.count -= 1

        player.sendMessage(Text.translatable("message.${CobbleTraining.MOD_ID}.inc_${ivStat.showdownId}", entity.displayName, newIV))

        return true
    }
}