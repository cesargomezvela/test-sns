package com.example.demo.config;

import org.springframework.cloud.aws.core.env.ResourceIdResolver;
import org.springframework.cloud.aws.messaging.core.NotificationMessagingTemplate;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSAsync;

@Configuration
public class SpringCloudAwsConfig {
	
	@Bean
    public QueueMessagingTemplate queueMessagingTemplate(AmazonSQSAsync amazonSqsAsync, ResourceIdResolver resourceIdResolver) {
        return new QueueMessagingTemplate(amazonSqsAsync, resourceIdResolver);
    }
	
	@Bean
    public NotificationMessagingTemplate notificationMessagingTemplate(AmazonSNS amazonSNS, ResourceIdResolver resourceIdResolver) {
        return new NotificationMessagingTemplate(amazonSNS, resourceIdResolver);
    }
}
