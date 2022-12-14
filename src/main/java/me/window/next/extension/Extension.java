package me.window.next.extension;

import me.window.permissions.PermissionProvider;

/**
 * Normal CityStom extension that Automaticlly creates a PermissionProvider.
 * @see me.heroostech.citystom.Extension
 */
public abstract class Extension extends me.heroostech.citystom.Extension {
    
    public PermissionProvider provider;
    
    @Override
    public void preInitialize() {
        provider = new PermissionProvider(getOrigin().getName());
    }
}
