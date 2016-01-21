package com.ugleh.bungeemod;

import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class BMListener implements Listener {
	public static String MOTD;

	@EventHandler
	public void onPing(ProxyPingEvent ev) {
		if (MOTD != null) {
			ServerPing newPing = new ServerPing();
			newPing.setDescription(MOTD);
			ev.setResponse(newPing);

		}
	}

	public String getMOTD() {
		return MOTD;
	}

	public static void setMOTD(String mOTD) {
		MOTD = mOTD;
	}
}
