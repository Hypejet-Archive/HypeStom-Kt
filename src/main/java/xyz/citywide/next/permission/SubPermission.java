package xyz.citywide.next.permission;

/**
 *Subpermission of a {@link PermissionProvider PermissionProvider}
 */
public class SubPermission extends PermissionProvider {

    /**
     * THe parent permission.
     */
    public PermissionProvider parent;

    /**
     * Creates a new subpermission
     * @param parent The parent permission can be either a permission provider or a SubPermission
     * @param extensionPermission The permission that this that will be checked
     */
    public SubPermission(PermissionProvider parent, String extensionPermission) {
        super(parent.extensionPermission + "." + extensionPermission);
        this.parent = parent;
    }

    /**
     * Creates a new subpermission
     * @param parent The parent permission can be either a permission provider or a SubPermission
     * @param opLevel The permission level where a player will be able to bypass permissions
     * @param extensionPermission The permission that will be checked
     */
    public SubPermission(PermissionProvider parent, int opLevel, String extensionPermission) {
        super(opLevel, parent.extensionPermission + "." + extensionPermission);
        this.parent = parent;
    }

    /**
     * Replaces permission with new permissions
     * @param extensionPermission The new permission
     */
    @Override
    public void updatePermissions(String extensionPermission) {
        super.updatePermissions(parent.extensionPermission + "." + extensionPermission);
    }
    /**
     * Replaces permission with new permissions
     * @param opLevel the new op level
     * @param extensionPermission The new permission
     */
    public void updatePermissions(int opLevel, String extensionPermission) {
        super.updatePermissions(opLevel, parent.extensionPermission + "." + extensionPermission);
    }
}
