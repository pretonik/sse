package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.core.Pollers;
import org.springframework.integration.endpoint.MethodInvokingMessageSource;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@IntegrationComponentScan
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class SseApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(SseApplication.class, args);
	}
	
	@Bean
	public MessageSource<?> randomStringMessageSource(SpeakerPublisher publisher){
		MethodInvokingMessageSource source = new MethodInvokingMessageSource();
		source.setObject(publisher);
		source.setMethodName("randomWord");
		return source;
		
	}
	
	@Bean
	DirectChannel inputChannel(){
		return new DirectChannel();
	}
	
	@Bean 
	DirectChannel outputChannel(){
		return new DirectChannel();
	}
	
	@Bean 
	IntegrationFlow myFlow(SpeakerPublisher publisher){
		return IntegrationFlows.from(this.randomStringMessageSource(publisher),c -> c.poller(Pollers.fixedDelay(5000)))
				.channel(inputChannel())
				.transform(p -> p.toString().toUpperCase())
				.channel(outputChannel())
				.get();
	}
	
}
