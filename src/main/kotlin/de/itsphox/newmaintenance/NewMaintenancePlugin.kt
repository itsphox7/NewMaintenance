package de.itsphox.newmaintenance

import de.itsphox.newmaintenance.listeners.PlayerLoginListener
import de.quadrixyt.qavlen.config.YamlConfig
import org.bstats.bukkit.Metrics
import org.bukkit.plugin.java.JavaPlugin


class NewMaintenancePlugin : JavaPlugin() {

    lateinit var messageConfig: YamlConfig
    lateinit var config: YamlConfig

    override fun onEnable() {
        val metrics = Metrics(this, 35462)
        initConfigs()

        server.pluginManager.registerEvents(PlayerLoginListener(), this)
    }

    override fun onDisable() {

    }

    fun initConfigs() {
        messageConfig = YamlConfig.load(dataFolder.toPath().resolve("messages.yml"))
        config = YamlConfig.load(dataFolder.toPath().resolve("config.yml"))
    }
}