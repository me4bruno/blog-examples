package de.bruns.example.spring.scheduling.annotation;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import de.bruns.example.spring.scheduling.ReceiveMailRunnable;

@Service
public class ReceiveMailAnnotationService {

	@Scheduled(fixedRate = 3000)
	public void receiveMailFixedRate() {
		ReceiveMailRunnable.execute(getClass().getSimpleName() + "-FixedRate");
	}

	@Scheduled(cron="*/5 * * * * *")
	public void receiveMailCron() {
		ReceiveMailRunnable.execute(getClass().getSimpleName() + "-TimeCron");
	}
}
