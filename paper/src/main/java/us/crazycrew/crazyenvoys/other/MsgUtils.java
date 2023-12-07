package us.crazycrew.crazyenvoys.other;

import com.badbones69.crazyenvoys.CrazyEnvoys;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import us.crazycrew.crazyenvoys.common.config.types.PluginConfig;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static java.util.regex.Matcher.quoteReplacement;

public class MsgUtils {

    private static final CrazyEnvoys plugin = CrazyEnvoys.get();

    public static String color(String message) {
        Matcher matcher = Pattern.compile("#[a-fA-F\\d]{6}").matcher(message);
        StringBuilder buffer = new StringBuilder();

        while (matcher.find()) {
            matcher.appendReplacement(buffer, net.md_5.bungee.api.ChatColor.of(matcher.group()).toString());
        }

        return ChatColor.translateAlternateColorCodes('&', matcher.appendTail(buffer).toString());
    }

    public static void sendMessage(CommandSender commandSender, String message, boolean prefixToggle) {
        if (message == null || message.isEmpty()) return;

        String prefix = getPrefix();

        if (commandSender instanceof Player player) {
            if (!prefix.isEmpty() && prefixToggle) player.sendMessage(color(message.replaceAll("%prefix%", quoteReplacement(prefix))).replaceAll("%Prefix%", quoteReplacement(prefix))); else player.sendMessage(color(message));

            return;
        }

        if (!prefix.isEmpty() && prefixToggle) commandSender.sendMessage(color(message.replaceAll("%prefix%", quoteReplacement(prefix))).replaceAll("%Prefix%", quoteReplacement(prefix))); else commandSender.sendMessage(color(message));
    }

    public static String getPrefix() {
        return color(plugin.getCrazyHandler().getConfigManager().getPluginConfig().getProperty(PluginConfig.command_prefix));
    }

    public static String getPrefix(String msg) {
        return color(getPrefix() + msg);
    }

    public static String sanitizeColor(String msg) {
        return sanitizeFormat(color(msg));
    }

    public static String sanitizeFormat(String string) {
        return TextComponent.toLegacyText(TextComponent.fromLegacyText(string));
    }

    public static String removeColor(String msg) {
        return ChatColor.stripColor(msg);
    }
}