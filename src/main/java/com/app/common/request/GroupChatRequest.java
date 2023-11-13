package com.app.common.request;

import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GroupChatRequest {
	
	private List<Integer> userIds;
	private String chat_name;
	private String chat_image;
	

}
