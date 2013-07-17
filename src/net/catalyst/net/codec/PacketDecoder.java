package net.catalyst.net.codec;

import net.catalyst.core.Server;
import net.catalyst.net.Packet;
import net.catalyst.plugin.server.ServerPacketHandler;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;


public class PacketDecoder extends FrameDecoder {

	protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer msg) {
		try {

			if (msg.readableBytes() >= 4) {
				msg.markReaderIndex();

				int id = msg.readInt();
				
				if(id > 100 || id < 0) { // can't be a valid packet
					msg.resetReaderIndex();
					return null;
				}
				ServerPacketHandler ph = Server.getServer().getPluginService().getPacketHandlers()[id];
				if(ph == null) { // packet does not exist
					if(id != 11)
						System.out.println("Unhandled Packet: " + id + " BufferLen: " + msg.readableBytes());
					msg.resetReaderIndex();
					return null;
				}
				int length = ph.getLength(msg);
				if(length == -1 || msg.readableBytes() < length) { // not enough data for this packet
					msg.resetReaderIndex();
					return null;
				}
				
				msg.resetReaderIndex();
				msg.skipBytes(4);
				byte[] payload = new byte[length];
				msg.readBytes(payload); // read remaining data
				
				Packet p = new Packet(ctx, payload, id);
				return p;
			} else {
				msg.resetReaderIndex();
				return null;
			}

		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}



}
