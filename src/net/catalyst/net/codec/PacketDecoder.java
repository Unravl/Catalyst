package net.catalyst.net.codec;

import net.catalyst.core.Server;
import net.catalyst.net.Packet;
import net.catalyst.plugin.server.ServerPacketHandler;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneDecoder;


public class PacketDecoder extends OneToOneDecoder {

	protected Object decode(ChannelHandlerContext ctx, Channel channel, Object t) {
		try {
			ChannelBuffer msg = (ChannelBuffer)t;
			if (msg.readableBytes() >= 4) {
				msg.markReaderIndex();

				int id = msg.readInt();

				if(id > 100 || id < 0) { // can't be a valid packet
					msg.resetReaderIndex();
					return null;
				}
				ServerPacketHandler ph = Server.getServer().getPluginService().getPacketHandlers()[id];
				if(ph == null) { // packet does not exist
					System.out.println("Unhandled Packet: " + id);
					msg.resetReaderIndex();
					return null;
				}
				if(msg.readableBytes() < ph.getExpectedLength()) { // not enough data for this packet
					msg.resetReaderIndex();
					return null;
				}
				
				byte[] payload = new byte[ph.getExpectedLength()];
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
