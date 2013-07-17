package net.catalyst.plugin.server.impl;

import org.jboss.netty.buffer.ChannelBuffer;

import net.catalyst.model.Client;
import net.catalyst.net.Packet;
import net.catalyst.plugin.server.ServerPacketHandler;

public class CS_10_CHAT_MESSAGE implements ServerPacketHandler {

	@Override
	public int getID() {
		return 10;
	}

	@Override
	public int getLength(ChannelBuffer buf) {
		if(buf.readableBytes() < 8)
			return -1; // not enough data to read length
		int len = buf.readInt() * 2;
		return 8 + len;
	}
	
	@Override
	public void handlePacket(Client client, Packet p) {
		int len = p.getBuffer().getInt();
		byte[] data = new byte[len * 2];
		p.getBuffer().get(data);
		String decoded = new String(data);
		

	}
}
