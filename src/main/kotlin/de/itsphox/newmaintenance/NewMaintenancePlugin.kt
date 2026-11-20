package de.itsphox.newmaintenance

import de.itsphox.newmaintenance.listeners.PlayerLoginListener
import org.bstats.bukkit.Metrics
import org.bukkit.plugin.java.JavaPlugin


class NewMaintenancePlugin : JavaPlugin() {

    override fun onEnable() {
        val metrics = Metrics(this, 35462)

        server.pluginManager.registerEvents(PlayerLoginListener(), this)
    }

    override fun onDisable() {

    }
}