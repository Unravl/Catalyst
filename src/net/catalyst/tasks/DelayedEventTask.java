package net.catalyst.tasks;

import net.catalyst.core.Server;
import net.catalyst.model.Client;

public abstract class DelayedEventTask implements Runnable {
	
	public DelayedEventTask(Client c, int ms, int loops) {
		this.setClient(c);
		this.setTime(ms);
		this.setLoops(loops);
		Server.getTaskManager().scheduledProcessor(this, time);
	}
	
	public DelayedEventTask(Client c, int ms) {
		this(c, ms, 1);
	}
	
	private Client client;
	private int time, loops = 1;

	@Override
	public void run() {
		loops--;
		execute();
		if(loops > 0)
			Server.getTaskManager().scheduledProcessor(this, time);
	}
	
	public abstract void execute();

	public void setClient(Client client) {
		this.client = client;
	}

	public Client getClient() {
		return client;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public int getTime() {
		return time;
	}

	public void setLoops(int loops) {
		this.loops = loops;
	}

	public int getLoops() {
		return loops;
	}

}
