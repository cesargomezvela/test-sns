package com.example.demo.config.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static com.example.demo.config.SpringCloudAwsConfig.QUEUE_NAME;

@RestController
@RequestMapping
public class SQSController {
	
	private static final Logger LOG = LoggerFactory.getLogger(SQSController.class);
	
	@Autowired
	private QueueMessagingTemplate queueMessagingTemplate;
	
	@PostMapping("/message-processing-queue")
	@ResponseStatus(HttpStatus.OK)
	public void sendMessageToMessageProcessingQueue(@RequestBody MessageToProcess message) {
		LOG.debug("Going to send message {} over SQS", message);
        this.queueMessagingTemplate.convertAndSend(QUEUE_NAME, message);
	}

    @SqsListener("${my.param.name}")
    private void receiveMessage(String message) {
        LOG.debug("Received SQS message {}", message);
        System.out.println("****************************");
        System.out.println("***receive message: "+message);
        System.out.println("****************************");
    }
}
