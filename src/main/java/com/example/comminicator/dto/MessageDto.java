package com.example.comminicator.dto;

import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageDto {

    private Integer id;
    private LocalDateTime timeStamp;
    private Boolean is_read;
    private UserDto user;
    private ChatDto chat;
    private String content;
}
