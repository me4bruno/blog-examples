package de.bruns.example.spring.scheduling.simple;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.scheduling.TaskScheduler;

import de.bruns.example.spring.scheduling.ReceiveMailRunnable;

public class ReceiveMailSimpleService implements InitializingBean {

	private final TaskScheduler taskScheduler;

	public ReceiveMailSimpleService(TaskScheduler taskScheduler) {
		this.taskScheduler = taskScheduler;
	}

	public void afterPropertiesSet() {
		ReceiveMailRunnable runnable = new ReceiveMailRunnable(getClass().getSimpleName());
		this.taskScheduler.scheduleAtFixedRate(runnable, 1000);
	}
}
