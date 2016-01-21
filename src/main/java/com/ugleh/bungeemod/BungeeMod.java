package com.ugleh.bungeemod;

import com.ugleh.bungeemod.commands.*;

import net.md_5.bungee.api.plugin.Plugin;

public class BungeeMod extends Plugin {

	@Override
	public void onEnable() {
		// Registering listeners
		getProxy().getPluginManager().registerListener(this, new BMListener());

		// Registering commands
		getProxy().getPluginManager().registerCommand(this, new CreateCommand());
		// getProxy().getPluginManager().registerCommand(this, new
		// SendCommand());
		getProxy().getPluginManager().registerCommand(this, new MotdCommand());
		getProxy().getPluginManager().registerCommand(this, new KickCommand());
	}
}
