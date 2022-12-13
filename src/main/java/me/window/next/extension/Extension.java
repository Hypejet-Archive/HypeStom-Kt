package me.window.next.extension;

import me.window.permissions.PermissionProvider;

public abstract class Extension extends me.heroostech.citystom.Extension {
    @Override
    public void preInitialize() {
        PermissionProvider provider = new PermissionProvider(getOrigin().getName());
    }
}
