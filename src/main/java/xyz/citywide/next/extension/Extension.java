package xyz.citywide.next.extension;

import xyz.citywide.next.command.Command;
import xyz.citywide.permissions.PermissionProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Normal CityStom extension that Automaticlly creates a PermissionProvider.
 * @see xyz.citywide.citystom.Extension
 */
public abstract class Extension extends xyz.citywide.citystom.Extension {

    /**
     * The extension PermissionProvider.
     *
     * @see PermissionProvider
     */
    public static PermissionProvider provider;

    public Extension(String permission, int oplevel) {
        provider = new PermissionProvider(oplevel, permission);
    }

    public Extension(String permission) {
        provider = new PermissionProvider(permission);
    }

    public Extension() {
        provider = new PermissionProvider(getOrigin().getName());
    }
}
