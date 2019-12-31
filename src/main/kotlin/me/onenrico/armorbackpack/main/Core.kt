package me.onenrico.armorbackpack.main

import me.onenrico.armorbackpack.listener.OpenListener
import org.bukkit.plugin.java.JavaPlugin

class Core : JavaPlugin() {
    companion object {
        lateinit var instance: Core
        fun getThis(): Core = instance
    }

    override fun onEnable() {
        print("ArmorBackpack Loaded...")
        instance = this
        server.pluginManager.registerEvents(OpenListener(), this)
    }
}