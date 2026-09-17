package core.kafka;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class KafkaClientProducer {
    private static final Logger logger = LoggerFactory.getLogger(KafkaClientProducer.class);
    private final KafkaProducer<String, String> producer;

    public KafkaClientProducer() {
        String bootstrapServers = System.getenv().getOrDefault("KAFKA_BOOTSTRAP_SERVERS", "localhost:9092");

        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        this.producer = new KafkaProducer<>(props);
    }

    public void sendMessage(String topic, String key, String message) {
        logger.info("Sending message to topic '{}': {}", topic, message);
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, message);

        producer.send(record, (metadata, exception) -> {
            if (exception != null) {
                logger.error("Error sending message to Kafka", exception);
            } else {
                logger.info("Message sent successfully to partition {} with offset {}", metadata.partition(), metadata.offset());
            }
        });
    }

    public void close() {
        producer.close();
    }
}