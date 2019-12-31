package me.onenrico.armorbackpack.listener

import me.onenrico.armorbackpack.NBTItem
import me.onenrico.armorbackpack.inventory.Backpack
import me.onenrico.armorbackpack.main.Core
import me.onenrico.armorbackpack.util.toSerialized
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.ItemStack
import org.bukkit.scheduler.BukkitRunnable

class OpenListener : Listener {
    @EventHandler
    fun onClick(e: InventoryClickEvent) {
        e.clickedInventory?.let {
//            print("Slot: ${e.slot} Type: ${e.slotType} Raw: ${e.rawSlot}")
            if (e.slotType == InventoryType.SlotType.ARMOR && e.rawSlot == 6) {
                if (e.isShiftClick && e.isRightClick) {
                    e.isCancelled = true
                    object : BukkitRunnable() {
                        override fun run() {
                            e.whoClicked.openInventory(
                                Backpack(
                                    e.whoClicked.inventory,
                                    it.getItem(e.slot) ?: ItemStack(Material.AIR)
                                ).inventory
                            )
                        }
                    }.runTaskLater(Core.instance, 1)
                }
            }
        }
    }

    @EventHandler
    fun onClose(e: InventoryCloseEvent) {
        e.inventory.let {
            if (it.holder is Backpack) {
                val backpack = it.holder as Backpack
                val saved = it.toSerialized()
                val armornbt = NBTItem(backpack.armor)
                armornbt.setString("bp", saved)
                backpack.pInventory.setItem(38, armornbt.item)
            }
        }
    }
}

