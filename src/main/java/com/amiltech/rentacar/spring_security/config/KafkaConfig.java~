package com.amiltech.rentacar.spring_security.config;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import java.util.Map;

@Configuration
public class KafkaConfig {
    @Bean
    NewTopic newTopics() {
        return TopicBuilder
                .name("user-registered-events-topic")
                .partitions(3)
                .replicas(1)
                .configs(Map.of("min.sync.replicas", "1"))
                .build();
    }
}
