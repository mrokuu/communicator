package com.example.comminicator.common.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
public class GroupChatRequest {

    private List<Integer> userIds;
    private String chatName;
    private String chatImage;
}
