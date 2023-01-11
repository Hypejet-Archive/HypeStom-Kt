package xyz.citywide.next.tablist;

import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.minestom.server.entity.Player;

import java.util.ArrayList;

public abstract class Tablist {

    @Getter @Setter private ArrayList<Component> header = new ArrayList<>();
    @Getter @Setter private ArrayList<Component> footer = new ArrayList<>();

    @Getter private final ArrayList<Player> players = new ArrayList<>();

    //public abstract void createPlayerList(Player player);

    public void addHeaderLine(Component line) {
        header.add(line);
    }
    public void addFooterLine(Component line) {
        footer.add(line);
    }
    public void removeHeaderLine(Component line) {
        header.remove(line);
    }
    public void removeFooterLine(Component line) {
        footer.remove(line);
    }
    public void removeHeaderLine(int line) {
        header.remove(line);
    }
    public void removeFooterLine(int line) {
        footer.remove(line);
    }

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

        Component str1 = Component.join(JoinConfiguration.separator(Component.text("\n")), header);
        Component str2 = Component.join(JoinConfiguration.separator(Component.text("\n")), footer);

        //Class<? extends Tablist> cls = this.getClass();

        for(Player player : players) {
            //str1.replaceText(TextReplacementConfig.builder().match(Pattern.compile("$.*@")).replacement().build());
            player.sendPlayerListHeaderAndFooter(str1, str2);
        }
    }
}
