package com.devsouzx.accounts.configuration.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {
    @Bean
    public NewTopic registrationTopic() {
        return TopicBuilder
                .name("letterboxdclone-new-register")
                .build();
    }

    @Bean
    public NewTopic passwordResetTopic() {
        return TopicBuilder
                .name("letterboxdclone-password-reset")
                .build();
    }
}
