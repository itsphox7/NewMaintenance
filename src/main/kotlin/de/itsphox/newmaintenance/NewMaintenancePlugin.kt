package de.itsphox.newmaintenance

import de.itsphox.newmaintenance.commands.MaintenanceCommandBrigadier
import de.itsphox.newmaintenance.listeners.PlayerLoginListener
import de.quadrixyt.qavlen.config.YamlConfig
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents
import org.bstats.bukkit.Metrics
import org.bukkit.plugin.java.JavaPlugin


class NewMaintenancePlugin : JavaPlugin() {

    lateinit var messageConfig: YamlConfig
    lateinit var config: YamlConfig

    override fun onEnable() {
        val metrics = Metrics(this, 35462)
        registerCommands()
        initConfigs()
        server.pluginManager.registerEvents(PlayerLoginListener(this), this)
    }

    fun initConfigs() {
        messageConfig = YamlConfig.load(dataFolder.toPath().resolve("messages.yml"))
        config = YamlConfig.load(dataFolder.toPath().resolve("config.yml"))
    }

    fun registerCommands() {
        lifecycleManager.registerEventHandler(LifecycleEvents.COMMANDS.newHandler { event ->
            MaintenanceCommandBrigadier.register(event.registrar(), this)
        })
    }
}