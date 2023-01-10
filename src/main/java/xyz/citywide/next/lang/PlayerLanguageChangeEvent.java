package xyz.citywide.next.lang;

import net.minestom.server.entity.Player;
import net.minestom.server.event.trait.PlayerEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public record PlayerLanguageChangeEvent(Player player, Locale locale, boolean isClient) implements PlayerEvent {
    @Deprecated
    @Override
    public @NotNull Player getPlayer() {
        return player;
    }
}
