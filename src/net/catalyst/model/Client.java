package net.catalyst.model;

import net.catalyst.core.Server;
import net.catalyst.net.ActionSender;
import net.catalyst.net.PacketBuilder;
import net.catalyst.util.Log;
import net.catalyst.util.Settings;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;


public class Client extends Entity {

	private Character character;
	private boolean initalised;
	private boolean changingAppearance = false;
	private ActionSender actionSender = new ActionSender(this);
	private long lastPing = System.currentTimeMillis();

	//private List<Packet> packets = Collections.synchronizedList(new ArrayList<Packet>());

	private ChannelHandlerContext channelHandlerContext;

	public void disconnect() {
		if(isInitalised())
			Server.getServer().unregisterClient(this);
		this.getChannelHandlerContext().getChannel().disconnect();

	}

	public void load() {
		setIndex(Server.getServer().registerClient(this));
	}
	
	public ActionSender getActionSender() {
		return actionSender;
	}

	public void bindCharacter(Character c) {
		setCharacter(c);
	}

	public Client(ChannelHandlerContext ctx) {
		this.channelHandlerContext = ctx;
	}


	public ChannelHandlerContext getChannelHandlerContext() {
		return channelHandlerContext;
	}

	public void setCharacter(Character character) {
		this.character = character;
	}

	public Character getCharacter() {
		return character;
	}

	public void setInitalised(boolean initalised) {
		this.initalised = initalised;
	}

	public boolean isInitalised() {
		return initalised;
	}

	public void setLastPing(long lastPing) {
		this.lastPing = lastPing;
	}

	public long getLastPing() {
		return lastPing;
	}

	public void setChangingAppearance(boolean changingAppearance) {
		this.changingAppearance = changingAppearance;
	}

	public boolean isChangingAppearance() {
		return changingAppearance;
	}


}
