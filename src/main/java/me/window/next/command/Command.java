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

public class Command extends net.minestom.server.command.builder.Command {

    private ArrayList<String> permissions = new ArrayList<>();
    private SubPermission provider;

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

    private boolean defaultCondition(CommandSender sender, String command) {
        return sender instanceof ConsoleSender || provider.hasExtensionPermission((Player) sender);
    }
}
