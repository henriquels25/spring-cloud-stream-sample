package io.henriquels25.cloudstream.demo.flightapi.messaging.utils;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.utils.KafkaTestUtils;

import java.util.Map;
import java.util.UUID;

public class KafkaConsumerUtils {

    public static Consumer<String, String> createConsumer(EmbeddedKafkaBroker embeddedKafka, String topic) {
        Map<String, Object> consumerProps = KafkaTestUtils.consumerProps(UUID.randomUUID().toString(),
                "true", embeddedKafka);
        consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        ConsumerFactory<String, String> cf = new DefaultKafkaConsumerFactory<>(consumerProps);
        Consumer<String, String> consumer = cf.createConsumer();
        embeddedKafka.consumeFromAnEmbeddedTopic(consumer, topic);
        return consumer;
    }

    public static ConsumerRecord<String, String> getNextRecord(Consumer<String, String> consumer, String topic) {
        return KafkaTestUtils.getRecords(consumer, 15000).records(topic).iterator().next();
    }

}
