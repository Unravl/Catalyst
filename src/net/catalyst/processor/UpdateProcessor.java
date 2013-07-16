package net.catalyst.processor;

import java.util.Iterator;

import net.catalyst.core.Server;
import net.catalyst.model.Client;
import net.catalyst.util.EntityList;


public class UpdateProcessor implements Runnable {
	
	Server server;
	
	public UpdateProcessor(Server s) {
		server = s;
	}

	@Override
	public void run() {
		server.setUpdating(true);
		EntityList<Client> clients = server.getDataService().getClients();
		Iterator<Client> ite = clients.iterator();
		while(ite.hasNext()) {
			//Client c = ite.next();
			//c.getActionSender().updatePosition();
			//c.getActionSender().playerUpdate();

		}
		server.setUpdating(false);
		Server.getTaskManager().scheduledProcessor(this, 600);
	}

}
