package net.catalyst.plugin.io;

import net.catalyst.model.Character;

public interface PlayerLoader {
	
	public boolean pluginActive();
	
	public Character fetchCharacter(String user, String pass);
	
	public void saveCharacter(Character c);

}
