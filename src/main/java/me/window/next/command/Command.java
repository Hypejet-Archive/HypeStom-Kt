package me.window.next.command;

import me.window.permissions.PermissionProvider;
import me.window.permissions.SubPermission;
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

    private ArrayList<String> permissions = new ArrayList<>();
    private SubPermission provider;

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
     * {@link Command#setPermissions(String...)}
     */
    public Command(@NotNull PermissionProvider provider, @NotNull String name, @Nullable String... aliases) {
        super(name, aliases);
        provider = new SubPermission(provider, name);
        setPermissions(name);
        setCondition(this::defaultCondition);
    }

    private void setPermissions(String... permissions) {
        this.permissions.clear();
        this.permissions.addAll(Arrays.asList(permissions));
        updatePermissions();
    }

    private void setPermissionLevel(int level) {
        provider.updatePermissions(level, permissions.toArray(new String[0]));
    }

    private void addPermissions(String... permissions) {
        this.permissions.addAll(List.of(permissions));
        updatePermissions();
    }

    private void removePermissions(String... permissions) {
        this.permissions.removeAll(List.of(permissions));
        updatePermissions();
    }

    private void updatePermissions() {
        provider.updatePermissions(permissions.toArray(new String[0]));
    }

    /**
     * Gives sender access if it is either a console or it has the command permission.
     */
    private boolean defaultCondition(CommandSender sender, String command) {
        return sender instanceof ConsoleSender || provider.hasExtensionPermission((Player) sender);
    }
}
