package org.hypejet.hype.tablist;

import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.minestom.server.entity.Player;

import java.util.ArrayList;

public abstract class Tablist {

    @Getter
    ArrayList<Player> players = new ArrayList<>();

    public abstract ArrayList<Component>[] createPlayerList(Player player);

    public void addPlayer(Player player) {
        players.add(player);
    }
    public void removePlayer(Player player) {
        players.remove(player);
    }
    public void removePlayer(int player) {
        players.remove(player);
    }

    public void refresh() {

        //Class<? extends Tablist> cls = this.getClass();

        for(Player player : players) {

            ArrayList<Component>[] arr = createPlayerList(player);
            if(arr.length != 2) {
                throw new RuntimeException("Array size must be 2!");
            }

            Component str1 = Component.join(JoinConfiguration.separator(Component.text("\n")), arr[0]);
            Component str2 = Component.join(JoinConfiguration.separator(Component.text("\n")), arr[1]);
            //str1.replaceText(TextReplacementConfig.builder().match(Pattern.compile("$.*@")).replacement().build());
            player.sendPlayerListHeaderAndFooter(str1, str2);
        }
    }
}
