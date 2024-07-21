package com.example

import com.example.platform.PlatformRegistry
import net.minecraft.block.AbstractBlock
import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.RegistryKeys

@Suppress("unused")
object ExampleModBlocks : PlatformRegistry<Registry<Block>, RegistryKey<Registry<Block>>, Block>() {
    override val registry: Registry<Block> = Registries.BLOCK
    override val registryKey: RegistryKey<Registry<Block>> = RegistryKeys.BLOCK

    val EXAMPLE_BLOCK = create("example_block", Block(AbstractBlock.Settings.copy(Blocks.GOLD_BLOCK)))
}