package de.bruns.example.spring.scheduling;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class ReceiveMailRunnable implements Runnable {
	
	private final String clazz;

	public ReceiveMailRunnable(String clazz) {
		this.clazz = clazz;
	}

	public void run() {
		execute(clazz);
	}

	public static void execute(String clazz) {
		DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM, Locale.GERMAN);	
		System.out.println(clazz + ": " + formatter.format(new Date()));
	}
}
