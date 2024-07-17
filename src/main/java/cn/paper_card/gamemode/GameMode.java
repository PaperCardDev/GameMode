package cn.paper_card.gamemode;

import org.bukkit.plugin.java.JavaPlugin;

public final class GameMode extends JavaPlugin {

    @Override
    public void onEnable() {
        new TheCommand(this).register(this);

        this.getServer().getPluginManager().registerEvents(new OnJoin(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
