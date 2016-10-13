package com.example;

import java.io.IOException;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import lombok.Data;

@RestController
@Data
public class RegisterController {
	
	private final int CLIENT_ID = 1;
	private final SpeechListener speechListener;
	

	@RequestMapping("/listen")
	SseEmitter listen() throws IOException {
		final SseEmitter sseEmitter = new SseEmitter();
		
		sseEmitter.send("hello, start to speak");
		speechListener.addSseEmitters(CLIENT_ID, sseEmitter);
		
		sseEmitter.onCompletion(() -> speechListener.remove(CLIENT_ID));
		    
		return sseEmitter;
		
	}
}
