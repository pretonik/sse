package com.example;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Component
public class SpeechListener {
	
	private static Logger logger = org.slf4j.LoggerFactory.getLogger(SpeechListener.class);
	
	/**
     * The list of the objects of SseEmitter.
     * The key of the map stands for submissionId.
     * The value of the map is the corresponding SseEmitter object.
     */
    private static Map<Long, SseEmitter> sseEmitters = new Hashtable<Long, SseEmitter>();

	@EventListener
	public void submissionEventHandler(SpeechEvent event) {
		long listenerId = event.getListenerId();
		String message = event.getMessage();
		SseEmitter sseEmitter = sseEmitters.get(listenerId);

		if (sseEmitter == null) {
			logger.warn(String.format("CANNOT get the SseEmitter for listener #%d.", listenerId));
			return;
		}
		 try {
			 sseEmitter.send(message);
		 } catch (IOException e) {
			 sseEmitter.complete();
             sseEmitters.remove(listenerId);
             logger.warn(e.getMessage());
         }
	}

	public void addSseEmitters(long submissionId, SseEmitter sseEmitter) {
		sseEmitters.put(submissionId, sseEmitter);
	}

	public void remove(Integer listenerId) {
		sseEmitters.remove(listenerId);
	}
}
