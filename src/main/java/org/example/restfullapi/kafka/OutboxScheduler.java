package org.example.restfullapi.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OutboxScheduler {

    private final OutboxRepository outboxRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public OutboxScheduler(OutboxRepository outboxRepository, KafkaTemplate<String, String> kafkaTemplate) {
        this.outboxRepository = outboxRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Scheduled(fixedRate = 5000) // Каждые 5 секунд
    public void sendMessages() {
        List<OutboxMessage> messages = outboxRepository.findBySentFalse();

        for (OutboxMessage message : messages) {
            kafkaTemplate.send("my-topic", message.getMessage());
            message.setSent(true);
            outboxRepository.save(message);
        }
    }
}