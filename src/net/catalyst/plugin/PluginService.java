package net.catalyst.plugin;

import java.io.File;
import java.util.ArrayList;

import net.catalyst.plugin.server.ServerPacketHandler;
import net.catalyst.util.Log;


public class PluginService {
	
	private ServerPacketHandler[] packetHandlers = new ServerPacketHandler[Byte.MAX_VALUE * 2];
	
	int number = 50;
	
	public ServerPacketHandler[] getPacketHandlers() {

		return packetHandlers;
	}
	
	public void load() {
		loadPacketHandlers();
		loadCharacterPlugins();
	}
	
	public void loadCharacterPlugins() {
		
	}
	

		
	@SuppressWarnings("unchecked")
	public void loadPacketHandlers() {
		Object[] classes = fetchPlugins("/src/net/catalyst/plugin/server/impl/", "net.catalyst.plugin.server.impl.");
		int count = 0;
		for(Object o : classes) {
			if (o instanceof ServerPacketHandler) {
				count++;
				ServerPacketHandler ph = (ServerPacketHandler) o;
				getPacketHandlers()[ph.getID()] = ph;
			}
		}
		//Log.system("Loaded " + count + " PacketHandlers");
			
	}
	
	public Object[] fetchPlugins(String path, String pkg) {
		try {
			ArrayList<Object> tmp = new ArrayList<Object>();
			for (File f : new File(System.getProperty("user.dir") + path).listFiles()) {
				if (f.isDirectory()) 
					continue;
				
				Class c = Class.forName(pkg + f.getName().replaceAll(".java", ""));
				Object o = c.newInstance();
				tmp.add(o);
			}
			if(tmp.size() > 0)
				return tmp.toArray();
			return null;
		} catch (Exception e) {
			Log.error(e);
			return null;
		}
	}


}
