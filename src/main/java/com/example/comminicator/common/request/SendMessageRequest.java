package com.example.comminicator.common.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SendMessageRequest {

    private Long chatId;
    private Long userId;
    private String content;
}
