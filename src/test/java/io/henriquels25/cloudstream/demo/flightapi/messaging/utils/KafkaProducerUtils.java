package io.henriquels25.cloudstream.demo.flightapi.messaging.utils;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.utils.KafkaTestUtils;

import java.util.HashMap;
import java.util.Map;

public class KafkaProducerUtils {

    public static Producer<String, String> createProducer(EmbeddedKafkaBroker embeddedKafka) {
        Map<String, Object> configs = new HashMap<>(KafkaTestUtils.producerProps(embeddedKafka));
        return new DefaultKafkaProducerFactory<>(configs, new StringSerializer(),
                new StringSerializer()).createProducer();
    }

}
