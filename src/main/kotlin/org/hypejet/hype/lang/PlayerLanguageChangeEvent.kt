package org.hypejet.hype.lang

import net.minestom.server.entity.Player
import net.minestom.server.event.trait.PlayerEvent
import org.jetbrains.annotations.NotNull
import java.util.*

data class PlayerLanguageChangeEvent(var pllayer: Player, val locale: Locale, val isClient: Boolean) :
    PlayerEvent {
    @Deprecated("")
    override fun getPlayer(): Player {
        return pllayer
    }
}