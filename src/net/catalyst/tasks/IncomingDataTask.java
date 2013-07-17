package net.catalyst.tasks;

import net.catalyst.core.Server;
import net.catalyst.model.Client;
import net.catalyst.net.Packet;
import net.catalyst.plugin.server.ServerPacketHandler;


public class IncomingDataTask implements Runnable {

	private Packet packet;
	private Client client;

	public IncomingDataTask(Client c, Packet p) {
		this.setPacket(p);
		this.setClient(c);
	}

	public void setClient(Client client) {
		this.client = client;
	}


	public Client getClient() {
		return client;
	}

	public void setPacket(Packet packet) {
		this.packet = packet;
	}

	public Packet getPacket() {
		return packet;
	}

	@Override
	public void run() {

		Server.getTaskManager().checkAndBlockThread();

		ServerPacketHandler handler = Server.getServer().getPluginService().getPacketHandlers()[getPacket().getID()];
		if(handler == null) {
			System.out.println("Unhandled Packet: " + getPacket().getID());
			return;
		}
		try {
			handler.handlePacket(client, packet);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
