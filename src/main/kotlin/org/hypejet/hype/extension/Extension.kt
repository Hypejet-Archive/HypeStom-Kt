package org.hypejet.hype.extension

import com.moandjiezana.toml.Toml
import lombok.Getter
import net.kyori.adventure.text.Component
import net.minestom.server.MinecraftServer
import net.minestom.server.command.builder.Command
import net.minestom.server.extensions.Extension
import net.minestom.server.instance.block.BlockHandler
import net.minestom.server.timer.ExecutionType
import net.minestom.server.timer.TaskSchedule
import net.minestom.server.world.DimensionType
import org.hypejet.hype.permission.PermissionProvider
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.time.Duration

abstract class Extension : Extension {

    /**
     * The extension PermissionProvider.
     *
     * @see PermissionProvider
     */
    open var provider: PermissionProvider
        set(value) {}

    /**
     * Create a new Extension and create PermissionProvider with custom op level requirement.
     *
     * @param permission The permission that this extension will use.
     * @param oplevel The custom op level requirement.
     *
     * @see Extension.Extension
     * @see Extension.Extension
     */
    constructor(permission: String?, oplevel: Int) {
        provider = permission?.let { PermissionProvider(oplevel, it) }!!
    }

    /**
     * Create a new Extension and create PermissionProvider.
     *
     * @param permission The permission that this extension will use.
     *
     * @see Extension.Extension
     * @see Extension.Extension
     */
    constructor(permission: String?) {
        provider = permission?.let { PermissionProvider(it) }!!
    }

    /**
     * Create a new Extension and create PermissionProvider with the extension name from extension.json.
     *
     * @see Extension.Extension
     * @see Extension.Extension
     */
    constructor() {
        provider = PermissionProvider(origin.name)
    }

    fun registerCommand(command: Command) {
        MinecraftServer.getCommandManager().register(command)
    }

    fun unregisterCommand(command: Command) {
        MinecraftServer.getCommandManager().unregister(command)
    }

    fun registerDimension(type: DimensionType) {
        MinecraftServer.getDimensionTypeManager().addDimension(type)
    }

    fun registerBlockHandler(handler: BlockHandler) {
        MinecraftServer.getBlockManager().registerHandler(handler.namespaceId) { handler }
    }

    fun runAsync(runnable: Runnable) {
        MinecraftServer.getSchedulerManager().scheduleTask(runnable, TaskSchedule.immediate(),
                TaskSchedule.stop(), ExecutionType.ASYNC)
    }

    fun runSync(runnable: Runnable) {
        MinecraftServer.getSchedulerManager().scheduleTask(runnable, TaskSchedule.immediate(),
                TaskSchedule.stop(), ExecutionType.SYNC)
    }

    fun runDelayedAsync(runnable: Runnable, delay: Duration) {
        MinecraftServer.getSchedulerManager().scheduleTask(runnable, TaskSchedule.duration(delay),
                TaskSchedule.stop(), ExecutionType.ASYNC)
    }

    fun runDelayedSync(runnable: Runnable, delay: Duration) {
        MinecraftServer.getSchedulerManager().scheduleTask(runnable, TaskSchedule.duration(delay),
                TaskSchedule.stop(), ExecutionType.SYNC)
    }

    val config: Toml?
        get() {
            val configResource = javaClass.classLoader.getResourceAsStream("config.toml")
            if (configResource == null) {
                logger.error(Component.text("config.toml does not exists in resources folder"))
                return null
            }
            val defaults = Toml()
            defaults.read(configResource)
            val toml = Toml(defaults)
            val path = Path.of(dataDirectory.toString(), "config.toml")
            return try {
                if (!Files.exists(path)) {
                    if (!Files.exists(dataDirectory)) Files.createDirectory(path)
                    Files.createFile(path)
                    val stream = Files.newOutputStream(path)
                    stream.write(configResource.readAllBytes())
                    configResource.close()
                    stream.close()
                    toml
                } else {
                    configResource.close()
                    toml.read(Files.newInputStream(path))
                }
            } catch (e: IOException) {
                throw RuntimeException(e)
            }
        }
}
