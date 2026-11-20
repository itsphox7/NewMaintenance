package de.itsphox.newmaintenance

import de.itsphox.newmaintenance.commands.MaintenanceCommandBrigadier
import de.itsphox.newmaintenance.listeners.PlayerLoginListener
import de.quadrixyt.qavlen.config.YamlConfig
import de.quadrixyt.qavlen.updater.UpdateChecker
import de.quadrixyt.qavlen.updater.UpdateResult
import dev.faststats.Metrics
import dev.faststats.bukkit.BukkitContext
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents
import org.bukkit.plugin.java.JavaPlugin


class NewMaintenancePlugin : JavaPlugin() {

    private val context = BukkitContext.Factory(this, "8f9e0980bdd545b88c5d429a4225deec")
        .metrics(Metrics.Factory::create)
        .create()

    lateinit var messageConfig: YamlConfig
    lateinit var config: YamlConfig

    override fun onEnable() {
        context.ready()
        registerCommands()
        initConfigs()
        server.pluginManager.registerEvents(PlayerLoginListener(this), this)

        if (config.getBoolean("check-update", true)) {
            val checker = UpdateChecker.modrinth("aXq1wdra", pluginMeta.version) { platform("paper"); minecraftVersion("26.3") }
            checker.checkAsync().thenAccept {result ->
                when (result) {
                    is UpdateResult.UpToDate -> logger.info("No update available. You are on the latest version!")
                    is UpdateResult.Available -> logger.info("Update ${result.latestVersion}: ${result.releaseUrl}")
                    is UpdateResult.Failed -> logger.warning("Update check failed: ${result.reason}")
                }
            }
        }
    }

    override fun onDisable() {
        context.shutdown()
    }

    fun initConfigs() {
        messageConfig = YamlConfig.load(dataFolder.toPath().resolve("messages.yml"), javaClass.classLoader)
        config = YamlConfig.load(dataFolder.toPath().resolve("config.yml"), javaClass.classLoader)
    }

    fun registerCommands() {
        lifecycleManager.registerEventHandler(LifecycleEvents.COMMANDS.newHandler { event ->
            MaintenanceCommandBrigadier.register(event.registrar(), this)
        })
    }
}