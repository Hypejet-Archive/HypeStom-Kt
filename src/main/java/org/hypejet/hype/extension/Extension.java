package org.hypejet.hype.extension;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.moandjiezana.toml.Toml;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.minestom.server.MinecraftServer;
import net.minestom.server.command.builder.Command;
import net.minestom.server.instance.block.BlockHandler;
import net.minestom.server.timer.ExecutionType;
import net.minestom.server.timer.TaskSchedule;
import net.minestom.server.world.DimensionType;
import org.hypejet.hype.permission.PermissionProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;

/**
 * Normal CityStom extension that Automatically creates a PermissionProvider.
 */
public abstract class Extension extends net.minestom.server.extensions.Extension {

    /**
     * The extension PermissionProvider.
     *
     * @see PermissionProvider
     */

    @Getter private static PermissionProvider provider;

    /**
     * Create a new Extension and create PermissionProvider with custom op level requirement.
     *
     * @param permission The permission that this extension will use.
     * @param oplevel The custom op level requirement.
     *
     * @see Extension#Extension()
     * @see Extension#Extension(String)
     */
    public Extension(String permission, int oplevel) {
        provider = new PermissionProvider(oplevel, permission);
    }

    /**
     * Create a new Extension and create PermissionProvider.
     *
     * @param permission The permission that this extension will use.
     *
     * @see Extension#Extension()
     * @see Extension#Extension(String) 
     */
    public Extension(String permission) {
        provider = new PermissionProvider(permission);
    }

    /**
     * Create a new Extension and create PermissionProvider with the extension name from extension.json.
     *
     * @see Extension#Extension(String)
     * @see Extension#Extension(String, int)
     */
    public Extension() {
        provider = new PermissionProvider(getOrigin().getName());
    }

    public final void registerCommand(@NotNull Command command) {
        MinecraftServer.getCommandManager().register(command);
    }

    public final void unregisterCommand(@NotNull Command command) {
        MinecraftServer.getCommandManager().unregister(command);
    }

    public final void registerDimension(@NotNull DimensionType type) {
        MinecraftServer.getDimensionTypeManager().addDimension(type);
    }

    public final void registerBlockHandler(@NotNull BlockHandler handler) {
        MinecraftServer.getBlockManager().registerHandler(handler.getNamespaceId(), () -> handler);
    }

    public final void runAsync(@NotNull Runnable runnable) {
        MinecraftServer.getSchedulerManager().scheduleTask(runnable, TaskSchedule.immediate(),
                TaskSchedule.stop(), ExecutionType.ASYNC);
    }

    public final void runSync(@NotNull Runnable runnable) {
        MinecraftServer.getSchedulerManager().scheduleTask(runnable, TaskSchedule.immediate(),
                TaskSchedule.stop(), ExecutionType.SYNC);
    }

    public final void runDelayedAsync(@NotNull Runnable runnable, @NotNull Duration delay) {
        MinecraftServer.getSchedulerManager().scheduleTask(runnable, TaskSchedule.duration(delay),
                TaskSchedule.stop(), ExecutionType.ASYNC);
    }

    public final void runDelayedSync(@NotNull Runnable runnable, @NotNull Duration delay) {
        MinecraftServer.getSchedulerManager().scheduleTask(runnable, TaskSchedule.duration(delay),
                TaskSchedule.stop(), ExecutionType.SYNC);
    }

    public final @Nullable Toml getConfig() {
        InputStream configResource = getClass().getClassLoader().getResourceAsStream("config.toml");
        if (configResource == null) {
            getLogger().error(Component.text("config.toml does not exists in resources folder"));
            return null;
        }

        Toml defaults = new Toml();
        defaults.read(configResource);

        Toml toml = new Toml(defaults);
        Path path = Path.of(String.valueOf(getDataDirectory()), "config.toml");

        try {
            if(!Files.exists(path)) {
                if(!Files.exists(getDataDirectory()))
                    Files.createDirectory(path);
                Files.createFile(path);
                OutputStream stream = Files.newOutputStream(path);
                stream.write(configResource.readAllBytes());
                configResource.close();
                stream.close();
                return toml;
            } else {
                configResource.close();
                return toml.read(Files.newInputStream(path));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param config The config type
     * @return New config using the config parameter as type
     *
     * @see <a href="https://github.com/google/gson/blob/master/UserGuide.md#object-examples">Gson Documentation</a>
     *
     */
    public <T extends Object> T getJsonConfig(T config) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        Path path = Path.of(String.valueOf(getDataDirectory()), "config.json");

        try {
            if(!Files.exists(path)) {
                if(!Files.exists(getDataDirectory()))
                    Files.createDirectory(getDataDirectory());
                Files.createFile(path);
                OutputStream stream = Files.newOutputStream(path);
                stream.write(gson.toJson(config).getBytes());
                stream.close();
                return config;
            } else {
                return (T) gson.fromJson(Files.readString(path), config.getClass());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

