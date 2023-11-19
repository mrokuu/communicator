package com.app.common.request;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SendMessageRequest {
	
	private Long chatId;
	private Long userId;
	private String content;
	


}
