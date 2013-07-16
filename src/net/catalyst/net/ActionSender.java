package net.catalyst.net;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;

import net.catalyst.model.Client;
import net.catalyst.util.Formula;

public class ActionSender {
	
	public Client client;
	
	public ActionSender(Client c) {
		client = c;
	}
	
	public void sendServerData(int code) {
		ChannelBuffer buf = ChannelBuffers.buffer(ChannelBuffers.LITTLE_ENDIAN, 16 + 0x1168);
		buf.writeInt(code); // id
		buf.writeInt(0); // accept
		buf.writeLong(663); // player id
		byte[] b = new byte[0x1168]; // da fuq
		buf.writeBytes(b);
		client.getChannelHandlerContext().getChannel().write(buf);
	}

}
