package com.example.comminicator.common.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SendMessageRequest {

    private Integer chatId;
    private Integer userId;
    private String content;
}
