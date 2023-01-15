package org.hypejet.hype.utils

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextDecoration
import net.kyori.adventure.text.minimessage.MiniMessage

object ChatUtil {
    fun format(message: String): Component {
        return MiniMessage.miniMessage().deserialize(message).decoration(TextDecoration.ITALIC, false)
    }
}
