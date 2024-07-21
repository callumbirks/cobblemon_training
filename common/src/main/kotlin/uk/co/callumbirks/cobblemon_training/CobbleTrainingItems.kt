package uk.co.callumbirks.cobblemon_training

import com.cobblemon.mod.common.api.pokemon.stats.Stats
import net.minecraft.item.Item
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.RegistryKeys
import uk.co.callumbirks.cobblemon_training.item.BottleCap
import uk.co.callumbirks.cobblemon_training.platform.PlatformRegistry

@Suppress("unused", "SameParameterValue")
object CobbleTrainingItems : PlatformRegistry<Registry<Item>, RegistryKey<Registry<Item>>, Item>() {
    override val registry: Registry<Item> = Registries.ITEM
    override val registryKey: RegistryKey<Registry<Item>> = RegistryKeys.ITEM

    val BOTTLECAP_ATTACK = create("bottlecap_attack", BottleCap(Stats.ATTACK))
    val BOTTLECAP_DEFENCE = create("bottlecap_defence", BottleCap(Stats.DEFENCE))
    val BOTTLECAP_HEALTH = create("bottlecap_health", BottleCap(Stats.HP))
    val BOTTLECAP_SPATTACK = create("bottlecap_spattack", BottleCap(Stats.SPECIAL_ATTACK))
    val BOTTLECAP_SPDEFENCE = create("bottlecap_spdefence", BottleCap(Stats.SPECIAL_DEFENCE))
    val BOTTLECAP_SPEED = create("bottlecap_speed", BottleCap(Stats.SPEED))
}