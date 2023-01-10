package xyz.citywide.next.lang;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.SneakyThrows;
import net.minestom.server.entity.Player;
import net.minestom.server.event.player.PlayerDisconnectEvent;
import net.minestom.server.event.player.PlayerSettingsChangeEvent;
import net.minestom.server.extensions.Extension;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public sealed interface LangApi permits LangApiImpl {
    @SneakyThrows
    static LangApi create(@NotNull Extension extension, @NotNull MongoDatabase database, @NotNull String collection) {
        MongoCollection<Document> coll = database.getCollection(collection);
        final LangApiImpl lang = new LangApiImpl(extension, database, coll);
        PlayerSettingsListener settingsListener = new PlayerSettingsListener();
        PlayerDisconnectListener disconnectListener = new PlayerDisconnectListener();
        extension.getEventNode().addListener(PlayerSettingsChangeEvent.class, event -> settingsListener.listener(event, lang));
        extension.getEventNode().addListener(PlayerDisconnectEvent.class, event -> disconnectListener.listener(event, settingsListener));
        return lang;
    }

    String getString(@NotNull Locale locale, @NotNull String name);

    Locale getLocale(@NotNull Player player);

    boolean setLocale(@NotNull Player player, @NotNull Locale locale);

    boolean setClientLocale(@NotNull Player player);

    boolean isClientLocale(@NotNull Player player);

    void createTable();
}
