package org.hypejet.hype.tablist

import lombok.Getter
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.JoinConfiguration
import net.minestom.server.entity.Player

abstract class Tablist {
    @Getter
    var players = ArrayList<Player>()
    abstract fun createPlayerList(player: Player?): Array<ArrayList<Component>?>
    fun addPlayer(player: Player) {
        players.add(player)
    }

    fun removePlayer(player: Player) {
        players.remove(player)
    }

    fun removePlayer(player: Int) {
        players.removeAt(player)
    }

    open fun refresh() {

        //Class<? extends Tablist> cls = this.getClass();
        for (player in players) {
            val arr = createPlayerList(player)
            if (arr.size != 2) {
                throw RuntimeException("Array size must be 2!")
            }
            var str1: Component = Component.empty()
            if(arr[0] != null) {
                str1 = Component.join(
                    JoinConfiguration.separator(Component.text("\n")),
                    arr[0]!!
                )
            }
            var str2: Component = Component.empty()
            if(arr[1] != null) {
                str2 = Component.join(
                    JoinConfiguration.separator(Component.text("\n")),
                    arr[1]!!
                )
            }
            //str1.replaceText(TextReplacementConfig.builder().match(Pattern.compile("$.*@")).replacement().build());
            player.sendPlayerListHeaderAndFooter(str1, str2)
        }
    }
}