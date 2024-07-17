package cn.paper_card.gamemode;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;

class OnJoin implements Listener {

    @EventHandler
    public void on(@NotNull PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        final GameMode gameMode = player.getGameMode();

        // 取消创造
        if (gameMode == GameMode.CREATIVE) {
            player.setGameMode(GameMode.SPECTATOR);
        }
    }
}
