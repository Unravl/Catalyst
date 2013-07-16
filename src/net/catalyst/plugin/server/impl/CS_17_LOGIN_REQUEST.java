package net.catalyst.plugin.server.impl;

import net.catalyst.core.Server;
import net.catalyst.model.Client;
import net.catalyst.net.Packet;
import net.catalyst.plugin.server.ServerPacketHandler;
import net.catalyst.util.Settings;

public class CS_17_LOGIN_REQUEST implements ServerPacketHandler {

	@Override
	public int getID() {
		return 17;
	}
	
	@Override
	public int getExpectedLength() {
		return 4;
	}

	@Override
	public void handlePacket(Client client, Packet p) {
		System.out.println("Player: " + client.getIndex() + " has connected");
		int server_ver = p.getBuffer().getInt();
		if(server_ver != Settings.SERVER_VERSION) {
			client.getActionSender().sendServerData(17); // incorrect client version
			client.destroy();
			return;
		} else if(Server.getServer().getClients().count() >= Settings.MAX_PLAYERS) {
			client.getActionSender().sendServerData(18); // server full
			client.destroy();
			return;
		}
		client.getActionSender().sendServerData(16); // success!
	}

}
