package net.catalyst.net;

import net.catalyst.net.codec.PacketDecoder;
import net.catalyst.net.codec.PacketEncoder;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;


public class PipelineFactory implements ChannelPipelineFactory {

	public PipelineFactory() {
		
	}

	@Override
	public ChannelPipeline getPipeline() throws Exception {
		ChannelPipeline pip = Channels.pipeline();
		pip.addLast("decoder", new PacketDecoder());
		//pip.addLast("encoder", new PacketEncoder());
		pip.addLast("handler", new ChannelHandler());
		return pip;
	}

}
