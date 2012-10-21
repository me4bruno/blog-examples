package de.bruns.example.spring.scheduling.namespace;

import de.bruns.example.spring.scheduling.ReceiveMailRunnable;

public class ReceiveMailNamespaceService {

	public void receiveMailFixedRate() {
		ReceiveMailRunnable.execute(getClass().getSimpleName() + "-FixedRate");
	}

	public void receiveMailCron() {
		ReceiveMailRunnable.execute(getClass().getSimpleName() + "-TimeCron");
	}

}
