package uk.co.callumbirks

import uk.co.callumbirks.CobbleTraining.exampleModResource
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemGroup.EntryCollector
import net.minecraft.item.ItemStack
import net.minecraft.registry.Registries
import net.minecraft.registry.RegistryKey
import net.minecraft.text.Text

@Suppress("unused", "SameParameterValue")
object CobbleTrainingItemGroups {
    private val ALL = arrayListOf<ItemGroupHolder>()

    private val ITEMS_KEY = create("items", ItemEntries) { ItemStack(CobbleTrainingItems.BOTTLECAP_ATTACK) }

    val ITEMS get() = Registries.ITEM_GROUP.get(ITEMS_KEY)

    fun register(consumer: (holder: ItemGroupHolder) -> ItemGroup) {
        ALL.forEach(consumer::invoke)
    }

    private fun create(name: String, entryCollector: EntryCollector, displayIconProvider: () -> ItemStack): RegistryKey<ItemGroup> {
        val key = RegistryKey.of(Registries.ITEM_GROUP.key, exampleModResource(name))
        ALL += ItemGroupHolder(key, displayIconProvider, entryCollector)
        return key
    }

    data class ItemGroupHolder(
        val key: RegistryKey<ItemGroup>,
        val displayIconProvider: () -> ItemStack,
        val entryCollector: EntryCollector,
        val displayName: Text = Text.translatable("itemGroup.${key.value.namespace}.${key.value.path}"),
    )

    private object ItemEntries : EntryCollector {
        override fun accept(displayContext: ItemGroup.DisplayContext, entries: ItemGroup.Entries) {
            entries.add(CobbleTrainingItems.BOTTLECAP_ATTACK)
        }
    }
}