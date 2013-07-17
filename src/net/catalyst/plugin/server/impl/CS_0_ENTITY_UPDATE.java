package net.catalyst.plugin.server.impl;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.zip.Inflater;

import org.jboss.netty.buffer.ChannelBuffer;

import net.catalyst.model.Client;
import net.catalyst.net.Packet;
import net.catalyst.plugin.server.ServerPacketHandler;

public class CS_0_ENTITY_UPDATE implements ServerPacketHandler {

	@Override
	public int getID() {
		return 0;
	}

	@Override
	public int getLength(ChannelBuffer buf) {
		if(buf.readableBytes() < 8)
			return -1; // not enough data to read length
		int len = buf.readInt();
		return 8 + len;
	}

	@Override
	public void handlePacket(Client client, Packet p) {
		try {
			int len = p.getBuffer().getInt();
			System.out.println("Buffer Len: " + p.getBuffer().remaining() + " - Packet Len: " + len);
			byte[] data = new byte[len];
			p.getBuffer().get(data);
			byte[] result = decompressByteArray(data);
			ByteBuffer buffer = ByteBuffer.wrap(result);
			buffer.order(ByteOrder.LITTLE_ENDIAN);
			
			System.out.println("X: " + (long)(buffer.getLong() & 0xffffffffL));
			System.out.println("Y: " + (long)(buffer.getLong() & 0xffffffffL));
			System.out.println("Z: " + (long)(buffer.getLong() & 0xffffffffL));
			System.out.println("New Byte Array Decrypted: " + result.length + " TXT: " + new String(result));

		} catch(Exception e) {
			e.printStackTrace();
		}

		//String decoded = new String(data);
		//System.out.println(decoded);

	}

	public byte[] decompressByteArray(byte[] bytes){

		ByteArrayOutputStream baos = null;
		Inflater iflr = new Inflater();
		iflr.setInput(bytes);
		baos = new ByteArrayOutputStream();
		byte[] tmp = new byte[10000];
		try{
			while(!iflr.finished()){
				int size = iflr.inflate(tmp);
				baos.write(tmp, 0, size);
			}
		} catch (Exception ex){

		} finally {
			try{
				if(baos != null) baos.close();
			} catch(Exception ex){}
		}

		return baos.toByteArray();
	}
}



