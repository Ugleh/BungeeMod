package com.ugleh.bungeemod.commands;

import java.net.InetSocketAddress;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.plugin.Command;

/**
 * The Create Server command class.
 */
public class CreateCommand extends Command {

	public CreateCommand() {
		super("createserver", "bungeemod.createserver"); // Adding a permission
															// here lets us not
															// have to check for
															// it later.
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (args.length >= 4) {
			createServer(sender, args);
		} else {
			sender.sendMessage(new ComponentBuilder("Invalid arguments. Try /createserver <name> <motd> <ip> <port>")
					.color(ChatColor.RED).create());
		}
	}

	/**
	 * Creates the server info and adds it to the server list.
	 *
	 * @param sender
	 *            The CommandSender sent from execute()
	 * @param args
	 *            The command args
	 */
	private void createServer(CommandSender sender, String[] args) {
		StringBuilder stringbuilder = new StringBuilder();
		for (String v : args) {
			stringbuilder.append(v);
			stringbuilder.append(' ');
		}
		String serverName = args[0];
		String serverMotd = stringbuilder.toString().replace(args[0] + " ", "").replace(args[args.length - 1] + " ", "")
				.replace(args[args.length - 2] + " ", "");
		String serverIP = args[args.length - 2];
		int serverPort = Integer.parseInt(args[args.length - 1]);
		InetSocketAddress socketAddress = new InetSocketAddress(serverIP, serverPort);
		ServerInfo si = ProxyServer.getInstance().constructServerInfo(serverName, socketAddress, serverMotd, false);
		ProxyServer.getInstance().getServers().put(serverName, si);

		// Just realized it said don't save in the config, runtime is fine.
		/*
		 * try { // Try adding the server info to the config file. Configuration
		 * bungeeConfig =
		 * ConfigurationProvider.getProvider(YamlConfiguration.class) .load(new
		 * File("config.yml")); bungeeConfig.set("servers." + serverName +
		 * ".address", serverIP + ":" + serverPort); bungeeConfig.set("servers."
		 * + serverName + ".restricted", false); //TODO: Figure out this new
		 * line issue. Yaml config is not setting the string with double quotes.
		 * bungeeConfig.set("servers." + serverName + ".motd", "\"" + serverMotd
		 * + "\"");
		 * ConfigurationProvider.getProvider(YamlConfiguration.class).save(
		 * bungeeConfig, new File("config.yml")); } catch (Exception e) {
		 * e.printStackTrace(); ProxyServer.getInstance().getLogger().severe(
		 * "Couldn't open Bungee config."); }
		 */
		sender.sendMessage(new ComponentBuilder(ChatColor.GREEN + "Server Created!").create());
		sender.sendMessage(
				new ComponentBuilder(ChatColor.DARK_PURPLE + "Name: " + ChatColor.LIGHT_PURPLE + si.getName())
						.create());
		sender.sendMessage(new ComponentBuilder(ChatColor.DARK_PURPLE + "MOTD: " + ChatColor.LIGHT_PURPLE
				+ ChatColor.translateAlternateColorCodes('&', si.getMotd())).create());
		sender.sendMessage(new ComponentBuilder(
				ChatColor.DARK_PURPLE + "Address: " + ChatColor.LIGHT_PURPLE + si.getAddress().toString()).create());

	}

}
