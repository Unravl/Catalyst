package net.catalyst.util;

public class Log {
	
	public static void system(String s) {
		System.out.println("[SYSTEM] " + s);
	}
		
	public static void error(String s) {
		System.out.println("[Error=" + Thread.currentThread().getName() + "]: " + s);
	}

	public static void error(Exception e) {
		System.out.println(Thread.currentThread().getName() + ": " + e.getMessage());
		e.printStackTrace();
	}

}
