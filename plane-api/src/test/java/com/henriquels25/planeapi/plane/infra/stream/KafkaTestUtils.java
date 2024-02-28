package com.henriquels25.planeapi.plane.infra.stream;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.test.EmbeddedKafkaBroker;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class KafkaTestUtils {

    private final EmbeddedKafkaBroker broker;

    public KafkaTestUtils(EmbeddedKafkaBroker broker) {
        this.broker = broker;
    }

    public Producer<String, String> createProducer() {
        Map<String, Object> configs = new HashMap<>(org.springframework.kafka.test.utils.KafkaTestUtils.producerProps(broker));
        return new DefaultKafkaProducerFactory<>(configs, new StringSerializer(),
                new StringSerializer()).createProducer();
    }

    public Consumer<String, String> createConsumer(String topic) {
        Map<String, Object> consumerProps = org.springframework.kafka.test.utils.KafkaTestUtils
                .consumerProps(UUID.randomUUID().toString(),
                        "true", broker);
        consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        ConsumerFactory<String, String> cf = new DefaultKafkaConsumerFactory<>(consumerProps,
                new StringDeserializer(), new StringDeserializer());
        Consumer<String, String> consumer = cf.createConsumer();
        broker.consumeFromAnEmbeddedTopic(consumer, topic);
        return consumer;
    }

    public ConsumerRecord<String, String> getLastRecord(Consumer<String, String> consumer, String topic) {
        return com.google.common.collect.Iterables.getLast(org.springframework.kafka.test.utils.KafkaTestUtils
                        .getRecords(consumer, Duration.ofSeconds(15)).records(topic));
    }

    public void sendMessage(String topic, String json) {
        Producer<String, String> producer = createProducer();

        producer.send(new ProducerRecord<>(topic, json));
    }

}
