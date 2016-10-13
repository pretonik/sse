package com.example;

import java.util.Random;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import lombok.Data;

@Service
@Data
public class SpeakerPublisher {

	private static final int CLIENT_ID = 1;
	private final ApplicationEventPublisher eventPublisher;


	@Scheduled(fixedDelay=5000)
	public void speak() {
		
		eventPublisher.publishEvent(new SpeechEvent(this, randomWord(6), CLIENT_ID));
	}
	
	public  String randomWord(int length) {
	    Random random = new Random();
	    StringBuilder word = new StringBuilder(length);
	    for (int i = 0; i < length; i++) {
	        word.append((char)('a' + random.nextInt(26)));
	    }

	    return word.toString();
	}
}
