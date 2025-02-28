package com.amiltech.rentacar.spring_security.config;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
@AllArgsConstructor
@NoArgsConstructor
public class KafkaProducerConfig {

    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> configProps = new HashMap<>();

        // Kafka broker settings
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9094");

        // Serializer settings
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        // Acknowledgement settings
        configProps.put(ProducerConfig.ACKS_CONFIG, "all");  // Wait for all replicas to acknowledge
        configProps.put(ProducerConfig.RETRIES_CONFIG, 3);  // Number of retries

        // Batch and performance settings
        configProps.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);  // Batch size (16KB)
        configProps.put(ProducerConfig.LINGER_MS_CONFIG, 1);  // Linger time before sending batch
        configProps.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);  // Buffer size (32MB)

        // Additional configurations
        configProps.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "snappy");  // Compression
        configProps.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);  // Idempotence for exactly-once delivery
        configProps.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, 30000);  // Request timeout
        configProps.put(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG, 120000);  // Delivery timeout
        configProps.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, 60000);  // Max block time

        return configProps;
    }

    @Bean
    public ProducerFactory<Long, Object> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public KafkaTemplate<Long, Object> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
