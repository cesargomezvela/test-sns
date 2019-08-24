package com.example.demo.config.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.aws.messaging.config.annotation.NotificationMessage;
import org.springframework.cloud.aws.messaging.config.annotation.NotificationSubject;
import org.springframework.cloud.aws.messaging.endpoint.NotificationStatus;
import org.springframework.cloud.aws.messaging.endpoint.annotation.NotificationMessageMapping;
import org.springframework.cloud.aws.messaging.endpoint.annotation.NotificationSubscriptionMapping;
import org.springframework.cloud.aws.messaging.endpoint.annotation.NotificationUnsubscribeConfirmationMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/sns/receive")
public class SNSEndpointReciever {
	private static final Logger logger = LoggerFactory.getLogger(SNSEndpointReciever.class);

	@NotificationSubscriptionMapping
	public void handleSubscriptionMessage(NotificationStatus status) {
		//We subscribe to start receive the message
		logger.info("Entra: handleSubscriptionMessage");
		System.out.println("Entra: handleSubscriptionMessage");
		status.confirmSubscription();
	}

	@NotificationMessageMapping
	public void handleNotificationMessage(@NotificationSubject String subject, @NotificationMessage String message) {
		// ...
		logger.info("Entra: handleNotificationMessage");
		System.out.println("Entra: handleNotificationMessage");
		System.out.println("Entra: "+subject+","+message);
	}

	@NotificationUnsubscribeConfirmationMapping
	public void handleUnsubscribeMessage(NotificationStatus status) {
		//e.g. the client has been unsubscribed and we want to "re-subscribe"
		logger.info("Entra: handleUnsubscribeMessage");
		System.out.println("Entra: handleUnsubscribeMessage");
		status.confirmSubscription();
	}
}
