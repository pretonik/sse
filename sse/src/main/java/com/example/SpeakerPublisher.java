package com.example;

import java.util.Random;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Service;

import lombok.Data;

@Service("speakerPublisher")
@Data
public class SpeakerPublisher {

	private static final int CLIENT_ID = 1;
	private final ApplicationEventPublisher eventPublisher;

	@ServiceActivator(inputChannel="outputChannel")
	public void speak(String s) {
		
		eventPublisher.publishEvent(new SpeechEvent(this, s, CLIENT_ID));
	}
	
	public  String randomWord() {
	    Random random = new Random();
	    int length = 6;;
		StringBuilder word = new StringBuilder( );
	    for (int i = 0; i < length; i++) {
	        word.append((char)('a' + random.nextInt(26)));
	    }

	    return word.toString();
	}
}
