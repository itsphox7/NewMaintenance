package de.itsphox.newmaintenance.listeners

import de.itsphox.newmaintenance.NewMaintenancePlugin
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerLoginEvent

class PlayerLoginListener(private val plugin: NewMaintenancePlugin) : Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    fun onPlayerLogin(event: PlayerLoginEvent) {
        if (!plugin.config.getBoolean("maintenance-enabled", false)) {
            return
        }

        if (event.player.hasPermission("newmaintenance.bypass")) {
            return
        }

        event.disallow(PlayerLoginEvent.Result.KICK_OTHER, plugin.messageConfig.getString("maintenance-kick-message", ""))
    }
}