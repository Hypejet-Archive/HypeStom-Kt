package xyz.citywide.next.lang;

import net.minestom.server.event.player.PlayerDisconnectEvent;

final class PlayerDisconnectListener {
    public void listener(PlayerDisconnectEvent event, PlayerSettingsListener listener) {
        listener.locales.remove(event.getPlayer(), listener.locales.get(event.getPlayer()));
    }
}
