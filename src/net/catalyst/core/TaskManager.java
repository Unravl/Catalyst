package net.catalyst.core;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import net.catalyst.util.Settings;


public class TaskManager {

	private ScheduledExecutorService eService = Executors.newScheduledThreadPool(Settings.SERVER_THREAD_COUNT);

	public void assignProcessor(Runnable r) {
		eService.submit(r);
	}

	public void scheduledProcessor(Runnable r, int time) {
		eService.schedule(r, time, TimeUnit.MILLISECONDS);
	}

	public void checkAndBlockThread() {
		boolean going = false;
		if(Server.getServer().isUpdating()) {
			while(Server.getServer().isUpdating()) {
				if(going != true) {
					System.out.println("Waiting for update to finish! " + Thread.currentThread().getName());
					going = true;
				}
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			if(going)
				System.out.println("Update Finished " + Thread.currentThread().getName());
		}
	}

}
