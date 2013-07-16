package net.catalyst.core;


import net.catalyst.model.Client;
import net.catalyst.util.EntityList;
import net.catalyst.util.Settings;


public class DataService { 
	
	private EntityList<Client> players = new EntityList<Client>(Settings.MAX_PLAYERS);

	public void setPlayers(EntityList<Client> players) {
		this.players = players;
	}

	public EntityList<Client> getClients() {
		return players;
	}

}
