package org.hypejet.hype.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;

public final class ChatUtil {
    public static Component format(String message) {
        return MiniMessage.miniMessage().deserialize(message).decoration(TextDecoration.ITALIC, false);
    }
}
