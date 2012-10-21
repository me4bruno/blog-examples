package de.bruns.example.spring.scheduling;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class ReceiveMailApp {

	public static void main(String[] args) {
		String[] configLocations = new String[] {"timer-simple.xml", "timer-namespace.xml", "timer-annotation.xml"};
		AbstractApplicationContext appContext = new ClassPathXmlApplicationContext(configLocations);
		appContext.registerShutdownHook();
	}

}
