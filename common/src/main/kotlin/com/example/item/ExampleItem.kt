package com.example.item

import net.minecraft.item.Item

class ExampleItem(settings: Settings): Item(settings) {
    data class Settings(val property: Int): Item.Settings()
}