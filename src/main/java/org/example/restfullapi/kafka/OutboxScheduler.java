package org.example.restfullapi.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Slf4j
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
            int attempt = 0;
            boolean sentSuccess = false;

            while (attempt < 3 && !sentSuccess) {
                try {
                    kafkaTemplate.send("my-topic", message.getMessage()).get();
                    message.setSent(true);
                    outboxRepository.save(message);
                    sentSuccess = true;
                } catch (InterruptedException | ExecutionException e) {
                    attempt++;
                    log.error("Ошибка отправки сообщения в Kafka: {} - {}", message.getMessage(), e.getMessage());
//                    if (attempt >= 3) {
//                        sendToDLQ(message);
//                    }
                }
            }

        }
    }



//    private void sendToDLQ(OutboxMessage message) {
//        try {
//            kafkaTemplate.send("my-dlq-topic", message.getMessage()).get();
//            log.info("Сообщение успешно отправлено в DLQ: {}", message.getMessage());
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//            log.error("Ошибка отправки сообщения в DLQ (прерывание): {}", message.getMessage());
//        } catch (java.util.concurrent.ExecutionException e) {
//            log.error("Ошибка отправки сообщения в DLQ: {} - {}", message.getMessage(), e.getMessage());
//        }
//    }
}