package xyz.citywide.next.extension;

import xyz.citywide.next.permission.PermissionProvider;

/**
 * Normal CityStom extension that Automatically creates a PermissionProvider.
 * @see xyz.citywide.citystom.Extension
 */
public abstract class Extension extends xyz.citywide.citystom.Extension {

    /**
     * The extension PermissionProvider.
     *
     * @see PermissionProvider
     */

    public static PermissionProvider provider;

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
}
