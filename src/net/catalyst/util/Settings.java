package net.catalyst.util;

public class Settings {
	
	public static final int LISTEN_PORT = 12345;
	public static final String LOCAL_IP = "localhost";
	
	public static final String SERVER_NAME = "Catalyst Cube World Server";
	
	public static final int NETTY_WORKER_COUNT = Runtime.getRuntime().availableProcessors() * 2;
	public static final int SERVER_THREAD_COUNT = Runtime.getRuntime().availableProcessors() * 2;
	
	public static final int MAX_PLAYERS = 4;
	public static final int SERVER_VERSION = 3;
	

}
