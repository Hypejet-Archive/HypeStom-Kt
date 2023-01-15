package org.hypejet.hype.permission

class SubPermission : PermissionProvider {
    /**
     * The parent permission.
     */
    //@Getter Kotlin does not support lombok yet
    var parent: PermissionProvider

    /**
     * Creates a new subpermission
     * @param parent The parent permission can be either a permission provider or a SubPermission
     * @param extensionPermission The permission that this that will be checked
     */
    constructor(
        parent: PermissionProvider,
        extensionPermission: String
    ) : super(parent.extensionPermission + "." + extensionPermission) {
        this.parent = parent
    }

    /**
     * Creates a new subpermission
     * @param parent The parent permission can be either a permission provider or a SubPermission
     * @param opLevel The permission level where a player will be able to bypass permissions
     * @param extensionPermission The permission that will be checked
     */
    constructor(parent: PermissionProvider, opLevel: Int, extensionPermission: String) : super(
        opLevel,
        parent.extensionPermission + "." + extensionPermission
    ) {
        this.parent = parent
    }

    /**
     * Replaces permission with new permissions
     * @param extensionPermission The new permission
     */
    override fun updatePermissions(extensionPermission: String) {
        super.updatePermissions(parent.extensionPermission + "." + extensionPermission)
    }

    /**
     * Replaces permission with new permissions
     * @param opLevel the new op level
     * @param extensionPermission The new permission
     */
    override fun updatePermissions(opLevel: Int, extensionPermission: String) {
        super.updatePermissions(opLevel, parent.extensionPermission + "." + extensionPermission)
    }
}