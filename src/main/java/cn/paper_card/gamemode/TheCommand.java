package cn.paper_card.gamemode;

import cn.paper_card.mc_command.NewMcCommand;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

class TheCommand extends NewMcCommand.HasSub {

    private final @NotNull Permission permission;
    private final @NotNull GameMode plugin;


    protected TheCommand(@NotNull GameMode plugin) {
        super("gamemode");
        this.plugin = plugin;
        this.permission = Objects.requireNonNull(plugin.getServer().getPluginManager().getPermission("paper-card.gamemode.command"));

        for (org.bukkit.GameMode value : org.bukkit.GameMode.values()) {
            this.addSub(new TheMode(value));
        }
    }

    @Override
    protected boolean canExecute(@NotNull CommandSender commandSender) {
        return commandSender.hasPermission(this.permission);
    }

    @Override
    protected void appendPrefix(TextComponent.@NotNull Builder text) {
        text.append(Component.text("[").color(NamedTextColor.GRAY));
        text.append(Component.text("游戏模式").color(NamedTextColor.DARK_AQUA));
        text.append(Component.text("]").color(NamedTextColor.GRAY));
    }

    @Override
    protected boolean onNotFound(@NotNull CommandSender sender, @NotNull String sub) {
        new Sender((sender)).warning("不支持的游戏模式：%s，请检查拼写是否正确".formatted(sub));
        return true;
    }

    @Override
    protected boolean onThisCommand(@NotNull CommandSender sender) {
        new Sender((sender)).warning("必须要指定要切换到什么游戏模式哦");
        return true;
    }

    class TheMode extends NewMcCommand {

        private final @NotNull Permission permission;
        private final @NotNull org.bukkit.GameMode mode;

        protected TheMode(final @NotNull org.bukkit.GameMode mode) {
            super(mode.name().toLowerCase());
            this.mode = mode;
            this.permission = this.addSubPermission(plugin.getServer().getPluginManager(), TheCommand.this.permission);
        }

        static @NotNull String modeChName(final @NotNull org.bukkit.GameMode mode) {
            return switch (mode) {
                case CREATIVE -> "创造";
                case SURVIVAL -> "生存";
                case ADVENTURE -> "冒险";
                case SPECTATOR -> "旁观";
            };
        }

        @Override
        protected void appendPrefix(TextComponent.@NotNull Builder text) {
            TheCommand.this.appendPrefix(text);
        }

        @Override
        protected boolean canExecute(@NotNull CommandSender commandSender) {
            return commandSender.hasPermission(this.permission);
        }

        @Override
        public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
            final Sender sd = new Sender(sender);

            if (!(sender instanceof final Player player)) {
                sd.error("该命令只能由玩家来执行");
                return true;
            }

            final org.bukkit.GameMode gameMode = player.getGameMode();

            final String name = modeChName(this.mode);

            if (gameMode != this.mode) {
                player.setGameMode(this.mode);
                sd.info("已切换您的游戏模式为" + name + "模式 :D");
            } else {
                sd.warning("无需切换，您已经是" + name + "模式");
            }
            return true;
        }

        @Override
        public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
            return null;
        }
    }
}
