package com.ugleh.bungeemod.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class KickCommand extends Command {

	public KickCommand() {
		super("kick", "bungeemod.kick");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (args.length < 2) { // Args must be 2 or more.

			sender.sendMessage(new ComponentBuilder("Invalid arguments. Try /kick <player|server|all> <reason>")
					.color(ChatColor.RED).create());
			return;

		}
		// Turn the args back into a string but remove arg[0].
		StringBuilder sbReason = new StringBuilder();
		for (String v : args) {
			sbReason.append(v);
			sbReason.append(' ');
		}
		// Create the BaseComponent once so we don't have to make it 3 times.
		BaseComponent[] reason = new ComponentBuilder(
				ChatColor.translateAlternateColorCodes('&', sbReason.toString().replace(args[0] + " ", ""))).create();

		// Check if the arg is server, all, or a players name.
		if (args[0].equalsIgnoreCase("all")) {
			// Kicks all players in all servers
			for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
				player.disconnect(reason);
			}
		} else if (args[0].equalsIgnoreCase("server")) {
			// Kicks players in current server.
			if (sender instanceof ProxiedPlayer) {
				for (ProxiedPlayer player : ((ProxiedPlayer) sender).getServer().getInfo().getPlayers()) {
					player.disconnect(reason);
				}
			} else {
				sender.sendMessage(new ComponentBuilder("Kicking using 'server' argument can only be used In-Game.")
						.color(ChatColor.RED).create());
			}
		} else {
			if (ProxyServer.getInstance().getPlayer(args[0]) != null) {
				ProxiedPlayer pl = ProxyServer.getInstance().getPlayer(args[0]);
				pl.disconnect(reason);
			} else {
				sender.sendMessage(new ComponentBuilder("Could not find player by the name of: " + args[0])
						.color(ChatColor.RED).create());
			}
		}

	}

}
