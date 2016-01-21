package com.ugleh.bungeemod.commands;

import com.ugleh.bungeemod.BMListener;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.plugin.Command;

public class MotdCommand extends Command {

	public MotdCommand() {
		super("setmotd", "bungeemod.motd");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (args.length == 0) {
			sender.sendMessage(
					new ComponentBuilder("Invalid arguments. Try /motd <message>").color(ChatColor.RED).create());
			return;
		}
		StringBuilder stringbuilder = new StringBuilder();
		for (String v : args) {
			stringbuilder.append(v);
			stringbuilder.append(' ');
		}
		BMListener.setMOTD(ChatColor.translateAlternateColorCodes('&', stringbuilder.toString()));
	}

}
