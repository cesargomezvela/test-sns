package com.example.demo.config;

import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.core.env.ResourceIdResolver;
import org.springframework.cloud.aws.messaging.core.NotificationMessagingTemplate;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.util.Topics;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.model.CreateQueueResult;
import com.amazonaws.services.sqs.model.GetQueueUrlResult;

@Configuration
public class SpringCloudAwsConfig {
	
	@Autowired
	private AmazonSQSAsync amazonSqsAsync;
	
	@Autowired
	private AmazonSNS amazonSNS;
	
	@Autowired
	private ResourceIdResolver resourceIdResolver;
	
	private String subscriptionArn;
	public static final String QUEUE_NAME = "cibp-busrefresh-queue-"+UUID.randomUUID().toString();
	
	@Bean
    public QueueMessagingTemplate queueMessagingTemplate(AmazonSQSAsync amazonSqsAsync, ResourceIdResolver resourceIdResolver) {
        return new QueueMessagingTemplate(amazonSqsAsync, resourceIdResolver);
    }
	
	@Bean
    public NotificationMessagingTemplate notificationMessagingTemplate(AmazonSNS amazonSNS, ResourceIdResolver resourceIdResolver) {
        return new NotificationMessagingTemplate(amazonSNS, resourceIdResolver);
    }
	
	@PostConstruct
	public void configure(  ) {
		CreateQueueResult queueCreateResult = amazonSqsAsync.createQueue(QUEUE_NAME);
		String snsTopicArn = resourceIdResolver.resolveToPhysicalResourceId("SnsTopic");
		this.subscriptionArn = Topics.subscribeQueue(amazonSNS, amazonSqsAsync, snsTopicArn, queueCreateResult.getQueueUrl());
		System.setProperty("my.param.name", QUEUE_NAME);
	}
	
	@PreDestroy
    public void onDestroy(  ){
		this.amazonSNS.unsubscribe(subscriptionArn);
		GetQueueUrlResult queueUrlResult = this.amazonSqsAsync.getQueueUrl(QUEUE_NAME);
		this.amazonSqsAsync.deleteQueue(queueUrlResult.getQueueUrl());
    }
}
