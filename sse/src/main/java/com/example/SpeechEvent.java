package com.example;

import org.springframework.context.ApplicationEvent;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of={"listenerId"},callSuper=false)
public class SpeechEvent extends ApplicationEvent{

	 /**
	 * 
	 */
	private static final long serialVersionUID = -8816184314176294891L;
	
	private final String message;
	private final long listenerId;
	 
	public SpeechEvent(Object source, String message, long listenerId) {
		super(source);
        this.message = message;
        this.listenerId = listenerId;
	}

}
