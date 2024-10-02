//package org.example.restfullapi.kafka;
//import org.apache.kafka.clients.admin.NewTopic;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.config.TopicBuilder;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.kafka.core.ProducerFactory;
//import org.springframework.kafka.core.DefaultKafkaProducerFactory;
//import org.apache.kafka.clients.producer.ProducerConfig;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Configuration
//public class KafkaProducerConfig {
//
//    @Value("${spring.kafka.producer.bootstrap-servers}")
//    private String bootstrapServers;
//
//    @Value("${spring.kafka.producer.key-serializer}")
//    private String keySerializer;
//
//    @Value("${spring.kafka.producer.value-serializer}")
//    private String valueSerializer;
//
//    Map<String,Object> producerConfig () {
//        Map<String, Object> props = new HashMap<>();
//        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
//        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, keySerializer);
//        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, valueSerializer);
//        return props;
//    }
//
//    @Bean
//    public KafkaTemplate<String, String> kafkaTemplate() {
//        return new KafkaTemplate<>(producerFactory());
//    }
//
//
//
//    @Bean
//    public NewTopic dlqTopic() {
//        return TopicBuilder.name("my-dlq-topic")
//                .partitions(1)
//                .replicas(1)
//                .build();
//    }
//
//    @Bean
//    public ProducerFactory<String, String> producerFactory() {
//        return new DefaultKafkaProducerFactory<>(producerConfig());
//    }
//}