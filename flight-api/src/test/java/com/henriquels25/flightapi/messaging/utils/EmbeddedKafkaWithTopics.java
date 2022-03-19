package com.henriquels25.flightapi.messaging.utils;

import org.springframework.kafka.test.context.EmbeddedKafka;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@EmbeddedKafka(topics = {"plane-arrived-v1",
        "flight-arrived-v1", "plane-arrived-dlq-v1", "flight-arrived-dlq-v1", "flight-finished-v1"},
        bootstrapServersProperty = "spring.cloud.stream.kafka.binder.brokers",
        partitions = 1)
public @interface EmbeddedKafkaWithTopics {
}
