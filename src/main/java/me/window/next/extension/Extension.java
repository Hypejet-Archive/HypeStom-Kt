package me.window.next.extension;

import me.window.next.command.Command;
import me.window.permissions.PermissionProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Normal CityStom extension that Automaticlly creates a PermissionProvider.
 * @see me.heroostech.citystom.Extension
 */
public abstract class Extension extends me.heroostech.citystom.Extension {

    /**
     * The extension PermissionProvider.
     *
     * @see PermissionProvider
     */
    public static PermissionProvider provider;
    
    @Override
    public void preInitialize() {
        provider = new PermissionProvider(getOrigin().getName());
    }
}
