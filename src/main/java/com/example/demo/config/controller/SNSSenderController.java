package com.example.demo.config.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.core.NotificationMessagingTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SNSSenderController {
	private final NotificationMessagingTemplate notificationMessagingTemplate;

    @Autowired
    public SNSSenderController(NotificationMessagingTemplate notificationMessagingTemplate) {
        this.notificationMessagingTemplate = notificationMessagingTemplate;
    }
	
	@PostMapping(value = "/sns/send", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	public String message(@RequestParam String subject, @RequestParam String msg) {
		this.notificationMessagingTemplate.sendNotification("SnsTopic", msg, subject);
		return "{\"message\":\"sent\"}";
	}
}
