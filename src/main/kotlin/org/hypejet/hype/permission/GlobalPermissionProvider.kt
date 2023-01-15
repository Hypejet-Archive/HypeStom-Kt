package org.hypejet.hype.permission

import net.minestom.server.entity.Player

object GlobalPermissionProvider {
    /**
     * Check if the player has a root permission. Uses op level 4 by default.
     * @param player the player
     * @param permission The permission to check
     * @return if the player has the permission
     */
    fun hasPermission(player: Player, permission: String?): Boolean {
        return player.hasPermission("*") || player.hasPermission(permission!!) || player.permissionLevel >= 4
    }

    /**
     * Check if the player has a root permission.
     * @param player the player
     * @param permission The permission to check
     * @param opLevel the minimum permission level where the player bypasses permission check.
     * @return if the player has the permission
     */
    fun hasPermission(player: Player, permission: String?, opLevel: Int): Boolean {
        return player.hasPermission("*") || player.hasPermission(permission!!) || player.permissionLevel >= opLevel
    }
}