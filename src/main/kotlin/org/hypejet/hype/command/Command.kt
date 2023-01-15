package org.hypejet.hype.command

import net.minestom.server.command.CommandSender
import net.minestom.server.command.ConsoleSender
import net.minestom.server.command.builder.Command
import net.minestom.server.command.builder.condition.CommandCondition
import net.minestom.server.entity.Player
import org.hypejet.hype.permission.PermissionProvider
import org.hypejet.hype.permission.SubPermission

open class Command(provider: PermissionProvider, name: String, vararg aliases: String?) : Command(name, *aliases) {
    /**
     * The permission provider for this command
     * @see SubPermission
     */
    //@Getter Kotlin does not support lombok yet
    protected var provider: SubPermission

    /**
     * Creates a new command.
     * Default permissions the extension's permissions with the command name as a subpermission.
     *
     * @param provider PermissionsProvider, automatically set in the Extension class.
     * @param name Name of the command SubPermission will be automatically created with this name.
     * @param aliases Nullable, aliases to the command that the CommandSenders can also run to execute the command.
     * <br></br>
     * <br></br>
     * See also:<br></br>
     * [Command.setPermission]
     */
    init {
        this.provider = SubPermission(provider, name)
        setPermission(name)
        condition = CommandCondition { sender: CommandSender?, command: String? -> defaultCondition(sender, command) }
    }

    /**
     * Sets the subcommand permission.
     * @param permission The permission to be used
     */
    fun setPermission(permission: String?) {
        if (permission != null) {
            provider.updatePermissions(permission)
        }
    }

    /**
     * Set the required permissions level required for bypassing permissions.
     * @param level The new op level
     */
    fun setPermissionLevel(level: Int) {
        provider.setOpLevel(level)
    }

    /**
     * Gives the sender access if it is either a console or if it has the command permission.
     */
    protected fun defaultCondition(sender: CommandSender?, command: String?): Boolean {
        return sender is ConsoleSender || (sender as Player?)?.let { provider.hasPermission(it) } == true
    }
}