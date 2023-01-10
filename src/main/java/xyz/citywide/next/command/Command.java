package xyz.citywide.next.command;

import xyz.citywide.next.permission.PermissionProvider;
import xyz.citywide.next.permission.SubPermission;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.adventure.audience.Audiences;
import net.minestom.server.command.CommandSender;
import net.minestom.server.command.ConsoleSender;
import net.minestom.server.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Normal Command witch also uses extra permission features.
 */
public class Command extends net.minestom.server.command.builder.Command {

    /**
     * The permission provider for this command
     * @see SubPermission
     */
    protected SubPermission provider;

    /**
     * Creates a new command.
     * Default permissions the extension's permissions with the command name as a subpermission.
     *
     * @param provider PermissionsProvider, automatically set in the Extension class.
     * @param name Name of the command SubPermission will be automatically created with this name.
     * @param aliases Nullable, aliases to the command that the CommandSenders can also run to execute the command.
     *<br>
     *                <br>
     * See also:<br>
     * {@link Command#setPermission(String)}
     */
    public Command(@NotNull PermissionProvider provider, @NotNull String name, @Nullable String... aliases) {
        super(name, aliases);
        this.provider = new SubPermission(provider, name);
        setPermission(name);
        setCondition(this::defaultCondition);
    }

    /**
     * Sets the subcommand permission.
     * @param permission The permission to be used
     */
    public void setPermission(String permission) {
        provider.updatePermissions(permission);
    }

    /**
     * Set the required permissions level required for bypassing permissions.
     * @param level The new op level
     */
    public void setPermissionLevel(int level) {
        provider.opLevel = level;
    }

    /**
     * Gives the sender access if it is either a console or if it has the command permission.
     */
    protected boolean defaultCondition(CommandSender sender, String command) {
        return sender instanceof ConsoleSender || provider.hasPermission((Player) sender);
    }
}
