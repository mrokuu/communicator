package com.app.request;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SendMessageRequest {
	
	private Integer chatId;
	private Integer userId;
	private String content;
	


}
