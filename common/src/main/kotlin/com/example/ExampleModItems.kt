package com.example

import net.minecraft.item.Item
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.RegistryKeys
import com.example.item.ExampleItem
import com.example.platform.PlatformRegistry
import net.minecraft.block.Block
import net.minecraft.item.BlockItem

@Suppress("unused", "SameParameterValue")
object ExampleModItems : PlatformRegistry<Registry<Item>, RegistryKey<Registry<Item>>, Item>() {
    override val registry: Registry<Item> = Registries.ITEM
    override val registryKey: RegistryKey<Registry<Item>> = RegistryKeys.ITEM

    val EXAMPLE_ITEM = create("example_item", ExampleItem(ExampleItem.Settings(ExampleMod.CONFIG.settings.setting1)))

    val EXAMPLE_BLOCK = blockItem("example_block", ExampleModBlocks.EXAMPLE_BLOCK)

    private fun blockItem(name: String, block: Block): BlockItem = this.create(name, BlockItem(block, Item.Settings()))
}