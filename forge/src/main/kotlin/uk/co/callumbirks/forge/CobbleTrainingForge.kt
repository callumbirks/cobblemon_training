package uk.co.callumbirks.forge

import net.minecraft.item.ItemGroup
import net.minecraft.registry.RegistryKeys
import net.minecraft.server.MinecraftServer
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
import net.minecraftforge.fml.loading.FMLEnvironment
import net.minecraftforge.registries.RegisterEvent
import net.minecraftforge.server.ServerLifecycleHooks
import thedarkcolour.kotlinforforge.forge.MOD_BUS
import uk.co.callumbirks.*

@Suppress("unused", "UNUSED_PARAMETER", "MemberVisibilityCanBePrivate")
@Mod(CobbleTraining.MOD_ID)
class CobbleTrainingForge : CobbleTrainingImplementation {
    init {
        with(MOD_BUS) {
            addListener(this@CobbleTrainingForge::initialize)
            CobbleTraining.preInitialize(this@CobbleTrainingForge)
        }
    }

    fun initialize(event: FMLCommonSetupEvent) {
        CobbleTraining.LOGGER.info("Initializing CobbleEggs Forge...")
        CobbleTraining.initialize()
    }

    override val modAPI = ModAPI.FORGE

    override fun registerItems() {
        with(MOD_BUS) {
            addListener<RegisterEvent> { event ->
                event.register(CobbleTrainingItems.registryKey) { helper ->
                    CobbleTrainingItems.register { identifier, item -> helper.register(identifier, item) }
                }
            }
            addListener<RegisterEvent> { event ->
                event.register(RegistryKeys.ITEM_GROUP) { helper ->
                    CobbleTrainingItemGroups.register { holder ->
                        val itemGroup = ItemGroup.builder()
                            .displayName(holder.displayName)
                            .icon(holder.displayIconProvider)
                            .entries(holder.entryCollector)
                            .build()
                        helper.register(holder.key, itemGroup)
                        itemGroup
                    }
                }
            }
        }
    }

    override fun environment(): Environment {
        return if (FMLEnvironment.dist.isClient) Environment.CLIENT else Environment.SERVER
    }

    override fun server(): MinecraftServer? = ServerLifecycleHooks.getCurrentServer()
}