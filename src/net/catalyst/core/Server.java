package net.catalyst.core;

import java.net.InetSocketAddress;
import java.nio.ByteOrder;
import java.util.concurrent.Executors;

import net.catalyst.model.Client;
import net.catalyst.net.PipelineFactory;
import net.catalyst.plugin.PluginService;
import net.catalyst.util.EntityList;
import net.catalyst.util.Log;
import net.catalyst.util.Settings;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.buffer.HeapChannelBufferFactory;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;


public class Server {

	public static int playersSinceRestart = 0;

	private PluginService pluginService = new PluginService();
	private DataService dataService = new DataService();
	private TaskManager taskManager = new TaskManager();
	private boolean updating = false;

	private static long curMemory;

	public static void main(String[] args) {


		long time = System.nanoTime();
		startMeasure();
		server = new Server();
		server.init();

		Log.system("Catalyst Cube World Server");
		Log.system("Cores: " + Runtime.getRuntime().availableProcessors());
		Log.system("Netty Workers: " + Settings.NETTY_WORKER_COUNT);
		Log.system("Server Threads: " + Settings.SERVER_THREAD_COUNT);
		Log.system("Memory Allocated: " + finishMeasure() + "kb" + " / " + (Runtime.getRuntime().totalMemory() / 1000) + "kb");
		Log.system("Startup Time: " + (System.nanoTime() - time) / 100000 + "ms");
		Log.system(Settings.SERVER_NAME + " is now Online!");

	}



	/**
	 * We start the measurement of how much memory gets used
	 */
	public static void startMeasure() {
		curMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
	}

	/**
	 * We finish the measurement of how much memory gets used Returns the Used
	 * memory (ram) from all the loaded data, in Megabytes
	 */
	public static long finishMeasure() {
		return ((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) - curMemory) / 1000;
	}

	public synchronized int registerClient(Client c) {
		/*if(c.getCharacter() == null) {
			Log.error("WARNING: CHARACTER MISSING @ REGISTRATION");
			return -1;
		}
		for(Client cli : getClients()) {
			if(cli.getCharacter().getName().equalsIgnoreCase(c.getCharacter().getName())) {
				System.out.println("REGISTRATION FAILED: CLIENT ALREADY EXISTED!");
				return -1;
			}
		}*/

		getClients().add(c);
		c.setInitalised(true);
		Log.system("CLIENT HAS BEEN REGISTERED");
		playersSinceRestart++;
		return playersSinceRestart;
	}

	public synchronized boolean unregisterClient(Client c) {
		try {
			if(getClients().contains(c) && c.getCharacter() != null) {
				getClients().remove(c.getIndex());
			}
			return true;
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	protected static Server server = null;

	public static Server getServer() {
		return server;
	}

	public static TaskManager getTaskManager() {
		return getServer().taskManager;
	}


	public void init() {
		initNetwork();
		getPluginService().load();
	}

	public void initNetwork() {
		try {
			ChannelFactory factory = new NioServerSocketChannelFactory(Executors
					.newCachedThreadPool(), Executors.newCachedThreadPool(),
					Settings.NETTY_WORKER_COUNT);

			ServerBootstrap bs = new ServerBootstrap(factory);

			bs.setPipelineFactory(new PipelineFactory());
			bs.setOption("child.tcpNoDelay", true);
			bs.setOption("tcpNoDelay", true);
			bs.setOption("child.keepAlive", true);
			bs.setOption("child.reuseAddress", true);
			bs.setOption("child.bufferFactory", new HeapChannelBufferFactory(ByteOrder.LITTLE_ENDIAN));

			bs.bind(new InetSocketAddress(Settings.LOCAL_IP, Settings.LISTEN_PORT));
		} catch(Exception e) {
			Log.system("Exception listening on " + Settings.LISTEN_PORT + ": " + e.getMessage());
		} 
	}

	public void setPluginService(PluginService pluginService) {
		this.pluginService = pluginService;
	}


	public PluginService getPluginService() {
		return pluginService;
	}


	public void setDataService(DataService dataService) {
		this.dataService = dataService;
	}

	public DataService getDataService() {
		return dataService;
	}

	public EntityList<Client> getClients() {
		return getDataService().getClients();
	}

	public void setTaskManager(TaskManager taskManager) {
		this.taskManager = taskManager;
	}




	public void setUpdating(boolean updating) {
		this.updating = updating;
	}

	public boolean isUpdating() {
		return updating;
	}

}
