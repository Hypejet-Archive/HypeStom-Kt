package org.hypejet.hype.permission

import net.minestom.server.entity.Player

open class PermissionProvider {
    /**
     * Permission
     */
    //@Getter Kotlin does not support lombok yet
    var extensionPermission: String

    /**
     * <pre>
     * The minimum permission level where the player bypasses permission check.
     * 1-4, 5 to disable.
    </pre> *
     */
    //@Getter Kotlin does not support lombok yet
    //@Setter Kotlin does not support lombok yet
    var opLevell: Int
    open fun getOpLevel(): Int {
        return opLevell
    }
    open fun setOpLevel(lvl: Int) {
        opLevell = lvl
    }

    /**
     * Creates a new PermissionProvider
     * @param extensionPermission The permission that this that will be checked
     */
    constructor(extensionPermission: String) {
        this.extensionPermission = extensionPermission
        opLevell = 4
    }

    /**
     * Creates a new PermissionProvider
     * @param opLevel The permission level where a player will be able to bypass permissions
     * @param extensionPermission The permission that this that will be checked
     */
    constructor(opLevel: Int, extensionPermission: String) {
        this.extensionPermission = extensionPermission
        this.opLevell = opLevel
    }

    /**
     * Replaces permission with new permissions
     * @param extensionPermission The new permissions
     */
    open fun updatePermissions(extensionPermission: String) {
        this.extensionPermission = extensionPermission
        opLevell = 4
    }

    /**
     * Replaces permission with new permissions
     * @param opLevel the new op level
     * @param extensionPermission The new permissions
     */
    open fun updatePermissions(opLevel: Int, extensionPermission: String) {
        this.extensionPermission = extensionPermission
        this.opLevell = opLevel
    }

    /**
     * Checks if the player has a subpermission
     * @param player the player to check
     * @param permission the permission
     * @return if the player has the permission
     */
    fun hasPermission(player: Player, permission: String): Boolean {
        return if (player.hasPermission("*") || player.permissionLevel >= opLevell) true else player.hasPermission("$extensionPermission.*") || player.hasPermission(
            "$extensionPermission.$permission"
        )
    }

    /**
     * Checks if the player has one of the provider permissions
     * @param player the player to check
     * @return if the player has permission
     */
    fun hasPermission(player: Player): Boolean {
        return if (player.hasPermission("*") || player.permissionLevel >= opLevell) true else player.hasPermission(
            extensionPermission
        )
    }

    /**
     * Checks if the player has a subpermission
     * @param player the player to check
     * @param permission the permission
     * @param opLevel override the default provider op level
     * @return if the player has the permission
     */
    fun hasPermission(player: Player, permission: String, opLevel: Int): Boolean {
        return if (player.hasPermission("*") || player.permissionLevel >= opLevel) true else player.hasPermission("$extensionPermission.*") || player.hasPermission(
            "$extensionPermission.$permission"
        )
    }

    /**
     * Checks if the player has one of the provider permissions
     * @param player the player to check
     * @param opLevel override the default provider op level
     * @return if the player has permission
     */
    fun hasPermission(player: Player, opLevel: Int): Boolean {
        return if (player.hasPermission("*") || player.permissionLevel >= opLevel) true else player.hasPermission(
            extensionPermission
        )
    }
}
