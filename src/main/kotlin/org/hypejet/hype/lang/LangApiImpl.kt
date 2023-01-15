package org.hypejet.hype.lang

import com.moandjiezana.toml.Toml
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters
import com.mongodb.client.model.Updates
import lombok.SneakyThrows
import net.minestom.server.entity.Player
import net.minestom.server.extensions.Extension
import org.bson.Document
import java.util.*

internal data class LangApiImpl(val extension: Extension, val database: MongoDatabase, val coll: MongoCollection<Document?>) : LangApi {
    override fun getString(locale: Locale, name: String): String? {
        val path = extension.dataDirectory.resolve("lang").resolve(locale.language + ".toml")
        return if (!path.toFile().exists()) {
            if (locale == Locale.ENGLISH) throw UnsupportedOperationException("No Default Language")
            getString(Locale.ENGLISH, name)
        } else {
            val toml = Toml().read(path.toFile())
            if (toml.getString(name) == null) {
                if (locale == Locale.ENGLISH) throw UnsupportedOperationException("No Default For $name")
                return getString(Locale.ENGLISH, name)
            }
            toml.getString(name)
        }
    }

    @SneakyThrows
    override fun getLocale(player: Player): Locale? {
        val document = coll.find(Filters.eq("title", "locales")).first() ?: return null
        return if (isClientLocale(player)) player.locale else Locale.forLanguageTag(document.getString(player.uuid.toString()))
    }

    @SneakyThrows
    override fun setLocale(player: Player, locale: Locale): Boolean {
        val document = coll.find(Filters.eq("title", "locales")).first() ?: return false
        val exists = document.containsKey(player.uuid.toString())
        if (exists) {
            val lang = document.getString(player.uuid.toString())
            if (locale == Locale.forLanguageTag(lang)) return true
        }
        coll.updateOne(Filters.eq("title", "locales"), Updates.set(player.uuid.toString(), locale.toLanguageTag()))
        return exists
    }

    @SneakyThrows
    override fun setClientLocale(player: Player): Boolean {
        val document = coll.find(Filters.eq("title", "locales")).first() ?: return false
        val exists = document.containsKey(player.uuid.toString())
        coll.updateOne(Filters.eq("title", "locales"), Updates.set(player.uuid.toString(), "CLIENT"))
        return exists
    }

    @SneakyThrows
    override fun isClientLocale(player: Player): Boolean {
        val document = coll.find(Filters.eq("title", "locales")).first() ?: return false
        val locale = document.getString(player.uuid.toString())
        if (locale == null) {
            setClientLocale(player)
            return true
        }
        return locale == "CLIENT"
    }

    @SneakyThrows
    override fun createTable() {
        if (coll.find(Filters.eq("title", "locales")).first() == null) {
            val document = Document("title", "locales")
            coll.insertOne(document)
        }
    }
}