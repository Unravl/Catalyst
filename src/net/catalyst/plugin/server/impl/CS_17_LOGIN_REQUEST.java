package net.catalyst.plugin.server.impl;

import org.jboss.netty.buffer.ChannelBuffer;

import net.catalyst.core.Server;
import net.catalyst.model.Client;
import net.catalyst.net.Packet;
import net.catalyst.plugin.server.ServerPacketHandler;
import net.catalyst.tasks.DelayedEventTask;
import net.catalyst.util.Settings;

public class CS_17_LOGIN_REQUEST implements ServerPacketHandler {

	@Override
	public int getID() {
		return 17;
	}

	@Override
	public int getLength(ChannelBuffer buf) {
		return 4;
	}

	@Override
	public void handlePacket(final Client client, Packet p) {
		int server_ver = p.getBuffer().getInt();
		if(server_ver != Settings.SERVER_VERSION) {
			client.getActionSender().sendServerData(17); // incorrect client version
			client.disconnect();
			return;
		} else if(Server.getServer().getClients().count() >= Settings.MAX_PLAYERS) {
			client.getActionSender().sendServerData(18); // server full
			client.disconnect();
			return;
		}
		System.out.println("LEN: " + p.getLength());
		client.load();
		System.out.println("Player: " + client.getIndex() + " has connected");
		client.getActionSender().sendServerData(16); // success!
		client.getActionSender().sendSeed();
		client.getActionSender().sendMessage("Welcome to Catalyst Server Emulator");

		new DelayedEventTask(client, 1000) {
			@Override
			public void execute() {
				client.getActionSender().sendMessage("Players Online: " + Server.getServer().getClients().count());
			}
		};


	}

}
