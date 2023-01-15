package org.hypejet.hype.lang;

import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.event.player.PlayerSettingsChangeEvent;

import java.util.HashMap;
import java.util.Locale;

final class PlayerSettingsListener {

    final HashMap<Player, Locale> locales = new HashMap<>();

    public void listener(PlayerSettingsChangeEvent event, LangApiImpl api) {
        if (!locales.containsKey(event.getPlayer())) {
            locales.put(event.getPlayer(), event.getPlayer().getLocale());
            MinecraftServer.getGlobalEventHandler().call(new PlayerLanguageChangeEvent(event.getPlayer(), event.getPlayer().getLocale(), true));
        }
        if(!locales.get(event.getPlayer()).equals(event.getPlayer().getLocale())) {
            locales.put(event.getPlayer(), event.getPlayer().getLocale());
            if (api.isClientLocale(event.getPlayer()))
                MinecraftServer.getGlobalEventHandler().call(new PlayerLanguageChangeEvent(event.getPlayer(), event.getPlayer().getLocale(), true));
        }
    }
}
