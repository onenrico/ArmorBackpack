package me.onenrico.armorbackpack.inventory

import me.onenrico.armorbackpack.NBTItem
import me.onenrico.armorbackpack.util.toInventory
import org.bukkit.Bukkit
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.PlayerInventory

class Backpack(val pInventory: PlayerInventory, val armor: ItemStack) : InventoryHolder {

    private lateinit var localInventory: Inventory

    init {
        run {
            val type = armor.type.toString()
            if (!type.contains("CHESTPLATE")) {
                return@run
            }
            val armornbt: NBTItem = NBTItem(armor)
            var quality = "NORMAL"
            var size = 9
            type.run {
                when {
                    contains("DIAMOND") -> {
                        quality = "Diamond"; size = 36
                    }
                    contains("GOLDEN") -> {
                        quality = "Golden"; size = 27
                    }
                    contains("IRON") -> {
                        quality = "Iron"; size = 18
                    }
                    contains("LEATHER") -> {
                        quality = "Leather"; size = 9
                    }
                }
            }
            localInventory = Bukkit.createInventory(this, size, "$quality Backpack")
            armornbt.getString("bp")?.toInventory(localInventory)
        }
    }

    override fun getInventory(): Inventory {
        return localInventory
    }

}