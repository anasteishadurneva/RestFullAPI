package org.example.restfullapi.kafka;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "outbox")
public class OutboxMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    private boolean sent;

}