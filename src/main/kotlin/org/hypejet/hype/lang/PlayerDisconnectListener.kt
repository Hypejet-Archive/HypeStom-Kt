package org.hypejet.hype.lang

import net.minestom.server.event.player.PlayerDisconnectEvent

internal class PlayerDisconnectListener {
    fun listener(event: PlayerDisconnectEvent, listener: PlayerSettingsListener) {
        listener.locales.remove(event.player, listener.locales[event.player])
    }
}
