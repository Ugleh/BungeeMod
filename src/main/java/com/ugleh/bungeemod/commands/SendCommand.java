package com.ugleh.bungeemod.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class SendCommand extends Command {

	public SendCommand() {
		super("bsend", "bungeemod.send"); // Adding a permission here lets us
											// not
											// have to check for it later.
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (args.length != 2) // Command takes 2 arguments.
		{
			sender.sendMessage(new ComponentBuilder("Invalid arguments. Try /bsend <player|server|all> <server>")
					.color(ChatColor.RED).create());
			return;
		}
		ServerInfo toServer = ProxyServer.getInstance().getServerInfo(args[1]);
		// Check if server exists first before anything else.
		if (toServer == null) {
			sender.sendMessage(new ComponentBuilder("Server doesn't exist").color(ChatColor.RED).create());
			return;
		}

		if (args[0].equalsIgnoreCase("all")) {
			for (ProxiedPlayer p : ProxyServer.getInstance().getPlayers()) {
				p.connect(toServer);
			}
		} else {
			// It's not All, so we need to check if its a server name before we
			// check player names.
			ServerInfo fromServer = ProxyServer.getInstance().getServerInfo(args[0]);
			if (fromServer != null) // It's a Server, lets send all the players
									// inside the server.
			{
				for (ProxiedPlayer p : fromServer.getPlayers()) {
					p.connect(toServer);
				}
			} else // Last option is it being a player
			{
				ProxiedPlayer player = ProxyServer.getInstance().getPlayer(args[0]);
				if (player == null) // If its not a player then tell the sender.
				{
					sender.sendMessage(new ComponentBuilder("That player doesn't exist or isn't online.")
							.color(ChatColor.RED).create());
					return;
				}
				player.connect(toServer);
			}
		}
		sender.sendMessage(new ComponentBuilder("Successfully sent players.").color(ChatColor.GREEN).create());
	}
}
