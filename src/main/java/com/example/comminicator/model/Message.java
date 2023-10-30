package com.example.comminicator.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;

    private LocalDateTime timeStamp;
    private Boolean is_read;

    @ManyToOne
    private User user;

    @ManyToOne
    @JoinColumn(name="chat_id")
    private Chat chat;
}
