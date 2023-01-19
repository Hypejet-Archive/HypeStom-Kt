package org.hypejet.hype.lang;

import com.moandjiezana.toml.Toml;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import lombok.SneakyThrows;
import net.minestom.server.entity.Player;
import net.minestom.server.extensions.Extension;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.Locale;

record LangApiImpl(@NotNull Extension extension, @NotNull MongoDatabase database, @NotNull MongoCollection<Document> coll) implements LangApi {
    @Override
    public String getString(@NotNull Locale locale, @NotNull String name) {
        Path path = extension.getDataDirectory().resolve("lang").resolve(locale.getLanguage() + ".toml");
        if(!path.toFile().exists()) {
            if(locale.equals(Locale.ENGLISH))
                throw new UnsupportedOperationException("No Default Language");
            return getString(Locale.ENGLISH, name);
        } else {
            Toml toml = new Toml().read(path.toFile());
            if(toml.getString(name) == null) {
                if(locale.equals(Locale.ENGLISH))
                    throw new UnsupportedOperationException("No Default For " + name);
                return getString(Locale.ENGLISH, name);
            }
            return toml.getString(name);
        }
    }

    @SneakyThrows
    @Override
    public Locale getLocale(@NotNull Player player) {
        Document document = coll.find(Filters.eq("title", "locales")).first();
        if(document == null) return null;
        if(isClientLocale(player))
            return player.getLocale();
        return Locale.forLanguageTag(document.getString(player.getUuid().toString()));
    }

    @SneakyThrows
    @Override
    public boolean setLocale(@NotNull Player player, @NotNull Locale locale) {
        Document document = coll.find(Filters.eq("title", "locales")).first();
        if(document == null) return false;
        boolean exists = document.containsKey(player.getUuid().toString());
        if(exists) {
            String lang = document.getString(player.getUuid().toString());
            if(locale.equals(Locale.forLanguageTag(lang)))
                return true;
        }
        coll.updateOne(Filters.eq("title", "locales"), Updates.set(player.getUuid().toString(), locale.toLanguageTag()));
        return exists;
    }

    @SneakyThrows
    @Override
    public boolean setClientLocale(@NotNull Player player) {
        Document document = coll.find(Filters.eq("title", "locales")).first();
        if(document == null) return false;
        boolean exists = document.containsKey(player.getUuid().toString());
        coll.updateOne(Filters.eq("title", "locales"), Updates.set(player.getUuid().toString(), "CLIENT"));
        return exists;
    }

    @SneakyThrows
    @Override
    public boolean isClientLocale(@NotNull Player player) {
        Document document = coll.find(Filters.eq("title", "locales")).first();
        if(document == null) return false;
        String locale = document.getString(player.getUuid().toString());
        if(locale == null) {
            setClientLocale(player);
            return true;
        }
        return locale.equals("CLIENT");
    }

    @SneakyThrows
    @Override
    public void createTable() {
        if(coll.find(Filters.eq("title", "locales")).first() == null) {
            Document document = new Document("title", "locales");
            coll.insertOne(document);
        }
    }
}
