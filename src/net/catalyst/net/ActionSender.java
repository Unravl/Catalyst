package net.catalyst.net;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;

import net.catalyst.model.Client;
import net.catalyst.util.Formula;
import net.catalyst.util.Settings;

public class ActionSender {

	public Client client;

	public ActionSender(Client c) {
		client = c;
	}

	public void sendServerData(int code) {
		try {
			ChannelBuffer buf = ChannelBuffers.buffer(ChannelBuffers.LITTLE_ENDIAN, 16 + 0x1168);
			buf.writeInt(code); // id
			buf.writeInt(0); // accept
			buf.writeLong(client.getIndex()); // player id
			byte[] b = new byte[0x1168]; // da fuq
			buf.writeBytes(b);
			client.getChannelHandlerContext().getChannel().write(buf.slice());
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void sendSeed() {
		try {
			ChannelBuffer buf = ChannelBuffers.buffer(ChannelBuffers.LITTLE_ENDIAN, 8);
			buf.writeInt(15); // id
			buf.writeInt(Settings.SEED & 0xffffffff); // seed
			
			client.getChannelHandlerContext().getChannel().write(buf.slice());
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void sendMessage(String string) {
		try {
			ChannelBuffer buf = ChannelBuffers.buffer(ChannelBuffers.LITTLE_ENDIAN, 1024);
			buf.writeInt(10); // packet id
			buf.writeLong(0); // player id
			byte[] data = string.getBytes("UTF-16LE");
			buf.writeInt(data.length / 2);
			buf.writeBytes(data);
			client.getChannelHandlerContext().getChannel().write(buf.slice()); // slice the buffer to the used amount of bytes
		} catch(Exception e) {
			e.printStackTrace();
		}

	}
	
	public void sendMessageFromEntity(String string, int id) {
		try {
			ChannelBuffer buf = ChannelBuffers.buffer(ChannelBuffers.LITTLE_ENDIAN, 1024);
			buf.writeInt(10); // packet id
			buf.writeLong(id); // player id
			byte[] data = string.getBytes("UTF-16LE");
			buf.writeInt(data.length / 2);
			buf.writeBytes(data);
			client.getChannelHandlerContext().getChannel().write(buf.slice()); // slice the buffer to the used amount of bytes
		} catch(Exception e) {
			e.printStackTrace();
		}

	}

}
