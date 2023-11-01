package com.hrs.customerservice.messaging;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.hrs.customerservice.models.CustomerDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TopicProducer {

	@Value("${producer.config.topic.name}")
	private String topicName;

	private final KafkaTemplate<String, CustomerDto> kafkaTemplate;

	public void send(CustomerDto customer) {
		log.info("Registered Customer : {}", customer.toString());
		kafkaTemplate.send(topicName, customer);
	}

}