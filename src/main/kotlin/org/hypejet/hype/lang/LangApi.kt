package org.hypejet.hype.lang

import com.mongodb.client.MongoDatabase
import lombok.SneakyThrows
import net.minestom.server.entity.Player
import net.minestom.server.event.player.PlayerDisconnectEvent
import net.minestom.server.event.player.PlayerSettingsChangeEvent
import net.minestom.server.extensions.Extension
import java.util.*

interface LangApi {
    fun getString(locale: Locale, name: String): String?
    fun getLocale(player: Player): Locale?
    fun setLocale(player: Player, locale: Locale): Boolean
    fun setClientLocale(player: Player): Boolean
    fun isClientLocale(player: Player): Boolean
    fun createTable()

    companion object {
        @SneakyThrows
        fun create(extension: Extension, database: MongoDatabase, collection: String): LangApi? {
            val coll = database.getCollection(collection)
            val lang = LangApiImpl(extension, database, coll)
            val settingsListener = PlayerSettingsListener()
            val disconnectListener = PlayerDisconnectListener()
            extension.eventNode.addListener(PlayerSettingsChangeEvent::class.java) { event: PlayerSettingsChangeEvent? ->
                if (event != null) {
                    settingsListener.listener(event, lang)
                }
            }
            extension.eventNode.addListener(PlayerDisconnectEvent::class.java) { event: PlayerDisconnectEvent? ->
                if (event != null) {
                    disconnectListener.listener(event, settingsListener)
                }
            }
            return lang
        }
    }
}