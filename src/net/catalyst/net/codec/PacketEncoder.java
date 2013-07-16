package net.catalyst.net.codec;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;




public class PacketEncoder extends OneToOneEncoder {

	@Override
	protected Object encode(ChannelHandlerContext arg0, Channel arg1, Object arg2) throws Exception {

		/*Packet p = (Packet)arg2;
		byte[] data = p.getData();
		int dataLength = data.length;
		ByteBuffer buffer;



		buffer = ByteBuffer.allocate(dataLength + 4);
		buffer.putInt(p.getID());

		buffer.put(data, 0, dataLength);
		buffer.flip();
		return new ByteBufferBackedChannelBuffer(buffer);*/
		
		// ========== NOT IN USE FOR NOW ============
		return null;
	}


}
