package net.catalyst.net;

import net.catalyst.core.Server;
import net.catalyst.model.Client;
import net.catalyst.tasks.IncomingDataTask;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.buffer.LittleEndianHeapChannelBuffer;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;


public class ChannelHandler extends SimpleChannelUpstreamHandler {

	@Override
	public void channelOpen(ChannelHandlerContext ctx, ChannelStateEvent e)  throws Exception {
		ctx.setAttachment(new Client(ctx));
	}

	@Override
	public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		Client c = (Client)ctx.getAttachment();
		if(c != null)
			c.disconnect();
	}

	
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)throws Exception {
		Packet msg = (Packet) e.getMessage();
		Client c = (Client)ctx.getAttachment();
	
		if(c != null)
			Server.getTaskManager().assignProcessor(new IncomingDataTask(c, msg));
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
		Client c = (Client)ctx.getAttachment();
		if(c != null) {
			c.disconnect();
		}
		System.out.println("Client Disconnected");
		//e.getCause().printStackTrace();
		//outboundChannel.write(msg);
	}
	
	




}
