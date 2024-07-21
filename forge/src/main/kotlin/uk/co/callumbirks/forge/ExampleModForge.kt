package uk.co.callumbirks.forge

import com.example.*
import net.minecraft.item.ItemGroup
import net.minecraft.registry.RegistryKeys
import net.minecraft.server.MinecraftServer
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
import net.minecraftforge.fml.loading.FMLEnvironment
import net.minecraftforge.registries.RegisterEvent
import net.minecraftforge.server.ServerLifecycleHooks
import thedarkcolour.kotlinforforge.forge.MOD_BUS

@Suppress("unused", "UNUSED_PARAMETER", "MemberVisibilityCanBePrivate")
@Mod(ExampleMod.MOD_ID)
class ExampleModForge : ExampleModImplementation {
    init {
        with(MOD_BUS) {
            addListener(this@ExampleModForge::initialize)
            ExampleMod.preInitialize(this@ExampleModForge)
        }
    }

    fun initialize(event: FMLCommonSetupEvent) {
        ExampleMod.LOGGER.info("Initializing CobbleEggs Forge...")
        ExampleMod.initialize()
    }

    override val modAPI = ModAPI.FORGE

    override fun registerItems() {
        with(MOD_BUS) {
            addListener<RegisterEvent> { event ->
                event.register(ExampleModItems.registryKey) { helper ->
                    ExampleModItems.register { identifier, item -> helper.register(identifier, item) }
                }
            }
            addListener<RegisterEvent> { event ->
                event.register(RegistryKeys.ITEM_GROUP) { helper ->
                    ExampleModItemGroups.register { holder ->
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

    override fun registerBlocks() {
        with(MOD_BUS) {
            addListener<RegisterEvent> { event ->
                event.register(ExampleModBlocks.registryKey) { helper ->
                    ExampleModBlocks.register { identifier, block -> helper.register(identifier, block) }
                }
            }
        }
    }

    override fun environment(): Environment {
        return if (FMLEnvironment.dist.isClient) Environment.CLIENT else Environment.SERVER
    }

    override fun server(): MinecraftServer? = ServerLifecycleHooks.getCurrentServer()
}