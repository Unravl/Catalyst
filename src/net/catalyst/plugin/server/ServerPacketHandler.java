package net.catalyst.plugin.server;

import net.catalyst.model.Client;
import net.catalyst.net.Packet;


public interface ServerPacketHandler {
	
	public int getID();
	
	public void handlePacket(Client client, Packet p);

	int getExpectedLength();

}
