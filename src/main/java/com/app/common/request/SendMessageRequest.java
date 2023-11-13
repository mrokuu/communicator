package com.app.common.request;

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
