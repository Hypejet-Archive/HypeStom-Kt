package org.hypejet.hype.tablist

import lombok.Getter
import lombok.Setter
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.JoinConfiguration
import net.minestom.server.entity.Player

class GlobalTablist : Tablist() {
    override fun createPlayerList(player: Player?): Array<ArrayList<Component>?> {
        return arrayOfNulls<ArrayList<Component>>(2)
    }

    @Getter
    @Setter
    private val header = ArrayList<Component>()

    @Getter
    @Setter
    private val footer = ArrayList<Component>()
    fun addHeaderLine(line: Component) {
        header.add(line)
    }

    fun addFooterLine(line: Component) {
        footer.add(line)
    }

    fun removeHeaderLine(line: Component) {
        header.remove(line)
    }

    fun removeFooterLine(line: Component) {
        footer.remove(line)
    }

    fun removeHeaderLine(line: Int) {
        header.removeAt(line)
    }

    fun removeFooterLine(line: Int) {
        footer.removeAt(line)
    }

    override fun refresh() {
        val str1 = Component.join(JoinConfiguration.separator(Component.text("\n")), header)
        val str2 = Component.join(JoinConfiguration.separator(Component.text("\n")), footer)

        //Class<? extends Tablist> cls = this.getClass();
        for (player in players) {
            //str1.replaceText(TextReplacementConfig.builder().match(Pattern.compile("$.*@")).replacement().build());
            player.sendPlayerListHeaderAndFooter(str1, str2)
        }
    }
}
