package me.onenrico.armorbackpack.util

import org.bukkit.Material
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import java.io.StringReader

fun ItemStack.toSerialized(): String {
    val config = YamlConfiguration()
    config.set("nop", this)
    return config.saveToString().replace("\n", "#nl#")
}

fun Inventory.toSerialized(): String {
    var result = ""
    contents.forEachIndexed { index, itemStack ->
        if (itemStack != null)
            result += "$index<@>${itemStack.toSerialized()}<#>"
    }
    return result
}

fun String.toItemStack(): ItemStack {
    return if (contains("#nl#")) {
        val raw = replace("#nl#", "\n")
        val config = YamlConfiguration.loadConfiguration(StringReader(raw))
        config.getItemStack("nop") ?: ItemStack(Material.AIR)
    } else {
        print("Failed to load $this")
        ItemStack(Material.AIR)
    }
}

fun String.toInventory(inv: Inventory) {
    inv.clear()
    if (contains("#nl#")) {
        split("<#>".toRegex()).forEach {
            if(it.isNotBlank()){
                val i = it.split("<@>".toRegex())[0].toIntOrNull() ?: 0
                val item = it.split("<@>".toRegex())[1].toItemStack()
                inv.setItem(i,item)
            }
        }
    }else{
        print("Failed to load $this")
    }
}