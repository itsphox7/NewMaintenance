package de.itsphox.newmaintenance.commands

import de.itsphox.newmaintenance.NewMaintenancePlugin
import net.strokkur.commands.Command
import net.strokkur.commands.Executes
import org.bukkit.command.CommandSender

@Command("maintenance")
class MaintenanceCommand(private val plugin: NewMaintenancePlugin) {

    @Executes("on")
    fun maintenanceOn(sender: CommandSender) {
        plugin.config.set("maintenance-enabled", true)
        plugin.config.save()
        val finalMessage = plugin.messageConfig.getComponent("maintenance-command-on", "status" to plugin.messageConfig.getComponent("enabled"))
        sender.sendMessage(finalMessage)
    }

    @Executes("off")
    fun maintenanceOff(sender: CommandSender) {
        plugin.config.set("maintenance-enabled", false)
        plugin.config.save()
        val finalMessage = plugin.messageConfig.getComponent("maintenance-command-off", "status" to plugin.messageConfig.getComponent("disabled"))
        sender.sendMessage(finalMessage)
    }

    @Executes("status")
    fun maintenanceStatus(sender: CommandSender) {
        val status = plugin.config.getBoolean("maintenance-enabled") ?: false
        val statusText = if (status) plugin.messageConfig.getComponent("status-enabled") else plugin.messageConfig.getComponent("status-disabled")
        sender.sendMessage(plugin.messageConfig.getComponent("maintenance-command-status", "status" to statusText))
    }
}