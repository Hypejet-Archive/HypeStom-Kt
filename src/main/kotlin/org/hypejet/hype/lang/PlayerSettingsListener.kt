package org.hypejet.hype.lang

import net.minestom.server.MinecraftServer
import net.minestom.server.entity.Player
import net.minestom.server.event.player.PlayerSettingsChangeEvent
import java.util.*

internal class PlayerSettingsListener {
    val locales = HashMap<Player, Locale?>()
    fun listener(event: PlayerSettingsChangeEvent, api: LangApiImpl) {
        if (!locales.containsKey(event.player)) {
            locales[event.player] = event.player.locale
            MinecraftServer.getGlobalEventHandler().call(
                PlayerLanguageChangeEvent(
                    event.player,
                    event.player.locale!!, true
                )
            )
        }
        if (locales[event.player] != event.player.locale) {
            locales[event.player] = event.player.locale
            if (api.isClientLocale(event.player)) MinecraftServer.getGlobalEventHandler().call(
                PlayerLanguageChangeEvent(
                    event.player,
                    event.player.locale!!, true
                )
            )
        }
    }
}